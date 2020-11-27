package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 * @author Javin Paul
 */
public class DBFuncs {
    public static String currentDate = ParsingData.getCurrentDate();
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://127.0.0.1:3306/departmentHR?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "JustWinners122";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static String[] getInfoFor(String columnLabel, String spread) {
        // Making query with dependencies of spread that user choose
        String currentTime = ParsingData.getCurrentTime();
        StringBuilder query = new StringBuilder();
        switch (spread) {
            case "Upcoming" -> query.append("SELECT * FROM diary.activities WHERE Date > \"" + currentDate +
                    "\" or (Date = \"" + currentDate + "\" and StartsAt >= \"" + currentTime + "\") ORDER BY Date, StartsAt ASC LIMIT 1;");
            case "Today" -> query.append("select * from diary.activities where Date = \"" + currentDate + "\" and StartsAt >= \"" + currentTime + "\" ORDER BY Date ASC, StartsAt ASC;");
            case "Week" -> query.append("select * from diary.activities where Date > \"" + currentDate + "\" and Date <= \"" + ParsingData.countSmth(currentDate, "Week") +
                    "\" or (Date = \"" + currentDate + "\" and StartsAt >= \"" + currentTime +"\") ORDER BY Date ASC, StartsAt ASC;");
            case "Month" -> query.append("select * from diary.activities where Date > \"" + currentDate + "\" and Date <= \"" + ParsingData.countSmth(currentDate, "Month") +
                    "\" or (Date = \"" + currentDate + "\" and StartsAt >= \"" + currentTime +"\") ORDER BY Date ASC, StartsAt ASC;");
            default -> query.append("select * from diary.activities");
        }
//        System.out.println(query.toString());
        StringBuilder result = new StringBuilder();
        ResultSet rs = executeQueryWithConnection(query.toString());
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
        ResultSet rs = executeQueryWithConnection(query);
        try {
            while (rs.next())
                result.append(rs.getString("phrase")).append(" ");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result.toString();
    }

    public static ResultSet executeQueryWithConnection(String query) {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return rs;
    }
}