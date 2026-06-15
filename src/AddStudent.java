import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AddStudent extends JPanel {

    JTextField id, name, dept, email, phone;

    public AddStudent() {

        setLayout(null);
        setBackground(Color.WHITE);

        // ================= TITLE =================
        JLabel title = new JLabel("ADD STUDENT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(250, 20, 200, 30);
        add(title);

        // ================= FORM CARD =================
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(180, 70, 400, 400);
        card.setBackground(new Color(245, 246, 250));
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        add(card);

        // ================= ID =================
        JLabel l0 = new JLabel("ID");
        l0.setBounds(30, 30, 100, 25);
        card.add(l0);

        id = new JTextField();
        id.setBounds(120, 30, 220, 30);
        card.add(id);

        // ================= NAME =================
        JLabel l1 = new JLabel("Name");
        l1.setBounds(30, 80, 100, 25);
        card.add(l1);

        name = new JTextField();
        name.setBounds(120, 80, 220, 30);
        card.add(name);

        // ================= DEPT =================
        JLabel l2 = new JLabel("Department");
        l2.setBounds(30, 130, 100, 25);
        card.add(l2);

        dept = new JTextField();
        dept.setBounds(120, 130, 220, 30);
        card.add(dept);

        // ================= EMAIL =================
        JLabel l3 = new JLabel("Email");
        l3.setBounds(30, 180, 100, 25);
        card.add(l3);

        email = new JTextField();
        email.setBounds(120, 180, 220, 30);
        card.add(email);

        // ================= PHONE =================
        JLabel l4 = new JLabel("Phone");
        l4.setBounds(30, 230, 100, 25);
        card.add(l4);

        phone = new JTextField();
        phone.setBounds(120, 230, 220, 30);
        card.add(phone);

        // ================= SAVE BUTTON =================
        JButton save = new JButton("SAVE STUDENT");
        save.setBounds(120, 300, 160, 40);

        save.setBackground(new Color(66, 153, 225));
        save.setForeground(Color.WHITE);
        save.setFocusPainted(false);
        save.setFont(new Font("Segoe UI", Font.BOLD, 13));

        card.add(save);

        // ================= SAVE ACTION =================
        ActionListener saveAction = e -> {

            String i = id.getText();
            String n = name.getText();
            String d = dept.getText();
            String em = email.getText();
            String ph = phone.getText();

            if (i.isEmpty() || n.isEmpty() || d.isEmpty() || em.isEmpty() || ph.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO students(id, name, department, email, phone) VALUES(?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(i));
                ps.setString(2, n);
                ps.setString(3, d);
                ps.setString(4, em);
                ps.setString(5, ph);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Student Added Successfully!");

                id.setText("");
                name.setText("");
                dept.setText("");
                email.setText("");
                phone.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error!");
            }
        };

        save.addActionListener(saveAction);

        // ================= ENTER KEY SUPPORT =================
        KeyAdapter enterKey = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveAction.actionPerformed(null);
                }
            }
        };

        id.addKeyListener(enterKey);
        name.addKeyListener(enterKey);
        dept.addKeyListener(enterKey);
        email.addKeyListener(enterKey);
        phone.addKeyListener(enterKey);
    }
}