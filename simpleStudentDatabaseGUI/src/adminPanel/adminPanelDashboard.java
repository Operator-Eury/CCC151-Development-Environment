package adminPanel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import adminPanel.adminPanelFunctions.*;
import customDialogs.checkEnrollmentForm;

public class adminPanelDashboard extends JFrame {
    protected JPanel adminPanelDashboard;
    protected JLabel greetingLabel;
    protected JPanel greetingMessagePanel;
    protected JTabbedPane dashboardTabs;
    protected JPanel tabbedDashboard;
    protected JPanel operationsPanel;
    protected JComboBox sortOptions;
    protected JTextField searchField;
    protected JTable studentDataTable;
    protected JButton updateInformation;
    protected JButton enrollStudent;
    protected JTextPane sortByText;
    protected JTextPane search;
    protected JPanel tablePanel;
    protected JPanel buttonsPanel;
    protected JTextPane studentCount;
    protected JTextPane ascendingOptions;
    protected JComboBox sortOptionsCombo;
    protected JPanel studentNumberPanel;
    protected JPanel updateHistory;
    private JPanel enrollmentForm;
    protected JPanel enrollmentFormHolder;
    protected JTextPane enrollmentFormHeader;
    protected JPanel headerPanel;
    protected JPanel accountManagement;
    protected JTextPane firstNameField;
    private JTextField firstNameTextField;
    protected JTextPane middleInitial;
    protected JTextField middleNameTextField;
    protected JTextPane lastNameField;
    protected JTextField lastNameTextField;
    protected JTextPane formTitle;
    protected JTextArea genderTextArea;
    protected JPanel informationFormPanel;
    protected JTextPane programCodeField;
    protected JTextPane yearLevelField;
    protected JComboBox genderComboBox;
    protected JComboBox programCodeComboBox;
    protected JComboBox yearLevelComboBox;
    protected JPanel programDetailsPanel;
    protected JPanel programNamePanel;
    protected JTextArea programName;
    protected JPanel collegeNamePanel;
    protected JTextArea collegeName;
    protected JPanel programDetailsHandle;
    protected JTextArea idNumberInfo;
    protected JPanel idNumberPanel;
    protected JTextField idNumberValue;
    protected JButton confirmEnrollmentButton;
    protected JPanel confirmButtonsPanel;
    protected JPanel upateFormHeader;
    protected JPanel updateFormDetails;
    protected JPanel updateFormPrograms;
    protected JPanel updateFormButtons;
    protected JButton updateFormButton;
    protected JPanel updateFormHolder1;
    protected JPanel updateFormHolder2;
    protected JPanel updateFormHolder3;
    protected JPanel updateFormHolder4;
    protected JTextArea updateIDNumberTextPane;
    protected JTextField updateIDNumberTextField;
    protected JTextArea insertUpdatedCollegeName;
    protected JTextArea insertUpdatedProgramName;
    protected JTextPane updateFirstName;
    protected JTextField updateFirstNameField;
    protected JTextPane updateMiddleName;
    protected JTextField updateMiddleNameTextField;
    protected JTextPane updateLastName;
    protected JTextField updateLastNameTextField;
    protected JTextArea updateGender;
    protected JComboBox updateGenderComboBox;
    protected JTextPane updateYearLevel;
    protected JTextPane updateProgramCode;
    protected JComboBox updateYearLevelCombo;
    protected JComboBox updateProgramCodeCombo;
    protected JTextPane updateFormHeader;
    protected JTextPane updateFormEnrollmentForm;
    protected JPanel updateFormHolder;
    protected JButton deleteStudentButton;
    protected JButton registerNewCollege;
    protected JButton proposeCourseButton;
    protected JPanel courseControlPanel;
    protected JPanel logoutPanel;
    protected JPanel invisiblePane;
    protected JTextPane handlerPane1;
    protected JPanel handlerPanel1;
    protected JTextField handlerField1;
    protected JTextPane handlerPane2;
    protected JTextField handlerField2;
    protected JComboBox collegeComboBox;
    protected JTextPane collegePane;
    protected JButton logOutButton;
    protected JPanel handlerPanel2;
    protected JButton registerHandlerButton;
    protected JButton updateHandlerButton;
    protected JPanel handlerPanel2Holder;
    protected JButton deleteHandlerButton;
    protected JButton closeFormHandler;
    protected JPanel handlerTablePanel;
    protected JTextPane collegeCode;
    protected JTextPane updateCollegeCode;
    protected JComboBox collegeCodeComboBox;
    protected JComboBox updateCollegeCodeComboBox;
    protected JTextPane selectedCollegeForCourse;
    protected JTextPane handlerFound;
    protected JTextField searchHandler;
    protected JTextPane searchHandlerPane;
    protected JTable tableHandlerJTable;
    protected JTextPane updateToCollege;
    protected JTextField updateField;
    protected JTextPane selectedCollegeFullName;
    protected JTextField selectedProgramField;
    public TableRowSorter<DefaultTableModel> sorter;
    private final applySortingOptions applySortingOptions;
    private updateStudentClass updater;


    public adminPanelDashboard() {
        setTitle("FrostyBytes Database");
        setMinimumSize(new Dimension(1000, 650));

        createTable(); // Populate the table
        createSortingCombo(); // Populate the combo
        createGenderCombo();
        createYearLevelCombo();
        createSortOptionsCombo();
        createProgramCodeCombo();
        setContentPane(adminPanelDashboard);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Initialize TableRowSorter here
        DefaultTableModel model = (DefaultTableModel) studentDataTable.getModel();
        sorter = new TableRowSorter<>(model);

        enrollActionListenerClass enrollActionListener =
                new enrollActionListenerClass(this, studentDataTable, idNumberValue, firstNameTextField,
                        middleNameTextField, lastNameTextField, genderComboBox,
                        yearLevelComboBox, programCodeComboBox, collegeCodeComboBox);

        for (int i = 0; i < studentDataTable.getColumnCount(); i++) {
            sorter.setSortable(i, false);
        }

        studentDataTable.setRowSorter(sorter);
        studentDataTable.setRowSelectionAllowed(true); // Allows row selection
        studentDataTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow multiple selection

        applyIDFormat(idNumberValue);


        // Initialize applySortingOptions and pass the sorter
        applySortingOptions = new applySortingOptions(this);
        applySortingOptions.SortingOptionsHandler(sorter);

        sortOptions.addActionListener(e -> applySortingOptions.applySortingOptions(sortOptions));
        sortOptionsCombo.addActionListener(e -> updateSorting());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applySearchFilter();
                studentDataTable.clearSelection();
                int rowCount = studentDataTable.getRowCount();
                studentCount.setText("Students Found: " + rowCount);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applySearchFilter();
                studentDataTable.clearSelection();
                int rowCount = studentDataTable.getRowCount();
                studentCount.setText("Students Found: " + rowCount);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applySearchFilter();
                studentDataTable.clearSelection();
                int rowCount = studentDataTable.getRowCount();
                studentCount.setText("Students Found: " + rowCount);
            }

        });

        enrollStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int enrollmentFormIndex = dashboardTabs.indexOfComponent(enrollmentForm);
                if (enrollmentFormIndex != -1) {
                    dashboardTabs.setSelectedIndex(enrollmentFormIndex);
                }
            }
        });

        confirmEnrollmentButton.addActionListener(new enrollActionListenerClass(this, studentDataTable, idNumberValue, firstNameTextField, middleNameTextField, lastNameTextField, genderComboBox, yearLevelComboBox, programCodeComboBox, collegeCodeComboBox));

        updateFormButton.addActionListener(e -> {
            String idNumber = updateIDNumberTextField.getText().trim();
            String firstName = updateFirstNameField.getText().trim();
            String middleName = updateMiddleNameTextField.getText().trim();
            String lastName = updateLastNameTextField.getText().trim();
            String gender = (String) updateGenderComboBox.getSelectedItem();
            String yearLevel = (String) updateYearLevelCombo.getSelectedItem();
            String programCode = (String) updateProgramCodeCombo.getSelectedItem();
            String collegeCode = (String) updateCollegeCodeComboBox.getSelectedItem();
            if (idNumber.isEmpty() || idNumber.isBlank()) {
                checkEnrollmentForm checkEnrollmentForm = new checkEnrollmentForm(null, "select id");
                checkEnrollmentForm.setVisible(true);
                return;
            }

            updater = new updateStudentClass(studentDataTable, enrollActionListener,
                    updateIDNumberTextField, updateFirstNameField, updateMiddleNameTextField,
                    updateLastNameTextField, updateGenderComboBox, updateYearLevelCombo, updateProgramCodeCombo, updateCollegeCodeComboBox);

            updater.updateStudent(idNumber, firstName, middleName, lastName, gender, yearLevel, programCode);


//            updateIDNumberTextField.setText("");
//            updateFirstNameField.setText("");
//            updateMiddleNameTextField.setText("");
//            updateLastNameTextField.setText("");
//            updateGenderComboBox.setSelectedIndex(0);
//            updateYearLevelCombo.setSelectedIndex(0);
//            updateProgramCodeCombo.setSelectedIndex(0);

        });

        updateInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentDataTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student to update.");
                    return;
                }

                // Convert to model index (in case of sorting)
                int modelRow = studentDataTable.convertRowIndexToModel(selectedRow);
                DefaultTableModel model = (DefaultTableModel) studentDataTable.getModel();

                // Extract existing data
                String idNumber = model.getValueAt(modelRow, 0).toString();
                String firstName = model.getValueAt(modelRow, 2).toString();
                String middleName = model.getValueAt(modelRow, 3).toString();
                String lastName = model.getValueAt(modelRow, 1).toString();
                String gender = model.getValueAt(modelRow, 5).toString();
                String yearLevel = model.getValueAt(modelRow, 4).toString();
                String programCode = model.getValueAt(modelRow, 6).toString();

                // Populate update form fields
                updateIDNumberTextField.setText(idNumber);
                updateFirstNameField.setText(firstName);
                updateMiddleNameTextField.setText(middleName);
                updateLastNameTextField.setText(lastName);
                updateGenderComboBox.setSelectedItem(gender);
                updateYearLevelCombo.setSelectedItem(yearLevel);
                updateProgramCodeCombo.setSelectedItem(programCode);

                // Switch to update form tab
                int updateFormIndex = dashboardTabs.indexOfComponent(updateHistory);
                if (updateFormIndex != -1) {
                    dashboardTabs.setSelectedIndex(updateFormIndex);
                }
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentDataTable.getSelectedRow();

                if (selectedRow == -1) {
                    checkEnrollmentForm errorDialog = new checkEnrollmentForm(null, "select delete");
                    errorDialog.setVisible(true);
                    return;
                }

                String idNumber = studentDataTable.getValueAt(selectedRow, 0).toString();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student to update.");
                    return;
                }

                // Convert to model index (in case of sorting)
                int modelRow = studentDataTable.convertRowIndexToModel(selectedRow);
                DefaultTableModel model = (DefaultTableModel) studentDataTable.getModel();

                // Extract existing data

                String firstName = model.getValueAt(modelRow, 2).toString();
                String middleName = model.getValueAt(modelRow, 3).toString();
                String lastName = model.getValueAt(modelRow, 1).toString();
                String gender = model.getValueAt(modelRow, 5).toString();
                String yearLevel = model.getValueAt(modelRow, 4).toString();
                String programCode = model.getValueAt(modelRow, 6).toString();

                // Populate update form fields
                updateIDNumberTextField.setText(idNumber);
                updateFirstNameField.setText(firstName);
                updateMiddleNameTextField.setText(middleName);
                updateLastNameTextField.setText(lastName);
                updateGenderComboBox.setSelectedItem(gender);
                updateYearLevelCombo.setSelectedItem(yearLevel);
                updateProgramCodeCombo.setSelectedItem(programCode);

                int confirm = JOptionPane.showOptionDialog(
                        null,
                        "Are you sure you want to delete this student?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, // Use WARNING for better UX
                        null, // No custom icon
                        new Object[]{"Yes", "No"}, // Button labels
                        "No" // Default selection
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    if (updater == null) { // Ensure updater is initialized before calling deleteStudent
                        updater = new updateStudentClass(studentDataTable, enrollActionListener,
                                updateIDNumberTextField, updateFirstNameField, updateMiddleNameTextField,
                                updateLastNameTextField, updateGenderComboBox, updateYearLevelCombo, updateProgramCodeCombo, updateCollegeCodeComboBox);
                    }
                    updater.deleteStudent(idNumber);
                }

                if (confirm == JOptionPane.NO_OPTION) {
                    studentDataTable.clearSelection();
                    updateIDNumberTextField.setText("");
                    updateFirstNameField.setText("");
                    updateMiddleNameTextField.setText("");
                    updateLastNameTextField.setText("");
                    updateGenderComboBox.setSelectedIndex(0);
                    updateYearLevelCombo.setSelectedIndex(0);
                    updateProgramCodeCombo.setSelectedIndex(0);
                    updateCollegeCodeComboBox.setSelectedIndex(0);

                }
            }
        });

        dashboardTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int enrollmentFormIndex = dashboardTabs.indexOfComponent(enrollmentForm);
                if (dashboardTabs.getSelectedIndex() == enrollmentFormIndex) {
                    // The enrollment form tab is selected
                    programCodeComboBox.setSelectedIndex(0);
                }

                int updateFormIndex = dashboardTabs.indexOfComponent(updateHistory);
                if (dashboardTabs.getSelectedIndex() != updateFormIndex) {

                    // Reset text fields
                    updateIDNumberTextField.setText("");
                    updateFirstNameField.setText("");
                    updateMiddleNameTextField.setText("");
                    updateLastNameTextField.setText("");
                    updateLastNameTextField.setText("");

                    // Reset combo boxes
                    updateGenderComboBox.setSelectedIndex(0);
                    updateYearLevelCombo.setSelectedIndex(0);
                    updateProgramCodeCombo.setSelectedIndex(0);
                    updateCollegeCodeComboBox.setSelectedIndex(0);
                }

                int acountManagementIndex = dashboardTabs.indexOfComponent(accountManagement);
                if (dashboardTabs.getSelectedIndex() == acountManagementIndex) {
                    invisiblePane.setVisible(false);
                }
            }
        });

        studentCount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                studentDataTable.clearSelection();
            }
        });

        populateTable populateTableInstance = new populateTable();

        programCodeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCode = (String) programCodeComboBox.getSelectedItem();

                if (selectedCode.equalsIgnoreCase("Select program code") || selectedCode.equalsIgnoreCase("No programs found")) {
                    programName.setVisible(true);
                    programName.setText("PLEASE SELECT PROGRAM TO ENROLL");
                } else {
                    programName.setVisible(true);
                    String[] details = populateTable.programCodeMap.getOrDefault(selectedCode, new String[]{"Unknown Program", "Unknown College"});

                    String programFullName = details[0]; // Program Name
                    String collegeCode = details[1]; // College Code

                    // Set text area to show both program name
                    programName.setText("CHOSEN PROGRAM: " + programFullName.toUpperCase());
                    autoSelectCollege(collegeCode);
                }
            }

            public void autoSelectCollege(String selectedCollegeCode){
                for (int i = 0; i < collegeCodeComboBox.getItemCount(); i++) {
                    if (collegeCodeComboBox.getItemAt(i).equals(selectedCollegeCode)) {
                        collegeCodeComboBox.setSelectedIndex(i); // Select matching college
                    }
                }
            }
        });

        updateProgramCodeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCode = (String) updateProgramCodeCombo.getSelectedItem();

                if (selectedCode.equalsIgnoreCase("Select program code") || selectedCode.equalsIgnoreCase("No programs found")) {
                    insertUpdatedProgramName.setVisible(true);
                    insertUpdatedProgramName.setText("PLEASE SELECT PROGRAM TO ENROLL");
                } else {
                    insertUpdatedProgramName.setVisible(true);
                    String[] details = populateTable.programCodeMap.getOrDefault(selectedCode, new String[]{"Unknown Program", "Unknown College"});

                    String programFullName = details[0]; // Program Name
                    String collegeCode = details[1]; // College Code

                    // Set text area to show both program name
                    insertUpdatedProgramName.setText("CHOSEN PROGRAM: " + programFullName.toUpperCase());
                    autoSelectCollege(collegeCode);
                }
            }
            public void autoSelectCollege(String selectedCollegeCode){
                for (int i = 0; i < updateCollegeCodeComboBox.getItemCount(); i++) {
                    if (updateCollegeCodeComboBox.getItemAt(i).equals(selectedCollegeCode)) {
                        updateCollegeCodeComboBox.setSelectedIndex(i); // Select matching college
                    }
                }
            }
        });

        collegeCodeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCode = (String) collegeCodeComboBox.getSelectedItem();

                if (selectedCode.equalsIgnoreCase("Select college")) {
                    collegeName.setVisible(true);
                    collegeName.setText("PLEASE SELECT COLLEGE TO ENROLL");
                    createProgramCodeCombo();

                } else {
                    collegeName.setVisible(true);
                    String[] details = populateTable.collegeCodeMap.getOrDefault(selectedCode, new String[]{"Unknown College"});

                    String collegeNameDetails = details[0];

                    collegeName.setText("CHOSEN COLLEGE: " + collegeNameDetails.toUpperCase());
                    String selectedProgram = (String) programCodeComboBox.getSelectedItem();

                    ActionListener[] listeners = programCodeComboBox.getActionListeners();
                    for (ActionListener listener : listeners) {
                        programCodeComboBox.removeActionListener(listener);
                    }

                    int itemCount = programCodeComboBox.getItemCount();

                    for (int i = itemCount - 1; i > 0; i--) { // Start from the last item and keep the first one
                        programCodeComboBox.removeItemAt(i);
                    }

                    filteredProgramsEnrollmentList(selectedCode);

                    boolean found = false;
                    for (int i = 0; i < programCodeComboBox.getItemCount(); i++) {
                        if (programCodeComboBox.getItemAt(i).equals(selectedProgram)) {
                            programCodeComboBox.setSelectedIndex(i);
                            found = true;
                            break;
                        }
                    }

                    // If not found, reset selection
                    if (!found) {
                        programCodeComboBox.setSelectedIndex(0); // "Select program code"
                    }

                    for (ActionListener listener : listeners) {
                        programCodeComboBox.addActionListener(listener);
                    }

                    programCodeComboBox.revalidate();
                    programCodeComboBox.repaint();
                }
            }

            private void filteredProgramsEnrollmentList (String selectedCollegeCode){
                int itemCount = programCodeComboBox.getItemCount();
                for (Map.Entry<String, String[]> entry : populateTable.programCodeMap.entrySet()) {
                    String programCode = entry.getKey();  // Program code (key)
                    String[] programDetails = entry.getValue(); // [College Code, Program Name]

                    String programFullName = programDetails[0];
                    String programCollege = programDetails[1];


                    if (programCollege.equals(selectedCollegeCode)) { // Check if college codes match
                        programCodeComboBox.addItem(programCode); // Add Program
                    }
                }
            }
        }

        );
        updateCollegeCodeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUpdateCollegeCode = (String) updateCollegeCodeComboBox.getSelectedItem();

                if (selectedUpdateCollegeCode.equalsIgnoreCase("Select college")) {
                    insertUpdatedCollegeName.setVisible(true);
                    insertUpdatedCollegeName.setText("PLEASE SELECT COLLEGE");
                    createProgramCodeCombo();
                } else {

                    String[] details = populateTable.collegeCodeMap.getOrDefault(selectedUpdateCollegeCode, new String[]{"Unknown College"});
                    String collegeNameDetails = details[0];

                    insertUpdatedCollegeName.setVisible(true);
                    insertUpdatedCollegeName.setText("CHOSEN COLLEGE: " + collegeNameDetails.toUpperCase());
                    String selectedProgram = (String) updateProgramCodeCombo.getSelectedItem();

                    ActionListener[] listeners = updateProgramCodeCombo.getActionListeners();
                    for (ActionListener listener : listeners) {
                        updateProgramCodeCombo.removeActionListener(listener);
                    }

                    int itemCount = updateProgramCodeCombo.getItemCount();

                    for (int i = itemCount - 1; i > 0; i--) { // Start from the last item and keep the first one
                        updateProgramCodeCombo.removeItemAt(i);
                    }

                    filteredUpdateProgramsEnrollmentList(selectedUpdateCollegeCode);

                    boolean found = false;
                    for (int i = 0; i < updateProgramCodeCombo.getItemCount(); i++) {
                        if (updateProgramCodeCombo.getItemAt(i).equals(selectedProgram)) {
                            updateProgramCodeCombo.setSelectedIndex(i);
                            found = true;
                            break;
                        }
                    }

                    // If not found, reset selection
                    if (!found) {
                        updateProgramCodeCombo.setSelectedIndex(0); // "Select program code"
                    }

                    for (ActionListener listener : listeners) {
                        updateProgramCodeCombo.addActionListener(listener);
                    }

                    updateProgramCodeCombo.revalidate();
                    updateProgramCodeCombo.repaint();

                }
            }
            private void filteredUpdateProgramsEnrollmentList (String selectedCollegeCode){
                int itemCount = updateProgramCodeCombo.getItemCount();
                for (Map.Entry<String, String[]> entry : populateTable.programCodeMap.entrySet()) {
                    String programCode = entry.getKey();  // Program code (key)
                    String[] programDetails = entry.getValue(); // [College Code, Program Name]

                    String programFullName = programDetails[0];
                    String programCollege = programDetails[1];


                    if (programCollege.equals(selectedCollegeCode)) { // Check if college codes match
                        updateProgramCodeCombo.addItem(programCode); // Add Program
                    }
                }
            }
        });
        proposeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invisiblePane.setVisible(true);

                collegePane.setVisible(true);
                collegeComboBox.setVisible(true);
                selectedCollegeForCourse.setVisible(true);
                handlerPane1.setVisible(true);
                handlerPane2.setVisible(true);
                registerHandlerButton.setVisible(true);
                updateHandlerButton.setVisible(true);
                deleteHandlerButton.setVisible(true);
                handlerTablePanel.setVisible(true);

                handlerPane1.setText("Program Name");
                handlerPane2.setText("Program Code");
                registerHandlerButton.setText("Register Program");
                updateHandlerButton.setText("Update Program");
                deleteHandlerButton.setText("Delete Program");
                updateToCollege.setText("Select Program Code To Edit");

                DefaultTableModel model2 = new populateTable().getProgramsTable();
                tableHandlerJTable.setModel(model2);

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                TableColumnModel columns2 = tableHandlerJTable.getColumnModel();

                for (int i = 0; i < tableHandlerJTable.getColumnCount(); i++) {
                    columns2.getColumn(i).setCellRenderer(centerRenderer);
                }

                for (int i = 0; i < tableHandlerJTable.getColumnCount(); i++) {
                    Class<?> colClass = tableHandlerJTable.getColumnClass(i);
                    tableHandlerJTable.setDefaultEditor(colClass, null); // Disables editing
                }

                int programsFound =  tableHandlerJTable.getRowCount();
                handlerFound.setText("Programs Found: " + programsFound);
                collegeComboBox.setSelectedIndex(0);

            }
        });
        registerNewCollege.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invisiblePane.setVisible(true);
                collegePane.setVisible(false);
                collegeComboBox.setVisible(false);
                selectedCollegeForCourse.setVisible(false);


                handlerPane1.setVisible(true);
                handlerPane2.setVisible(true);
                registerHandlerButton.setVisible(true);
                updateHandlerButton.setVisible(true);
                deleteHandlerButton.setVisible(true);
                handlerFound.setVisible(true);
                handlerTablePanel.setVisible(true);
                updateToCollege.setVisible(true);

                handlerPane1.setText("College Name");
                handlerPane2.setText("College Code");
                registerHandlerButton.setText("Register College");
                updateHandlerButton.setText("Update College");
                deleteHandlerButton.setText("Delete College");
                updateToCollege.setText("Select College Code To Edit");

                collegeComboBox.setSelectedIndex(0);

                DefaultTableModel model2 = new populateTable().getCollgeTable();
                tableHandlerJTable.setModel(model2);

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                TableColumnModel columns2 = tableHandlerJTable.getColumnModel();

                for (int i = 0; i < tableHandlerJTable.getColumnCount(); i++) {
                    columns2.getColumn(i).setCellRenderer(centerRenderer);
                }

                for (int i = 0; i < tableHandlerJTable.getColumnCount(); i++) {
                    Class<?> colClass = tableHandlerJTable.getColumnClass(i);
                    tableHandlerJTable.setDefaultEditor(colClass, null); // Disables editing
                }

                int collegesFound =  tableHandlerJTable.getRowCount();
                handlerFound.setText("Colleges Found: " + collegesFound);

            }
        });
        collegeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCode = (String) collegeComboBox.getSelectedItem();

                if (selectedCode.equalsIgnoreCase("Select college")) {
                    selectedCollegeForCourse.setText("PLEASE SELECT COLLEGE TO REGISTER PROGRAM");

                } else {
                    String[] details = populateTable.collegeCodeMap.getOrDefault(selectedCode, new String[]{"Unknown College"});

                    String collegeNameDetails = details[0];

                    selectedCollegeForCourse.setText(collegeNameDetails.toUpperCase());
                }
            }
        });
        closeFormHandler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlerPane1.setVisible(false);
                handlerPane2.setVisible(false);
                handlerTablePanel.setVisible(false);
                invisiblePane.setVisible(false);
            }
        });

        registerHandlerButton.addActionListener(new ActionListener() {

            private boolean isCollegeDuplicate(String collegeCode, String collegeName, JTable tableHandlerJTable) {
                DefaultTableModel model = (DefaultTableModel) tableHandlerJTable.getModel();
                int rowCount = model.getRowCount();

                for (int row = 0; row < rowCount; row++) {
                    String existingCode = model.getValueAt(row, 0).toString().trim(); // Column 0 = College Code
                    String existingName = model.getValueAt(row, 1).toString().trim(); // Column 1 = College Name

                    if (existingCode.equalsIgnoreCase(collegeCode)) {
                        return true; // Duplicate college code found
                    }
                    if (existingName.equalsIgnoreCase(collegeName)) {
                        return true; // Duplicate college name found
                    }
                }
                return false; // No duplicate found
            }

            private void reloadTableFromCSV(String csvPath) {
                DefaultTableModel model = (DefaultTableModel) tableHandlerJTable.getModel();
                model.setRowCount(0); // Clear existing table data

                try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
                    String line;
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 2) {
                            model.addRow(new Object[]{data[0].trim(), data[1].trim()});
                        }

                        if (data.length == 3) {
                            model.addRow(new Object[]{data[0].trim(), data[1].trim(), data[2].trim()});
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Update label for colleges found
                if(registerHandlerButton.getText().equals("Register Program")) {
                    handlerFound.setText("Programs Found: " + model.getRowCount());
                }
                if(registerHandlerButton.getText().equals("Register College")) {
                    handlerFound.setText("Colleges Found: " + model.getRowCount());
                }
            }


            @Override
            public void actionPerformed(ActionEvent e) {
                if (registerHandlerButton.getText().equals("Register College")) {
                    String collegeName = handlerField1.getText().trim();
                    String collegeCode = handlerField2.getText().trim().toUpperCase();

                    String projectDir = System.getProperty("user.dir");
                    String csvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "colleges" + File.separator + "colleges.csv";
                    File csvPathFile = new File(csvPath);

                    try {
                        if (collegeName.isEmpty() || collegeName.isBlank()) {
                            throw new ValidationException("Supply College Name");
                        }
                        if (collegeCode.isEmpty() || collegeCode.isBlank()) {
                            throw new ValidationException("Supply College Code");
                        }

                        if (isCollegeDuplicate(collegeCode, collegeName, tableHandlerJTable)) {
                            throw new ValidationException("College Code or Name already exists!");
                        }

                        // Append the new college to the CSV
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPathFile, true))) {
                            bw.write(collegeCode + "," + collegeName);
                            bw.newLine();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error writing to file!", "File Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }

                        // Refresh JTable with updated CSV data
                        reloadTableFromCSV(csvPath);
                        JOptionPane.showMessageDialog(null, "College Registered Successfully!");
                        handlerField1.setText("");
                        handlerField2.setText("");
                        updateField.setText("");


                    } catch (ValidationException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (registerHandlerButton.getText().equals("Register Program")) {
                    String programName = handlerField1.getText().trim();
                    String programCode = handlerField2.getText().trim().toUpperCase();
                    String collegeCode = collegeComboBox.getSelectedItem().toString().trim();

                    String projectDir = System.getProperty("user.dir");
                    String csvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
                    File csvPathFile = new File(csvPath);

                    try {
                        if (programName.isEmpty() || programName.isBlank()) {
                            throw new ValidationException("Supply Program Name");
                        }
                        if (programCode.isEmpty() || programCode.isBlank()) {
                            throw new ValidationException("Supply Program Code");
                        }

                        if (collegeCode.equalsIgnoreCase("Select College")) {
                            throw new ValidationException("Supply College to register the program");
                        }

                        if (isCollegeDuplicate(programCode, programName, tableHandlerJTable)) {
                            throw new ValidationException("Program Code or Name already exists!");
                        }

                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPathFile, true))) {
                            bw.write(programCode + "," + programName + "," + collegeCode);
                            bw.newLine();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error writing to file!", "File Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }

                        // Refresh JTable with updated CSV data
                        JOptionPane.showMessageDialog(null, "Program Registered Successfully!");
                        reloadTableFromCSV(csvPath);
                        handlerField1.setText("");
                        handlerField2.setText("");
                        updateField.setText("");
                        collegeComboBox.setSelectedIndex(0);


                    } catch (ValidationException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            class ValidationException extends Exception {
                public ValidationException(String message) {
                    super(message);
                }
            }

        });
        updateHandlerButton.addActionListener(new ActionListener() {
            private void reloadTableFromCSV(String csvPath) {
                DefaultTableModel model = (DefaultTableModel) tableHandlerJTable.getModel();
                model.setRowCount(0); // Clear existing table data

                try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
                    String line;
                    br.readLine(); // Skip header
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 2) {
                            model.addRow(new Object[]{data[0].trim(), data[1].trim()});
                        }

                        if (data.length == 3) {
                            model.addRow(new Object[]{data[0].trim(), data[1].trim(), data[2].trim()});
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(updateHandlerButton.getText().equals("Update Program")) {
                    handlerFound.setText("Programs Found: " + model.getRowCount());
                }
                if(updateHandlerButton.getText().equals("Update College")) {
                    handlerFound.setText("Colleges Found: " + model.getRowCount());
                }
            }

            private void updateProgramsCSV(String oldCollegeCode, String newCollegeCode) {
                if (oldCollegeCode.equals(newCollegeCode)) {
                    return; // No need to update if the code remains the same
                }

                String projectDir = System.getProperty("user.dir");
                String programsCsvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                        "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
                File programsCsvFile = new File(programsCsvPath);

                try {
                    ArrayList<String> lines = new ArrayList<>();

                    // Read and update `programs.csv`
                    try (BufferedReader br = new BufferedReader(new FileReader(programsCsvFile))) {
                        String header = br.readLine();
                        lines.add(header); // Keep header
                        String line;

                        while ((line = br.readLine()) != null) {
                            String[] data = line.split(",");
                            if (data.length >= 3 && data[2].trim().equalsIgnoreCase(oldCollegeCode)) {
                                lines.add(data[0] + "," + data[1] + "," + newCollegeCode); // Update College Code
                            } else {
                                lines.add(line);
                            }
                        }
                    }

                    // Write back to `programs.csv`
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(programsCsvFile))) {
                        for (String line : lines) {
                            bw.write(line);
                            bw.newLine();
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating programs.csv", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (updateHandlerButton.getText().equals("Update College")) {
                    String selectedCode = updateField.getText().trim().toUpperCase();
                    String newCode = handlerField2.getText().trim().toUpperCase();
                    String newName = handlerField1.getText().trim();

                    String projectDir = System.getProperty("user.dir");
                    String csvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "colleges" + File.separator + "colleges.csv";
                    File csvFile = new File(csvPath);

                    if (selectedCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter a valid College Code to update!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        ArrayList<String> lines = new ArrayList<>();
                        boolean found = false;
                        String oldCollegeName = "";
                        String oldCollegeCode = selectedCode;

                        // Read the file and check if the college exists
                        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                            String header = br.readLine();  // Read and store header
                            lines.add(header);  // Keep the header
                            String line;

                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 2 && data[0].trim().equalsIgnoreCase(selectedCode)) {
                                    oldCollegeName = data[1].trim();
                                    found = true;
                                }
                                lines.add(line);
                            }
                        }

                        if (!found) {
                            JOptionPane.showMessageDialog(null, "College Code not found!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Ensure at least one field (code or name) is being updated
                        if (newCode.isEmpty() && newName.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Provide a new College Code or Name to update!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // If new code is empty, keep the old one
                        if (newCode.isEmpty()) {
                            newCode = selectedCode;
                        }

                        // If new name is empty, keep the old one
                        if (newName.isEmpty()) {
                            newName = oldCollegeName;
                        }

                        // Check for duplicate college code or name (only if they changed)
                        if (!newCode.equalsIgnoreCase(selectedCode) || !newName.equalsIgnoreCase(oldCollegeName)) {
                            for (String line : lines) {
                                String[] data = line.split(",");
                                if (data.length >= 2) {
                                    String existingCode = data[0].trim();
                                    String existingName = data[1].trim();

                                    if (existingCode.equalsIgnoreCase(newCode) && !existingCode.equalsIgnoreCase(selectedCode)) {
                                        JOptionPane.showMessageDialog(null, "College Code already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                    if (existingName.equalsIgnoreCase(newName) && !existingName.equalsIgnoreCase(oldCollegeName)) {
                                        JOptionPane.showMessageDialog(null, "College Name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                        }

                        // Update the college record
                        ArrayList<String> updatedLines = new ArrayList<>();
                        for (String line : lines) {
                            String[] data = line.split(",");
                            if (data.length >= 2 && data[0].trim().equalsIgnoreCase(selectedCode)) {
                                updatedLines.add(newCode + "," + newName); // Update row
                            } else {
                                updatedLines.add(line);
                            }
                        }

                        // Write updated data back to the CSV file
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
                            for (String line : updatedLines) {
                                bw.write(line);
                                bw.newLine();
                            }
                        }

                        // Update programs.csv if the college code changed
                        updateProgramsCSV(oldCollegeCode, newCode);

                        // Reload JTable with updated CSV data
                        reloadTableFromCSV(csvPath);
                        JOptionPane.showMessageDialog(null, "College Updated Successfully!");
                        handlerField1.setText("");
                        handlerField2.setText("");
                        updateField.setText("");

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error updating college.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (updateHandlerButton.getText().equals("Update Program")) {
                    String selectedCode = updateField.getText().trim().toUpperCase();
                    String newCode = handlerField2.getText().trim().toUpperCase();
                    String newName = handlerField1.getText().trim();
                    String newCollegeCode = collegeComboBox.getSelectedItem().toString().trim();

                    String projectDir = System.getProperty("user.dir");

                    String csvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
                    File csvFile = new File(csvPath);

                    if (selectedCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter a valid Program Code to update!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        ArrayList<String> lines = new ArrayList<>();
                        boolean found = false;
                        String oldProgramName = "";
                        String oldProgramCollegeCode = "";
                        String oldProgramCode = selectedCode;

                        // Read the file and check if the program exists
                        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                            String header = br.readLine();  // Read and store header
                            lines.add(header);  // Keep the header
                            String line;

                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length == 3 && data[0].trim().equalsIgnoreCase(selectedCode)) {
                                    oldProgramName = data[1].trim();
                                    oldProgramCollegeCode = data[2].trim();
                                    found = true;
                                }
                                lines.add(line);
                            }
                        }

                        if (!found) {
                            JOptionPane.showMessageDialog(null, "Program Code not found!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Ensure at least one field (code, name, or college) is being updated
                        if (newCode.isEmpty() && newName.isEmpty() && newCollegeCode.equalsIgnoreCase(oldProgramCollegeCode)) {
                            JOptionPane.showMessageDialog(null, "Provide a new Program Code, Name, or College to update!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Keep old values if fields are empty
                        if (newCode.isEmpty()) newCode = selectedCode;
                        if (newName.isEmpty()) newName = oldProgramName;
                        if (newCollegeCode.equalsIgnoreCase("Select College")) newCollegeCode = oldProgramCollegeCode;

                        // Check for duplicate program code or name
                        for (String line : lines) {
                            String[] data = line.split(",");
                            if (data.length == 3) {
                                String existingCode = data[0].trim();
                                String existingName = data[1].trim();

                                if (existingCode.equalsIgnoreCase(newCode) && !existingCode.equalsIgnoreCase(selectedCode)) {
                                    JOptionPane.showMessageDialog(null, "Program Code already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                if (existingName.equalsIgnoreCase(newName) && !existingName.equalsIgnoreCase(oldProgramName)) {
                                    JOptionPane.showMessageDialog(null, "Program Name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                        }

                        // Update the program record
                        ArrayList<String> updatedLines = new ArrayList<>();
                        for (String line : lines) {
                            String[] data = line.split(",");
                            if (data.length == 3 && data[0].trim().equalsIgnoreCase(selectedCode)) {
                                updatedLines.add(newCode + "," + newName + "," + newCollegeCode); // Update row
                            } else {
                                updatedLines.add(line);
                            }
                        }

                        // Write updated data back to the CSV file
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
                            for (String line : updatedLines) {
                                bw.write(line);
                                bw.newLine();
                            }
                        }

                        // Reload JTable with updated CSV data
                        reloadTableFromCSV(csvPath);
                        JOptionPane.showMessageDialog(null, "Program Updated Successfully!");
                        handlerField1.setText("");
                        handlerField2.setText("");
                        updateField.setText("");
                        collegeComboBox.setSelectedIndex(0);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error updating program.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        deleteHandlerButton.addActionListener(new ActionListener() {

            private void reloadTableFromCSV(String csvPath) {
                DefaultTableModel model = (DefaultTableModel) tableHandlerJTable.getModel();
                model.setRowCount(0); // Clear existing table data

                try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
                    String line;
                    br.readLine(); // Skip header
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 2) {
                            model.addRow(new Object[]{data[0].trim(), data[1].trim()});
                        }

                        if (data.length == 3) {
                            model.addRow(new Object[]{data[0].trim(), data[1].trim(), data[2].trim()});
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (deleteHandlerButton.getText().equals("Delete College"))
                    handlerFound.setText("Colleges Found: " + model.getRowCount());
                if (deleteHandlerButton.getText().equals("Delete Program"))
                    handlerFound.setText("Programs Found: " + model.getRowCount());
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (deleteHandlerButton.getText().trim().equals("Delete College")) {

                    String selectedCode = updateField.getText().trim().toUpperCase();

                    if (selectedCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter a valid College Code to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String projectDir = System.getProperty("user.dir");

                    String collegesCsvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "colleges" + File.separator + "colleges.csv";
                    String programsCsvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
                    String studentsCsvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "students" + File.separator + "students.csv";

                    File collegesCsvFile = new File(collegesCsvPath);
                    File programsCsvFile = new File(programsCsvPath);
                    File studentsCsvFile = new File(studentsCsvPath);

                    try {
                        // Step 1: Check if any students are enrolled in a program under the college
                        HashSet<String> collegePrograms = new HashSet<>();

                        // Get all program codes under this college
                        try (BufferedReader br = new BufferedReader(new FileReader(programsCsvFile))) {
                            br.readLine(); // Skip header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 3 && data[2].trim().equalsIgnoreCase(selectedCode)) {
                                    collegePrograms.add(data[0].trim());
                                }
                            }
                        }

                        boolean studentsExist = false;

                        // Check if any student is enrolled in these programs
                        try (BufferedReader br = new BufferedReader(new FileReader(studentsCsvFile))) {
                            br.readLine(); // Skip header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 7 && collegePrograms.contains(data[6].trim())) {
                                    studentsExist = true;
                                    break;
                                }
                            }
                        }

                        if (studentsExist) {
                            JOptionPane.showMessageDialog(null, "Cannot delete college. Students are still enrolled under it.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        boolean collegeExists = false;

                        // Check if the college exists in colleges.csv
                        try (BufferedReader br = new BufferedReader(new FileReader(collegesCsvFile))) {
                            br.readLine(); // Skip header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 2 && data[0].trim().equalsIgnoreCase(selectedCode)) {
                                    collegeExists = true;
                                    break;
                                }
                            }
                        }

                        if (!collegeExists) {
                            JOptionPane.showMessageDialog(null, "College Code not found!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Step 2: Remove the college from colleges.csv
                        ArrayList<String> updatedColleges = new ArrayList<>();
                        try (BufferedReader br = new BufferedReader(new FileReader(collegesCsvFile))) {
                            String header = br.readLine();
                            updatedColleges.add(header); // Keep header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 2 && data[0].trim().equalsIgnoreCase(selectedCode)) {
                                    continue; // Skip the college being deleted
                                }
                                updatedColleges.add(line);
                            }
                        }

                        // Write updated data back to colleges.csv
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(collegesCsvFile))) {
                            for (String line : updatedColleges) {
                                bw.write(line);
                                bw.newLine();
                            }
                        }

                        // Step 3: Remove related programs from programs.csv
                        ArrayList<String> updatedPrograms = new ArrayList<>();
                        try (BufferedReader br = new BufferedReader(new FileReader(programsCsvFile))) {
                            String header = br.readLine();
                            updatedPrograms.add(header); // Keep header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 3 && data[2].trim().equalsIgnoreCase(selectedCode)) {
                                    continue; // Skip the program related to the deleted college
                                }
                                updatedPrograms.add(line);
                            }
                        }

                        // Write updated data back to programs.csv
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(programsCsvFile))) {
                            for (String line : updatedPrograms) {
                                bw.write(line);
                                bw.newLine();
                            }
                        }

                        // Reload JTable after deletion
                        reloadTableFromCSV(collegesCsvPath);
                        JOptionPane.showMessageDialog(null, "College deleted successfully!");
                        updateField.setText("");

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error deleting college.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (deleteHandlerButton.getText().equals("Delete Program")) {
                    String selectedProgram = updateField.getText();

                    String projectDir = System.getProperty("user.dir");

                    String programsCsvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "programs" + File.separator + "programs.csv";
                    String studentsCsvPath = projectDir + File.separator + "pythonScripts" + File.separator +
                            "studentInformationTable" + File.separator + "students" + File.separator + "students.csv";

                    File programsCsvFile = new File(programsCsvPath);
                    File studentsCsvFile = new File(studentsCsvPath);

                    try {
                        // Step 1: Check if any students are enrolled in a program under the college
                        HashSet<String> collegePrograms = new HashSet<>();

                        // Get all program codes under this college
                        try (BufferedReader br = new BufferedReader(new FileReader(programsCsvFile))) {
                            br.readLine(); // Skip header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 3 && data[2].trim().equalsIgnoreCase(selectedProgram)) {
                                    collegePrograms.add(data[0].trim());
                                }
                            }
                        }

                        boolean studentsExist = false;

                        // Check if any student is enrolled in these programs
                        try (BufferedReader br = new BufferedReader(new FileReader(studentsCsvFile))) {
                            br.readLine(); // Skip header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 7 && selectedProgram.equalsIgnoreCase(data[6].trim())) {
                                    studentsExist = true;
                                    break;
                                }
                            }
                        }

                        if (studentsExist) {
                            JOptionPane.showMessageDialog(null, "Cannot delete program. Students are still enrolled under it.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        boolean programExist = false;

                        // Check if the programs exists in programs.csv
                        try (BufferedReader br = new BufferedReader(new FileReader(programsCsvFile))) {
                            br.readLine(); // Skip header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 2 && data[0].trim().equalsIgnoreCase(selectedProgram)) {
                                    programExist = true;
                                    break;
                                }
                            }
                        }

                        if (!programExist) {
                            JOptionPane.showMessageDialog(null, "Program Code not found!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Step 2: Remove the program from programs.csv
                        ArrayList<String> updatedPrograms = new ArrayList<>();
                        try (BufferedReader br = new BufferedReader(new FileReader(programsCsvFile))) {
                            String header = br.readLine();
                            updatedPrograms.add(header); // Keep header
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length >= 2 && data[0].trim().equalsIgnoreCase(selectedProgram)) {
                                    continue; // Skip the college being deleted
                                }
                                updatedPrograms.add(line);
                            }
                        }

                        // Write updated data back to programs.csv
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(programsCsvFile))) {
                            for (String line : updatedPrograms) {
                                bw.write(line);
                                bw.newLine();
                            }
                        }

                        // Reload JTable after deletion
                        reloadTableFromCSV(String.valueOf(programsCsvFile));
                        JOptionPane.showMessageDialog(null, "Program deleted successfully!");
                        updateField.setText("");

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error deleting Program.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        searchHandler.getDocument().addDocumentListener(new DocumentListener() {
            private void filterTable() {
                if (registerHandlerButton.getText().equals("Register College")) {
                    String query = searchHandler.getText().trim();
                    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tableHandlerJTable.getModel());
                    tableHandlerJTable.setRowSorter(sorter);

                    if (query.isEmpty()) {
                        sorter.setRowFilter(null); // Show all rows if the search field is empty
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Case-insensitive search
                    }
                    int collegesCount = tableHandlerJTable.getRowCount();
                    handlerFound.setText("Colleges found: " + collegesCount);
                }

                if (registerHandlerButton.getText().equals("Register Program")) {
                    String query = searchHandler.getText().trim();
                    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tableHandlerJTable.getModel());
                    tableHandlerJTable.setRowSorter(sorter);

                    if (query.isEmpty()) {
                        sorter.setRowFilter(null); // Show all rows if the search field is empty
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Case-insensitive search
                    }
                    int collegesCount = tableHandlerJTable.getRowCount();
                    handlerFound.setText("Programs found: " + collegesCount);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"Yes", "No"},
                        "No"
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void updateSorting() {

        // Then get the selected order (ascending or descending) from the second combo
        String order = (String) sortOptionsCombo.getSelectedItem();
        boolean ascending = "Ascending".equals(order);  // Default is ascending

        // Apply the selected sorting order
        applySortingOptions.setSortOptions(ascending);
    }

    //populates the combo
    private void createSortingCombo() {
        sortOptions.setModel(new DefaultComboBoxModel(new String[]{
                "Select group options",
                "By ID Number",
                "By Surname",
                "By Year",
                "By Program Code",
                "By Gender"
        }));
    }

    private void createSortOptionsCombo() {
        sortOptionsCombo.setModel(new DefaultComboBoxModel(new String[]{
                "Ascending",
                "Descending"
        }));
    }

    private void createGenderCombo() {
        genderComboBox.setModel(new DefaultComboBoxModel(new String[]{
                "Select gender",
                "Male",
                "Female"
        }));

        updateGenderComboBox.setModel(new DefaultComboBoxModel(new String[]{
                "Select gender",
                "Male",
                "Female"
        }));
    }

    private void createYearLevelCombo() {
        yearLevelComboBox.setModel(new DefaultComboBoxModel(new String[]{
                "Select year level",
                "1st Year",
                "2nd Year",
                "3rd Year",
                "4th Year"
        }));

        updateYearLevelCombo.setModel(new DefaultComboBoxModel(new String[]{
                "Select year level",
                "1st Year",
                "2nd Year",
                "3rd Year",
                "4th Year"
        }));
    }

    private void createProgramCodeCombo() {
        populateTable populateTable = new populateTable();
        String[] programCodes = populateTable.getProgramCodes();
        String[] collegeCodes = populateTable.getCollegeNames();

        DefaultComboBoxModel model = new DefaultComboBoxModel(new String[]{
                "Select program code"
        });

        for (String code : programCodes) {
            model.addElement(code);
        }

        DefaultComboBoxModel updateModel = new DefaultComboBoxModel(new String[]{
                "Select program code"
        });

        for (String code : programCodes) {
            updateModel.addElement(code);
        }

        DefaultComboBoxModel collegeCodeModel = new DefaultComboBoxModel(new String[]{
                "Select College"
        });

        for (String code : collegeCodes) {
            collegeCodeModel.addElement(code);
        }

        DefaultComboBoxModel updateCollegeModel = new DefaultComboBoxModel(new String[]{
                "Select College"
        });

        for (String code : collegeCodes) {
            updateCollegeModel.addElement(code);
        }

        programCodeComboBox.setModel(model);
        updateProgramCodeCombo.setModel(updateModel);

        collegeCodeComboBox.setModel(collegeCodeModel);
        updateCollegeCodeComboBox.setModel(updateCollegeModel);

        collegeComboBox.setModel(collegeCodeModel);

    }

    //creates and displays the table
    public void createTable() {
        DefaultTableModel model = new populateTable().populateTable();
        studentDataTable.setModel(model);

        sorter = new TableRowSorter<>(model);
        studentDataTable.setRowSorter(sorter);

        //fixing columns on their place
        studentDataTable.getTableHeader().setReorderingAllowed(false);
        studentDataTable.setColumnSelectionAllowed(false);
        studentDataTable.getTableHeader().setResizingAllowed(false);

        tableHandlerJTable.getTableHeader().setReorderingAllowed(false);
        tableHandlerJTable.setColumnSelectionAllowed(false);
        tableHandlerJTable.getTableHeader().setReorderingAllowed(false);

        runPythonScript runPythonScript = new runPythonScript();
        runPythonScript.runPythonScript();
        runPythonScript.runPythonScriptForProgramCode();

        populateTable populateTable = new populateTable();
        populateTable.populateTable();

        //adjust a specific column width
        TableColumnModel columns = studentDataTable.getColumnModel();
        columns.getColumn(0).setMinWidth(55);

        //centering columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < studentDataTable.getColumnCount(); i++) {
            columns.getColumn(i).setCellRenderer(centerRenderer);
        }

        for (int i = 0; i < studentDataTable.getColumnCount(); i++) {
            Class<?> colClass = studentDataTable.getColumnClass(i);
            studentDataTable.setDefaultEditor(colClass, null); // Disables editing
        }


        int rowCount = studentDataTable.getRowCount();
        studentCount.setText("Students Found: " + rowCount);
    }

    public void applySearchFilter() {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null); // Show all if search is empty
            return;
        }

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                String id = entry.getStringValue(0).toLowerCase();        // ID Number (Column 0)
                String firstName = entry.getStringValue(1).toLowerCase(); // First Name (Column 1)
                String surname = entry.getStringValue(2).toLowerCase();   // Surname (Column 2)
                String programCode = entry.getStringValue(6).toLowerCase(); // Program Code (Column 6)

                if (searchText.matches("[\\d-]+")) { // If numeric, search in ID
                    return id.contains(searchText);
                } else { // If input is text, search in First Name, Surname, or Program Code
                    return firstName.contains(searchText) ||
                            surname.contains(searchText) ||
                            programCode.contains(searchText);
                }
            }
        });
    }

    public void updateStudentCount() {
        int rowCount = studentDataTable.getRowCount();
        studentCount.setText("Students Found: " + rowCount);
    }

    private void applyIDFormat(JTextField idNumberValue) {
        ((AbstractDocument) idNumberValue.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                // Remove non-digits and enforce max length of 9 (including dash)
                newText = newText.replaceAll("[^\\d]", ""); // Remove non-digit characters
                newText = newText.substring(0, Math.min(newText.length(), 8)); // Limit to 8 digits only

                // Auto-format: Insert "-" after the first 4 digits
                if (newText.length() > 4) {
                    newText = newText.substring(0, 4) + "-" + newText.substring(4);
                }

                fb.replace(0, fb.getDocument().getLength(), newText, attrs);
            }
        });

        // Ensure formatting on focus lost
        idNumberValue.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                formatID(idNumberValue);
            }
        });
    }

    private void formatID(JTextField field) {
        String text = field.getText().replaceAll("[^\\d]", ""); // Remove non-digits
        while (text.length() < 8) {
            text = "0" + text; // Pad with leading zeros
        }
        field.setText(text.substring(0, 4) + "-" + text.substring(4)); // Apply format
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        adminPanelDashboard = new JPanel();
        adminPanelDashboard.setLayout(new GridBagLayout());
        greetingMessagePanel = new JPanel();
        greetingMessagePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 0, 10);
        adminPanelDashboard.add(greetingMessagePanel, gbc);
        greetingLabel = new JLabel();
        greetingLabel.setText("INSERT TEXT GREETING HERE");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        greetingMessagePanel.add(greetingLabel, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 5, 10);
        adminPanelDashboard.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.9;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);
        adminPanelDashboard.add(panel2, gbc);
        dashboardTabs = new JTabbedPane();
        dashboardTabs.setFocusable(false);
        dashboardTabs.setRequestFocusEnabled(false);
        dashboardTabs.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 5, 10);
        panel2.add(dashboardTabs, gbc);
        tabbedDashboard = new JPanel();
        tabbedDashboard.setLayout(new GridBagLayout());
        dashboardTabs.addTab("Student Data Information", tabbedDashboard);
        operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        tabbedDashboard.add(operationsPanel, gbc);
        sortOptions = new JComboBox();
        sortOptions.setLightWeightPopupEnabled(true);
        sortOptions.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        operationsPanel.add(sortOptions, gbc);
        searchField = new JTextField();
        searchField.setAlignmentX(0.0f);
        searchField.setAutoscrolls(false);
        searchField.setColumns(20);
        searchField.setFocusable(true);
        searchField.setHorizontalAlignment(10);
        searchField.setInheritsPopupMenu(false);
        searchField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        operationsPanel.add(searchField, gbc);
        sortByText = new JTextPane();
        sortByText.setEditable(false);
        sortByText.setFocusCycleRoot(false);
        sortByText.setFocusable(false);
        sortByText.setOpaque(false);
        sortByText.setRequestFocusEnabled(false);
        sortByText.setText("Sort by:");
        sortByText.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        operationsPanel.add(sortByText, gbc);
        search = new JTextPane();
        search.setEditable(false);
        search.setFocusCycleRoot(false);
        search.setOpaque(false);
        search.setRequestFocusEnabled(false);
        search.setText("Search");
        search.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        operationsPanel.add(search, gbc);
        ascendingOptions = new JTextPane();
        ascendingOptions.setEditable(false);
        ascendingOptions.setFocusable(false);
        ascendingOptions.setOpaque(false);
        ascendingOptions.setText("Sorting options:");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        operationsPanel.add(ascendingOptions, gbc);
        sortOptionsCombo = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        operationsPanel.add(sortOptionsCombo, gbc);
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        tabbedDashboard.add(tablePanel, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 3, 5, 3);
        tablePanel.add(scrollPane1, gbc);
        studentDataTable = new JTable();
        studentDataTable.setAutoCreateColumnsFromModel(true);
        studentDataTable.setAutoCreateRowSorter(false);
        studentDataTable.setAutoResizeMode(4);
        studentDataTable.setAutoscrolls(false);
        studentDataTable.setCellSelectionEnabled(false);
        studentDataTable.setColumnSelectionAllowed(false);
        studentDataTable.setDoubleBuffered(true);
        studentDataTable.setEnabled(true);
        studentDataTable.setFocusable(false);
        studentDataTable.setOpaque(true);
        studentDataTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
        studentDataTable.setRequestFocusEnabled(false);
        studentDataTable.setRowSelectionAllowed(false);
        studentDataTable.setUpdateSelectionOnSort(true);
        studentDataTable.setVerifyInputWhenFocusTarget(false);
        scrollPane1.setViewportView(studentDataTable);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        tabbedDashboard.add(buttonsPanel, gbc);
        updateInformation = new JButton();
        updateInformation.setText("Update Student Information");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 5);
        buttonsPanel.add(updateInformation, gbc);
        enrollStudent = new JButton();
        enrollStudent.setText("Enroll Student");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add(enrollStudent, gbc);
        studentNumberPanel = new JPanel();
        studentNumberPanel.setLayout(new GridBagLayout());
        studentNumberPanel.setAlignmentX(0.5f);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        tabbedDashboard.add(studentNumberPanel, gbc);
        studentCount = new JTextPane();
        studentCount.setEditable(false);
        studentCount.setFocusCycleRoot(false);
        studentCount.setFocusable(false);
        studentCount.setOpaque(false);
        studentCount.setRequestFocusEnabled(false);
        studentCount.setText("Students Found: ");
        studentCount.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        studentNumberPanel.add(studentCount, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        studentNumberPanel.add(spacer1, gbc);
        updateHistory = new JPanel();
        updateHistory.setLayout(new GridBagLayout());
        dashboardTabs.addTab("Update Form", updateHistory);
        upateFormHeader = new JPanel();
        upateFormHeader.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        updateHistory.add(upateFormHeader, gbc);
        updateFormHolder = new JPanel();
        updateFormHolder.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        upateFormHeader.add(updateFormHolder, gbc);
        updateFormHeader = new JTextPane();
        updateFormHeader.setEditable(false);
        updateFormHeader.setFocusCycleRoot(false);
        updateFormHeader.setFocusable(false);
        updateFormHeader.setOpaque(false);
        updateFormHeader.setRequestFocusEnabled(false);
        updateFormHeader.setText("FrostyBytes University: Chill. Learn. Innovate.");
        updateFormHeader.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        updateFormHolder.add(updateFormHeader, gbc);
        updateFormEnrollmentForm = new JTextPane();
        updateFormEnrollmentForm.setEditable(false);
        updateFormEnrollmentForm.setFocusCycleRoot(false);
        updateFormEnrollmentForm.setFocusable(false);
        updateFormEnrollmentForm.setOpaque(false);
        updateFormEnrollmentForm.setRequestFocusEnabled(false);
        updateFormEnrollmentForm.setText("Enrollment Form");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormHolder.add(updateFormEnrollmentForm, gbc);
        updateFormDetails = new JPanel();
        updateFormDetails.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        updateHistory.add(updateFormDetails, gbc);
        updateFirstName = new JTextPane();
        updateFirstName.setAutoscrolls(false);
        updateFirstName.setEditable(false);
        updateFirstName.setFocusCycleRoot(false);
        updateFirstName.setFocusable(false);
        updateFirstName.setOpaque(false);
        updateFirstName.setRequestFocusEnabled(false);
        updateFirstName.setText("First Name: ");
        updateFirstName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateFirstName, gbc);
        updateFirstNameField = new JTextField();
        updateFirstNameField.setColumns(10);
        updateFirstNameField.setFocusCycleRoot(true);
        updateFirstNameField.setFocusTraversalPolicyProvider(true);
        updateFirstNameField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        updateFormDetails.add(updateFirstNameField, gbc);
        updateMiddleName = new JTextPane();
        updateMiddleName.setFocusable(false);
        updateMiddleName.setName("");
        updateMiddleName.setOpaque(false);
        updateMiddleName.setRequestFocusEnabled(false);
        updateMiddleName.setText("Middle Name: ");
        updateMiddleName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateMiddleName, gbc);
        updateMiddleNameTextField = new JTextField();
        updateMiddleNameTextField.setColumns(5);
        updateMiddleNameTextField.setFocusCycleRoot(true);
        updateMiddleNameTextField.setFocusTraversalPolicyProvider(true);
        updateMiddleNameTextField.setRequestFocusEnabled(true);
        updateMiddleNameTextField.setVerifyInputWhenFocusTarget(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(updateMiddleNameTextField, gbc);
        updateLastName = new JTextPane();
        updateLastName.setEditable(false);
        updateLastName.setFocusCycleRoot(false);
        updateLastName.setFocusable(false);
        updateLastName.setOpaque(false);
        updateLastName.setRequestFocusEnabled(false);
        updateLastName.setText("Last Name: ");
        updateLastName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateLastName, gbc);
        updateLastNameTextField = new JTextField();
        updateLastNameTextField.setColumns(10);
        updateLastNameTextField.setDoubleBuffered(true);
        updateLastNameTextField.setDragEnabled(true);
        updateLastNameTextField.setFocusCycleRoot(true);
        updateLastNameTextField.setFocusTraversalPolicyProvider(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(updateLastNameTextField, gbc);
        updateGender = new JTextArea();
        updateGender.setEditable(false);
        updateGender.setFocusable(false);
        updateGender.setOpaque(false);
        updateGender.setRequestFocusEnabled(false);
        updateGender.setText("Gender: ");
        updateGender.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateGender, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(spacer4, gbc);
        updateGenderComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(updateGenderComboBox, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(spacer7, gbc);
        updateProgramCode = new JTextPane();
        updateProgramCode.setAutoscrolls(false);
        updateProgramCode.setEditable(false);
        updateProgramCode.setFocusCycleRoot(false);
        updateProgramCode.setFocusable(false);
        updateProgramCode.setOpaque(false);
        updateProgramCode.setRequestFocusEnabled(false);
        updateProgramCode.setText("Program Code: ");
        updateProgramCode.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateProgramCode, gbc);
        updateProgramCodeCombo = new JComboBox();
        updateProgramCodeCombo.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(updateProgramCodeCombo, gbc);
        updateYearLevelCombo = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(updateYearLevelCombo, gbc);
        updateYearLevel = new JTextPane();
        updateYearLevel.setAutoscrolls(false);
        updateYearLevel.setEditable(false);
        updateYearLevel.setFocusCycleRoot(false);
        updateYearLevel.setFocusable(false);
        updateYearLevel.setOpaque(false);
        updateYearLevel.setRequestFocusEnabled(false);
        updateYearLevel.setText("Year Level: ");
        updateYearLevel.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateYearLevel, gbc);
        updateCollegeCode = new JTextPane();
        updateCollegeCode.setAutoscrolls(false);
        updateCollegeCode.setEditable(false);
        updateCollegeCode.setFocusCycleRoot(false);
        updateCollegeCode.setFocusable(false);
        updateCollegeCode.setOpaque(false);
        updateCollegeCode.setRequestFocusEnabled(false);
        updateCollegeCode.setText("College: ");
        updateCollegeCode.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormDetails.add(updateCollegeCode, gbc);
        updateCollegeCodeComboBox = new JComboBox();
        updateCollegeCodeComboBox.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormDetails.add(updateCollegeCodeComboBox, gbc);
        updateFormPrograms = new JPanel();
        updateFormPrograms.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        updateHistory.add(updateFormPrograms, gbc);
        updateFormHolder1 = new JPanel();
        updateFormHolder1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        updateFormPrograms.add(updateFormHolder1, gbc);
        updateFormHolder2 = new JPanel();
        updateFormHolder2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        updateFormHolder1.add(updateFormHolder2, gbc);
        insertUpdatedCollegeName = new JTextArea();
        insertUpdatedCollegeName.setAutoscrolls(false);
        insertUpdatedCollegeName.setEditable(false);
        insertUpdatedCollegeName.setFocusable(false);
        insertUpdatedCollegeName.setOpaque(false);
        insertUpdatedCollegeName.setRequestFocusEnabled(false);
        insertUpdatedCollegeName.setText("PLEASE SELECT COLLEGE");
        insertUpdatedCollegeName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 10, 0);
        updateFormHolder2.add(insertUpdatedCollegeName, gbc);
        updateFormHolder3 = new JPanel();
        updateFormHolder3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        updateFormHolder1.add(updateFormHolder3, gbc);
        insertUpdatedProgramName = new JTextArea();
        insertUpdatedProgramName.setAutoscrolls(false);
        insertUpdatedProgramName.setEditable(false);
        insertUpdatedProgramName.setFocusable(false);
        insertUpdatedProgramName.setOpaque(false);
        insertUpdatedProgramName.setRequestFocusEnabled(false);
        insertUpdatedProgramName.setText("PLEASE SELECT PROGRAM");
        insertUpdatedProgramName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 0);
        updateFormHolder3.add(insertUpdatedProgramName, gbc);
        updateFormHolder4 = new JPanel();
        updateFormHolder4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 0, 0);
        updateFormHolder1.add(updateFormHolder4, gbc);
        updateIDNumberTextPane = new JTextArea();
        updateIDNumberTextPane.setEditable(false);
        updateIDNumberTextPane.setFocusable(false);
        updateIDNumberTextPane.setOpaque(false);
        updateIDNumberTextPane.setRequestFocusEnabled(false);
        updateIDNumberTextPane.setText("ID Number: ");
        updateIDNumberTextPane.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 5, 5, 5);
        updateFormHolder4.add(updateIDNumberTextPane, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormHolder4.add(spacer8, gbc);
        updateIDNumberTextField = new JTextField();
        updateIDNumberTextField.setColumns(7);
        updateIDNumberTextField.setEditable(false);
        updateIDNumberTextField.setEnabled(false);
        updateIDNumberTextField.setHorizontalAlignment(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 5, 5);
        updateFormHolder4.add(updateIDNumberTextField, gbc);
        updateFormButtons = new JPanel();
        updateFormButtons.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        updateHistory.add(updateFormButtons, gbc);
        updateFormButton = new JButton();
        updateFormButton.setText("Update Credentials");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 3, 0);
        updateFormButtons.add(updateFormButton, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormButtons.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormButtons.add(spacer10, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        updateFormButtons.add(spacer11, gbc);
        deleteStudentButton = new JButton();
        deleteStudentButton.setText("Remove Student");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateFormButtons.add(deleteStudentButton, gbc);
        enrollmentForm = new JPanel();
        enrollmentForm.setLayout(new GridBagLayout());
        enrollmentForm.setFocusable(false);
        enrollmentForm.setOpaque(true);
        dashboardTabs.addTab("Enrollment Form", enrollmentForm);
        enrollmentFormHolder = new JPanel();
        enrollmentFormHolder.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        enrollmentForm.add(enrollmentFormHolder, gbc);
        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        enrollmentFormHolder.add(headerPanel, gbc);
        enrollmentFormHeader = new JTextPane();
        enrollmentFormHeader.setEditable(false);
        enrollmentFormHeader.setFocusCycleRoot(false);
        enrollmentFormHeader.setFocusable(false);
        enrollmentFormHeader.setOpaque(false);
        enrollmentFormHeader.setRequestFocusEnabled(false);
        enrollmentFormHeader.setText("FrostyBytes University: Chill. Learn. Innovate.");
        enrollmentFormHeader.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        headerPanel.add(enrollmentFormHeader, gbc);
        formTitle = new JTextPane();
        formTitle.setEditable(false);
        formTitle.setFocusCycleRoot(false);
        formTitle.setFocusable(false);
        formTitle.setOpaque(false);
        formTitle.setRequestFocusEnabled(false);
        formTitle.setText("Enrollment Form");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        headerPanel.add(formTitle, gbc);
        informationFormPanel = new JPanel();
        informationFormPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        enrollmentForm.add(informationFormPanel, gbc);
        firstNameField = new JTextPane();
        firstNameField.setAutoscrolls(false);
        firstNameField.setEditable(false);
        firstNameField.setFocusCycleRoot(false);
        firstNameField.setFocusable(false);
        firstNameField.setOpaque(false);
        firstNameField.setRequestFocusEnabled(false);
        firstNameField.setText("First Name: ");
        firstNameField.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(firstNameField, gbc);
        firstNameTextField = new JTextField();
        firstNameTextField.setColumns(10);
        firstNameTextField.setText("");
        firstNameTextField.setVerifyInputWhenFocusTarget(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        informationFormPanel.add(firstNameTextField, gbc);
        middleInitial = new JTextPane();
        middleInitial.setFocusable(false);
        middleInitial.setName("");
        middleInitial.setOpaque(false);
        middleInitial.setRequestFocusEnabled(false);
        middleInitial.setText("Middle Name: ");
        middleInitial.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(middleInitial, gbc);
        middleNameTextField = new JTextField();
        middleNameTextField.setColumns(5);
        middleNameTextField.setFocusCycleRoot(true);
        middleNameTextField.setFocusTraversalPolicyProvider(true);
        middleNameTextField.setRequestFocusEnabled(true);
        middleNameTextField.setVerifyInputWhenFocusTarget(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(middleNameTextField, gbc);
        lastNameField = new JTextPane();
        lastNameField.setEditable(false);
        lastNameField.setFocusCycleRoot(false);
        lastNameField.setFocusable(false);
        lastNameField.setOpaque(false);
        lastNameField.setRequestFocusEnabled(false);
        lastNameField.setText("Last Name: ");
        lastNameField.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(lastNameField, gbc);
        lastNameTextField = new JTextField();
        lastNameTextField.setAutoscrolls(true);
        lastNameTextField.setColumns(10);
        lastNameTextField.setFocusCycleRoot(true);
        lastNameTextField.setFocusTraversalPolicyProvider(true);
        lastNameTextField.setVerifyInputWhenFocusTarget(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(lastNameTextField, gbc);
        genderTextArea = new JTextArea();
        genderTextArea.setEditable(false);
        genderTextArea.setFocusable(false);
        genderTextArea.setOpaque(false);
        genderTextArea.setRequestFocusEnabled(false);
        genderTextArea.setText("Gender: ");
        genderTextArea.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(genderTextArea, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(spacer12, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(spacer13, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(spacer14, gbc);
        genderComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(genderComboBox, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(spacer16, gbc);
        final JPanel spacer17 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(spacer17, gbc);
        yearLevelComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(yearLevelComboBox, gbc);
        yearLevelField = new JTextPane();
        yearLevelField.setAutoscrolls(false);
        yearLevelField.setEditable(false);
        yearLevelField.setFocusCycleRoot(false);
        yearLevelField.setFocusable(false);
        yearLevelField.setOpaque(false);
        yearLevelField.setRequestFocusEnabled(false);
        yearLevelField.setText("Year Level: ");
        yearLevelField.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(yearLevelField, gbc);
        programCodeComboBox = new JComboBox();
        programCodeComboBox.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(programCodeComboBox, gbc);
        programCodeField = new JTextPane();
        programCodeField.setAutoscrolls(false);
        programCodeField.setEditable(false);
        programCodeField.setFocusCycleRoot(false);
        programCodeField.setFocusable(false);
        programCodeField.setOpaque(false);
        programCodeField.setRequestFocusEnabled(false);
        programCodeField.setText("Program Code: ");
        programCodeField.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(programCodeField, gbc);
        collegeCode = new JTextPane();
        collegeCode.setAutoscrolls(false);
        collegeCode.setEditable(false);
        collegeCode.setFocusCycleRoot(false);
        collegeCode.setFocusable(false);
        collegeCode.setOpaque(false);
        collegeCode.setRequestFocusEnabled(false);
        collegeCode.setText("College: ");
        collegeCode.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        informationFormPanel.add(collegeCode, gbc);
        collegeCodeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        collegeCodeComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informationFormPanel.add(collegeCodeComboBox, gbc);
        programDetailsPanel = new JPanel();
        programDetailsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        enrollmentForm.add(programDetailsPanel, gbc);
        programDetailsHandle = new JPanel();
        programDetailsHandle.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        programDetailsPanel.add(programDetailsHandle, gbc);
        programNamePanel = new JPanel();
        programNamePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        programDetailsHandle.add(programNamePanel, gbc);
        collegeName = new JTextArea();
        collegeName.setAutoscrolls(false);
        collegeName.setEditable(false);
        collegeName.setFocusable(false);
        collegeName.setOpaque(false);
        collegeName.setRequestFocusEnabled(false);
        collegeName.setText("PLEASE SELECT COLLEGE");
        collegeName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 10, 0);
        programNamePanel.add(collegeName, gbc);
        collegeNamePanel = new JPanel();
        collegeNamePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        programDetailsHandle.add(collegeNamePanel, gbc);
        programName = new JTextArea();
        programName.setAutoscrolls(false);
        programName.setEditable(false);
        programName.setFocusable(false);
        programName.setOpaque(false);
        programName.setRequestFocusEnabled(false);
        programName.setText("PLEASE SELECT PROGRAM");
        programName.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 0);
        collegeNamePanel.add(programName, gbc);
        idNumberPanel = new JPanel();
        idNumberPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 0, 0);
        programDetailsHandle.add(idNumberPanel, gbc);
        idNumberInfo = new JTextArea();
        idNumberInfo.setEditable(false);
        idNumberInfo.setFocusable(false);
        idNumberInfo.setOpaque(false);
        idNumberInfo.setRequestFocusEnabled(false);
        idNumberInfo.setText("ID Number: ");
        idNumberInfo.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 5, 5, 5);
        idNumberPanel.add(idNumberInfo, gbc);
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        idNumberPanel.add(spacer18, gbc);
        idNumberValue = new JTextField();
        idNumberValue.setColumns(7);
        idNumberValue.setHorizontalAlignment(10);
        idNumberValue.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 5, 5);
        idNumberPanel.add(idNumberValue, gbc);
        confirmButtonsPanel = new JPanel();
        confirmButtonsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        enrollmentForm.add(confirmButtonsPanel, gbc);
        confirmEnrollmentButton = new JButton();
        confirmEnrollmentButton.setText("Confirm Enrollment");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 3, 0);
        confirmButtonsPanel.add(confirmEnrollmentButton, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        confirmButtonsPanel.add(spacer19, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        confirmButtonsPanel.add(spacer20, gbc);
        final JPanel spacer21 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        confirmButtonsPanel.add(spacer21, gbc);
        accountManagement = new JPanel();
        accountManagement.setLayout(new GridBagLayout());
        accountManagement.setAlignmentY(0.5f);
        dashboardTabs.addTab("Manage Account", accountManagement);
        courseControlPanel = new JPanel();
        courseControlPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        accountManagement.add(courseControlPanel, gbc);
        registerNewCollege = new JButton();
        registerNewCollege.setText("Register College");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        courseControlPanel.add(registerNewCollege, gbc);
        proposeCourseButton = new JButton();
        proposeCourseButton.setText("Propose Program");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        courseControlPanel.add(proposeCourseButton, gbc);
        final JPanel spacer22 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        courseControlPanel.add(spacer22, gbc);
        final JPanel spacer23 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        courseControlPanel.add(spacer23, gbc);
        logoutPanel = new JPanel();
        logoutPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        accountManagement.add(logoutPanel, gbc);
        logOutButton = new JButton();
        logOutButton.setText("Log out");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 5, 10, 5);
        logoutPanel.add(logOutButton, gbc);
        invisiblePane = new JPanel();
        invisiblePane.setLayout(new GridBagLayout());
        invisiblePane.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 10, 15);
        accountManagement.add(invisiblePane, gbc);
        handlerPanel1 = new JPanel();
        handlerPanel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        invisiblePane.add(handlerPanel1, gbc);
        handlerField1 = new JTextField();
        handlerField1.setColumns(20);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(handlerField1, gbc);
        handlerPane2 = new JTextPane();
        handlerPane2.setEditable(false);
        handlerPane2.setOpaque(false);
        handlerPane2.setRequestFocusEnabled(false);
        handlerPane2.setText("College Code");
        handlerPane2.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(handlerPane2, gbc);
        handlerField2 = new JTextField();
        handlerField2.setColumns(20);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(handlerField2, gbc);
        collegePane = new JTextPane();
        collegePane.setEditable(false);
        collegePane.setOpaque(false);
        collegePane.setText("Select College for this Program");
        collegePane.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(collegePane, gbc);
        final JPanel spacer24 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        handlerPanel1.add(spacer24, gbc);
        final JPanel spacer25 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        handlerPanel1.add(spacer25, gbc);
        final JPanel spacer26 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        handlerPanel1.add(spacer26, gbc);
        handlerPane1 = new JTextPane();
        handlerPane1.setEditable(false);
        handlerPane1.setFocusCycleRoot(false);
        handlerPane1.setFocusable(false);
        handlerPane1.setOpaque(false);
        handlerPane1.setRequestFocusEnabled(false);
        handlerPane1.setText("College Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(handlerPane1, gbc);
        final JPanel spacer27 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        handlerPanel1.add(spacer27, gbc);
        selectedCollegeForCourse = new JTextPane();
        selectedCollegeForCourse.setEditable(false);
        selectedCollegeForCourse.setFocusCycleRoot(false);
        selectedCollegeForCourse.setFocusable(false);
        selectedCollegeForCourse.setOpaque(false);
        selectedCollegeForCourse.setRequestFocusEnabled(false);
        selectedCollegeForCourse.setText("PLEASE SELECT COLLEGE TO REGISTER PROGRAM");
        selectedCollegeForCourse.setVerifyInputWhenFocusTarget(false);
        selectedCollegeForCourse.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(selectedCollegeForCourse, gbc);
        collegeComboBox = new JComboBox();
        collegeComboBox.setAutoscrolls(true);
        collegeComboBox.setEditable(false);
        collegeComboBox.setLightWeightPopupEnabled(true);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        collegeComboBox.setModel(defaultComboBoxModel2);
        collegeComboBox.setVerifyInputWhenFocusTarget(false);
        collegeComboBox.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(collegeComboBox, gbc);
        updateToCollege = new JTextPane();
        updateToCollege.setEditable(false);
        updateToCollege.setOpaque(false);
        updateToCollege.setRequestFocusEnabled(false);
        updateToCollege.setText("Select College Code To Edit");
        updateToCollege.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(updateToCollege, gbc);
        updateField = new JTextField();
        updateField.setColumns(20);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel1.add(updateField, gbc);
        handlerPanel2 = new JPanel();
        handlerPanel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        invisiblePane.add(handlerPanel2, gbc);
        handlerPanel2Holder = new JPanel();
        handlerPanel2Holder.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        handlerPanel2.add(handlerPanel2Holder, gbc);
        registerHandlerButton = new JButton();
        registerHandlerButton.setText("Register College");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel2Holder.add(registerHandlerButton, gbc);
        updateHandlerButton = new JButton();
        updateHandlerButton.setText("Update College");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel2Holder.add(updateHandlerButton, gbc);
        deleteHandlerButton = new JButton();
        deleteHandlerButton.setText("Delete College");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel2Holder.add(deleteHandlerButton, gbc);
        closeFormHandler = new JButton();
        closeFormHandler.setText("Close Form");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 3, 5, 3);
        handlerPanel2Holder.add(closeFormHandler, gbc);
        handlerTablePanel = new JPanel();
        handlerTablePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 20, 15, 10);
        invisiblePane.add(handlerTablePanel, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        handlerTablePanel.add(panel3, gbc);
        searchHandler = new JTextField();
        searchHandler.setAlignmentX(0.0f);
        searchHandler.setAutoscrolls(false);
        searchHandler.setColumns(20);
        searchHandler.setFocusable(true);
        searchHandler.setHorizontalAlignment(10);
        searchHandler.setInheritsPopupMenu(false);
        searchHandler.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(searchHandler, gbc);
        searchHandlerPane = new JTextPane();
        searchHandlerPane.setEditable(false);
        searchHandlerPane.setFocusCycleRoot(false);
        searchHandlerPane.setOpaque(false);
        searchHandlerPane.setRequestFocusEnabled(false);
        searchHandlerPane.setText("Search");
        searchHandlerPane.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(searchHandlerPane, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        handlerTablePanel.add(panel4, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(scrollPane2, gbc);
        tableHandlerJTable = new JTable();
        tableHandlerJTable.setAutoCreateColumnsFromModel(true);
        tableHandlerJTable.setAutoCreateRowSorter(false);
        tableHandlerJTable.setAutoResizeMode(2);
        tableHandlerJTable.setAutoscrolls(false);
        tableHandlerJTable.setCellSelectionEnabled(false);
        tableHandlerJTable.setColumnSelectionAllowed(false);
        tableHandlerJTable.setDoubleBuffered(true);
        tableHandlerJTable.setEnabled(true);
        tableHandlerJTable.setFocusable(false);
        tableHandlerJTable.setOpaque(true);
        tableHandlerJTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
        tableHandlerJTable.setRequestFocusEnabled(false);
        tableHandlerJTable.setRowSelectionAllowed(false);
        tableHandlerJTable.setUpdateSelectionOnSort(false);
        tableHandlerJTable.setVerifyInputWhenFocusTarget(false);
        scrollPane2.setViewportView(tableHandlerJTable);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel5.setAlignmentX(0.5f);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        handlerTablePanel.add(panel5, gbc);
        handlerFound = new JTextPane();
        handlerFound.setEditable(false);
        handlerFound.setFocusCycleRoot(false);
        handlerFound.setFocusable(false);
        handlerFound.setOpaque(false);
        handlerFound.setRequestFocusEnabled(false);
        handlerFound.setText("Colleges Found: ");
        handlerFound.setVerifyInputWhenFocusTarget(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel5.add(handlerFound, gbc);
        final JPanel spacer28 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(spacer28, gbc);
        middleNameTextField.setNextFocusableComponent(lastNameTextField);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return adminPanelDashboard;
    }

}
