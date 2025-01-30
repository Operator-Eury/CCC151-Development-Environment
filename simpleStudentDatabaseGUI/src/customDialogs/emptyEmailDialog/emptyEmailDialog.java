package customDialogs.emptyEmailDialog;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class emptyEmailDialog extends invalidEmailDialog {

    public emptyEmailDialog(JDialog parent) {

        super(parent);
        setTitle("What's an empty Email doing here?");
        messageField.setText("That doesn't seem right. You might try checking your Email?");


    }

}
