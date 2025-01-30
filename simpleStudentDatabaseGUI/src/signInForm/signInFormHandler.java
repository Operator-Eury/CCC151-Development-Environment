package signInForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

public class signInFormHandler extends JDialog {
    private JPanel signInFormDashboard;
    private JTextArea emailForm;
    private JTextField emailInputArea;
    private JTextArea passwordForm;
    private JTextArea signUpPrompt;
    private JButton signUpAsStudentButton;
    private JButton signUpAsAdminButton;
    private JPasswordField passwordInputField;
    private JButton signInButton;

    public signInFormHandler(JFrame parent) {
        super(parent, "Welcome Back, FrostScribe!", true);
        setResizable(false);
        setMinimumSize(new Dimension(350, 225));
        setContentPane(signInFormDashboard);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //clear from memory since sign in is one-time use only
        pack();
        setLocationRelativeTo(parent);


        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailValue = emailInputArea.getText();
                String passwordValue = String.valueOf(passwordInputField.getPassword());
                Component frame = getOwner();

                if (!validEmail(emailValue)) {
                    showErrorDialog(frame, "Whoops~! Something's seems wrong with your Email.", "Kindly check your Email");
                    return;
                }

                if (passwordValue.isEmpty()) {
                    showErrorDialog(frame, "Whoops~! Check your password. Such empty~", "An empty void tries to be within me");
                    return;
                }

                // Get the current working directory where the executable or JAR is running
                String projectDir = System.getProperty("user.dir");

                // Build the absolute path to the Python script, assuming it's in a "resources" folder
                String pythonScriptPath = projectDir + File.separator + "pythonScripts" + File.separator + "verifyCredentials" + File.separator + "verify_credentials.py";

                // Verify the file exists at the calculated path
                File pythonScriptFile = new File(pythonScriptPath);
                if (!pythonScriptFile.exists()) {
                    showErrorDialog(frame, "Python script not found!", "Error");
                    return;
                }

                ProcessBuilder pythonScript = new ProcessBuilder("python", pythonScriptPath, emailValue, passwordValue);
                try {
                    Process process = pythonScript.start();
                    if (!process.waitFor(30, TimeUnit.SECONDS)) {
                        showErrorDialog(frame, "The Python script took too long to respond.", "Timeout");
                        process.destroy();
                        return;
                    }

                    handlePythonScriptOutput(process, frame);
                } catch (IOException | InterruptedException ex) {
                    showErrorDialog(frame, "An error occurred while starting the verification process.", "Verification Error");
                    ex.printStackTrace();
                }
            }

            private void showErrorDialog(Component frame, String message, String title) {
                JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
            }

            private void handlePythonScriptOutput(Process process, Component frame) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                     BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }

                    StringBuilder errorOutput = new StringBuilder();
                    while ((line = errorReader.readLine()) != null) {
                        errorOutput.append(line).append("\n");
                    }

                    if (errorOutput.length() > 0) {
                        JOptionPane.showMessageDialog(frame, errorOutput.toString(), "Python Script Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, output.toString(), "Verification Result", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (IOException error) {
                    showErrorDialog(frame, "An error occurred while reading the Python script output.", "Reading Error");
                    error.printStackTrace();
                }
            }

            private boolean validEmail(String emailValue) {
                if (emailValue == null || emailValue.isEmpty()) {
                    return false;
                }
                String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(emailValue);
                return matcher.matches();
            }
        });
    }
}