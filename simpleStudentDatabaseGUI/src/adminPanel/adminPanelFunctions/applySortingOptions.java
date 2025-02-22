package adminPanel.adminPanelFunctions;

import adminPanel.adminPanelDashboard;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JComboBox;
import javax.swing.table.TableRowSorter;

public class applySortingOptions {
    private TableRowSorter sorter;
    private int selectedColumnIndex = -1;
    private adminPanelDashboard dashboard;  // Add this reference

    // Modify the constructor to accept the dashboard reference
    public applySortingOptions(adminPanelDashboard dashboard) {
        this.dashboard = dashboard;  // Store the reference
    }

    public void SortingOptionsHandler(TableRowSorter sorter) {
        this.sorter = sorter;
    }

    // Method to apply sorting options based on the selection
    public void applySortingOptions(JComboBox sortOptions) {
        int selectedIndex = sortOptions.getSelectedIndex();

        int columnIndex = -1;
        boolean ascending = true;

        if (selectedIndex == 0) {
            sorter.setSortKeys(null);
            return;
        }

        switch (selectedIndex) {
            case 1:
                columnIndex = 0; // ID Number column
                ascending = true;
                break;
            case 2:
                columnIndex = 1; // Surname column
                ascending = true;
                break;
            case 3:
                columnIndex = 4; // Year column
                ascending = true;
                break;
            case 4:
                columnIndex = 6; // College Code column
                ascending = true;
                break;
            case 5:
                columnIndex = 5; // Gender column
                ascending = true;
                break;
            default:
                sorter.setSortKeys(null);
                return;
        }

        this.selectedColumnIndex = columnIndex;
        setSortOptions(ascending);

        // Call updateSorting after applying sorting logic
        dashboard.updateSorting();
    }

    // Method to set sorting order (ascending or descending)
    public void setSortOptions(boolean ascending) {
        if (selectedColumnIndex == -1) {
            sorter.setSortKeys(null);
            return;
        }

        sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(selectedColumnIndex, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING)));
    }
}
