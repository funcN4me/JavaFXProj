package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class SetUpComponents {

    // Setting up an accordion for chosen spread
    public static Accordion setUpAccordion(Accordion activitiesAccordion, String spread) {
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
            pane.setStyle("-fx-border-color: #38ee00;");
            pane.setExpanded(false);
            activitiesAccordion.getPanes().add(pane);
        }

        return activitiesAccordion;
    }

    // Setting up content to the titled panes
    private static TitledPane setUpTitledPanel(TitledPane pane, String spread, int currentPosition) {
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
        setUpStyleForTitledPanel(pane, dateLabels, startsAtLabels, currentPosition);

        return pane;
    }

    // Setting up a timer for showing current time
    public static void setUpTimer(Label currentTimeLabel) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            currentTimeLabel.setText("Current time: " + ParsingData.getCurrentDate("forTimer") + " " + ParsingData.getCurrentTime());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    // Setting up style for titled pane depending on the remaining time before the event start
    private static void setUpStyleForTitledPanel(TitledPane pane, String[] dateLabels, String[] startsAtLabels, int currentPosition) {
        long[] differences = ParsingData.countDifference(dateLabels[currentPosition], startsAtLabels[currentPosition]);
        if (differences[0] == 0) {
            if (differences[1] == 0) {
                if (differences[2] <= 60 && differences[2] > 30)
                    pane.setStyle("-fx-border-color: #e8b20e;"); // Set up yellow color
                if (differences[2] <= 30)
                    pane.setStyle("-fx-border-color: #ff6b6b;"); // Set up red color
            }
            else
                pane.setStyle("-fx-border-color: #5693f5;"); // Set up blue color
        }
        else
            pane.setStyle("-fx-border-color: #5693f5;"); // Set up blue color
    }

}
