import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UpdateStudent extends JPanel {

    JTextField searchField;
    JTextField id, name, dept, email, phone;

    int oldId = -1; // ✅ original ID store করার জন্য

    public UpdateStudent() {

        setLayout(null);
        setBackground(Color.WHITE);

        // ================= TITLE =================
        JLabel title = new JLabel("UPDATE STUDENT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(250, 15, 250, 30);
        add(title);

        // ================= SEARCH =================
        searchField = new JTextField();
        searchField.setBounds(250, 55, 220, 32);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(searchField);

        JButton searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(480, 55, 110, 32);

        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchBtn.setBackground(new Color(52, 152, 219));
        searchBtn.setForeground(Color.WHITE);   // ✅ text visible fix
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);

        add(searchBtn);

        // ================= CARD =================
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(180, 100, 420, 360);
        card.setBackground(new Color(245, 246, 250));
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(card);

        // ================= ID =================
        JLabel l1 = new JLabel("Student ID");
        l1.setBounds(30, 30, 100, 25);
        card.add(l1);

        id = new JTextField();
        id.setBounds(140, 30, 240, 30);
        card.add(id);

        // ================= NAME =================
        JLabel l2 = new JLabel("Name");
        l2.setBounds(30, 80, 100, 25);
        card.add(l2);

        name = new JTextField();
        name.setBounds(140, 80, 240, 30);
        card.add(name);

        // ================= DEPT =================
        JLabel l3 = new JLabel("Department");
        l3.setBounds(30, 130, 100, 25);
        card.add(l3);

        dept = new JTextField();
        dept.setBounds(140, 130, 240, 30);
        card.add(dept);

        // ================= EMAIL =================
        JLabel l4 = new JLabel("Email");
        l4.setBounds(30, 180, 100, 25);
        card.add(l4);

        email = new JTextField();
        email.setBounds(140, 180, 240, 30);
        card.add(email);

        // ================= PHONE =================
        JLabel l5 = new JLabel("Phone");
        l5.setBounds(30, 230, 100, 25);
        card.add(l5);

        phone = new JTextField();
        phone.setBounds(140, 230, 240, 30);
        card.add(phone);

        // ================= UPDATE =================
        JButton updateBtn = new JButton("UPDATE");
        updateBtn.setBounds(140, 290, 120, 35);

        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        updateBtn.setBackground(new Color(46, 204, 113));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFocusPainted(false);
        updateBtn.setBorderPainted(false);

        card.add(updateBtn);

        // ================= SEARCH ACTION =================
        searchBtn.addActionListener(e -> {

            try {
                Connection con = DBConnection.getConnection();

                String sql =
                        "SELECT * FROM students WHERE " +
                        "CAST(id AS CHAR) LIKE ? OR name LIKE ? OR department LIKE ? OR email LIKE ? OR phone LIKE ?";

                PreparedStatement ps = con.prepareStatement(sql);

                String key = "%" + searchField.getText() + "%";

                for (int i = 1; i <= 5; i++) ps.setString(i, key);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    oldId = rs.getInt("id"); // ✅ original ID save

                    id.setText(String.valueOf(rs.getInt("id")));
                    name.setText(rs.getString("name"));
                    dept.setText(rs.getString("department"));
                    email.setText(rs.getString("email"));
                    phone.setText(rs.getString("phone"));

                } else {
                    JOptionPane.showMessageDialog(this, "Student Not Found!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // ================= UPDATE FIXED =================
        updateBtn.addActionListener(e -> {

            try {

                Connection con = DBConnection.getConnection();

                int newId = Integer.parseInt(id.getText());

                String sql =
                        "UPDATE students SET id=?, name=?, department=?, email=?, phone=? WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, newId);
                ps.setString(2, name.getText());
                ps.setString(3, dept.getText());
                ps.setString(4, email.getText());
                ps.setString(5, phone.getText());

                // ✅ IMPORTANT FIX: old ID used here
                ps.setInt(6, oldId);

                int x = ps.executeUpdate();

                if (x > 0) {
                    JOptionPane.showMessageDialog(this, "Updated Successfully!");
                    oldId = newId; // update reference
                } else {
                    JOptionPane.showMessageDialog(this, "Update Failed!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Occurred!");
            }
        });
    }
}