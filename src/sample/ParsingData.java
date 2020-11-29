package sample;

import java.time.Duration;
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

    // Overloaded method for returning human-readable format of date
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

    // Counting time differences between current time and time of event
    public static long[] countDifference(String dateOfEvent, String startTimeOfEvent) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTimeOfEvent = LocalDateTime.parse(dateOfEvent + " " + startTimeOfEvent, dateTimeFormat);
        LocalDateTime localCurrentDateTime = LocalDateTime.parse(getCurrentDate() + " " + getCurrentTime(), dateTimeFormat);
        Duration duration = Duration.between(localDateTimeOfEvent, localCurrentDateTime);
        long[] differences = {duration.toDays(), duration.toHours(), Math.abs(duration.toMinutes())};
        return differences;
    }

    public static String convertDateFor(String date, String ForWhat) {
        StringBuilder convertedDate = new StringBuilder();
        switch (ForWhat) {
            case "For User": {
                String[] splittedDate = date.split("-");
                convertedDate.append(splittedDate[2] + "." + splittedDate[1] + "." + splittedDate[0]);
                break;
            }
            case "For DB": {
                String[] splittedDate = date.split("\\.");
                convertedDate.append(splittedDate[2] + "-" + splittedDate[1] + "-" + splittedDate[0]);
                break;
            }
            default:
                throw new NullPointerException("You should specify for what you are converting date");
        }
        return convertedDate.toString();
    }

    public static String parseTime(String[] number){
        for (int i = 0; i < number.length; i++) {
            if (number[i].length() == 1) {
                number[i] = "0" + number[i];
            }
        }

        return number[0] + ":" + number[1] + ":" + number[2] ;
    }
}