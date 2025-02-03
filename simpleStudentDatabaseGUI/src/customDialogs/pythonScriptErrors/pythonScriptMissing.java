package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class pythonScriptMissing extends invalidEmailDialog {

    public pythonScriptMissing(JDialog parent) {

        super(parent);
        setTitle("Python Script Does not Exist");
        messageField.setText("There seems to be an error in locating the script.");

    }
}
