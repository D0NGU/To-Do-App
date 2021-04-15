package ntnu.idatt2001;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskStage extends Stage {
    public enum Mode {
        NEW, EDIT
    }

    private final Mode mode;
    private Task existingTask = null;
    private Task result;

    public TaskStage() {
        super();
        this.mode = Mode.NEW;
        createStage();
    }

    public TaskStage(Task task) {
        super();
        this.mode = Mode.EDIT;
        this.existingTask = task;
        createStage();
    }

    public Task getResult() {
        return result;
    }


    private void createStage() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        TextField taskNameField = new TextField();
        TextArea taskDescriptionField = new TextArea();
        TextField deadLineTime = new TextField();
        TextField startDateTime = new TextField(LocalTime.now().format(timeFormat));
        DatePicker deadlineDate = new DatePicker();
        DatePicker startDateDate = new DatePicker(LocalDate.now());
        TextField taskCategoryField = new TextField();
        ChoiceBox<String> priorityChoiceBox = new ChoiceBox<>();
        ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();

        taskNameField.setPromptText("Task name");
        taskCategoryField.setPromptText("Category");
        taskDescriptionField.setPromptText("Task description");
        deadLineTime.setPromptText("hh:mm");
        deadLineTime.setPrefWidth(70);
        startDateTime.setPrefWidth(70);
        deadlineDate.setPrefWidth(110);
        startDateDate.setPrefWidth(110);
        taskCategoryField.setPrefWidth(110);
        taskDescriptionField.setPrefHeight(100);
        priorityChoiceBox.getItems().addAll("High", "Medium", "Low");
        priorityChoiceBox.setValue("High");
        statusChoiceBox.getItems().addAll("to do", "doing","done");
        statusChoiceBox.setValue("to do");

        //creating the gridpane
        GridPane gpTaskPane = new GridPane();
        gpTaskPane.setHgap(13);
        gpTaskPane.setVgap(6);
        gpTaskPane.setPadding(new Insets(10, 10, 10, 10));

        //adding and styling all the buttons
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #a3ffb3");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: tomato");
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: lightgreen");
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: tomato");

        //which buttons are added to the gridpane
        if (mode == Mode.NEW) {
            gpTaskPane.add(addButton, 5, 8);
            gpTaskPane.add(cancelButton, 1, 8);
            GridPane.setHalignment(addButton, HPos.RIGHT);
            GridPane.setHalignment(cancelButton, HPos.LEFT);
        } else if (mode == Mode.EDIT) {
            gpTaskPane.add(saveButton, 5, 8);
            gpTaskPane.add(deleteButton, 1, 8);
            GridPane.setHalignment(saveButton, HPos.RIGHT);
            GridPane.setHalignment(deleteButton, HPos.LEFT);

            //filling in the correct info about the task
            taskDescriptionField.setText(existingTask.getDescription());
            taskNameField.setText(existingTask.getName());
            taskCategoryField.setText(existingTask.getCategory().getName());
            startDateTime.setText(existingTask.getStartDate().toLocalTime().format(timeFormat));
            startDateDate.setValue(existingTask.getStartDate().toLocalDate());
            deadlineDate.setValue(existingTask.getDeadline().toLocalDate());
            deadLineTime.setText(existingTask.getDeadline().toLocalTime().format(timeFormat));
            statusChoiceBox.setValue(existingTask.getStatus());

            if (existingTask.getPriority() == 1) {
                priorityChoiceBox.setValue("Low");
            } else if (existingTask.getPriority() == 2) {
                priorityChoiceBox.setValue("Medium");
            } else if (existingTask.getPriority() == 3) {
                priorityChoiceBox.setValue("High");
            }
        }

        //gpTaskPane.add(outsideBox, 0, 0, 7, 10);
        gpTaskPane.add(taskNameField, 1, 1, 5, 1);
        gpTaskPane.add(taskDescriptionField, 2, 6, 4, 2);
        gpTaskPane.add(new Label("Description:"), 1, 6);
        gpTaskPane.add(new Label("Deadline:"), 1, 4);
        gpTaskPane.add(deadlineDate, 2, 4);
        gpTaskPane.add(deadLineTime, 3, 4);
        gpTaskPane.add(new Label("Startdate"), 1, 3);
        gpTaskPane.add(startDateDate, 2, 3);
        gpTaskPane.add(startDateTime, 3, 3);
        gpTaskPane.add(new Label("Category:"), 1, 5);
        gpTaskPane.add(taskCategoryField, 2, 5);
        gpTaskPane.add(new Label("Priority:"), 4, 3);
        gpTaskPane.add(priorityChoiceBox, 5, 3);
        gpTaskPane.add(new Label("Status:"), 4, 4);
        gpTaskPane.add(statusChoiceBox, 5, 4);

        Scene taskScene = new Scene(gpTaskPane, 460, 300);
        super.setScene(taskScene);
        super.initModality(Modality.APPLICATION_MODAL);

        addButton.setOnAction(actionEvent -> {
            //checks what priority it needs to set
            int priority2 = 1;
            if (priorityChoiceBox.getValue().equals("High")) {
                priority2 = 3;
            } else if (priorityChoiceBox.getValue().equals("Medium")) {
                priority2 = 2;
            } else if (priorityChoiceBox.getValue().equals("Low")) {
                priority2 = 1;
            }
            result = new Task(taskNameField.getText(), statusChoiceBox.getValue(), priority2,
                    taskDescriptionField.getText(), LocalDateTime.of(deadlineDate.getValue(), LocalTime.parse(deadLineTime.getText()))
                    , LocalDateTime.of(startDateDate.getValue(), LocalTime.parse(startDateTime.getText()))
                    , new Category(taskCategoryField.getText(), ""));
            super.close();
        });

        cancelButton.setOnAction(actionEvent -> {
            super.close();
        });

        saveButton.setOnAction(actionEvent -> {
            int priority = 1;
            if (priorityChoiceBox.getValue().equals("High")) {
                priority = 3;
            } else if (priorityChoiceBox.getValue().equals("Medium")) {
                priority = 2;
            } else if (priorityChoiceBox.getValue().equals("Low")) {
                priority = 1;
            }
            existingTask.setPriority(priority);
            existingTask.setName(taskNameField.getText());
            existingTask.setDescription(taskDescriptionField.getText());
            existingTask.setStatus(statusChoiceBox.getValue());
            existingTask.setDeadline(LocalDateTime.of(deadlineDate.getValue(), LocalTime.parse(deadLineTime.getText())));
            existingTask.setStartDate(LocalDateTime.of(startDateDate.getValue(), LocalTime.parse(startDateTime.getText())));
            existingTask.setCategory(new Category(taskCategoryField.getText(), ""));

            super.close();
        });

        deleteButton.setOnAction(actionEvent -> {
            result = existingTask;
            super.close();
        });
    }
}
