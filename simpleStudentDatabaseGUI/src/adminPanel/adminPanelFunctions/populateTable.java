package adminPanel.adminPanelFunctions;
import customDialogs.pythonScriptErrors.pythonScriptMissing;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class populateTable {

    public DefaultTableModel populateTable() {
        DefaultTableModel model = new DefaultTableModel();

        try {
            String projectDir = System.getProperty("user.dir");
            String csvPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "students" + File.separator + "students.csv";
            File csvPathFile = new File(csvPath);

            if (!csvPathFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return model;
            }

            // Read the generated CSV file
            BufferedReader reader = new BufferedReader(new FileReader(csvPath));
            List<String[]> csvData = new ArrayList<>();
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Remove BOM if it exists (specifically for UTF-8 files with BOM)
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                String[] rowData = line.split(",");

                if (isFirstLine) {
                    model.setColumnIdentifiers(rowData);
                    isFirstLine = false;
                } else {
                    csvData.add(rowData);
                }
            }
            reader.close();

            // Populate the table model with CSV data
            for (String[] row : csvData) {
                model.addRow(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getCollgeTable() {
        DefaultTableModel model = new DefaultTableModel();

        try {
            String projectDir = System.getProperty("user.dir");
            String csvPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "colleges" + File.separator + "colleges.csv";
            File csvPathFile = new File(csvPath);

            if (!csvPathFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return model;
            }

            BufferedReader reader = new BufferedReader(new FileReader(csvPath));
            List<String[]> csvData = new ArrayList<>();
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Remove BOM if it exists (specifically for UTF-8 files with BOM)
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                String[] rowData = line.split(",");

                if (isFirstLine) {
                    model.setColumnIdentifiers(rowData);
                    isFirstLine = false;
                } else {
                    csvData.add(rowData);
                }
            }

            reader.close();

            for (String[] row : csvData) {
                model.addRow(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getProgramsTable() {
        DefaultTableModel model = new DefaultTableModel();

        try {
            String projectDir = System.getProperty("user.dir");
            String csvPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
            File csvPathFile = new File(csvPath);

            if (!csvPathFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return model;
            }

            BufferedReader reader = new BufferedReader(new FileReader(csvPath));
            List<String[]> csvData = new ArrayList<>();
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Remove BOM if it exists (specifically for UTF-8 files with BOM)
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                String[] rowData = line.split(",");

                if (isFirstLine) {
                    model.setColumnIdentifiers(rowData);
                    isFirstLine = false;
                } else {
                    csvData.add(rowData);
                }
            }

            reader.close();

            for (String[] row : csvData) {
                model.addRow(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static HashMap<String, String[]> programCodeMap = new HashMap<>(); // Map for program details
    public static HashMap<String, String[]> collegeCodeMap = new HashMap<>();

    public String[] getProgramCodes() {
        programCodeMap.clear(); // Clear previous data
        List<String> programCodes = new ArrayList<>();

        try {
            String projectDir = System.getProperty("user.dir");
            String csvPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
            File csvPathFile = new File(csvPath);

            if (!csvPathFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return new String[]{"No Programs Found"};
            }

            BufferedReader reader = new BufferedReader(new FileReader(csvPath));
            reader.readLine(); // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3); // Split into three parts
                if (parts.length == 3) { // Ensure all three columns exist
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    String collegeCode = parts[2].trim();

                    programCodes.add(code);
                    programCodeMap.put(code, new String[]{name, collegeCode}); // Store code -> {name, collegeCode}
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return programCodes.isEmpty() ? new String[]{"No Programs Found"} : programCodes.toArray(new String[0]);
    }

    public String[] getCollegeNames() {
        List<String> collegeCodes = new ArrayList<>();
        try {
            String projectDir = System.getProperty("user.dir");
            String csvPathForCollege = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "colleges" + File.separator + "colleges.csv";
            File csvPathFileForCollege = new File(csvPathForCollege);

            if (!csvPathFileForCollege.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return new String[]{"No Colleges Found"};
            }

            BufferedReader reader = new BufferedReader(new FileReader(csvPathForCollege));
            reader.readLine(); // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2); // Split into three parts
                if (parts.length == 2) { // Ensure all number of columns exist
                    String code = parts[0].trim();
                    String name = parts[1].trim();

                    collegeCodes.add(code);
                    collegeCodeMap.put(code, new String[]{name}); // Store code -> {name}
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return collegeCodes.isEmpty() ? new String[]{"No Colleges Found"} : collegeCodes.toArray(new String[0]);
    }
}
