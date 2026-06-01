import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;

public class StudentDatabaseApp extends JFrame {
    private Connection connection;
    private JTabbedPane tabbedPane;

    public StudentDatabaseApp() {
        setTitle("Student Database System (XAMPP Edition)");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("1. Connection", createConnectionForm());
        tabbedPane.addTab("2. Register (INSERT)", createRegistrationForm());
        tabbedPane.addTab("3. View (SELECT)", createViewerForm());
        tabbedPane.addTab("4. Update (UPDATE)", createUpdateForm());
        tabbedPane.addTab("5. Delete (DELETE)", createDeleteForm());
        add(tabbedPane);
    }

    private JPanel createConnectionForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        JTextField hostField = new JTextField("localhost", 15);
        JTextField portField = new JTextField("3306", 5);
        JTextField dbNameField = new JTextField("students_db", 15);
        JTextField userField = new JTextField("root", 15);
        JPasswordField passField = new JPasswordField("", 15); // XAMPP default is a blank password
        JTextField urlField = new JTextField();
        urlField.setEditable(false);
        urlField.setBackground(new Color(240, 240, 240));
        JLabel statusLabel = new JLabel("Status: Disconnected");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(Color.RED);
        Runnable updateUrl = () -> {
            String host = hostField.getText().trim();
            String port = portField.getText().trim();
            String db = dbNameField.getText().trim();
            String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
            urlField.setText(url);
        };
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateUrl.run();
            }

            public void removeUpdate(DocumentEvent e) {
                updateUrl.run();
            }

            public void changedUpdate(DocumentEvent e) {
                updateUrl.run();
            }
        };
        hostField.getDocument().addDocumentListener(docListener);
        portField.getDocument().addDocumentListener(docListener);
        dbNameField.getDocument().addDocumentListener(docListener);
        updateUrl.run();
        JButton connectBtn = new JButton("Connect to XAMPP MySQL");
        connectBtn.addActionListener(e -> {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
                String url = urlField.getText();
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                connection = DriverManager.getConnection(url, user, pass);
                statusLabel.setText("Status: Connected Successfully!");
                statusLabel.setForeground(new Color(0, 150, 0));
                String createTableSql = "CREATE TABLE IF NOT EXISTS `students` (" +
                        "`id` INT NOT NULL AUTO_INCREMENT, " +
                        "`name` VARCHAR(100), " +
                        "`email` VARCHAR(100), " +
                        "`course` VARCHAR(50), " +
                        "`year` VARCHAR(20), " +
                        "`gender` VARCHAR(10), " +
                        "`pin` VARCHAR(50), " +
                        "PRIMARY KEY (`id`))";
                Statement stmt = connection.createStatement();
                stmt.execute(createTableSql);
                stmt.close();
            } catch (Exception ex) {
                statusLabel.setText("Status: Connection Failed!");
                statusLabel.setForeground(Color.RED);
                JOptionPane.showMessageDialog(panel, "MySQL Error:\n" + ex.getMessage(), "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        int row = 0;
        panel.add(new JLabel("Host:"), getGbc(gbc, 0, row));
        panel.add(hostField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Port:"), getGbc(gbc, 0, row));
        panel.add(portField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Database Name:"), getGbc(gbc, 0, row));
        panel.add(dbNameField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("MySQL Username:"), getGbc(gbc, 0, row));
        panel.add(userField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("MySQL Password:"), getGbc(gbc, 0, row));
        panel.add(passField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("JDBC URL:"), getGbc(gbc, 0, row));
        panel.add(urlField, getGbc(gbc, 1, row++));
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(connectBtn, getGbc(gbc, 1, row++));
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(statusLabel, getGbc(gbc, 1, row));
        return panel;
    }

    private JPanel createRegistrationForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Register New Student"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JComboBox<String> courseCombo = new JComboBox<>(
                new String[] { "Computer Science", "Software Engineering", "Data Science", "Information Technology" });
        JComboBox<String> yearCombo = new JComboBox<>(new String[] { "1st Year", "2nd Year", "3rd Year", "4th Year" });
        JRadioButton maleRadio = new JRadioButton("Male");
        JRadioButton femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        maleRadio.setSelected(true);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        JPasswordField pinField = new JPasswordField(20);
        JLabel idLabel = new JLabel("Generated ID: Pending...");
        idLabel.setForeground(Color.BLUE);
        JButton registerBtn = new JButton("Register Student");
        JButton resetBtn = new JButton("Clear Form");
        registerBtn.addActionListener(e -> {
            if (!checkConnection())
                return;
            try {
                String sql = "INSERT INTO `students` (`name`, `email`, `course`, `year`, `gender`, `pin`) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, nameField.getText().trim());
                pstmt.setString(2, emailField.getText().trim());
                pstmt.setString(3, (String) courseCombo.getSelectedItem());
                pstmt.setString(4, (String) yearCombo.getSelectedItem());
                pstmt.setString(5, maleRadio.isSelected() ? "Male" : "Female");
                pstmt.setString(6, new String(pinField.getPassword()));
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    idLabel.setText("Generated ID: " + rs.getInt(1));
                    JOptionPane.showMessageDialog(panel, "Student Registered Successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    resetBtn.doClick();
                }
                pstmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Database Error:\n" + ex.getMessage(), "Insert Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        resetBtn.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            courseCombo.setSelectedIndex(0);
            yearCombo.setSelectedIndex(0);
            maleRadio.setSelected(true);
            pinField.setText("");
        });
        int row = 0;
        panel.add(new JLabel("Full Name:"), getGbc(gbc, 0, row));
        panel.add(nameField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Email Address:"), getGbc(gbc, 0, row));
        panel.add(emailField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Course:"), getGbc(gbc, 0, row));
        panel.add(courseCombo, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Year Level:"), getGbc(gbc, 0, row));
        panel.add(yearCombo, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Gender:"), getGbc(gbc, 0, row));
        panel.add(genderPanel, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Security PIN:"), getGbc(gbc, 0, row));
        panel.add(pinField, getGbc(gbc, 1, row++));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.add(registerBtn);
        btnPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        btnPanel.add(resetBtn);
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnPanel, getGbc(gbc, 1, row++));
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(idLabel, getGbc(gbc, 1, row));
        return panel;
    }

    private JPanel createViewerForm() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        JTextField searchField = new JTextField();
        JButton refreshBtn = new JButton("Refresh Data");
        topPanel.add(new JLabel("Real-time Search (by Name): "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(refreshBtn, BorderLayout.EAST);
        DefaultTableModel model = new DefaultTableModel(
                new String[] { "ID", "Name", "Email", "Course", "Year", "Gender" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void search() {
                String text = searchField.getText();
                if (text.trim().length() == 0)
                    sorter.setRowFilter(null);
                else
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
            }

            public void insertUpdate(DocumentEvent e) {
                search();
            }

            public void removeUpdate(DocumentEvent e) {
                search();
            }

            public void changedUpdate(DocumentEvent e) {
                search();
            }
        });
        JLabel countLabel = new JLabel("Total Records: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 12));
        Runnable loadData = () -> {
            if (!checkConnection())
                return;
            try {
                model.setRowCount(0);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `students` ORDER BY `id` DESC");
                int count = 0;
                while (rs.next()) {
                    model.addRow(new Object[] {
                            rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                            rs.getString("course"), rs.getString("year"), rs.getString("gender")
                    });
                    count++;
                }
                countLabel.setText("Total Records: " + count);
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Failed to load data:\n" + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        };
        refreshBtn.addActionListener(e -> loadData.run());
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 2)
                loadData.run();
        });
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(countLabel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createUpdateForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Search and Update Student Record"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        JTextField idSearchField = new JTextField(10);
        JButton fetchBtn = new JButton("Fetch Record");
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField courseField = new JTextField(20);
        JButton updateBtn = new JButton("Save Changes");
        updateBtn.setEnabled(false);

        fetchBtn.addActionListener(e -> {
            if (!checkConnection())
                return;
            String searchId = idSearchField.getText().trim();
            if (searchId.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid Student ID.", "Input Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String sql = "SELECT `name`, `email`, `course` FROM `students` WHERE `id` = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(searchId));
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nameField.setText(rs.getString("name"));
                    emailField.setText(rs.getString("email"));
                    courseField.setText(rs.getString("course"));
                    updateBtn.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(panel, "No student found with ID: " + searchId, "Not Found",
                            JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    emailField.setText("");
                    courseField.setText("");
                    updateBtn.setEnabled(false);
                }
                rs.close();
                pstmt.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Student ID must be a number.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Database Error:\n" + ex.getMessage(), "Fetch Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        updateBtn.addActionListener(e -> {
            if (!checkConnection())
                return;
            try {
                String sql = "UPDATE `students` SET `name` = ?, `email` = ?, `course` = ? WHERE `id` = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, nameField.getText().trim());
                pstmt.setString(2, emailField.getText().trim());
                pstmt.setString(3, courseField.getText().trim());
                pstmt.setInt(4, Integer.parseInt(idSearchField.getText().trim()));

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(panel, "Student record updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to update record. It may have been deleted.",
                            "Update Failed", JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Database Error:\n" + ex.getMessage(), "Update Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        int row = 0;
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.add(idSearchField);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(fetchBtn);

        panel.add(new JLabel("Search by Student ID:"), getGbc(gbc, 0, row));
        panel.add(searchPanel, getGbc(gbc, 1, row++));

        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(new JLabel("Edit Name:"), getGbc(gbc, 0, row));
        panel.add(nameField, getGbc(gbc, 1, row++));

        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Edit Email:"), getGbc(gbc, 0, row));
        panel.add(emailField, getGbc(gbc, 1, row++));
        panel.add(new JLabel("Edit Course:"), getGbc(gbc, 0, row));
        panel.add(courseField, getGbc(gbc, 1, row++));

        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(updateBtn, getGbc(gbc, 1, row));

        return panel;
    }

    private JPanel createDeleteForm() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "Name", "Email", "Course" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel countLabel = new JLabel("Total Records: 0");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh Table");
        JButton deleteBtn = new JButton("Delete Selected Student");
        deleteBtn.setForeground(Color.RED);
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 12));
        btnPanel.add(refreshBtn);
        btnPanel.add(deleteBtn);
        bottomPanel.add(countLabel, BorderLayout.WEST);
        bottomPanel.add(btnPanel, BorderLayout.EAST);
        Runnable loadData = () -> {
            if (!checkConnection())
                return;
            try {
                model.setRowCount(0);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt
                        .executeQuery("SELECT `id`, `name`, `email`, `course` FROM `students` ORDER BY `id` ASC");
                int count = 0;
                while (rs.next()) {
                    model.addRow(new Object[] { rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                            rs.getString("course") });
                    count++;
                }
                countLabel.setText("Total Records: " + count);
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Failed to load data:\n" + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        };
        refreshBtn.addActionListener(e -> loadData.run());
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 4)
                loadData.run();
        });
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "Please select a student from the table to delete.",
                        "No Row Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = (String) model.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Are you absolutely sure you want to delete the record for:\n" + name + " (ID: " + id
                            + ")?\nThis action cannot be undone.",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String sql = "DELETE FROM `students` WHERE `id` = ?";
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    pstmt.close();
                    model.removeRow(selectedRow);
                    countLabel.setText("Total Records: " + model.getRowCount());
                    JOptionPane.showMessageDialog(panel, "Record deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Database Error:\n" + ex.getMessage(), "Delete Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    private boolean checkConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(this, "Please connect to the database in Tab 1 first.", "Not Connected",
                        JOptionPane.WARNING_MESSAGE);
                tabbedPane.setSelectedIndex(0);
                return false;
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private GridBagConstraints getGbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new StudentDatabaseApp().setVisible(true));
    }
}