package signInForm;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;
import customDialogs.invalidEmailDialog.*;
import customDialogs.emptyEmailDialog.*;
import customDialogs.emptyPasswordDialog.*;
import customDialogs.pythonScriptErrors.*;

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

                if (emailValue.isBlank()) {
                    emptyEmailDialog emptyEmailDialog = new emptyEmailDialog(signInFormHandler.this);
                    emptyEmailDialog.setVisible(true);
                    return;
                }

                if (!validEmail(emailValue)) {
                    invalidEmailDialog invalidEmailDialog = new invalidEmailDialog(signInFormHandler.this); //creating a JDialog object
                    invalidEmailDialog.setVisible(true);
                    return;
                }

                if (passwordValue.isBlank()) {
                    emptyPasswordDialog emptyPasswordDialog = new emptyPasswordDialog(signInFormHandler.this);
                    emptyPasswordDialog.setVisible(true);
                    return;
                }

                // Get the current working directory where the executable or JAR is running
                String projectDir = System.getProperty("user.dir");

                // Build the absolute path to the Python script, assuming it's in a "resources" folder
                String pythonScriptPath = projectDir + File.separator + "pythonScripts" + File.separator + "verifyCredentials" + File.separator + "verify_credentials.py";

                // Verify the file exists at the calculated path
                File pythonScriptFile = new File(pythonScriptPath);
                if (!pythonScriptFile.exists()) {
                    pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(signInFormHandler.this);
                    pythonScriptMissing.setVisible(true);
                    return;
                }

                ProcessBuilder pythonScript = new ProcessBuilder("python", pythonScriptPath, emailValue, passwordValue);

                try {
                    Process process = pythonScript.start();
                    if (!process.waitFor(30, TimeUnit.SECONDS)) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                pythonScriptTimedOut pythonScriptTimedOut = new pythonScriptTimedOut(signInFormHandler.this);
                                pythonScriptTimedOut.setVisible(true);
                            }
                        });
                        process.destroy();
                    } else {
                        handlePythonScriptOutputs(process, frame);
                    }
                } catch (IOException | InterruptedException ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            handlePythonScriptOutput handlePythonScriptOutput = new handlePythonScriptOutput(signInFormHandler.this);
                            handlePythonScriptOutput.setVisible(true);
                        }
                    });
                    ex.printStackTrace();
                }
            }

            private void showErrorDialog(Component frame, String message, String title) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        pythonErrorDialog pythonErrorDialog = new pythonErrorDialog(signInFormHandler.this, "An error occured while running the script.", "Script Error");
                        pythonErrorDialog.setVisible(true);
                    }
                });
            }

            private void handlePythonScriptOutputs(Process process, Component frame) {
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
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                pythonErrorDialog pythonErrorDialog = new pythonErrorDialog(signInFormHandler.this, "An error occured while running the script.\n\n" + errorOutput.toString(), "Script Error");
                                pythonErrorDialog.setVisible(true);
                            }
                        });

                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                pythonVerificationResult pythonVerificationResult = new pythonVerificationResult(signInFormHandler.this, " " + output.toString());
                                pythonVerificationResult.setVisible(true);
                            }
                        });

                        if (output.toString().contains("Login successful! Account Type:")) {
                                    signInFormHandler.this.dispose();

                        }
                    }

                } catch (IOException error) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            showErrorDialog(frame, "An error occurred while reading the Python script output.", "Reading Error");
                        }
                    });
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        signInFormDashboard = new JPanel();
        signInFormDashboard.setLayout(new GridBagLayout());
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        signInFormDashboard.add(panel1, gbc);
        emailForm = new JTextArea();
        emailForm.setEditable(false);
        emailForm.setFocusable(false);
        Font emailFormFont = this.$$$getFont$$$(null, -1, 17, emailForm.getFont());
        if (emailFormFont != null) emailForm.setFont(emailFormFont);
        emailForm.setOpaque(false);
        emailForm.setRequestFocusEnabled(false);
        emailForm.setText("Email:");
        emailForm.setVerifyInputWhenFocusTarget(false);
        emailForm.setWrapStyleWord(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel1.add(emailForm, gbc);
        emailInputArea = new JTextField();
        emailInputArea.setAlignmentX(0.5f);
        emailInputArea.setAutoscrolls(false);
        emailInputArea.setDragEnabled(false);
        emailInputArea.setHorizontalAlignment(10);
        emailInputArea.setInheritsPopupMenu(false);
        emailInputArea.setText("");
        emailInputArea.setToolTipText("Enter your email here");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 2, 0, 2);
        panel1.add(emailInputArea, gbc);
        passwordForm = new JTextArea();
        passwordForm.setAutoscrolls(false);
        passwordForm.setEditable(false);
        passwordForm.setFocusable(false);
        Font passwordFormFont = this.$$$getFont$$$(null, -1, 17, passwordForm.getFont());
        if (passwordFormFont != null) passwordForm.setFont(passwordFormFont);
        passwordForm.setOpaque(false);
        passwordForm.setRequestFocusEnabled(false);
        passwordForm.setText("Password:");
        passwordForm.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel1.add(passwordForm, gbc);
        passwordInputField = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 2, 0, 2);
        panel1.add(passwordInputField, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        signInFormDashboard.add(panel2, gbc);
        signUpPrompt = new JTextArea();
        signUpPrompt.setAutoscrolls(false);
        signUpPrompt.setEditable(false);
        signUpPrompt.setFocusable(false);
        Font signUpPromptFont = this.$$$getFont$$$(null, -1, 17, signUpPrompt.getFont());
        if (signUpPromptFont != null) signUpPrompt.setFont(signUpPromptFont);
        signUpPrompt.setOpaque(false);
        signUpPrompt.setRequestFocusEnabled(false);
        signUpPrompt.setText("Don't have a Frostscribe account yet?");
        signUpPrompt.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 0, 0);
        panel2.add(signUpPrompt, gbc);
        signUpAsStudentButton = new JButton();
        signUpAsStudentButton.setFocusPainted(false);
        signUpAsStudentButton.setText("Sign Up as Student");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel2.add(signUpAsStudentButton, gbc);
        signUpAsAdminButton = new JButton();
        signUpAsAdminButton.setFocusPainted(false);
        signUpAsAdminButton.setText("Sign Up as Admin");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel2.add(signUpAsAdminButton, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        signInFormDashboard.add(panel3, gbc);
        signInButton = new JButton();
        signInButton.setAlignmentX(0.5f);
        signInButton.setFocusPainted(false);
        signInButton.setFocusable(true);
        signInButton.setLabel("Sign In");
        signInButton.setMargin(new Insets(0, 0, 0, 0));
        signInButton.setMaximumSize(new Dimension(-1, 34));
        signInButton.setMinimumSize(new Dimension(-1, -1));
        signInButton.setPreferredSize(new Dimension(55, 24));
        signInButton.setText("Sign In");
        signInButton.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(5, 0, 0, 15);
        panel3.add(signInButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return signInFormDashboard;
    }

}