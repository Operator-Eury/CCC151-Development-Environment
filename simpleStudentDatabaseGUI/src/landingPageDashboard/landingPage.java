package landingPageDashboard;
import javax.swing.*;
import java.awt.*;

public class landingPage extends JFrame {
    private JPanel landingPagePanel;
    private JTextArea welcomeMessage;
    private JButton studentAccessorButton;
    private JButton adminAccessorButton;
    private JPanel accessorButtonsPanel;
    private JPanel divPanel;
    private JPanel welcomeMessagePanel;
    private JPanel videoPlaceHolderPanel;

    public landingPage() {
        setTitle("FrostyByte Database");
        setResizable(false);
        setVisible(true);
        setMinimumSize(new Dimension(400, 350));
        setContentPane(landingPagePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }
}
