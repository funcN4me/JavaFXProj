package sample;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.sound.midi.SysexMessage;

public class Controller {
    @FXML
    public String[] oldData;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Accordion activitiesAccordion;

    @FXML
    private ComboBox activitiesComboBox;

    @FXML
    private ScrollPane scrollPaneForAccordion;

    @FXML
    private TextField changeName;

    @FXML
    private VBox VBoxChanges;

    @FXML
    private Label editEventModeLabel;

    @FXML
    private Label addEventModeLabel;

    @FXML
    private Label setDurationLabel;

    @FXML
    private Label setStartTimeLabel;

    @FXML
    private URL location;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label phraseOfDayLabel;

    @FXML
    private DatePicker changeDatePicker;

    @FXML
    void Dada(MouseDragEvent event) {
        System.out.println("jopa");
    }

    @FXML
    private Slider statusSlider;

    @FXML
    private TextField changeHours;

    @FXML
    private TextField changeMinutes;

    @FXML
    void addEventAction(ActionEvent event) {
        String[] timeForDB = {changeHours.getText(), changeMinutes.getText(), changeSeconds.getText()};
        boolean check = true;
        if (!addActionDefender(changeName.getText())) {
            check = false;
            changeName.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changeDatePicker.getEditor().getText())) {
            check = false;
            changeDatePicker.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changeHours.getText())) {
            check = false;
            changeHours.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changeMinutes.getText())) {
            check = false;
            changeMinutes.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changeSeconds.getText())) {
            check = false;
            changeSeconds.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changeStartHours.getText())) {
            check = false;
            changeStartHours.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changeStartMinutes.getText())) {
            check = false;
            changeStartMinutes.setStyle("-fx-border-color:  #cf044b");
        }
        if (!addActionDefender(changePlace.getText())) {
            check = false;
            changePlace.setStyle("-fx-border-color:  #cf044b");
        }
        if (check) {
            changePlace.setStyle("");
            changeStartMinutes.setStyle("");
            changeStartHours.setStyle("");
            changeSeconds.setStyle("");
            changeMinutes.setStyle("");
            changeHours.setStyle("");
            changeDatePicker.setStyle("");
            changeName.setStyle("");
            DBFuncs.addEvent(changeName.getText(), ParsingData.convertDateFor(changeDatePicker.getEditor().getText(),"For DB"), ParsingData.parseTime(timeForDB),
                    Integer.toString(Integer.parseInt(changeStartHours.getText()) * 60 + Integer.parseInt(changeStartMinutes.getText())), changePlace.getText());
        }

    }

    public static boolean addActionDefender(String nameField) {
        if (nameField.length() != 0)
            return true;
        return false;
    }

    @FXML
    private Button addEvent;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label secretModeLabel;

    @FXML
    private Label secretModeSecondLabel;

    @FXML
    private Button applyColorForWindowBtn;

    @FXML
    private Button applyColorForTextBtn;

    @FXML
    private TabPane tabPane;

    @FXML
    private Button resetColorBtn;

    @FXML
    void applyColorForText(ActionEvent event) {
        System.out.println(colorPicker.getValue().toString());
        phraseOfDayLabel.setStyle("-fx-text-fill: #" + colorPicker.getValue().toString().replace("0x", ""));
        welcomeLabel.setStyle("-fx-text-fill: #" + colorPicker.getValue().toString().replace("0x", ""));
        setDurationLabel.setStyle("-fx-text-fill: #" + colorPicker.getValue().toString().replace("0x", ""));
        setStartTimeLabel.setStyle("-fx-text-fill: #" + colorPicker.getValue().toString().replace("0x", ""));
        editEventModeLabel.setStyle("-fx-text-fill: #" + colorPicker.getValue().toString().replace("0x", ""));
        addEventModeLabel.setStyle("-fx-text-fill: #" + colorPicker.getValue().toString().replace("0x", ""));
    }

    @FXML
    void applyColorForWindow(ActionEvent event) {
        tabPane.setStyle("-fx-background-color: #" + colorPicker.getValue().toString().replace("0x", ""));
    }

    @FXML
    void resetBtn(ActionEvent event) {
        tabPane.setStyle("");
        phraseOfDayLabel.setStyle("");
        welcomeLabel.setStyle("");
        setDurationLabel.setStyle("");
        setStartTimeLabel.setStyle("");
        editEventModeLabel.setStyle("");
        addEventModeLabel.setStyle("");
    }


    @FXML
    private TextField changeSeconds;

    @FXML
    private TextField changeStartHours;

    @FXML
    private TextField changeStartMinutes;

    @FXML
    private TextField changePlace;

    @FXML
    private Button confirmChangesBtn;

    @FXML
    void confirmChanges(ActionEvent event) {

        System.out.println(Arrays.toString(oldData));
        String[] timeForDB = {changeHours.getText(), changeMinutes.getText(), changeSeconds.getText()};
        DBFuncs.changeEvent(oldData[0], oldData[1], oldData[2], oldData[3], oldData[4], changeName.getText(),
                ParsingData.convertDateFor(changeDatePicker.getEditor().getText(),"For DB") , ParsingData.parseTime(timeForDB), Integer.toString(Integer.parseInt(changeStartHours.getText()) * 60 + Integer.parseInt(changeStartMinutes.getText())), changePlace.getText());
    }


    @FXML
    void increaseStartHours(ScrollEvent event) {
        String hours = scrollDefender(changeStartHours.getText());
        int hour = Integer.parseInt(hours);

        if (event.getDeltaY() >= 0) {
            changeStartHours.setText(Integer.toString(++hour));
        }
        else {
            changeStartHours.setText(Integer.toString(--hour));
        }
    }


    @FXML
    void increaseStartMinutes(ScrollEvent event) {
        String minutes = scrollDefender(changeStartMinutes.getText());
        int minute = Integer.parseInt(minutes);

        if (event.getDeltaY() >= 0) {
            changeStartMinutes.setText(Integer.toString(++minute));
        }
        else {
            changeStartMinutes.setText(Integer.toString(--minute));
        }
    }

    @FXML
    void increaseHours(ScrollEvent event) {
        String hours = scrollDefender(changeHours.getText());
        int hour = Integer.parseInt(hours);

        if (event.getDeltaY() >= 0) {
            changeHours.setText(Integer.toString(++hour));
        }
        else {
            changeHours.setText(Integer.toString(--hour));
        }
    }


    @FXML
    void increaseMinutes(ScrollEvent event) {
        String minutes = scrollDefender(changeMinutes.getText());
        int minute = Integer.parseInt(minutes);

        if (event.getDeltaY() >= 0) {
            changeMinutes.setText(Integer.toString(++minute));
        }
        else {
            changeMinutes.setText(Integer.toString(--minute));
        }
    }

    @FXML
    void increaseSeconds(ScrollEvent event) {
        String seconds = scrollDefender(changeSeconds.getText());
        int second = Integer.parseInt(seconds);

        if (event.getDeltaY() >= 0) {
            changeSeconds.setText(Integer.toString(++second));
        }
        else {
            changeSeconds.setText(Integer.toString(--second));
        }
    }


    @FXML
    void Goodbye(MouseEvent event) {
        MouseButton mouseButton = event.getButton();
        int clicks = event.getClickCount();
        boolean status = colorPicker.isVisible();
        if (clicks == 5) {
            colorPicker.setVisible(!status);
            secretModeLabel.setVisible(!status);
            secretModeSecondLabel.setVisible(!status);
            applyColorForTextBtn.setVisible(!status);
            applyColorForWindowBtn.setVisible(!status);
            resetColorBtn.setVisible(!status);

        }
    }


    @FXML
    private Label welcomeLabel;

    @FXML
    private Label currentTimeLabel;


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
        datePicker.getEditor().clear();
        }

    @FXML
    void rebuildAccordionWithDatePicker(ActionEvent event) {
        activitiesAccordion.getPanes().clear();

        activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, datePicker.getEditor().getText());
    }


    @FXML
    void initialize() {
        assert phraseOfDayLabel != null : "fx:id=\"phraseOfDayLabel\" was not injected: check your FXML file 'pizdec.fxml'.";
        assert confirmChangesBtn != null : "fx:id=\"button1\" was not injected: check your FXML file 'pizdec.fxml'.";
        assert welcomeLabel != null : "fx:id=\"welcomeLabel\" was not injected: check your FXML file 'pizdec.fxml'.";

        phraseOfDayLabel.setText("Phrase of day: " + DBFuncs.getPhrase());
        activitiesComboBox.getItems().addAll("Your upcoming event", "Your events for today", "Your events for week", "Your events for month");

        SetUpComponents.setUpTimer(currentTimeLabel);
        activitiesAccordion = SetUpComponents.setUpAccordion(activitiesAccordion, "Upcoming"); // Set up accordion for upcoming event

        DBFuncs.deletePastEvents();
        changesDefender();
        sliderMode();
//        System.out.println(SetUpComponents.setUpEditEventBtn());

    }

    public void sliderMode(){
        statusSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                if (statusSlider.getValue() == 1)
                {
                    changeName.clear();
                    changeDatePicker.getEditor().clear();
                    changeHours.clear();
                    changeMinutes.clear();
                    changeSeconds.clear();
                    changeStartHours.clear();
                    changeStartMinutes.clear();
                    changePlace.clear();

                    addEvent.setVisible(true);
                    confirmChangesBtn.setVisible(false);
                    statusSlider.setDisable(true);
                    VBoxChanges.setStyle("-fx-border-color: linear-gradient(to bottom right, #00a159, #804b15, #393481); -fx-border-width:  6px");
                }
                if (statusSlider.getValue() == 0) {
                    VBoxChanges.setStyle("-fx-border-color:  linear-gradient(to top left, #772a9d, #0561a1, #690831); -fx-border-width:  6px");
                }
            }
        });
    }


    public void changesDefender() {
        changeHours.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        changeHours.setText(oldValue);
                    }
                    if ((Integer.parseInt(newValue) >= 23)) {
                        changeHours.setText("23");
                        System.out.println("ahha");
                    }
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Lol");
                }
            }
        });

        changeMinutes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        changeMinutes.setText(oldValue);
                    }
                    if ((Integer.parseInt(newValue) >= 59)) {
                        changeMinutes.setText("59");
                        System.out.println("ahha");
                    }
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Lol");
                }
            }
        });

        changeSeconds.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        changeSeconds.setText(oldValue);
                    }
                    if ((Integer.parseInt(newValue) >= 59)) {
                        changeSeconds.setText("59");
                        System.out.println("ahha");
                    }
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Lol");
                }
            }
        });

        changeStartMinutes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        changeStartMinutes.setText(oldValue);
                    }
                    if ((Integer.parseInt(newValue) >= 59)) {
                        changeStartMinutes.setText("59");
                        System.out.println("ahha");
                    }
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Lol");
                }
            }
        });

        changeStartHours.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        changeStartHours.setText(oldValue);
                    }
                    if ((Integer.parseInt(newValue) >= 23)) {
                        changeStartHours.setText("23");
                        System.out.println("ahha");
                    }
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Lol");
                }
            }
        });
    }

    public void setVBoxStatus(boolean status) {
        confirmChangesBtn.setVisible(status);

        statusSlider.setDisable(!status);

        addEvent.setVisible(!status);
        VBoxChanges.setVisible(status);

        statusSlider.setValue(status ? 0 : 1);
    }

    public void updateChangeOutput(String nameOfEvent, String dateOfEvent, String startTimeOfEvent, String durationOfEvent, String placeOfEvent) {
        String[] parsedStartTime = startTimeOfEvent.split(":");
        changeName.setText(nameOfEvent);
        changeDatePicker.getEditor().setText(ParsingData.convertDateFor(dateOfEvent, "For User"));
        changeStartHours.setText(Integer.toString(Integer.parseInt(durationOfEvent) / 60));
        changeStartMinutes.setText(Integer.toString(Integer.parseInt(durationOfEvent) % 60));
        changeHours.setText(parsedStartTime[0]);
        changeMinutes.setText(parsedStartTime[1]);
        changeSeconds.setText(parsedStartTime[2]);
        changePlace.setText(placeOfEvent);
    }

    public String scrollDefender(String field) {
        StringBuilder output = new StringBuilder();
        if (field.length() == 0) {
            output.append("00");
        }
        else
            return field;
        return output.toString();
    }

}
