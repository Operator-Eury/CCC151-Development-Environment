package adminPanel.adminPanelFunctions;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class studentValidator {

    // Custom exception for validation errors
    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static void validateStudentData(String idNumber, String firstName, String middleName,
                                           String lastName, String gender, String yearLevel,
                                           String programCode, JTable studentDataTable, boolean isUpdate)
            throws ValidationException {
        // Validate ID Number (only for enrollment)
        if (!isUpdate) {
            if (idNumber.isBlank()) {
                throw new ValidationException("ID Number");
            }
            if (!idNumber.matches("\\d{4}-\\d{4}")) {
                throw new ValidationException("Format");
            }
            if (isIDNumberDuplicate(idNumber, studentDataTable)) {
                throw new ValidationException("Duplicate");
            }
        }

        // Validate First and Last Name
        if (firstName.isBlank()) {
            throw new ValidationException("First Name");
        }
        if (lastName.isBlank()) {
            throw new ValidationException("Last Name");
        }

        // Validate ComboBox Selections
        if (gender.equals("Select gender")) {
            throw new ValidationException("Gender");
        }
        if (yearLevel.equals("Select year level")) {
            throw new ValidationException("Year Level");
        }
        if (programCode.equals("Select program code")) {
            throw new ValidationException("Program Code");
        }

        // Check for duplicate Name only when adding a new student
        if (!isUpdate && isNameDuplicate(firstName, middleName, lastName, studentDataTable)) {
            throw new ValidationException("A student already exists");
        }


    }

    // Check for duplicate ID numbers (only for enrollment)
    private static boolean isIDNumberDuplicate(String newID, JTable studentDataTable) {
        DefaultTableModel tableModel = (DefaultTableModel) studentDataTable.getModel();
        int rowCount = tableModel.getRowCount();

        for (int row = 0; row < rowCount; row++) {
            String existingID = tableModel.getValueAt(row, 0).toString().trim(); // ID Number is in column 0
            if (existingID.equals(newID)) {
                return true; // Found duplicate
            }
        }
        return false; // No duplicate found
    }

    private static boolean isNameDuplicate(String firstName, String middleName, String lastName, JTable studentDataTable) {
        DefaultTableModel tableModel = (DefaultTableModel) studentDataTable.getModel();
        int rowCount = tableModel.getRowCount();

        for (int row = 0; row < rowCount; row++) {
            String existingLastName = tableModel.getValueAt(row, 1).toString().trim(); // Last Name is in column 1
            String existingFirstName = tableModel.getValueAt(row, 2).toString().trim(); // First Name is in column 2
            String existingMiddleInitial = tableModel.getValueAt(row, 3).toString().trim(); // Middle Initial is in column 3

            if (existingLastName.equalsIgnoreCase(lastName) &&
                    existingFirstName.equalsIgnoreCase(firstName) &&
                    existingMiddleInitial.equalsIgnoreCase(middleName)) {
                return true; // Duplicate Name found
            }
        }
        return false; // No duplicate Name
    }

}



