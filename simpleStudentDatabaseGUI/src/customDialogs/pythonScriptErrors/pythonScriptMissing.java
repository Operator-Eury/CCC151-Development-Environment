package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class pythonScriptMissing extends invalidEmailDialog {

    public pythonScriptMissing(JDialog parent) {

        super(parent);
        setTitle("An empty void tries to be within me");
        messageField.setText("Whoa~ there! Check your password. Such a barren place.");

    }
}
