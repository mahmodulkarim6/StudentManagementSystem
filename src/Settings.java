import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Settings extends JPanel {

    JTextField username;
    JPasswordField password;
    JButton toggleBtn;

    boolean showPassword = false;

    public Settings() {

        setLayout(null);
        setBackground(Color.WHITE);

        // ================= TITLE =================
        JLabel title = new JLabel("ADMIN SETTINGS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(220, 20, 250, 30);
        add(title);

        // ================= CARD =================
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(160, 70, 450, 300);
        card.setBackground(new Color(245, 246, 250));
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(card);

        // ================= USERNAME =================
        JLabel l1 = new JLabel("Username");
        l1.setBounds(40, 40, 120, 25);
        card.add(l1);

        username = new JTextField();
        username.setBounds(160, 40, 220, 30);
        card.add(username);

        // ================= PASSWORD =================
        JLabel l2 = new JLabel("Password");
        l2.setBounds(40, 100, 120, 25);
        card.add(l2);

        password = new JPasswordField();
        password.setBounds(160, 100, 180, 30);
        card.add(password);

        // ================= SHOW/HIDE PASSWORD =================
        toggleBtn = new JButton("👁");
        toggleBtn.setBounds(345, 100, 35, 30);
        toggleBtn.setFocusPainted(false);
        card.add(toggleBtn);

        toggleBtn.addActionListener(e -> {

            showPassword = !showPassword;

            if (showPassword) {
                password.setEchoChar((char) 0);
                toggleBtn.setText("🙈");
            } else {
                password.setEchoChar('•');
                toggleBtn.setText("👁");
            }
        });

        // ================= SAVE BUTTON =================
        JButton save = new JButton("SAVE CHANGES");
        save.setBounds(160, 170, 160, 40);

        save.setBackground(new Color(52, 152, 219));
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Segoe UI", Font.BOLD, 13));
        save.setFocusPainted(false);
        save.setBorderPainted(false);

        card.add(save);

        // ================= RELOAD BUTTON =================
        JButton reload = new JButton("RELOAD");
        reload.setBounds(330, 170, 90, 40);

        reload.setBackground(new Color(149, 165, 166));
        reload.setForeground(Color.WHITE);
        reload.setFocusPainted(false);
        reload.setBorderPainted(false);

        card.add(reload);

        // ================= LOAD DATA =================
        loadAdminData();

        reload.addActionListener(e -> loadAdminData());

        // ================= SAVE ACTION =================
        save.addActionListener(e -> {

            try {

                if (username.getText().trim().isEmpty() ||
                        password.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
                    return;
                }

                Connection con = DBConnection.getConnection();

                String sql =
                        "UPDATE admin SET username=?, password=? WHERE id=1";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, username.getText());
                ps.setString(2, new String(password.getPassword()));

                int x = ps.executeUpdate();

                if (x > 0) {
                    JOptionPane.showMessageDialog(this, "Settings Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No Changes Saved!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    // ================= LOAD ADMIN DATA =================
    void loadAdminData() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM admin WHERE id=1";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                username.setText(rs.getString("username"));
                password.setText(rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}