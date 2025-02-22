package adminPanel.adminPanelFunctions;

import customDialogs.pythonScriptErrors.pythonScriptMissing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class runPythonScript {
    public void runPythonScript() {
        try {
            String projectDir = System.getProperty("user.dir");
            String pythonScriptPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "studentInformationTable.py";
            File pythonScriptFile = new File(pythonScriptPath);

            if (!pythonScriptFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return;
            }

            // Specify the Python script path and the command to execute
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Optional: Print script output
            }

            // Wait for the Python script to finish
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runPythonScriptForProgramCode() {
        try {
            String projectDir = System.getProperty("user.dir");
            String pythonScriptPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "addProgramCode.py";
            File pythonScriptFile = new File(pythonScriptPath);

            if (!pythonScriptFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return;
            }

            // Specify the Python script path and the command to execute
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Optional: Print script output
            }

            // Wait for the Python script to finish
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}