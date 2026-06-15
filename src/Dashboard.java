import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Dashboard extends JFrame {

    JPanel mainPanel;
    JLabel timeLabel;

    JPanel sideBar;

    ViewStudents viewStudents;

    public Dashboard() {

        setTitle("Student Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(true);

        // ================= SIDEBAR =================
        sideBar = new JPanel();
        sideBar.setLayout(null);
        sideBar.setBounds(0, 0, 250, 700);
        sideBar.setBackground(new Color(26, 32, 44));
        add(sideBar);

        JLabel menuTitle = new JLabel("MENU");
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menuTitle.setBounds(85, 20, 100, 30);
        sideBar.add(menuTitle);

        JButton addBtn = createButton("Add Student", 80);
        JButton viewBtn = createButton("View Students", 140);
        JButton updateBtn = createButton("Update Student", 200);
        JButton deleteBtn = createButton("Delete Student", 260);
        JButton reportBtn = createButton("Reports", 320);
        JButton settingBtn = createButton("Settings", 380);
        JButton logoutBtn = createButton("Logout", 500);

        sideBar.add(addBtn);
        sideBar.add(viewBtn);
        sideBar.add(updateBtn);
        sideBar.add(deleteBtn);
        sideBar.add(reportBtn);
        sideBar.add(settingBtn);
        sideBar.add(logoutBtn);

        // ================= HEADER =================
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(280, 20, 200, 30);
        add(title);

        // ================= DATE + TIME (RIGHT SIDE) =================
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        timeLabel.setBounds(900, 25, 280, 20);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(timeLabel);

        startClock();

        // ================= MAIN PANEL =================
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBounds(270, 90, 900, 530);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(mainPanel);

        viewStudents = new ViewStudents();
        showWelcome();

        // ================= BUTTON ACTIONS =================
        addBtn.addActionListener(e -> switchPanel(new AddStudent()));
        viewBtn.addActionListener(e -> switchPanel(viewStudents));
        updateBtn.addActionListener(e -> switchPanel(new UpdateStudent()));
        deleteBtn.addActionListener(e -> switchPanel(new DeleteStudent()));
        reportBtn.addActionListener(e -> switchPanel(new Report()));
        settingBtn.addActionListener(e -> switchPanel(new Settings()));

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Login();
            }
        });

        // ================= RESIZE =================
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {

                int w = getWidth();
                int h = getHeight();

                sideBar.setBounds(0, 0, 250, h);
                mainPanel.setBounds(270, 90, w - 290, h - 120);
                timeLabel.setBounds(w - 320, 25, 280, 20);
            }
        });

        setVisible(true);
    }

    // ================= PANEL SWITCH =================
    void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // ================= CLOCK =================
    void startClock() {

        Timer timer = new Timer(1000, e -> {

            DateTimeFormatter dtf =
                    DateTimeFormatter.ofPattern("dd MMM yyyy | hh:mm:ss a");

            timeLabel.setText(LocalDateTime.now().format(dtf));
        });

        timer.start();
    }

    // ================= WELCOME =================
    void showWelcome() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(
                "<html><center><h1>Hello..!! Welcome to Admin Panel</h1><br>Student Management System<br>Developed by Mahmodul Karim Akash<br>City University</center></html>",
                SwingConstants.CENTER
        );

        panel.add(label, BorderLayout.CENTER);
        mainPanel.add(panel);
    }

    // ================= BUTTON STYLE =================
    JButton createButton(String text, int y) {

        JButton btn = new JButton(text);

        btn.setBounds(20, y, 210, 45);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btn.setBackground(new Color(45, 55, 72));
        btn.setForeground(Color.WHITE);

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(66, 153, 225));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(45, 55, 72));
            }
        });

        return btn;
    }
}