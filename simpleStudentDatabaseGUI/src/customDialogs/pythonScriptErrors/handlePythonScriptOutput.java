package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class handlePythonScriptOutput extends invalidEmailDialog {

    public handlePythonScriptOutput(JDialog parent) {

        super(parent);
        setTitle("Verification Error");
        messageField.setText("An error occurred while starting the verification process.");
    }
}
