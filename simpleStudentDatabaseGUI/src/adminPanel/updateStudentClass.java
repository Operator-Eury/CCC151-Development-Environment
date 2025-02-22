package adminPanel;
import adminPanel.adminPanelFunctions.studentValidator;
import customDialogs.checkEnrollmentForm;
import adminPanel.adminPanelFunctions.enrollActionListenerClass;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class updateStudentClass {
    private JTable studentDataTable;
    private enrollActionListenerClass enrollActionListener;
    private JTextField updateIDNumberTextField;
    private JTextField updateFirstNameField;
    private JTextField updateMiddleNameTextField;
    private JTextField updateLastNameTextField;
    private JComboBox updateGenderComboBox;
    private JComboBox updateYearLevelCombo;
    private JComboBox updateProgramCodeCombo;
    private JComboBox updateCollegeCodeComboBox;

    public updateStudentClass(JTable studentDataTable, enrollActionListenerClass enrollActionListener,
                              JTextField updateIDNumberTextField, JTextField updateFirstNameField,
                              JTextField updateMiddleNameTextField, JTextField updateLastNameTextField,
                              JComboBox updateGenderComboBox, JComboBox updateYearLevelCombo, JComboBox updateProgramCodeCombo, JComboBox updateCollegeCodeComboBox) {
        this.studentDataTable = studentDataTable;
        this.enrollActionListener = enrollActionListener;
        this.updateIDNumberTextField = updateIDNumberTextField;
        this.updateFirstNameField = updateFirstNameField;
        this.updateMiddleNameTextField = updateMiddleNameTextField;
        this.updateLastNameTextField = updateLastNameTextField;
        this.updateGenderComboBox = updateGenderComboBox;
        this.updateYearLevelCombo = updateYearLevelCombo;
        this.updateProgramCodeCombo = updateProgramCodeCombo;
        this.updateCollegeCodeComboBox = updateCollegeCodeComboBox;
    }

    public void updateStudent(String idNumber, String firstName, String middleName, String lastName,
                              String gender, String yearLevel, String programCode)
    {

        try {
            studentValidator.validateStudentData(
                    idNumber, firstName, middleName, lastName, gender, yearLevel, programCode,
                    studentDataTable,
                    true // true = Update (skip duplicate ID check)
            );

            updateIDNumberTextField.setText("");
            updateFirstNameField.setText("");
            updateMiddleNameTextField.setText("");
            updateLastNameTextField.setText("");
            updateGenderComboBox.setSelectedIndex(0);
            updateYearLevelCombo.setSelectedIndex(0);
            updateProgramCodeCombo.setSelectedIndex(0);
            updateCollegeCodeComboBox.setSelectedIndex(0);

        } catch (studentValidator.ValidationException error) {
            checkEnrollmentForm checkEnrollmentForm = new checkEnrollmentForm(null, error.getMessage());
            checkEnrollmentForm.setVisible(true);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) studentDataTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String currentID = model.getValueAt(i, 0).toString();
            if (currentID.equals(idNumber)) {
                // Update the student data
                model.setValueAt(lastName, i, 1);
                model.setValueAt(firstName, i, 2);
                model.setValueAt(middleName, i, 3);
                model.setValueAt(yearLevel, i, 4);
                model.setValueAt(gender, i, 5);
                model.setValueAt(programCode, i, 6);

                studentDataTable.clearSelection();
                checkEnrollmentForm checkEnrollmentForm = new checkEnrollmentForm(null, "update");
                checkEnrollmentForm.setVisible(true);
                enrollActionListener.proceedToSave();
                return;
            }
        }
    }

    public void deleteStudent(String idNumber) {
        DefaultTableModel model = (DefaultTableModel) studentDataTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String currentID = model.getValueAt(i, 0).toString();
            if (currentID.equals(idNumber)) {
                model.removeRow(i); // Remove the row

                // Clear form fields after deletion
                clearFormFields();

                checkEnrollmentForm confirmation = new checkEnrollmentForm(null, "deleted");
                confirmation.setVisible(true);
                enrollActionListener.proceedToSave();
                return;
            }
        }

        // Show an error message if the student ID is not found
        checkEnrollmentForm errorDialog = new checkEnrollmentForm(null, "Error: Student not found.");
        errorDialog.setVisible(true);
    }

    private void clearFormFields() {
        updateIDNumberTextField.setText("");
        updateFirstNameField.setText("");
        updateMiddleNameTextField.setText("");
        updateCollegeCodeComboBox.setSelectedIndex(0);
        updateLastNameTextField.setText("");
        updateGenderComboBox.setSelectedIndex(0);
        updateYearLevelCombo.setSelectedIndex(0);
        updateProgramCodeCombo.setSelectedIndex(0);
    }

}
