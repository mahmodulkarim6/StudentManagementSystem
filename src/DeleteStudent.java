import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class DeleteStudent extends JPanel {

    JTextField searchField;

    JTextField id, name, dept, email, phone;

    public DeleteStudent() {

        setLayout(null);
        setBackground(Color.WHITE);

        // ================= TITLE =================
        JLabel title = new JLabel("DELETE STUDENT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(250, 15, 250, 30);
        add(title);

        // ================= SEARCH =================
        searchField = new JTextField();
        searchField.setBounds(250, 60, 220, 32);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(searchField);

        JButton searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(480, 60, 110, 32);

        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchBtn.setBackground(new Color(52, 152, 219));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);

        add(searchBtn);

        // ================= CARD =================
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(180, 110, 420, 320);
        card.setBackground(new Color(245, 246, 250));
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(card);

        // ================= FIELDS =================
        JLabel l1 = new JLabel("ID");
        l1.setBounds(30, 30, 100, 25);
        card.add(l1);

        id = new JTextField();
        id.setBounds(140, 30, 240, 30);
        id.setEditable(false);
        card.add(id);

        JLabel l2 = new JLabel("Name");
        l2.setBounds(30, 80, 100, 25);
        card.add(l2);

        name = new JTextField();
        name.setBounds(140, 80, 240, 30);
        name.setEditable(false);
        card.add(name);

        JLabel l3 = new JLabel("Department");
        l3.setBounds(30, 130, 100, 25);
        card.add(l3);

        dept = new JTextField();
        dept.setBounds(140, 130, 240, 30);
        dept.setEditable(false);
        card.add(dept);

        JLabel l4 = new JLabel("Email");
        l4.setBounds(30, 180, 100, 25);
        card.add(l4);

        email = new JTextField();
        email.setBounds(140, 180, 240, 30);
        email.setEditable(false);
        card.add(email);

        JLabel l5 = new JLabel("Phone");
        l5.setBounds(30, 230, 100, 25);
        card.add(phone = new JTextField());
        phone.setBounds(140, 230, 240, 30);
        phone.setEditable(false);

        // ================= DELETE BUTTON =================
        JButton deleteBtn = new JButton("DELETE STUDENT");
        deleteBtn.setBounds(140, 270, 170, 35);

        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);

        card.add(deleteBtn);

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

        // ================= DELETE ACTION =================
        deleteBtn.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                Connection con = DBConnection.getConnection();

                String sql = "DELETE FROM students WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(id.getText()));

                int x = ps.executeUpdate();

                if (x > 0) {
                    JOptionPane.showMessageDialog(this, "Student Deleted Successfully!");

                    // clear fields
                    id.setText("");
                    name.setText("");
                    dept.setText("");
                    email.setText("");
                    phone.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Delete Failed!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Occurred!");
            }
        });
    }
}