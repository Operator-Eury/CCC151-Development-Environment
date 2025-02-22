package customDialogs.confirmEnrollment;
import customDialogs.invalidEmailDialog.invalidEmailDialog;
import customDialogs.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class confirmEnrollment extends  invalidEmailDialog {
    public confirmEnrollment(JDialog parent, String output) {
        super(parent);
        setMinimumSize(new Dimension(300, 225));
        setTitle("Confirm Enrollment");

        messageField.setText("Please ensure that every details is inputted correctly." + "\n"
                + "Are you sure to confirm enrollment?");

        confirmButton.setText("Confirm");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkEnrollmentForm checkEnrollmentForm;
                checkEnrollmentForm = new checkEnrollmentForm(confirmEnrollment.this, output);
            }
        });
    }
}
