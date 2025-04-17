package users;

import movie.MovieSelection;

import javax.swing.*;
import java.awt.*;

public class LanguageSelection extends JFrame {
    private final String name, email, phone;

    public LanguageSelection(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

        setTitle("Select Language");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        add(new JLabel("Select your preferred language:", SwingConstants.CENTER));

        JButton gujaratiButton = new JButton("Gujarati");
        JButton hindiButton = new JButton("Hindi");
        JButton englishButton = new JButton("English");

        add(gujaratiButton);
        add(hindiButton);
        add(englishButton);

        gujaratiButton.addActionListener(e -> openMovieSelection("Gujarati"));
        hindiButton.addActionListener(e -> openMovieSelection("Hindi"));
        englishButton.addActionListener(e -> openMovieSelection("English"));

        setVisible(true);
    }

    private void openMovieSelection(String language) {
        dispose();
        new MovieSelection(name, email, phone, language);
    }
}
