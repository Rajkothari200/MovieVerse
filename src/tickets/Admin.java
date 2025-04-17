package tickets;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Admin extends JFrame {
    public Admin() {
        setTitle("Admin Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextArea bookingsArea = new JTextArea();
        bookingsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingsArea);

        //Display Panel
        List<String> bookings = BookingManager.getAllBookings();
        if (bookings.isEmpty()) {
            bookingsArea.setText("No bookings yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String b : bookings) {
                sb.append(b.replace("|", " | ")).append("\n");
            }
            bookingsArea.setText(sb.toString());
        }

        //Revenue Panel
        JPanel revenuePanel = new JPanel(new GridLayout(2, 1));
        JLabel revenueLabel = new JLabel("Total Revenue: ₹" + BookingManager.getTotalRevenue(), SwingConstants.CENTER);
        JButton resetButton = new JButton("Reset All Bookings");

        resetButton.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset all bookings?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                BookingManager.clearBookings();
                JOptionPane.showMessageDialog(this, "All bookings cleared.");
                dispose();
                new Admin();
            }
        });

        revenuePanel.add(revenueLabel);
        revenuePanel.add(resetButton);

        panel.add(new JLabel("All Bookings:", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(revenuePanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Admin();
    }
}
