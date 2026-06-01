import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class EmployeeProfileViewer extends JFrame {

    // Employee data: ID, Name, Role, Department, (photo placeholder color)
    private Object[][] employees;
    private int currentIndex = 0;

    // Layers
    private JLayeredPane layeredPane;

    // Background layer
    private JLabel backgroundLabel;

    // Middle layer (glass pane style semi-transparent panel + table)
    private JPanel glassPanel;
    private JTable empTable;
    private DefaultTableModel tableModel;

    // Top layer (profile display)
    private JPanel profilePanel;
    private JLabel profilePhotoLabel;
    private JLabel profileNameLabel;
    private JLabel profileRoleLabel;

    // Navigation buttons
    private JButton prevBtn;
    private JButton nextBtn;

    // Employee photo colors (simulated profile images)
    private Color[] photoColors = {
            new Color(80, 130, 200),
            new Color(180, 80, 80),
            new Color(60, 160, 100),
            new Color(180, 120, 50),
            new Color(130, 70, 180)
    };

    public EmployeeProfileViewer(Object[][] empData) {
        this.employees = empData;

        // Apply Windows Look and Feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println("Windows L&F not available, using system default.");
            }
        }

        setTitle("Employee Profile Card Viewer");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildLayeredPane();
        buildNavigationBar();
        loadProfileAtIndex(0);
        selectTableRow(0);
    }

    // ---- Build layered pane ----
    private void buildLayeredPane() {
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1000, 580));

        int W = 1000, H = 580;

        // ---- LAYER 1: Background ----
        // Gradient background painted via custom JPanel
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 30, 60),
                        getWidth(), getHeight(), new Color(50, 80, 140));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setBounds(0, 0, W, H);
        layeredPane.add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        // ---- LAYER 2: Semi-transparent glass panel with employee table ----
        glassPanel = new JPanel(new BorderLayout(8, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                g2.setColor(new Color(15, 25, 50));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        glassPanel.setBounds(20, 20, 420, H - 40);
        glassPanel.setPreferredSize(new Dimension(420, H - 40));

        JLabel tableTitle = new JLabel("  Employee Directory");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableTitle.setForeground(new Color(180, 200, 255));
        tableTitle.setBorder(new EmptyBorder(0, 0, 6, 0));
        tableTitle.setOpaque(false);
        glassPanel.add(tableTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[] { "ID", "Name", "Role", "Department" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        empTable = new JTable(tableModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        empTable.setOpaque(false);
        empTable.setFillsViewportHeight(true);
        empTable.setRowHeight(30);
        empTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        empTable.setForeground(new Color(220, 235, 255));
        empTable.setSelectionBackground(new Color(60, 100, 200));
        empTable.setSelectionForeground(Color.WHITE);
        empTable.setShowGrid(false);
        empTable.setIntercellSpacing(new Dimension(0, 2));
        empTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        empTable.getTableHeader().setForeground(new Color(150, 180, 240));
        empTable.getTableHeader().setBackground(new Color(25, 40, 80));
        empTable.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(80, 110, 180)));
        empTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? new Color(20, 35, 70) : new Color(30, 50, 95));
                    c.setForeground(new Color(200, 220, 255));
                }
                return c;
            }
        });
        // Set component border and sizing via JComponent methods
        empTable.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

        // Load employee data into table
        for (Object[] row : employees) {
            tableModel.addRow(new Object[] { row[0], row[1], row[2], row[3] });
        }

        JScrollPane scrollPane = new JScrollPane(empTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 90, 160), 1, true));
        scrollPane.setPreferredSize(new Dimension(400, 440));
        glassPanel.add(scrollPane, BorderLayout.CENTER);

        // Table row click — cycle profile
        empTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && empTable.getSelectedRow() >= 0) {
                currentIndex = empTable.getSelectedRow();
                loadProfileAtIndex(currentIndex);
            }
        });

        layeredPane.add(glassPanel, JLayeredPane.PALETTE_LAYER);

        // ---- LAYER 3: Profile card (top layer) ----
        profilePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.92f));
                g2.setColor(new Color(10, 18, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        profilePanel.setOpaque(false);
        profilePanel.setBounds(460, 20, W - 480, H - 40);
        profilePanel.setBorder(BorderFactory.createLineBorder(new Color(70, 110, 190), 1, true));

        // Profile photo (simulated via colored JLabel with icon-like appearance)
        profilePhotoLabel = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        profilePhotoLabel.setOpaque(false);
        profilePhotoLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        profilePhotoLabel.setForeground(Color.WHITE);
        profilePhotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePhotoLabel.setVerticalAlignment(SwingConstants.CENTER);
        int photoSize = 140;
        int panelW = W - 480;
        profilePhotoLabel.setBounds((panelW - photoSize) / 2, 40, photoSize, photoSize);
        // JComponent methods for border and sizing
        profilePhotoLabel.setBorder(BorderFactory.createLineBorder(new Color(80, 130, 220), 3, true));
        profilePhotoLabel.setPreferredSize(new Dimension(photoSize, photoSize));
        profilePhotoLabel.setMinimumSize(new Dimension(photoSize, photoSize));
        profilePhotoLabel.setMaximumSize(new Dimension(photoSize, photoSize));
        profilePanel.add(profilePhotoLabel);

        // Name label (large)
        profileNameLabel = new JLabel("", SwingConstants.CENTER);
        profileNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        profileNameLabel.setForeground(new Color(200, 225, 255));
        profileNameLabel.setBounds(10, 200, panelW - 20, 36);
        profileNameLabel.setBorder(new EmptyBorder(0, 0, 4, 0));
        profilePanel.add(profileNameLabel);

        // Role label (smaller)
        profileRoleLabel = new JLabel("", SwingConstants.CENTER);
        profileRoleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        profileRoleLabel.setForeground(new Color(130, 170, 240));
        profileRoleLabel.setBounds(10, 245, panelW - 20, 26);
        profileRoleLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        profilePanel.add(profileRoleLabel);

        // Horizontal divider
        JSeparator divider = new JSeparator();
        divider.setForeground(new Color(60, 90, 160));
        divider.setBounds(30, 285, panelW - 60, 2);
        profilePanel.add(divider);

        layeredPane.add(profilePanel, JLayeredPane.MODAL_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    // ---- Navigation bar ----
    private void buildNavigationBar() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navPanel.setBackground(new Color(15, 25, 55));
        navPanel.setBorder(new MatteBorder(1, 0, 0, 0, new Color(50, 80, 140)));

        // Previous button with icon (simulated with Unicode arrow), mnemonic, tooltip
        prevBtn = new JButton("\u25C4  Previous");
        prevBtn.setMnemonic(KeyEvent.VK_P);
        prevBtn.setToolTipText("Go to previous employee (Alt+P)");
        styleNavButton(prevBtn);
        prevBtn.addActionListener(e -> navigateTo(currentIndex - 1));

        // Next button
        nextBtn = new JButton("Next  \u25BA");
        nextBtn.setMnemonic(KeyEvent.VK_N);
        nextBtn.setToolTipText("Go to next employee (Alt+N)");
        styleNavButton(nextBtn);
        nextBtn.addActionListener(e -> navigateTo(currentIndex + 1));

        navPanel.add(prevBtn);
        navPanel.add(nextBtn);
        add(navPanel, BorderLayout.SOUTH);
    }

    private void styleNavButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(50, 90, 180));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 120, 210), 1, true),
                new EmptyBorder(10, 24, 10, 24)));
        btn.setPreferredSize(new Dimension(160, 44));
        btn.setMinimumSize(new Dimension(160, 44));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(70, 120, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(50, 90, 180));
            }
        });
    }

    private void navigateTo(int index) {
        if (employees.length == 0)
            return;
        currentIndex = (index + employees.length) % employees.length;
        loadProfileAtIndex(currentIndex);
        selectTableRow(currentIndex);
    }

    private void selectTableRow(int index) {
        if (index >= 0 && index < employees.length) {
            empTable.setRowSelectionInterval(index, index);
            empTable.scrollRectToVisible(empTable.getCellRect(index, 0, true));
        }
    }

    private void loadProfileAtIndex(int index) {
        if (index < 0 || index >= employees.length)
            return;
        Object[] emp = employees[index];

        // Get initials for photo
        String name = (String) emp[1];
        String role = (String) emp[2];
        String dept = (String) emp[3];
        String initial = name.isEmpty() ? "?" : String.valueOf(name.charAt(0)).toUpperCase();

        // Set photo color and initial
        Color photoColor = photoColors[index % photoColors.length];
        profilePhotoLabel.setBackground(photoColor);
        profilePhotoLabel.setText(initial);

        profileNameLabel.setText(name);
        profileRoleLabel.setText(role + "  |  " + dept);

        profilePanel.repaint();
    }

    public static void main(String[] args) {
        // Take employee data from user
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Employee Profile Card Viewer ===");
        System.out.print("How many employees do you want to enter? ");
        int n = Integer.parseInt(sc.nextLine().trim());

        Object[][] employees = new Object[n][4];
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Employee " + (i + 1) + " ---");
            System.out.print("Employee ID: ");
            employees[i][0] = sc.nextLine().trim();
            System.out.print("Name: ");
            employees[i][1] = sc.nextLine().trim();
            System.out.print("Role: ");
            employees[i][2] = sc.nextLine().trim();
            System.out.print("Department: ");
            employees[i][3] = sc.nextLine().trim();
        }
        sc.close();

        final Object[][] finalEmp = employees;
        SwingUtilities.invokeLater(() -> {
            EmployeeProfileViewer viewer = new EmployeeProfileViewer(finalEmp);
            viewer.setVisible(true);
        });
    }
}
