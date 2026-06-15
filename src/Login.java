import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame {

    JTextField user;
    JPasswordField pass;
    JCheckBox showPassword;

    public Login() {

        setTitle("Student Management System - Login");
        setSize(450, 320);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel title = new JLabel("Student Management System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(60, 20, 350, 30);
        add(title);

        JLabel subtitle = new JLabel("Admin Login");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setBounds(175, 55, 100, 20);
        add(subtitle);

        JLabel u = new JLabel("Username");
        u.setBounds(60, 100, 100, 25);
        add(u);

        user = new JTextField();
        user.setBounds(160, 100, 200, 30);
        add(user);

        JLabel p = new JLabel("Password");
        p.setBounds(60, 145, 100, 25);
        add(p);

        pass = new JPasswordField();
        pass.setBounds(160, 145, 200, 30);
        add(pass);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(160, 180, 150, 25);
        add(showPassword);

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                pass.setEchoChar((char) 0);
            } else {
                pass.setEchoChar('•');
            }
        });

        JButton btn = new JButton("Login");
        btn.setBounds(160, 220, 120, 35);
        add(btn);

        // Button click
        btn.addActionListener(e -> login());

        // Enter key support
        getRootPane().setDefaultButton(btn);

        setVisible(true);
    }

    void login() {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT * FROM admin WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getText().trim());
            ps.setString(2,
                new String(pass.getPassword()).trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                dispose();
                new Dashboard();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
                );

            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Database Connection Error!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
        }

        new Login();
    }
}