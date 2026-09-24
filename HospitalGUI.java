import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

 class HospitalGUI extends JFrame {

    private final Hospital hospital = new Hospital();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private Doctor currentDoctor;
    private Patient currentPatient;

    private static final Color TEXT_DARK = new Color(30, 55, 85);
    private static final Color TEXT_MUTED = new Color(90, 110, 130);
    private static final Color HEADER_BG = new Color(224, 238, 250);
    private static final Color BG = new Color(247, 250, 252);
    private static final Color CARD_BORDER = new Color(224, 230, 236);
    private static final Color PRIMARY = new Color(41, 128, 185);

    private static final Font FONT_H1 = new Font("SansSerif", Font.BOLD, 24);
    private static final Font FONT_H2 = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font FONT_ICON = new Font("SansSerif", Font.PLAIN, 30);

    public HospitalGUI() {
        setTitle("Hospital Patient Report Management System");
        setSize(920, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        seedDemoData();

        mainPanel.setBackground(BG);
        mainPanel.add(buildHomePanel(), "home");
        mainPanel.add(buildDoctorLoginPanel(), "doctorLogin");
        mainPanel.add(buildPatientLoginPanel(), "patientLogin");
        add(mainPanel);
        cardLayout.show(mainPanel, "home");
    }

    private void seedDemoData() {
        Doctor d = new Doctor("Dr. Rahim", 40, "Male", "01712-345678", "Dhaka",
                "DOC101", "Dr. Rahim", "Cardiology");
        hospital.addDoctor(d);

        String[][] demo = {
                {"P001", "Rahim Ahmed", "28", "Male"},
                {"P002", "Ayesha Khan", "32", "Female"},
                {"P003", "Samiul Islam", "45", "Male"}
        };
        for (String[] row : demo) {
            Patient p = new Patient(row[1], Integer.parseInt(row[2]), row[3], "01800000000", "Chattogram",
                    row[0], "pass123", "B+", "2026-08-24");
            hospital.addPatient(p);
        }
    }

    // ---------- reusable styled pieces ----------

    private JPanel headerBar(String title, String subtitle, String rightLine1, String rightLine2) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(new EmptyBorder(18, 26, 18, 26));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(FONT_H1);
        titleLbl.setForeground(TEXT_DARK);
        JLabel subLbl = new JLabel(subtitle);
        subLbl.setFont(FONT_LABEL);
        subLbl.setForeground(TEXT_MUTED);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(titleLbl);
        left.add(Box.createVerticalStrut(4));
        left.add(subLbl);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        if (rightLine1 != null) {
            JLabel r1 = new JLabel(rightLine1);
            r1.setFont(FONT_LABEL);
            r1.setForeground(TEXT_DARK);
            r1.setAlignmentX(Component.RIGHT_ALIGNMENT);
            right.add(r1);
        }
        if (rightLine2 != null) {
            JLabel r2 = new JLabel(rightLine2);
            r2.setFont(FONT_LABEL);
            r2.setForeground(TEXT_DARK);
            r2.setAlignmentX(Component.RIGHT_ALIGNMENT);
            right.add(r2);
        }

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    /** রঙিন, ক্লিকযোগ্য অ্যাকশন কার্ড — মকআপের মতো icon + label */
    /** এই মেথডটা চালানোর সময় System.out-এ যা প্রিন্ট হয়, সেটা ধরে রেখে GUI dialog-এ দেখানোর জন্য */
    private String captureConsole(Runnable action) {
        java.io.PrintStream original = System.out;
        java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(buffer));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        String text = buffer.toString();
        return text.isEmpty() ? "(কোনো তথ্য পাওয়া যায়নি)" : text;
    }

    private void showConsoleResult(String title, String content) {
        JTextArea area = new JTextArea(content);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(480, 200));
        JOptionPane.showMessageDialog(this, scroll, title, JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel actionCard(String icon, String label, Color bg, Runnable onClick) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1, true),
                new EmptyBorder(18, 10, 18, 10)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(180, 110));
        card.setMinimumSize(new Dimension(150, 100));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(FONT_ICON);
        iconLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(FONT_H2);
        textLbl.setForeground(TEXT_DARK);
        textLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(iconLbl);
        card.add(Box.createVerticalStrut(8));
        card.add(textLbl);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { onClick.run(); }
        });
        return card;
    }

    private JPanel infoPanel(String title, String[][] rows) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(0, 0, 16, 0)));

        JLabel titleLbl = new JLabel("  " + title);
        titleLbl.setFont(FONT_H2);
        titleLbl.setForeground(TEXT_DARK);
        titleLbl.setOpaque(true);
        titleLbl.setBackground(HEADER_BG);
        titleLbl.setBorder(new EmptyBorder(12, 14, 12, 14));
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        panel.add(titleLbl);

        for (String[] row : rows) {
            JPanel line = new JPanel(new BorderLayout());
            line.setOpaque(false);
            line.setBorder(new EmptyBorder(6, 16, 6, 16));
            line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            line.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel k = new JLabel(row[0]);
            k.setFont(FONT_LABEL);
            k.setForeground(PRIMARY);
            k.setPreferredSize(new Dimension(110, 20));
            JLabel v = new JLabel(": " + row[1]);
            v.setFont(FONT_LABEL);
            v.setForeground(TEXT_DARK);
            line.add(k, BorderLayout.WEST);
            line.add(v, BorderLayout.CENTER);
            line.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(line);
        }
        panel.add(Box.createVerticalGlue());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40 + rows.length * 34));
        return panel;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(FONT_LABEL);
        table.getTableHeader().setFont(FONT_H2.deriveFont(13f));
        table.getTableHeader().setBackground(HEADER_BG);
        table.getTableHeader().setForeground(TEXT_DARK);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(235, 244, 251));
        return table;
    }

    private JPanel tableCard(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1, true));

        JLabel titleLbl = new JLabel("  " + title);
        titleLbl.setFont(FONT_H2);
        titleLbl.setForeground(TEXT_DARK);
        titleLbl.setOpaque(true);
        titleLbl.setBackground(HEADER_BG);
        titleLbl.setBorder(new EmptyBorder(12, 14, 12, 14));

        panel.add(titleLbl, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JButton simpleButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_H2.deriveFont(14f));
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 18, 10, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JTextField niceField(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(FONT_LABEL);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                new EmptyBorder(6, 8, 6, 8)));
        return f;
    }

    private JPasswordField nicePasswordField(int cols) {
        JPasswordField f = new JPasswordField(cols);
        f.setFont(FONT_LABEL);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                new EmptyBorder(6, 8, 6, 8)));
        return f;
    }

    private JPanel centeredWrapper(JComponent content) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);
        wrapper.add(content);
        return wrapper;
    }

    private JPanel card(JComponent... children) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(24, 28, 24, 28)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        for (int i = 0; i < children.length; i++) {
            gbc.gridy = i;
            card.add(children[i], gbc);
        }
        return card;
    }

    // ================= HOME =================
    private JPanel buildHomePanel() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BG);
        page.add(headerBar("Hospital Report System", "Please choose your role", null, null), BorderLayout.NORTH);

        JLabel prompt = new JLabel("Continue as:");
        prompt.setFont(FONT_H1);
        JButton doctorBtn = simpleButton("Doctor");
        JButton patientBtn = simpleButton("Patient");
        doctorBtn.addActionListener(e -> cardLayout.show(mainPanel, "doctorLogin"));
        patientBtn.addActionListener(e -> cardLayout.show(mainPanel, "patientLogin"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRow.setOpaque(false);
        btnRow.add(doctorBtn);
        btnRow.add(patientBtn);

        page.add(centeredWrapper(card(prompt, btnRow)), BorderLayout.CENTER);
        return page;
    }

    // ================= DOCTOR LOGIN =================
    private JPanel buildDoctorLoginPanel() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BG);
        page.add(headerBar("Doctor Login", "Enter your credentials", null, null), BorderLayout.NORTH);

        JLabel label = new JLabel("Doctor ID");
        label.setFont(FONT_LABEL);
        JTextField idField = niceField(16);

        JButton loginBtn = simpleButton("Login");
        JButton backBtn = simpleButton("Back");
        backBtn.setBackground(new Color(149, 165, 166));

        loginBtn.addActionListener(e -> {
            Doctor d = hospital.doctorLogin(idField.getText().trim());
            if (d != null) {
                currentDoctor = d;
                mainPanel.add(buildDoctorDashboard(), "doctorDash");
                cardLayout.show(mainPanel, "doctorDash");
            } else {
                JOptionPane.showMessageDialog(this, "Doctor ID not found.");
            }
        });
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnRow.setOpaque(false);
        btnRow.add(backBtn);
        btnRow.add(loginBtn);

        page.add(centeredWrapper(card(label, idField, btnRow)), BorderLayout.CENTER);
        return page;
    }

    // ================= DOCTOR DASHBOARD =================
    private JPanel buildDoctorDashboard() {
        JPanel page = new JPanel(new BorderLayout(0, 16));
        page.setBackground(BG);
        page.add(headerBar("Doctor Dashboard", "Welcome, " + currentDoctor.getDoctorName(),
                "Department: " + currentDoctor.getSpecialization(),
                "ID: " + currentDoctor.getDoctorID()), BorderLayout.NORTH);

        // ---- অ্যাকশন কার্ড রো ----
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        cardsRow.setBackground(BG);
        cardsRow.setBorder(new EmptyBorder(0, 24, 0, 24));

        JPanel[] cardsHolder = new JPanel[1]; // dashboard পুনরায় বসাতে রেফারেন্স রাখার জন্য

        cardsRow.add(actionCard("➕", "Add Patient", new Color(224, 240, 255), () -> {
            String id = JOptionPane.showInputDialog(this, "New Patient ID:");
            if (id == null) return;
            String name = JOptionPane.showInputDialog(this, "Name:");
            String ageStr = JOptionPane.showInputDialog(this, "Age:");
            String gender = JOptionPane.showInputDialog(this, "Gender:");
            String pass = JOptionPane.showInputDialog(this, "Password:");
            String blood = JOptionPane.showInputDialog(this, "Blood Group:");
            int age = 0;
            try { age = Integer.parseInt(ageStr); } catch (Exception ex) { /* default 0 */ }
            Patient p = new Patient(name, age, gender, "", "", id, pass, blood, "2026-01-01");
            currentDoctor.addPatient(hospital, p);
            JOptionPane.showMessageDialog(this, "Patient added: " + id);
            refreshDoctorDashboard();
        }));

        cardsRow.add(actionCard("📝", "Write Report", new Color(224, 248, 235), () -> {
            String pid = JOptionPane.showInputDialog(this, "Patient ID:");
            if (pid == null) return;
            String test = JOptionPane.showInputDialog(this, "Test Name:");
            String rid = currentDoctor.createReport(hospital, pid, test);
            String result = JOptionPane.showInputDialog(this, "Result:");
            currentDoctor.uploadReport(hospital, rid, result);
            String med = JOptionPane.showInputDialog(this, "Medicine:");
            String dose = JOptionPane.showInputDialog(this, "Dosage:");
            String days = JOptionPane.showInputDialog(this, "Days:");
            String advice = JOptionPane.showInputDialog(this, "Advice:");
            currentDoctor.writePrescription(hospital, rid, med, dose, days, advice);
            JOptionPane.showMessageDialog(this, "Report " + rid + " completed for " + pid);
            refreshDoctorDashboard();
        }));

        cardsRow.add(actionCard("🔔", "Send Notification", new Color(238, 230, 250), () -> {
            String pid = JOptionPane.showInputDialog(this, "Patient ID:");
            if (pid == null) return;
            String msg = JOptionPane.showInputDialog(this, "Message:");
            if (msg == null) return;
            try {
                Patient target = hospital.searchPatient(pid);
                target.addNotification(msg);
                JOptionPane.showMessageDialog(this, "Notification sent to " + pid + ": " + msg);
            } catch (InvalidPatientException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }));

        cardsRow.add(actionCard("🚪", "Logout", new Color(253, 232, 232), () -> {
            currentDoctor = null;
            cardLayout.show(mainPanel, "home");
        }));

        // ---- নিচের দুই প্যানেল: Doctor Information + Recent Patients ----
        String[][] infoRows = {
                {"Name", currentDoctor.getDoctorName()},
                {"Department", currentDoctor.getSpecialization()},
                {"Doctor ID", currentDoctor.getDoctorID()},
                {"Phone", currentDoctor.getPhone()},
                {"Address", currentDoctor.getAddress()}
        };
        JPanel doctorInfo = infoPanel("Doctor Information", infoRows);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Age", "Gender", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Person person : hospital.getPeople()) {
            if (person instanceof Patient) {
                Patient p = (Patient) person;
                model.addRow(new Object[]{p.getPatientID(), p.getName(), p.getAge(), p.getGender(),
                        patientStatus(p)});
            }
        }
        JTable table = styledTable(model);
        JPanel patientsTable = tableCard("Recent Patients", table);

        JPanel bottomSplit = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomSplit.setBackground(BG);
        bottomSplit.setBorder(new EmptyBorder(0, 24, 24, 24));
        bottomSplit.add(doctorInfo);
        bottomSplit.add(patientsTable);

        JPanel cardsWrap = new JPanel(new BorderLayout());
        cardsWrap.setBackground(BG);
        cardsWrap.setBorder(new EmptyBorder(20, 0, 0, 0));
        cardsWrap.add(cardsRow, BorderLayout.CENTER);
        cardsWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        cardsWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomSplit.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BG);
        body.add(cardsWrap);
        body.add(bottomSplit);

        page.add(body, BorderLayout.CENTER);
        return page;
    }
    private String patientStatus(Patient p) {
        ArrayList<String> ids = p.getReportIDs();
        if (ids.isEmpty()) return "No Report";
        for (String id : ids) {
            String[] r = hospital.searchReport(id);
            if (r != null && (r[5].equals("Pending") || r[5].equals("Processing"))) {
                return "Pending";
            }
        }
        return "Report Ready";
    }

    private void refreshDoctorDashboard() {
        mainPanel.add(buildDoctorDashboard(), "doctorDash");
        cardLayout.show(mainPanel, "doctorDash");
    }

    // ================= PATIENT LOGIN =================
    private JPanel buildPatientLoginPanel() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BG);
        page.add(headerBar("Patient Login", "Enter your credentials", null, null), BorderLayout.NORTH);

        JLabel idLabel = new JLabel("Patient ID");
        idLabel.setFont(FONT_LABEL);
        JTextField idField = niceField(16);
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(FONT_LABEL);
        JPasswordField passField = nicePasswordField(16);

        JButton loginBtn = simpleButton("Login");
        JButton backBtn = simpleButton("Back");
        backBtn.setBackground(new Color(149, 165, 166));

        loginBtn.addActionListener(e -> {
            try {
                Patient p = hospital.patientLogin(idField.getText().trim(), new String(passField.getPassword()));
                currentPatient = p;
                mainPanel.add(buildPatientDashboard(), "patientDash");
                cardLayout.show(mainPanel, "patientDash");
            } catch (InvalidPatientException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnRow.setOpaque(false);
        btnRow.add(backBtn);
        btnRow.add(loginBtn);

        page.add(centeredWrapper(card(idLabel, idField, passLabel, passField, btnRow)), BorderLayout.CENTER);
        return page;
    }

    // ================= PATIENT DASHBOARD =================
    private JPanel buildPatientDashboard() {
        JPanel page = new JPanel(new BorderLayout(0, 16));
        page.setBackground(BG);
        page.add(headerBar("Patient Dashboard", "Welcome, " + currentPatient.getName(),
                "Blood Group: " + currentPatient.getBloodGroup(),
                "ID: " + currentPatient.getPatientID()), BorderLayout.NORTH);

        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        cardsRow.setBackground(BG);
        cardsRow.setBorder(new EmptyBorder(0, 24, 0, 24));

        cardsRow.add(actionCard("📄", "View Reports", new Color(224, 240, 255), () -> {
            String output = captureConsole(() -> currentPatient.viewReport(hospital));
            showConsoleResult("My Reports", output);
        }));
        cardsRow.add(actionCard("🕘", "History", new Color(224, 248, 235), () -> {
            String output = captureConsole(() -> currentPatient.viewHistory(hospital));
            showConsoleResult("Report History", output);
        }));
        cardsRow.add(actionCard("🔔", "Notifications", new Color(238, 230, 250), () -> {
            ArrayList<String> notes = currentPatient.getNotifications();
            StringBuilder sb = new StringBuilder();
            if (notes.isEmpty()) {
                sb.append("কোনো নোটিফিকেশন নেই।");
            } else {
                for (int i = 0; i < notes.size(); i++) {
                    sb.append((i + 1)).append(". ").append(notes.get(i)).append("\n");
                }
            }
            showConsoleResult("Notifications", sb.toString());
        }));
        cardsRow.add(actionCard("🚪", "Logout", new Color(253, 232, 232), () -> {
            currentPatient = null;
            cardLayout.show(mainPanel, "home");
        }));

        String[][] infoRows = {
                {"Name", currentPatient.getName()},
                {"Blood Group", currentPatient.getBloodGroup()},
                {"Patient ID", currentPatient.getPatientID()},
                {"Phone", currentPatient.getPhone()},
                {"Address", currentPatient.getAddress()}
        };
        JPanel patientInfo = infoPanel("Patient Information", infoRows);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Report ID", "Test", "Date", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (String id : currentPatient.getReportIDs()) {
            String[] r = hospital.searchReport(id);
            if (r != null) {
                model.addRow(new Object[]{id, r[2], r[4], r[5]});
            }
        }
        JTable table = styledTable(model);
        JPanel reportsTable = tableCard("My Reports", table);

        JPanel bottomSplit = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomSplit.setBackground(BG);
        bottomSplit.setBorder(new EmptyBorder(0, 24, 24, 24));
        bottomSplit.add(patientInfo);
        bottomSplit.add(reportsTable);

        JPanel cardsWrap = new JPanel(new BorderLayout());
        cardsWrap.setBackground(BG);
        cardsWrap.setBorder(new EmptyBorder(20, 0, 0, 0));
        cardsWrap.add(cardsRow, BorderLayout.CENTER);
        cardsWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        cardsWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomSplit.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BG);
        body.add(cardsWrap);
        body.add(bottomSplit);

        page.add(body, BorderLayout.CENTER);
        return page;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalGUI().setVisible(true));
    }
}