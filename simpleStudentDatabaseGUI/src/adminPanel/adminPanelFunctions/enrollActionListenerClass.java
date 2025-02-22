package adminPanel.adminPanelFunctions;
import adminPanel.adminPanelDashboard;
import customDialogs.checkEnrollmentForm;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class enrollActionListenerClass implements ActionListener {
    private  adminPanelDashboard dashboard;
    private JTextField firstNameTextField;
    private JTextField middleNameTextField;
    private JTextField lastNameTextField;
    private JComboBox genderComboBox;
    private JComboBox yearLevelComboBox;
    private JComboBox programCodeComboBox;
    private JTextField idNumberValue;
    private JTable studentDataTable;
    private JComboBox collegeCodeComboBox;

    //Instance creator
    public enrollActionListenerClass(adminPanelDashboard dashboard, JTable studentDataTable,JTextField idNumberValue,JTextField firstNameTextField, JTextField middleNameTextField, JTextField lastNameTextField, JComboBox genderComboBox, JComboBox yearLevelComboBox, JComboBox programCodeComboBox, JComboBox collegeCodeComboBox) {
        this.dashboard = dashboard;
        this.studentDataTable = studentDataTable;
        this.idNumberValue = idNumberValue;
        this.firstNameTextField = firstNameTextField;
        this.middleNameTextField = middleNameTextField;
        this.lastNameTextField = lastNameTextField;
        this.genderComboBox = genderComboBox;
        this.yearLevelComboBox = yearLevelComboBox;
        this.programCodeComboBox = programCodeComboBox;
        this.collegeCodeComboBox = collegeCodeComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            studentValidator.validateStudentData(
                    idNumberValue.getText().trim(),
                    firstNameTextField.getText().trim(),
                    middleNameTextField.getText().trim(),
                    lastNameTextField.getText().trim(),
                    genderComboBox.getSelectedItem().toString().trim(),
                    yearLevelComboBox.getSelectedItem().toString().trim(),
                    programCodeComboBox.getSelectedItem().toString().trim(),
                    studentDataTable,
                    false // false = Enrollment (checks for duplicate ID)
            );
        } catch (studentValidator.ValidationException error) {
            checkEnrollmentForm checkEnrollmentForm = new checkEnrollmentForm(null, error.getMessage());
            checkEnrollmentForm.setVisible(true);
            return; // Stop execution if validation fails
        }

        checkEnrollmentForm checkEnrollmentForm = new checkEnrollmentForm(null, "");
        checkEnrollmentForm.setVisible(true);


        String idNumber = idNumberValue.getText().trim();
        String firstName = firstNameTextField.getText().trim();
        String middleName = middleNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String gender = genderComboBox.getSelectedItem().toString().trim();
        String yearLevel = yearLevelComboBox.getSelectedItem().toString().trim();
        String programCode = programCodeComboBox.getSelectedItem().toString().trim();

        DefaultTableModel tableModel = (DefaultTableModel) studentDataTable.getModel();
        tableModel.addRow(new Object[]{idNumber, lastName, firstName, middleName, yearLevel, gender, programCode});

        // Optional: Clear fields after adding
        idNumberValue.setText("");
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        lastNameTextField.setText("");
        genderComboBox.setSelectedIndex(0);
        yearLevelComboBox.setSelectedIndex(0);
        programCodeComboBox.setSelectedIndex(0);
        collegeCodeComboBox.setSelectedIndex(0);

        proceedToSave();
    }

    public  void proceedToSave(){
        saveTableToCSV();
    }

    public void saveTableToCSV() {
        String middleName = middleNameTextField.getText().trim();
        middleName = middleName.isEmpty() ? "" : middleName.substring(0, 1);
        String completeMiddleName = middleNameTextField.getText().trim();
        try {
            String projectDir = System.getProperty("user.dir");
            String csvPath = projectDir + File.separator + "pythonScripts" + File.separator + "studentInformationTable" + File.separator + "students" + File.separator + "students.csv";
            File csvFile = new File(csvPath);

            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));

            DefaultTableModel tableModel = (DefaultTableModel) studentDataTable.getModel();
            int columnCount = tableModel.getColumnCount();
            int rowCount = tableModel.getRowCount();

            // Write column headers
            for (int i = 0; i < columnCount; i++) {
                writer.write(tableModel.getColumnName(i));
                if (i < columnCount - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            // Write row data

            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                        writer.write(tableModel.getValueAt(row, col).toString());
                    if (col < columnCount - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            writer.close();
            System.out.println("CSV file updated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        dashboard.updateStudentCount();
    }

}
