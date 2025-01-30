package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class pythonVerificationResult extends invalidEmailDialog{
    public pythonVerificationResult(JDialog parent, String message) {
        super(parent);
        setTitle("Verification Result");
        messageField.setText(message);
    }
}
