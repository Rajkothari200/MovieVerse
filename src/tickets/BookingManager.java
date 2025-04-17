package tickets;
import java.io.*;
import java.util.*;

public class BookingManager {
    private static final String FILE_NAME = "bookings.txt";

    public static void saveBooking(String movie, String date, String time, List<String> seats, double price) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(movie + "|" + date + "|" + time + "|" + String.join(",", seats) + "|" + price);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getBookedSeats(String movie, String date, String time) {
        Set<String> booked = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 &&
                        parts[0].equals(movie) &&
                        parts[1].equals(date) &&
                        parts[2].equals(time)) {
                    booked.addAll(Arrays.asList(parts[3].split(",")));
                }
            }
        } catch (IOException e) {
        }
        return booked;
    }

    public static List<String> getAllBookings() {
        List<String> all = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                all.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }

    public static void clearBookings() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getTotalRevenue() {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    total += Double.parseDouble(parts[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }
}
