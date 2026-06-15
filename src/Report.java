import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Report extends JPanel {

    JLabel status;

    public Report() {

        setLayout(null);

        JLabel title = new JLabel("STUDENT REPORT EXPORT");
        title.setBounds(40, 20, 300, 30);
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        add(title);

        JButton export = new JButton("Export to Excel (CSV)");
        export.setBounds(40, 70, 200, 35);
        add(export);

        status = new JLabel("Ready to export...");
        status.setBounds(40, 120, 400, 25);
        add(status);

        export.addActionListener(e -> exportCSV());
    }

    void exportCSV() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int count = 0;

            // ================= FILE NAME WITH TIME =================
            DateTimeFormatter dtf =
                    DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

            String fileName =
                    "students_report_" +
                    LocalDateTime.now().format(dtf) +
                    ".csv";

            // ================= SAVE DIALOG =================
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File(fileName));

            int option = chooser.showSaveDialog(this);

            if (option != JFileChooser.APPROVE_OPTION) {
                status.setText("Export cancelled.");
                return;
            }

            File file = chooser.getSelectedFile();

            FileWriter fw = new FileWriter(file);

            // ================= HEADER =================
            fw.write("ID,Name,Department,Email,Phone\n");

            // ================= DATA =================
            while (rs.next()) {

                fw.write(
                        rs.getInt("id") + "," +
                        rs.getString("name") + "," +
                        rs.getString("department") + "," +
                        rs.getString("email") + "," +
                        rs.getString("phone") + "\n"
                );

                count++;
            }

            fw.close();

            status.setText("Exported " + count + " students successfully!");

            JOptionPane.showMessageDialog(
                    this,
                    "Excel File Saved Successfully!\nTotal: " + count
            );

        } catch (Exception e) {
            e.printStackTrace();
            status.setText("Export failed!");
        }
    }
}