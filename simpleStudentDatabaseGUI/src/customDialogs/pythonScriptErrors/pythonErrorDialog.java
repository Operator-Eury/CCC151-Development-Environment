package customDialogs.pythonScriptErrors;
import customDialogs.invalidEmailDialog.*;
import javax.swing.*;

public class pythonErrorDialog extends invalidEmailDialog {

    public pythonErrorDialog(JDialog parent, String message, String title) {

        super(parent);
        setTitle(title);
        messageArea.setText(message);
        messageArea.setVisible(true);
        messageFieldPanel.setVisible(false);
        messageFieldPanel.revalidate();
        messageFieldPanel.repaint();

    }
}
