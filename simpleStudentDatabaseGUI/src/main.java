import landingPageDashboard.*;
import adminPanel.adminPanelDashboard;
import javax.swing.*;
import java.awt.*;

public class main {
    //this is the main program where it would call classes grouped by folders
    //should primarily call classes and nothing more
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        Font currentFont = UIManager.getFont("Button.font");

        // Set the new font size (e.g., 16px)
        Font newFontSize = currentFont.deriveFont(16f); // Adjust the font size here

        // Apply the new font to a wide variety of components
        UIManager.put("Button.font", newFontSize);
        UIManager.put("Label.font", newFontSize);
        UIManager.put("TextField.font", newFontSize);
        UIManager.put("TextArea.font", newFontSize);
        UIManager.put("ComboBox.font", newFontSize);
        UIManager.put("Table.font", newFontSize);
        UIManager.put("TableHeader.font", newFontSize);
        UIManager.put("TextPane.font", newFontSize);
        UIManager.put("EditorPane.font", newFontSize);
        UIManager.put("PasswordField.font", newFontSize);
        UIManager.put("RadioButton.font", newFontSize);
        UIManager.put("CheckBox.font", newFontSize);
        UIManager.put("List.font", newFontSize);
        UIManager.put("Menu.font", newFontSize);
        UIManager.put("MenuItem.font", newFontSize);
        UIManager.put("ToolTip.font", newFontSize);
        UIManager.put("Tree.font", newFontSize);
        UIManager.put("TabbedPane.font", newFontSize);
        UIManager.put("ScrollPane.font", newFontSize);

        SwingUtilities.invokeLater(adminPanelDashboard::new);
    }
}