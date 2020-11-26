package sample;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Controller {
    private int hour, second, minute;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Accordion activitiesAccordion;

    @FXML
    private ComboBox activitiesComboBox;

    @FXML
    private URL location;

    @FXML
    private Label phraseOfDayLabel;

    @FXML
    private Button button1;

    @FXML
    private Label welcomeLabel;

//    @FXML
//    private TitledPane Smth;

    @FXML
    private Label currentTimeLabel;

    @FXML
    void Hello(ActionEvent event) {
        System.out.println("Privet");
    }

    @FXML
    void rebuildAccordion(ActionEvent event) {
        activitiesAccordion.getPanes().clear();
        System.out.println(activitiesComboBox.getValue().toString());

        if (activitiesComboBox.getValue().toString().contains("today"))
            setUpAccordion(activitiesAccordion, "Today");
        if (activitiesComboBox.getValue().toString().contains("week"))
            setUpAccordion(activitiesAccordion, "Week");
        if (activitiesComboBox.getValue().toString().contains("month"))
            setUpAccordion(activitiesAccordion, "Month");
        if (activitiesComboBox.getValue().toString().contains("upcoming"))
            setUpAccordion(activitiesAccordion, "Upcoming");
        }


    @FXML
    void initialize() {
        assert phraseOfDayLabel != null : "fx:id=\"phraseOfDayLabel\" was not injected: check your FXML file 'pizdec.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'pizdec.fxml'.";
        assert welcomeLabel != null : "fx:id=\"welcomeLabel\" was not injected: check your FXML file 'pizdec.fxml'.";
        phraseOfDayLabel.setText("Phrase of day: " + DBFuncs.getPhrase());
        activitiesComboBox.getItems().addAll("Your upcoming event", "Your events for today", "Your events for week", "Your events for month");
        setUpTimer();
        System.out.println("141414");
        setUpAccordion(activitiesAccordion, "Upcoming");
    }

    public void setUpTimer() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            currentTimeLabel.setText("Current time: " + ParsingData.getCurrentDate("forTimer") + " " + ParsingData.getCurrentTime());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public TitledPane setUpTitledPanel(TitledPane pane, String spread, int currentPosition) {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        String[] dateLabels = DBFuncs.getInfoFor("Date", spread);
        String[] startsAtLabels = DBFuncs.getInfoFor("StartsAt", spread);
        String[] durationLabels = DBFuncs.getInfoFor("Duration", spread);
        String[] placeLabels = DBFuncs.getInfoFor("Place", spread);

        grid.add(new Label("Date of event: " + dateLabels[currentPosition]), 0,1);
        grid.add(new Label("Event starting at: " + startsAtLabels[currentPosition]), 0, 2);
        grid.add(new Label("Duration of event: " + durationLabels[currentPosition]), 0,3);
        grid.add(new Label("Where it will be: " + placeLabels[currentPosition]), 0,4);

        pane.setContent(grid);
        return pane;
    }
    public Accordion setUpAccordion(Accordion activitiesAccordion, String spread) {
        String[] namesOfActivities = DBFuncs.getInfoFor("Name", spread);
        if (namesOfActivities[0].length() != 0) {

            for (int i = 0; i < namesOfActivities.length; i++) {
                TitledPane pane = new TitledPane();
                pane.setText(namesOfActivities[i]);
                pane = setUpTitledPanel(pane, spread, i);
                activitiesAccordion.getPanes().add(pane);
            }
        }
        else {
            TitledPane pane = new TitledPane();
            pane.setText("Nothing to do. Good job!");
            pane.setExpanded(false);
            activitiesAccordion.getPanes().add(pane);
        }

        return activitiesAccordion;
    }

}
