package users;

import users.LanguageSelection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

public class LoginScreen extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JButton submitButton;

    private static final String SHEETDB_URL = "https://sheetdb.io/api/v1/lugmyq46v4puu";

    public LoginScreen() {
        setTitle("User Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        submitButton = new JButton("Submit");
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                if (!validateInput(name, email, phone)) return;

                if (sendDataToGoogleSheets(name, email, phone)) {
                    JOptionPane.showMessageDialog(null, "User Logged In successfully!");
                    dispose();
                    new LanguageSelection(name, email, phone);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save data. Try again.");
                }
            }
        });

        setVisible(true);
    }

    private boolean validateInput(String name, String email, String phone) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }


        if (!name.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(this, "Name should only contain alphabets!");
            return false;
        }


        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|in|org|net|edu|gov|co)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format! Please enter a valid email.");
            return false;
        }


        if (!phone.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Phone number must contain exactly 10 digits!");
            return false;
        }

        return true;
    }

    private boolean sendDataToGoogleSheets(String name, String email, String phone) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(SHEETDB_URL);
            post.setHeader("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("Name", name);
            json.put("Email", email);
            json.put("Phone", phone);

            post.setEntity(new StringEntity(json.toString()));

            try (CloseableHttpResponse response = client.execute(post)) {
                return response.getCode() == 201;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
