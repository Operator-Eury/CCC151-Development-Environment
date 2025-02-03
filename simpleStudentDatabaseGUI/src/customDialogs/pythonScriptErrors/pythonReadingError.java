package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class pythonReadingError extends invalidEmailDialog{
    public pythonReadingError(JDialog parent){
        super(parent);
        setTitle("Reading Error");
        messageField.setText("An error occurred while reading the Python script output.");
    }

}
