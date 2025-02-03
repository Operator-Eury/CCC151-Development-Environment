package adminPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.*;
import customDialogs.pythonScriptErrors.pythonScriptMissing;

//downloaded libraries
//links: http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class adminPanelDashboard extends JFrame {
    protected JPanel adminPanelDashboard;
    protected JLabel greetingLabel;
    protected JPanel greetingMessagePanel;
    protected JTabbedPane tabbedPane1;
    protected JPanel tabbedDashboard;
    protected JPanel operationsPanel;
    protected JComboBox sortOptions;
    protected JTextField searchField;
    protected JTable studentDataTable;
    protected JButton button1;
    protected JButton button2;
    protected JTextPane sortByText;
    protected JTextPane search;
    protected JPanel tablePanel;
    protected JPanel buttonsPanel;

    public adminPanelDashboard() {
        setTitle("FrostyBytes Database");
        setMinimumSize(new Dimension(425, 350));
        createTable();
        setContentPane(adminPanelDashboard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    //creates and displays the table
    public void createTable() {
        DefaultTableModel model = new DefaultTableModel();
        studentDataTable.setModel(model);

        runPythonScript();
        populateTable();

        //fixing columns on their place
        studentDataTable.getTableHeader().setReorderingAllowed(false);
        studentDataTable.setColumnSelectionAllowed(false);
        studentDataTable.getTableHeader().setResizingAllowed(true);

        //adjust a specific column width
        TableColumnModel columns = studentDataTable.getColumnModel();
        columns.getColumn(0).setMinWidth(55);

        //centering columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < studentDataTable.getColumnCount(); i++) {
            columns.getColumn(i).setCellRenderer(centerRenderer);
        }


    }

    public void populateTable() {
        try {
            String projectDir = System.getProperty("user.dir");
            String jsonPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "students" + File.separator + "students.json";
            File jsonPathFile = new File(jsonPath);

            if (!jsonPathFile.exists()) {
                pythonScriptMissing pythonScriptMissing = new pythonScriptMissing(null);
                pythonScriptMissing.setVisible(true);
                return;
            }

            // Read the generated JSON file
            BufferedReader reader = new BufferedReader(new FileReader(jsonPath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Remove BOM if it exists (specifically for UTF-8 files with BOM)
            String jsonString = jsonContent.toString();
            if (jsonString.startsWith("\uFEFF")) {
                jsonString = jsonString.substring(1);
            }

            // Parse JSON content using JSONParser from JSON.simple
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

            // Create a DefaultTableModel
            DefaultTableModel model = (DefaultTableModel) studentDataTable.getModel();

            // Define the column headers order directly as an array
            String[] columnOrder = {"ID Number", "First Name", "Last Name", "Middle Initial", "Year Level", "Gender", "Program Code"};
            model.setColumnIdentifiers(columnOrder);

            // Add rows to the table based on JSON data
            for (Object obj : jsonArray) {
                JSONObject row = (JSONObject) obj;

                // Create an array to hold the row data in the correct order
                Object[] rowData = new Object[columnOrder.length];
                for (int i = 0; i < columnOrder.length; i++) {
                    rowData[i] = row.get(columnOrder[i]);
                }

                // Add the row to the table model
                model.addRow(rowData);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


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
        adminPanelDashboard = new JPanel();
        adminPanelDashboard.setLayout(new GridBagLayout());
        greetingMessagePanel = new JPanel();
        greetingMessagePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 0, 10);
        adminPanelDashboard.add(greetingMessagePanel, gbc);
        greetingLabel = new JLabel();
        greetingLabel.setText("INSERT TEXT GREETING HERE");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        greetingMessagePanel.add(greetingLabel, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 5, 10);
        adminPanelDashboard.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.9;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);
        adminPanelDashboard.add(panel2, gbc);
        tabbedPane1 = new JTabbedPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(tabbedPane1, gbc);
        tabbedDashboard = new JPanel();
        tabbedDashboard.setLayout(new GridBagLayout());
        tabbedPane1.addTab("Student Data Information", tabbedDashboard);
        operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        tabbedDashboard.add(operationsPanel, gbc);
        sortOptions = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        operationsPanel.add(sortOptions, gbc);
        searchField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        operationsPanel.add(searchField, gbc);
        sortByText = new JTextPane();
        sortByText.setEditable(false);
        sortByText.setFocusCycleRoot(false);
        sortByText.setFocusable(false);
        sortByText.setOpaque(false);
        sortByText.setRequestFocusEnabled(false);
        sortByText.setText("Sort by:");
        sortByText.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        operationsPanel.add(sortByText, gbc);
        search = new JTextPane();
        search.setEditable(false);
        search.setFocusCycleRoot(false);
        search.setOpaque(false);
        search.setRequestFocusEnabled(false);
        search.setText("Search");
        search.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        operationsPanel.add(search, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        operationsPanel.add(spacer1, gbc);
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        tabbedDashboard.add(tablePanel, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        tablePanel.add(scrollPane1, gbc);
        studentDataTable = new JTable();
        studentDataTable.setAutoCreateColumnsFromModel(true);
        studentDataTable.setAutoCreateRowSorter(false);
        studentDataTable.setAutoResizeMode(2);
        studentDataTable.setAutoscrolls(false);
        studentDataTable.setCellSelectionEnabled(false);
        studentDataTable.setColumnSelectionAllowed(false);
        studentDataTable.setDoubleBuffered(true);
        studentDataTable.setEnabled(true);
        studentDataTable.setFocusable(false);
        studentDataTable.setOpaque(true);
        studentDataTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
        studentDataTable.setRequestFocusEnabled(false);
        studentDataTable.setRowSelectionAllowed(false);
        studentDataTable.setUpdateSelectionOnSort(false);
        studentDataTable.setVerifyInputWhenFocusTarget(false);
        scrollPane1.setViewportView(studentDataTable);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        tabbedDashboard.add(buttonsPanel, gbc);
        button1 = new JButton();
        button1.setText("Button");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 5);
        buttonsPanel.add(button1, gbc);
        button2 = new JButton();
        button2.setText("Button");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add(button2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return adminPanelDashboard;
    }

}
