package tickets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import users.LanguageSelection;


public class TicketPreview extends JFrame {

    private String userName, movieName, date, time, language, movieType, email;
    private List<String> selectedSeats;
    private double totalPrice;

    public TicketPreview(String userName, String movieName, String date, String time,
                         String language, String movieType, List<String> selectedSeats,
                         double totalPrice, String email) {

        this.userName = userName;
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.language = language;
        this.movieType = movieType;
        this.selectedSeats = selectedSeats;
        this.totalPrice = totalPrice;
        this.email = email;

        setTitle("Ticket Preview");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder ticket = new StringBuilder();
        ticket.append("-------- Movie Ticket --------\n\n");
        ticket.append("Name: ").append(userName).append("\n");
        ticket.append("Movie: ").append(movieName).append(" (").append(movieType).append(")\n");
        ticket.append("Language: ").append(language).append("\n");
        ticket.append("Date: ").append(date).append("\n");
        ticket.append("Time: ").append(time).append("\n");
        ticket.append("Seats: ").append(String.join(", ", selectedSeats)).append("\n");
        ticket.append("Total: ₹").append(String.format("%.2f", totalPrice)).append("\n");
        ticket.append("\n-----------------------------");

        previewArea.setText(ticket.toString());
        add(new JScrollPane(previewArea), BorderLayout.CENTER);

        JButton exportButton = new JButton("Export as PDF & Email");
        exportButton.addActionListener(this::exportAsPDF);
        add(exportButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void exportAsPDF(ActionEvent e) {
        try {
            java.io.File dir = new java.io.File("tickets");
            if (!dir.exists()) dir.mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = "tickets/Ticket_" + timestamp + ".pdf";

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font contentFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Movie Ticket", titleFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Name: " + userName, contentFont));
            document.add(new Paragraph("Movie: " + movieName + " (" + movieType + ")", contentFont));
            document.add(new Paragraph("Language: " + language, contentFont));
            document.add(new Paragraph("Date: " + date, contentFont));
            document.add(new Paragraph("Time: " + time, contentFont));
            document.add(new Paragraph("Seats: " + String.join(", ", selectedSeats), contentFont));
            document.add(new Paragraph("Total: ₹" + String.format("%.2f", totalPrice), contentFont));

            document.close();

            sendEmailWithTicket(email, filePath);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to export or email ticket.");
        }
    }

    private void sendEmailWithTicket(String recipientEmail, String filePath) {
        final String senderEmail = "rajpkothari2000@gmail.com";
        final String appPassword = "qdxejbsokqmkfjoc"; // Replace with your app password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your Movie Ticket 🎟️");

            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText("Hi " + userName + ",\n\nYour ticket is attached. Enjoy the show!\n\n🍿🎬");

            MimeBodyPart attachment = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachment.setDataHandler(new DataHandler(source));
            attachment.setFileName("MovieTicket.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBody);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);

            Transport.send(message);


            JOptionPane.showMessageDialog(this, "Ticket emailed to " + recipientEmail);


            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Thank you for booking your ticket!",
                    "Booking Complete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Book Another Ticket", "Exit"},
                    "Book Another Ticket"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.dispose(); // Close the current window
                new users.LanguageSelection(userName, email, ""); // Pass phone if needed
            } else {
                System.exit(0); // Exit the app
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send email.");
        }
    }


    public static void main(String[] args) {
        new TicketPreview(
                "Rahul Sanghvi", "Interstellar", "2025-04-06", "11:00 AM",
                "English", "3D", List.of("A1", "A2", "B3"),
                950.00, "rahul@gmail.com"
        );
    }
}
