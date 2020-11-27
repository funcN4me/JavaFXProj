package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;


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

    public static long[] countDifference(String dateOfEvent, String startTimeOfEvent) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTimeOfEvent = LocalDateTime.parse(dateOfEvent + " " + startTimeOfEvent, dateTimeFormat);
        LocalDateTime localCurrentDateTime = LocalDateTime.parse(getCurrentDate() + " " + getCurrentTime(), dateTimeFormat);
        Duration duration = Duration.between(localDateTimeOfEvent, localCurrentDateTime);
        long[] differences = {duration.toDays(), duration.toHours(), Math.abs(duration.toMinutes())};
        System.out.println(differences[2]);
        return differences;
    }

    public static void Smth() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2020-11-20 00:39:00", dateTimeFormatter);
        LocalDateTime localDateTime1 = LocalDateTime.parse("2020-11-21 00:30:00", dateTimeFormatter);
        Duration duration = Duration.between(localDateTime, localDateTime1);
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
        System.out.println(localDateTime.getMinute());
    }
}