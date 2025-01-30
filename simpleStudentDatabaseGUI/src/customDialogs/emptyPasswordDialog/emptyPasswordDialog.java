package customDialogs.emptyPasswordDialog;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class emptyPasswordDialog extends invalidEmailDialog {

    public emptyPasswordDialog(JDialog parent) {

        super(parent);
        setTitle("An empty void tries to be within me");
        messageField.setText("Whoa~ there! Check your password. Such a barren place.");
    }
}
