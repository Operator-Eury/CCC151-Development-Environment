package landingPageDashboard;
import signInForm.signInFormHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setTitle("FrostyBytes Database");
        setResizable(false);
        setVisible(true);
        setMinimumSize(new Dimension(425, 350));
        setContentPane(landingPagePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        studentAccessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInFormHandler signInFormHandler = new signInFormHandler(landingPage.this);
                signInFormHandler.setVisible(true);
            }
        });

        adminAccessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInFormHandler signInFormHandler = new signInFormHandler(landingPage.this);
                signInFormHandler.setVisible(true);
            }
        });
    }
}
