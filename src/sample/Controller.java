package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {
    private int hour, second, minute;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Accordion activitiesAccordion;

    @FXML
    private ComboBox activitiesComboBox;

    @FXML
    private ScrollPane scrollPaneForAccordion;

    @FXML
    private URL location;

    @FXML
    private Label phraseOfDayLabel;

    @FXML
    private Button button1;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label currentTimeLabel;

    @FXML
    void Hello(ActionEvent event) {
        System.out.println("Privet");
    }

    // Rebuilding accordion for events output
    @FXML
    void rebuildAccordion(ActionEvent event) {
        activitiesAccordion.getPanes().clear();

        if (activitiesComboBox.getValue().toString().contains("today"))
            activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, "Today");
        if (activitiesComboBox.getValue().toString().contains("week"))
            activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, "Week");
        if (activitiesComboBox.getValue().toString().contains("month"))
            activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, "Month");
        if (activitiesComboBox.getValue().toString().contains("upcoming"))
            activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, "Upcoming");
        scrollPaneForAccordion.setContent(activitiesAccordion);
        }


    @FXML
    void initialize() {
        assert phraseOfDayLabel != null : "fx:id=\"phraseOfDayLabel\" was not injected: check your FXML file 'pizdec.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'pizdec.fxml'.";
        assert welcomeLabel != null : "fx:id=\"welcomeLabel\" was not injected: check your FXML file 'pizdec.fxml'.";

        phraseOfDayLabel.setText("Phrase of day: " + DBFuncs.getPhrase());
        activitiesComboBox.getItems().addAll("Your upcoming event", "Your events for today", "Your events for week", "Your events for month");

        SetUpComponents.setUpTimer(currentTimeLabel);
        activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, "Upcoming"); // Set up accordion for upcoming event
    }

}
