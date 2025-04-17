package movie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import tickets.BookingManager;
import tickets.Confirmation;

public class SeatSelection extends JFrame {
    private String name, email, phone, selectedLanguage, selectedMovie, selectedDate, selectedTime;
    private JPanel mainPanel;
    private JButton proceedButton;
    private int reclinerCount = 0, premiumCount = 0, executiveCount = 0;
    private double reclinerPrice, premiumPrice, executivePrice;
    private final List<String> selectedSeats = new ArrayList<>();
    private final Set<String> lockedSeats;

    public SeatSelection(String name, String email, String phone, String selectedLanguage, String selectedMovie, String selectedDate, String selectedTime) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.selectedLanguage = selectedLanguage;
        this.selectedMovie = selectedMovie;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;

        setSeatPricesByLanguage();
        lockedSeats = BookingManager.getBookedSeats(selectedMovie, selectedDate, selectedTime);

        setTitle("Seat Selection");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel label = new JLabel("Select your seats for " + selectedMovie + " on " + selectedDate + " at " + selectedTime, SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        createSeatPanel("Recliner - ₹" + (int) reclinerPrice, 6, "recliner", 'A');
        createSeatPanel("Premium - ₹" + (int) premiumPrice, 8, "premium", 'B');
        createSeatPanel("Executive - ₹" + (int) executivePrice, 10, "executive", 'C');

        JLabel screenLabel = new JLabel("SCREEN", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(screenLabel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        proceedButton = new JButton("Proceed");

        backButton.addActionListener(e -> {
            dispose();
            new MovieSelection(name, email, phone, selectedLanguage);
        });

        proceedButton.setFont(new Font("Arial", Font.BOLD, 14));
        proceedButton.addActionListener(new ProceedButtonHandler());

        buttonPanel.add(backButton);
        buttonPanel.add(proceedButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void setSeatPricesByLanguage() {
        switch (selectedLanguage.toLowerCase()) {
            case "gujarati" -> {
                reclinerPrice = 350;
                premiumPrice = 250;
                executivePrice = 200;
            }
            case "hindi" -> {
                reclinerPrice = 400;
                premiumPrice = 280;
                executivePrice = 220;
            }
            case "english" -> {
                reclinerPrice = 450;
                premiumPrice = 300;
                executivePrice = 250;
            }
            default -> {
                reclinerPrice = 400;
                premiumPrice = 280;
                executivePrice = 220;
            }
        }
    }

    private void createSeatPanel(String categoryWithPrice, int totalSeats, String type, char rowLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(categoryWithPrice, SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JPanel seatGrid = new JPanel(new GridLayout(2, totalSeats / 2, 5, 5));

        for (int i = 0; i < totalSeats; i++) {
            String seatLabel = "" + rowLabel + (i + 1);
            JButton seatButton = new JButton(seatLabel);
            seatButton.setFocusPainted(false);

            if (lockedSeats.contains(seatLabel)) {
                seatButton.setBackground(Color.LIGHT_GRAY);
                seatButton.setEnabled(false);
            } else {
                seatButton.setBackground(Color.WHITE);
                seatButton.addActionListener(new SeatSelectionHandler(seatButton, type, seatLabel));
            }

            seatGrid.add(seatButton);
        }

        panel.add(seatGrid, BorderLayout.CENTER);
        mainPanel.add(panel);
    }

    private class SeatSelectionHandler implements ActionListener {
        private final JButton seat;
        private boolean selected = false;
        private final String type;
        private final String seatLabel;

        public SeatSelectionHandler(JButton seat, String type, String seatLabel) {
            this.seat = seat;
            this.type = type;
            this.seatLabel = seatLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selected = !selected;
            seat.setBackground(selected ? Color.GREEN : Color.WHITE);
            if (selected) {
                selectedSeats.add(seatLabel);
                switch (type) {
                    case "recliner" -> reclinerCount++;
                    case "premium" -> premiumCount++;
                    case "executive" -> executiveCount++;
                }
            } else {
                selectedSeats.remove(seatLabel);
                switch (type) {
                    case "recliner" -> reclinerCount--;
                    case "premium" -> premiumCount--;
                    case "executive" -> executiveCount--;
                }
            }
        }
    }

    private class ProceedButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int totalTickets = reclinerCount + premiumCount + executiveCount;
            double totalPrice = (reclinerCount * reclinerPrice) + (premiumCount * premiumPrice) + (executiveCount * executivePrice);

            if (totalTickets == 0) {
                JOptionPane.showMessageDialog(SeatSelection.this, "Please select at least one seat.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String movieType = selectedMovie.contains("3D") ? "3D" : "2D";

            BookingManager.saveBooking(selectedMovie, selectedDate, selectedTime, selectedSeats, totalPrice);
            new Confirmation(name, selectedMovie, selectedDate, selectedTime, selectedLanguage, movieType,
                    reclinerCount, premiumCount, executiveCount, totalPrice, selectedSeats, email);
            dispose();
        }
    }

    public static void main(String[] args) {
        new SeatSelection("Rahul Sanghvi", "rahul@gmail.com", "1234567890", "Gujarati", "Reva", "2025-04-06", "09:30");
    }
}
