package sample;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ParsingData {
    // This shit was made for counting difference between current date and future date with spread you choose
    public static String countSmth(String date , String spread) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        StringBuilder result = new StringBuilder();
        switch (spread) {
            case "Week" -> result.append(parsedDate.plusWeeks(1).toString());
            case "Month" -> result.append(parsedDate.plusMonths(1).toString());
            default -> result.append(date);
        }
        return result.toString();
    }
    // Wow this method give us a current date. Spectacular!
    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getCurrentDate(String forWhat) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    // Same as upper method, but with time
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}