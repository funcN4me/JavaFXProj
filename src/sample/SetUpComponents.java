package sample;

import com.sun.javafx.geom.ConcentricShapePair;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.naming.ldap.Control;
import java.util.concurrent.ConcurrentHashMap;


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
            pane.setStyle("-fx-border-color: #38ee00;"); // Set up green color
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

        String[] nameLabels = DBFuncs.getInfoFor("Name", spread);
        String[] dateLabels = DBFuncs.getInfoFor("Date", spread);
        String[] startsAtLabels = DBFuncs.getInfoFor("StartsAt", spread);
        String[] durationLabels = DBFuncs.getInfoFor("Duration", spread);
        String[] placeLabels = DBFuncs.getInfoFor("Place", spread);

        Button doneBtn = new Button("I've done with it!");
        Button editEventBtn = new Button("Edit this event");

        editEventBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.myControllerHandle.setVBoxStatus(true);
                Main.myControllerHandle.updateChangeOutput(nameLabels[currentPosition], dateLabels[currentPosition], startsAtLabels[currentPosition], durationLabels[currentPosition], placeLabels[currentPosition]);
                Main.myControllerHandle.oldData = new String[]{nameLabels[currentPosition], dateLabels[currentPosition], startsAtLabels[currentPosition], durationLabels[currentPosition], placeLabels[currentPosition]};
//                Main.myControllerHandle.confirmChanges();
            }
        });

        doneBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.setStyle("-fx-border-color: #38ee00;");
                DBFuncs.deleteEvent(dateLabels[currentPosition], startsAtLabels[currentPosition], durationLabels[currentPosition], placeLabels[currentPosition]);
            }
        });

        grid.add(new Label("Date of event: " + ParsingData.convertDateFor(dateLabels[currentPosition], "For User")), 0,1);
        grid.add(new Label("Event starts at: " + startsAtLabels[currentPosition]), 0, 2);
        grid.add(new Label("Duration of event: " + durationLabels[currentPosition]), 0,3);
        grid.add(new Label("Where it will be: " + placeLabels[currentPosition]), 0,4);
        grid.add(doneBtn, 0, 5);
        grid.add(editEventBtn, 0, 6);

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
                if (differences[2] <= 30) {
                    pane.setStyle("-fx-border-color: #ff6b6b;"); // Set up red color
                }
            }
            else
                pane.setStyle("-fx-border-color: #5693f5;"); // Set up blue color
        }
        else
            pane.setStyle("-fx-border-color: #5693f5;"); // Set up blue color
    }

//    public static boolean setUpEditEventBtn() {
//        boolean a;
//        editEventBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//            }
//        });
//
//
//    }
}
