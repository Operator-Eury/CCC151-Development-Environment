import landingPageDashboard.*;
import javax.swing.*;
import adminPanel.*;

public class main {
    //this is the main program where it would call classes grouped by folders
    //should primarily call classes and nothing more
    public static void main(String[] args) {
        SwingUtilities.invokeLater(adminPanelDashboard::new);
    }
}