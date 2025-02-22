package customDialogs;
import javax.swing.*;
import customDialogs.invalidEmailDialog.*;

public class checkEnrollmentForm extends invalidEmailDialog {
    public checkEnrollmentForm(JDialog parent, String caseTest) {
        super(parent);
        String errors = caseTest;

        if (errors.equals("")) {
            acceptedEnrollment();
        } else if (errors.equals("update")) {
            updateAccepted();
        } else if (errors.equals("deleted")) {
            deletionCompleted();
        } else {
            switchCases(errors);
        }

    }

    public void switchCases (String errors) {

        switch (errors.toLowerCase()) {

            case "format":
                setTitle("ID Number Format Error");
                messageField.setText("Enter a valid ID Number format");
                break;

            case "duplicate":
                setTitle("Duplicate ID Number");
                messageField.setText("Another student with this ID number already exists");
                break;

            case "id number":
                setTitle("Enter ID Number");
                messageField.setText("Supply the student's ID Number");
                break;

            case "first name":
                setTitle("Enter First Name");
                messageField.setText("Supply the student's first name");
                break;

            case "last name":
                setTitle("Enter Last Name");
                messageField.setText("Supply the student's last name");
                break;

            case "gender":
                setTitle("Gender Missing");
                messageField.setText("Supply the student's gender");
                break;

            case "year level":
                setTitle("Enter Year Level");
                messageField.setText("Supply the student's year level");
                break;

            case "program code":
                setTitle("Enter Program Code");
                messageField.setText("Supply the student's program code");
                break;

            case "a student already exists":
                setTitle("Student already exists");
                messageField.setText("Student already exists");
                break;

            case "student not found":
                setTitle("Student Not Found");
                messageField.setText("Student Not Found. Please check ID Number");
                break;

            case "select id":
                setTitle("Select Student ID");
                messageField.setText("Select Student ID in the Student Data Information Table");
                break;

            case "select delete":
                setTitle("Select Student to Delete");
                messageField.setText("Select Student to Delete in the Student Data Information Table");
                break;

            default:
                JOptionPane.showMessageDialog(null, "Unknown error. Please report this error.");
                break;
        }
    }

    public void acceptedEnrollment(){
        setTitle("Enrollment Successful");
        messageField.setText("Student is enrolled successfully");
    }

    public void updateAccepted(){
        setTitle("Update Successful");
        messageField.setText("Student information successfully updated");
    }

    public void deletionCompleted(){
        setTitle("Deletion Successful");
        messageField.setText("Student information successfully deleted");
    }
}