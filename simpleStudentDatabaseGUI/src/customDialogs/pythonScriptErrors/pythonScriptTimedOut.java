package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class pythonScriptTimedOut extends invalidEmailDialog {

    public pythonScriptTimedOut(JDialog parent){

        super(parent);
        setTitle("Something timed out within me.");
        messageField.setText("The Python script took too long to respond.");
    }
}
