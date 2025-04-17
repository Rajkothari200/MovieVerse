package main;

import javax.swing.*;
import users.LoginScreen;
import tickets.Admin;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you an admin?", "Login Mode", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                String password = JOptionPane.showInputDialog(null, "Enter admin password:");
                if ("admin123".equals(password)) {
                    new Admin();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password.");
                }
            } else {
                new LoginScreen();
            }
        });
    }
}
