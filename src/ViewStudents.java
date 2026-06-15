import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewStudents extends JPanel {

    JTable table;
    DefaultTableModel model;

    JTextField searchField;
    JButton refreshBtn;

    public ViewStudents() {

        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 252));

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("Student Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(20, 20, 20));
        header.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(Color.WHITE);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 32));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        refreshBtn = new JButton("Refresh");
        styleButton(refreshBtn, new Color(52, 152, 219));

        right.add(searchField);
        right.add(refreshBtn);

        header.add(right, BorderLayout.EAST);

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Department", "Email", "Phone", "Action"}, 0
        ) {
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };

        table = new JTable(model);

        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // ===== HEADER STYLE (FIXED BOLD) =====
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(235, 238, 242));
        table.getTableHeader().setForeground(Color.BLACK);

        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);

        // ================= ACTION COLUMN =================
        table.getColumn("Action").setCellRenderer((tbl, value, isSelected, hasFocus, row, col) -> {

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setBackground(Color.WHITE);

            JButton edit = new JButton("Edit");
            JButton delete = new JButton("Delete");

            styleButton(edit, new Color(52, 152, 219));
            styleButton(delete, new Color(231, 76, 60));

            panel.add(edit);
            panel.add(delete);

            return panel;
        });

        table.getColumn("Action").setCellEditor(new DefaultCellEditor(new JCheckBox()) {

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            JButton edit = new JButton("Edit");
            JButton delete = new JButton("Delete");

            {
                styleButton(edit, new Color(52, 152, 219));
                styleButton(delete, new Color(231, 76, 60));

                panel.add(edit);
                panel.add(delete);

                edit.addActionListener(e -> editRow(table.getSelectedRow()));
                delete.addActionListener(e -> deleteRow(table.getSelectedRow()));
            }

            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                return panel;
            }

            public Object getCellEditorValue() {
                return "";
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(sp, BorderLayout.CENTER);

        loadData();

        refreshBtn.addActionListener(e -> loadData());

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                searchData(searchField.getText());
            }
        });
    }

    // ================= LOAD =================
    public void loadData() {

        model.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM students");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        "Action"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= SEARCH =================
    public void searchData(String key) {

        model.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM students WHERE " +
                    "CAST(id AS CHAR) LIKE ? OR name LIKE ? OR department LIKE ? OR email LIKE ? OR phone LIKE ?";

            PreparedStatement ps = con.prepareStatement(sql);

            String v = "%" + key + "%";

            for (int i = 1; i <= 5; i++) ps.setString(i, v);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        "Action"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= EDIT =================
    void editRow(int row) {

        String id = model.getValueAt(row, 0).toString();

        JTextField n = new JTextField(model.getValueAt(row, 1).toString());
        JTextField d = new JTextField(model.getValueAt(row, 2).toString());
        JTextField e = new JTextField(model.getValueAt(row, 3).toString());
        JTextField p = new JTextField(model.getValueAt(row, 4).toString());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Name")); panel.add(n);
        panel.add(new JLabel("Dept")); panel.add(d);
        panel.add(new JLabel("Email")); panel.add(e);
        panel.add(new JLabel("Phone")); panel.add(p);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Student", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            try {

                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE students SET name=?, department=?, email=?, phone=? WHERE id=?"
                );

                ps.setString(1, n.getText());
                ps.setString(2, d.getText());
                ps.setString(3, e.getText());
                ps.setString(4, p.getText());
                ps.setInt(5, Integer.parseInt(id));

                ps.executeUpdate();

                loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ================= DELETE =================
    void deleteRow(int row) {

        try {

            int id = (int) model.getValueAt(row, 0);

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE id=?");

            ps.setInt(1, id);
            ps.executeUpdate();

            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= BUTTON STYLE =================
    void styleButton(JButton btn, Color color) {

        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}