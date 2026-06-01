import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

// ===================== MODEL =====================
class StudentMarksModel {
    private String[] columnNames = { "Roll No", "Name", "Math", "Physics", "Chemistry", "English", "CS" };
    private Object[][] data;

    public StudentMarksModel(Object[][] data) {
        this.data = data;
    }

    public String[] getColumnNames() { return columnNames; }
    public Object[][] getData()      { return data; }

    public String getGrade(int row) {
        double avg = getAverage(row);
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    public double getAverage(int row) {
        int total = 0;
        for (int col = 2; col <= 6; col++) {
            total += (int) data[row][col];
        }
        return (double) total / 5;
    }
}

// ===================== VIEW =====================
class StudentMarksView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField filterField;
    private JSplitPane splitPane;

    // Right panel components
    private JLabel studentNameLabel;
    private JLabel gradeImageLabel;
    private JLabel gradeTextLabel;
    private JLabel avgLabel;
    private JLabel[] subjectLabels;
    private JButton exportBtn;

    private final String[] subjects = { "Math", "Physics", "Chemistry", "English", "CS" };

    public StudentMarksView() {
        setTitle("Student Marks Dashboard");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUI();
    }

    private void buildUI() {
        // ---- LEFT PANEL ----
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(new TitledBorder("Student Records"));

        JPanel filterPanel = new JPanel(new BorderLayout(5, 5));
        filterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        filterPanel.add(new JLabel("Search by Name: "), BorderLayout.WEST);
        filterField = new JTextField();
        filterPanel.add(filterField, BorderLayout.CENTER);
        leftPanel.add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                null,
                new String[]{ "Roll No", "Name", "Math", "Physics", "Chemistry", "English", "CS" }) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                return (c >= 2) ? Integer.class : String.class;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // prepareRenderer for alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, sel, focus, row, col);
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 240, 255));
                    c.setForeground(Color.DARK_GRAY);
                }
                return c;
            }
        });
        table.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, sel, focus, row, col);
                setHorizontalAlignment(CENTER);
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 240, 255));
                    int marks = (value != null) ? (int) value : 0;
                    c.setForeground(marks < 50 ? Color.RED : new Color(0, 120, 0));
                }
                return c;
            }
        });

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        leftPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- RIGHT PANEL ----
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("Student Details"));
        rightPanel.setBackground(new Color(245, 248, 255));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(245, 248, 255));
        detailsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        studentNameLabel = new JLabel("Select a student");
        studentNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        studentNameLabel.setForeground(new Color(30, 60, 120));
        studentNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentNameLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Grade badge using ImageIcon approach via colored JLabel (grade image)
        gradeImageLabel = new JLabel("?", SwingConstants.CENTER);
        gradeImageLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        gradeImageLabel.setOpaque(true);
        gradeImageLabel.setBackground(new Color(200, 215, 240));
        gradeImageLabel.setForeground(new Color(30, 60, 120));
        gradeImageLabel.setBorder(BorderFactory.createLineBorder(new Color(100, 140, 200), 3, true));
        gradeImageLabel.setPreferredSize(new Dimension(100, 100));
        gradeImageLabel.setMaximumSize(new Dimension(100, 100));
        gradeImageLabel.setMinimumSize(new Dimension(100, 100));
        gradeImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gradeTextLabel = new JLabel("Grade: —", SwingConstants.CENTER);
        gradeTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gradeTextLabel.setForeground(new Color(60, 100, 180));
        gradeTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gradeTextLabel.setBorder(new EmptyBorder(6, 0, 6, 0));

        avgLabel = new JLabel("Average: —", SwingConstants.CENTER);
        avgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        avgLabel.setForeground(Color.DARK_GRAY);
        avgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        avgLabel.setBorder(new EmptyBorder(0, 0, 12, 0));

        detailsPanel.add(studentNameLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        detailsPanel.add(gradeImageLabel);
        detailsPanel.add(gradeTextLabel);
        detailsPanel.add(avgLabel);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(180, 200, 230));
        detailsPanel.add(sep);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        subjectLabels = new JLabel[5];
        for (int i = 0; i < subjects.length; i++) {
            subjectLabels[i] = new JLabel(subjects[i] + ": —");
            subjectLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            subjectLabels[i].setForeground(Color.DARK_GRAY);
            subjectLabels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            subjectLabels[i].setBorder(new CompoundBorder(
                new EmptyBorder(4, 0, 4, 0),
                new MatteBorder(0, 0, 1, 0, new Color(220, 230, 245))
            ));
            detailsPanel.add(subjectLabels[i]);
        }

        detailsPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Export button with mnemonic (Alt+E) and tooltip
        exportBtn = new JButton("Export Report");
        exportBtn.setMnemonic(KeyEvent.VK_E);
        exportBtn.setToolTipText("Export the selected student's report (Alt+E)");
        exportBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exportBtn.setBackground(new Color(60, 120, 200));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setOpaque(true);
        exportBtn.setFocusPainted(false);
        exportBtn.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 90, 170), 1, true),
            new EmptyBorder(8, 18, 8, 18)
        ));
        exportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportBtn.setEnabled(false);
        detailsPanel.add(exportBtn);

        rightPanel.add(detailsPanel, BorderLayout.CENTER);

        // ---- SPLIT PANE ----
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(720);
        splitPane.setResizeWeight(1.0);
        splitPane.setDividerSize(6);
        add(splitPane, BorderLayout.CENTER);
    }

    // Getters for controller
    public JTable getTable()                          { return table; }
    public DefaultTableModel getTableModel()          { return tableModel; }
    public TableRowSorter<DefaultTableModel> getSorter() { return sorter; }
    public JTextField getFilterField()                { return filterField; }
    public JLabel getStudentNameLabel()               { return studentNameLabel; }
    public JLabel getGradeImageLabel()                { return gradeImageLabel; }
    public JLabel getGradeTextLabel()                 { return gradeTextLabel; }
    public JLabel getAvgLabel()                       { return avgLabel; }
    public JLabel[] getSubjectLabels()                { return subjectLabels; }
    public JButton getExportBtn()                     { return exportBtn; }
}

// ===================== CONTROLLER =====================
class StudentMarksController {
    private StudentMarksModel model;
    private StudentMarksView view;

    private static final String[] GRADES_ORDER = { "A+", "A", "B", "C", "D", "F" };
    private static final Color[]  GRADE_COLORS  = {
        new Color(0,  180, 120),   // A+
        new Color(50, 160, 220),   // A
        new Color(100,180, 80),    // B
        new Color(220,180, 0),     // C
        new Color(230,120, 0),     // D
        new Color(210, 50, 50)     // F
    };

    public StudentMarksController(StudentMarksModel model, StudentMarksView view) {
        this.model = model;
        this.view  = view;
        loadTableData();
        attachListeners();
    }

    private void loadTableData() {
        DefaultTableModel tm = view.getTableModel();
        tm.setRowCount(0);
        for (Object[] row : model.getData()) {
            tm.addRow(row);
        }
    }

    private void attachListeners() {
        // Real-time filter on Name column
        view.getFilterField().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        // Row selection
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = view.getTable().getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = view.getTable().convertRowIndexToModel(viewRow);
                    updateDetailPanel(modelRow);
                }
            }
        });

        // Export button — shows JOptionPane confirmation dialog
        view.getExportBtn().addActionListener(e -> {
            int viewRow = view.getTable().getSelectedRow();
            if (viewRow < 0) return;
            int modelRow = view.getTable().convertRowIndexToModel(viewRow);
            String name  = (String) model.getData()[modelRow][1];
            String grade = model.getGrade(modelRow);
            double avg   = model.getAverage(modelRow);

            int confirm = JOptionPane.showConfirmDialog(
                view,
                "Export report for " + name + "?\n\n" +
                "Grade  : " + grade + "\n" +
                "Average: " + String.format("%.1f", avg) + " / 100",
                "Confirm Export",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(view,
                    "Report for " + name + " exported successfully!",
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void applyFilter() {
        String text = view.getFilterField().getText().trim();
        if (text.isEmpty()) {
            view.getSorter().setRowFilter(null);
        } else {
            view.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
        }
    }

    private void updateDetailPanel(int modelRow) {
        Object[] rowData = model.getData()[modelRow];
        String   name    = (String) rowData[1];
        String   grade   = model.getGrade(modelRow);
        double   avg     = model.getAverage(modelRow);
        String[] subjects = { "Math", "Physics", "Chemistry", "English", "CS" };

        view.getStudentNameLabel().setText(name);
        view.getGradeTextLabel().setText("Grade: " + grade);
        view.getAvgLabel().setText(String.format("Average: %.1f / 100", avg));

        Color badgeColor = GRADE_COLORS[GRADE_COLORS.length - 1];
        for (int i = 0; i < GRADES_ORDER.length; i++) {
            if (GRADES_ORDER[i].equals(grade)) { badgeColor = GRADE_COLORS[i]; break; }
        }
        view.getGradeImageLabel().setText(grade);
        view.getGradeImageLabel().setBackground(badgeColor);
        view.getGradeImageLabel().setForeground(Color.WHITE);

        JLabel[] subLabels = view.getSubjectLabels();
        for (int i = 0; i < subjects.length; i++) {
            int marks = (int) rowData[i + 2];
            subLabels[i].setText(subjects[i] + ":  " + marks + " / 100  " + (marks < 50 ? "✘" : "✔"));
            subLabels[i].setForeground(marks < 50 ? new Color(200, 0, 0) : new Color(0, 120, 0));
        }
        view.getExportBtn().setEnabled(true);
    }
}

// ===================== MAIN =====================
public class StudentMarksDashboard {
    public static void main(String[] args) {
        // Take input from user
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Student Marks Dashboard ===");
        System.out.print("How many students do you want to enter? ");
        int n = Integer.parseInt(sc.nextLine().trim());

        Object[][] data = new Object[n][7];
        String[] subjects = { "Math", "Physics", "Chemistry", "English", "CS" };

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Student " + (i + 1) + " ---");
            System.out.print("Roll No: ");
            data[i][0] = sc.nextLine().trim();
            System.out.print("Name: ");
            data[i][1] = sc.nextLine().trim();
            for (int j = 0; j < 5; j++) {
                System.out.print(subjects[j] + " marks (0-100): ");
                data[i][j + 2] = Integer.parseInt(sc.nextLine().trim());
            }
        }
        sc.close();

        // Apply Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus L&F not available, using default.");
        }

        final Object[][] finalData = data;
        SwingUtilities.invokeLater(() -> {
            StudentMarksModel      model = new StudentMarksModel(finalData);
            StudentMarksView       view  = new StudentMarksView();
            new StudentMarksController(model, view);
            view.setVisible(true);
        });
    }
}
