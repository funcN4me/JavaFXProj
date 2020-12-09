package sample;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;


public class DBFuncs {
    public static String currentDate = ParsingData.getCurrentDate();
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://127.0.0.1:3306/departmentHR?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "****";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static String[] getInfoFor(String columnLabel, String spread) {
        // Making query with dependencies of spread that user choose
        String currentTime = ParsingData.getCurrentTime();
        StringBuilder query = new StringBuilder();
        switch (spread) {
            case "Upcoming" -> query.append("SELECT * FROM diary.activities WHERE Date > \"" + currentDate + "\" or (Date = \"" +
                    currentDate + "\" and StartsAt >= \"" + currentTime + "\") ORDER BY Date, StartsAt ASC LIMIT 1;");
            case "Today" -> query.append("select * from diary.activities where Date = \"" + currentDate + "\" and StartsAt >= \"" +
                    currentTime + "\" ORDER BY Date ASC, StartsAt ASC;");
            case "Week" -> query.append("select * from diary.activities where Date > \"" + currentDate + "\" and Date <= \"" +
                    ParsingData.countSmth(currentDate, "Week") +
                    "\" or (Date = \"" + currentDate + "\" and StartsAt >= \"" + currentTime + "\") ORDER BY Date ASC, StartsAt ASC;");
            case "Month" -> query.append("select * from diary.activities where Date > \"" + currentDate + "\" and Date <= \"" +
                    ParsingData.countSmth(currentDate, "Month") +
                    "\" or (Date = \"" + currentDate + "\" and StartsAt >= \"" + currentTime + "\") ORDER BY Date ASC, StartsAt ASC;");
            default -> {
                if (spread.equalsIgnoreCase(ParsingData.convertDateFor(currentDate, "For User"))) {
                    query.append("select * from diary.activities WHERE Date = \"" + currentDate +
                            "\" and StartsAt >= \"" + currentTime + "\" ORDER BY Date ASC, StartsAt ASC;");
                } else {
                    query.append("select * from diary.activities WHERE Date = \"" +
                            ParsingData.convertDateFor(spread, "For DB") + "\" ORDER BY StartsAt ASC;");
                }
            }
        }
        StringBuilder result = new StringBuilder();
        ResultSet rs = executeSelectQuery(query.toString());
        try {
            while (rs.next()) {
                result.append(rs.getString(columnLabel)).append("\n");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result.toString().split("\n");
    }

    // Get a random motivate quot for today
    public static String getPhrase() {
        StringBuilder result = new StringBuilder();
        Random rd = new Random();
        int id = (Math.abs(rd.nextInt()) % 16) + 1;
        String query = "SELECT * FROM diary.dayphrase WHERE id_phrase = " + id + ";";
        ResultSet rs = executeSelectQuery(query);
        try {
            while (rs.next())
                result.append(rs.getString("phrase")).append(" ");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result.toString();
    }


    public static int parsingTimeDuration(String StartsAt)
    {
        int result;

        result = Integer.parseInt(StartsAt.split(":")[0]) * 60 + Integer.parseInt(StartsAt.split(":")[1]);


        return result;
    }

//    public static void jopaVGovne() {
//        String queryTwo = "SELECT * FROM diary.activities WHERE Date = DATE_ADD(\" 2020-12-01 \", INTERVAL 1 DAY);";
//        ResultSet rs = executeSelectQuery(queryTwo);
//        try {
//            System.out.print(rs.first());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//    }

    public static void checkIntersected(String dateOfEvent, String startOfEvent, String durationOfEvent){

        String queryOne = "SELECT * FROM diary.activities WHERE Date = \"" + dateOfEvent + "\";";
        String queryTwo = "SELECT * FROM diary.activities WHERE Date = DATE_ADD(\"" + dateOfEvent + "\", INTERVAL 1 DAY);";
// не рассматривает дела из предыдущего дня, которые накладываются на день назначения эвента, продублировать все что происходит со следующим днем на предыдущий
// найти функцию обратную DATE_ADD, занести всё в единую функцию
//        StringBuilder resultStartsAt = new StringBuilder();
//        StringBuilder resultDuration = new StringBuilder();
        StringBuilder idIntersectedEvent = new StringBuilder();

        int eventStart = Integer.parseInt(startOfEvent.split(":")[0]) * 60 + Integer.parseInt(startOfEvent.split(":")[1]);
        int eventStop = eventStart + Integer.parseInt(durationOfEvent);

        ResultSet rsOne = executeSelectQuery(queryOne);
        ResultSet rsTwo = executeSelectQuery(queryTwo);

        Integer nextEventStart;
        Integer nextEventStop;

        try {
            while (rsOne.next()) {

//                String a = rsOne.getString("StartsAt");
//                String b = rsOne.getString("StartsAt");

                nextEventStart = Integer.parseInt(rsOne.getString("StartsAt").split(":")[0]) * 60
                        + Integer.parseInt(rsOne.getString("StartsAt").split(":")[1]);
                nextEventStop = nextEventStart + Integer.parseInt(rsOne.getString("Duration"));

                if ((eventStart < nextEventStart && nextEventStart < eventStop) || (nextEventStop < eventStart && nextEventStop < eventStop) &&
                        rsOne.getString("Intersected").equals("0"))
                {
                    idIntersectedEvent.append(rsOne.getString("id_activity")).append(" ");
                }

                if (rsTwo.first()) {
                    nextEventStart = Integer.parseInt(rsTwo.getString("StartsAt").split(":")[0]) * 60 + Integer.parseInt(rsTwo.getString("StartsAt").split(":")[0]) + 24 * 60;
                    nextEventStop = nextEventStart + Integer.parseInt(rsTwo.getString("Duration")) + 24 * 60;

                    if ((eventStart < nextEventStart && nextEventStart < eventStop) || (nextEventStop < eventStart && nextEventStop < eventStop) &&
                            rsTwo.getString("Intersected").equals("0")) {
                        idIntersectedEvent.append(rsTwo.getString("id_activity")).append(" ");
                    }
                }
//                resultStartsAt.append(rsTwo.getString("StartsAt")).append(" ");
//                // 12:00:00, 13:00:00, 14:00:00
//                resultDuration.append(rsTwo.getString("Duration")).append(" ");
//                // 16, 26, 91


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        System.out.print(Arrays.toString(idIntersectedEvent.toString().split(" ")));

        /* SELECT * FROM diary.activities WHERE (Date = "2020-11-30" or Date = DATE_ADD("2020-11-30", INTERVAL 1 DAY))
and addtime(StartsAt, "00:15:00") <= "01:10:00";*/

//        SELECT * FROM diary.activities WHERE Date >= DATE_ADD(\"" + dateOfEvent + "\", INTERVAL 1 DAY);
//    return false;
    }

    public static void deletePastEvents() {
        String currentTime = ParsingData.getCurrentTime();
        String query = "DELETE FROM diary.activities WHERE Date < \"" + currentDate + "\" or (Date = \"" +
                currentDate + "\" and StartsAt < \"" + currentTime + "\");";
        executeDUIQuery(query);
    }

    public static void deleteEvent(String dateOfEvent, String startTimeOfEvent, String durationOfEvent, String placeOfEvent) {

        String query = "DELETE FROM diary.activities WHERE Date = \"" + dateOfEvent + "\" and StartsAt = \"" +
                startTimeOfEvent + "\" and Duration = " + durationOfEvent + " and Place = \"" + placeOfEvent + "\";";
        System.out.println(query);
        executeDUIQuery(query);
    }

    public static void changeEvent(String nameOfEvent, String dateOfEvent, String startTimeOfEvent, String durationOfEvent, String placeOfEvent,
                                   String newNameOfEvent, String newDateOfEvent, String newStartTimeOfEvent, String newDurationOfEvent, String newPlaceOfEvent) {

        String query = "UPDATE diary.activities SET " + "Date = \""
                + newDateOfEvent + "\", Name = \"" + newNameOfEvent + "\", StartsAt = \"" + newStartTimeOfEvent + "\", Duration = "
                + newDurationOfEvent + ", Place = \"" + newPlaceOfEvent + "\" WHERE Date = \"" + dateOfEvent + "\" and StartsAt = \"" + startTimeOfEvent +
                "\" and Duration = " + durationOfEvent + " and Place = \"" + placeOfEvent + "\";";
        executeDUIQuery(query);
    }


    public static void addEvent(String nameEvent, String dateOfEvent, String startTimeOfEvent, String durationOfEvent, String placeOfEvent) {

        String queryForMaxId = "SELECT MAX(id_activity) FROM diary.activities";
        StringBuilder result = new StringBuilder();
        ResultSet rs = executeSelectQuery(queryForMaxId);
        try {
            while (rs.next())
                result.append(rs.getString("MAX(id_activity)"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String query = "INSERT INTO diary.activities VALUES (" + (Integer.parseInt(result.toString()) + 1) + ", \""
                + nameEvent +"\", \"" + dateOfEvent + "\", \"" + startTimeOfEvent + "\", "
                + durationOfEvent + ", \"" + placeOfEvent + "\"" + ", 0);";
        System.out.println(query);
        executeDUIQuery(query);
    }


    public static ResultSet executeSelectQuery(String query) {
        try {
            // Opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // Getting Statement object to execute query
            stmt = con.createStatement();

            // Executing SELECT query
            rs = stmt.executeQuery(query);

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
        }
        return rs;
    }

    public static void executeDUIQuery(String query) {
        int countRS = 0;
        try {
            // Opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // Getting Statement object to execute query
            stmt = con.createStatement();

            countRS = stmt.executeUpdate(query);

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
        }
    }

}
