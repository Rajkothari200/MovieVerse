package movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.toedter.calendar.JDateChooser;
import tickets.BookingManager;
import users.LanguageSelection;

public class MovieSelection extends JFrame {
    private String name, email, phone, selectedLanguage;
    private JComboBox<String> movieBox, timeBox;
    private JDateChooser dateChooser;

    public MovieSelection(String name, String email, String phone, String selectedLanguage) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.selectedLanguage = selectedLanguage;

        setTitle("Movie Selection");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Select Movie:"));
        String[] movies;
        switch (selectedLanguage.toLowerCase()) {
            case "english" -> movies = new String[]{"Avengers Endgame (3D)", "Interstellar", "Inception"};
            case "hindi" -> movies = new String[]{"RRR", "3 Idiots", "Baahubali"};
            case "gujarati" -> movies = new String[]{"Chello Divas", "Hellaro", "Reva"};
            default -> movies = new String[]{"RRR", "3 Idiots", "Baahubali"};
        }
        movieBox = new JComboBox<>(movies);
        panel.add(movieBox);

        panel.add(new JLabel("Select Date:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        // Limit date selection to today + 7 days
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 7);
        dateChooser.setMinSelectableDate(min.getTime());
        dateChooser.setMaxSelectableDate(max.getTime());

        panel.add(dateChooser);

        panel.add(new JLabel("Select Show Time:"));
        timeBox = new JComboBox<>();
        panel.add(timeBox);

        updateTimeBox();
        movieBox.addActionListener(e -> updateTimeBox());


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.addActionListener(e -> {
            dispose();
            new LanguageSelection(name, email, phone);
        });

        nextButton.addActionListener(new NextButtonHandler());

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateTimeBox() {
        timeBox.removeAllItems();
        String selectedMovie = (String) movieBox.getSelectedItem();
        String[] timings = getShowTimesForMovie(selectedMovie);
        for (String time : timings) {
            timeBox.addItem(time);
        }
    }

    private String[] getShowTimesForMovie(String movie) {
        return switch (movie) {
            case "Reva", "Hellaro", "Chello Divas" -> new String[]{"09:30 AM", "01:00 PM", "07:00 PM"};
            case "3 Idiots", "RRR", "Baahubali" -> new String[]{"10:00 AM", "02:00 PM", "08:00 PM"};
            case "Avengers Endgame (3D)", "Interstellar", "Inception" -> new String[]{"11:00 AM", "03:30 PM", "09:15 PM"};
            default -> new String[]{"10:00 AM", "01:30 PM", "06:00 PM"};
        };
    }

    private class NextButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedMovie = (String) movieBox.getSelectedItem();
            Date selectedDateObj = dateChooser.getDate();
            if (selectedDateObj == null) {
                JOptionPane.showMessageDialog(MovieSelection.this, "Please select a date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String selectedDate = sdf.format(selectedDateObj);
            String selectedTime = (String) timeBox.getSelectedItem();

            int totalSeats = 6 + 8 + 10;
            int bookedSeats = BookingManager.getBookedSeats(selectedMovie, selectedDate, selectedTime).size();

            if (bookedSeats >= totalSeats) {
                JOptionPane.showMessageDialog(MovieSelection.this,
                        "This show is sold out. Please select another showtime.",
                        "Sold Out", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new SeatSelection(name, email, phone, selectedLanguage, selectedMovie, selectedDate, selectedTime);
            dispose();
        }
    }

    public static void main(String[] args) {
        new MovieSelection("John Doe", "john@example.com", "1234567890", "English");
    }
}
