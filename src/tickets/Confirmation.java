package tickets;

import movie.SeatSelection;
import tickets.TicketPreview;
import movie.SeatSelection;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Confirmation extends JFrame {
    public Confirmation(String userName, String movieName, String date, String showTime, String language, String movieType,
                        int reclinerCount, int premiumCount, int executiveCount, double totalPrice, List<String> selectedSeats, String email) {

        setTitle("Booking Confirmation");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(13, 1, 10, 10));

        add(new JLabel("Booking Confirmation", SwingConstants.CENTER));
        add(new JLabel("Name: " + userName));
        add(new JLabel("Movie: " + movieName));
        add(new JLabel("Language: " + language));
        add(new JLabel("Type: " + movieType));
        add(new JLabel("Date: " + date));
        add(new JLabel("Time: " + showTime));
        add(new JLabel("Recliner Seats: " + reclinerCount));
        add(new JLabel("Premium Seats: " + premiumCount));
        add(new JLabel("Executive Seats: " + executiveCount));
        add(new JLabel("Total Price: ₹" + String.format("%.2f", totalPrice)));
        add(new JLabel("Selected Seats: " + String.join(", ", selectedSeats)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        JButton confirmButton = new JButton("Confirm & Preview Ticket");


        backButton.addActionListener(e -> {
            dispose();
            new SeatSelection(userName, email, "", language, movieName, date, showTime);
        });

        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Booking Confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new TicketPreview(userName, movieName, date, showTime, language, movieType, selectedSeats, totalPrice, email);
            dispose();
        });



        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);
        add(buttonPanel);

        setVisible(true);
    }
}
