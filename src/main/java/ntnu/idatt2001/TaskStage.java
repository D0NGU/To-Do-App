package ntnu.idatt2001;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * class that represents one task stage
 */
public class TaskStage extends Stage {
    public enum Mode {
        NEW, VIEW
    }

    private final Mode mode;
    private Task existingTask = null;
    private Task result;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private TextField taskNameField = new TextField();
    private TextArea taskDescriptionField = new TextArea();
    private TextField deadlineTime = new TextField();
    private TextField startDateTime = new TextField(LocalTime.now().format(timeFormat));
    private DatePicker deadlineDate = new DatePicker();
    private DatePicker startDateDate = new DatePicker(LocalDate.now());
    private TextField taskCategoryField = new TextField();
    private ChoiceBox<String> priorityChoiceBox = new ChoiceBox<>();
    private ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
    private String textFinishDateTime;


    /**
     * Constructor to create a new add task stage
     */
    public TaskStage() {
        super();
        this.mode = Mode.NEW;
        createStage();
    }

    /**
     * Constructor to create a new view task stage
     * @param task - the task to view
     */
    public TaskStage(Task task) {
        super();
        this.mode = Mode.VIEW;
        this.existingTask = task;
        createStage();
    }

    /**
     * method to get the task from createStage() method
     * @return a task if save or delete is clicked, null if edit or cancel is clicked
     */
    public Task getResult() {
        return result;
    }

    /**
     * method to create the stage
     */
    private void createStage() {
        taskNameField.setPromptText("Task name");
        taskCategoryField.setPromptText("Category");
        taskDescriptionField.setPromptText("Task description");
        deadlineTime.setPromptText("hh:mm");
        deadlineTime.setPrefWidth(70);
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
            gpTaskPane.add(addButton, 5, 9);
            gpTaskPane.add(cancelButton, 1, 9);
            GridPane.setHalignment(addButton, HPos.RIGHT);
            GridPane.setHalignment(cancelButton, HPos.LEFT);
        } else if (mode == Mode.VIEW) {
            gpTaskPane.add(saveButton, 5, 9);
            gpTaskPane.add(deleteButton, 1, 9);
            GridPane.setHalignment(saveButton, HPos.RIGHT);
            GridPane.setHalignment(deleteButton, HPos.LEFT);

            //filling in the correct info about the task
            taskDescriptionField.setText(existingTask.getDescription());
            taskNameField.setText(existingTask.getName());
            taskCategoryField.setText(existingTask.getCategory().getName());
            startDateTime.setText(existingTask.getStartDate().toLocalTime().format(timeFormat));
            startDateDate.setValue(existingTask.getStartDate().toLocalDate());
            statusChoiceBox.setValue(existingTask.getStatus());
            if(existingTask.getStatus().equals("done")) {
                textFinishDateTime = existingTask.getFinishDate().getDayOfMonth() + "/" + existingTask.getFinishDate().getMonthValue() + "/" + existingTask.getFinishDate().getYear() + " " +
                        existingTask.getFinishDate().getHour() + ":" + new DecimalFormat("00").format(existingTask.getFinishDate().getMinute());

            }else if(!existingTask.getStatus().equals("done")){
                textFinishDateTime = "Not Finished";
            }
            if(existingTask.getDeadline() == null){
                deadlineDate.setPromptText("No deadline");
            }else{
                deadlineDate.setValue(existingTask.getDeadline().toLocalDate());
                deadlineTime.setText(existingTask.getDeadline().toLocalTime().format(timeFormat));
            }

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
        gpTaskPane.add(taskDescriptionField, 2, 7, 4, 2);
        gpTaskPane.add(new Label("Description:"), 1, 7);
        gpTaskPane.add(new Label("Deadline:"), 1, 4);
        gpTaskPane.add(deadlineDate, 2, 4);
        gpTaskPane.add(new Label("Finish Date:"),1,5);
        if(mode == Mode.NEW){
            gpTaskPane.add(new Label("Not Finished"),2,5);
        } else if(mode == Mode.VIEW){
            gpTaskPane.add(new Label(textFinishDateTime),2,5);
        }
        gpTaskPane.add(deadlineTime, 3, 4);
        gpTaskPane.add(new Label("Startdate"), 1, 3);
        gpTaskPane.add(startDateDate, 2, 3);
        gpTaskPane.add(startDateTime, 3, 3);
        gpTaskPane.add(new Label("Category:"), 1, 6);
        gpTaskPane.add(taskCategoryField, 2, 6);
        gpTaskPane.add(new Label("Priority:"), 4, 3);
        gpTaskPane.add(priorityChoiceBox, 5, 3);
        gpTaskPane.add(new Label("Status:"), 4, 4);
        gpTaskPane.add(statusChoiceBox, 5, 4);

        //setting the scene with the gridpane
        Scene taskScene = new Scene(gpTaskPane, 460, 300);
        super.setScene(taskScene);
        super.initModality(Modality.APPLICATION_MODAL);

        addButton.setOnAction(actionEvent -> {
            //checks what priority it needs to set
            int priority = 1;
            if (priorityChoiceBox.getValue().equals("High")) {
                priority = 3;
            } else if (priorityChoiceBox.getValue().equals("Medium")) {
                priority = 2;
            } else if (priorityChoiceBox.getValue().equals("Low")) {
                priority = 1;
            }

            //checking if the category is empty or not
            Category category = null;
            if(taskCategoryField.getText().isBlank() ){
                category = new Category("Miscellaneous");
            } else {
                category = new Category(taskCategoryField.getText().trim());
            }

            if(validateInput().isBlank()){
                startDateDate.setValue(startDateDate.getConverter().fromString(startDateDate.getEditor().getText()));
                //checking whether the task has a deadline or not
                if(deadlineDate.getEditor().getText().isEmpty() && deadlineTime.getText().isBlank()){
                    //creating the new task without a deadline
                    result = new Task(taskNameField.getText().trim(), statusChoiceBox.getValue(), priority,
                            taskDescriptionField.getText(), LocalDateTime.of(startDateDate.getValue()
                            , LocalTime.parse(startDateTime.getText())), category);
                }else if (!(deadlineDate.getEditor().getText().isEmpty()) && deadlineTime.getText().isBlank()){
                    //if a deadline has a date but no time, then the time is automatically set to 23:59
                    deadlineDate.setValue(deadlineDate.getConverter().fromString(deadlineDate.getEditor().getText()));
                    deadlineTime.setText("23:59");
                    result = new Task(taskNameField.getText().trim(), statusChoiceBox.getValue(), priority,
                            taskDescriptionField.getText(), LocalDateTime.of(deadlineDate.getValue(), LocalTime.parse(deadlineTime.getText()))
                            , LocalDateTime.of(startDateDate.getValue(), LocalTime.parse(startDateTime.getText())), category);
                }else{
                    //creating the new task with a deadline
                    deadlineDate.setValue(deadlineDate.getConverter().fromString(deadlineDate.getEditor().getText()));
                    result = new Task(taskNameField.getText().trim(), statusChoiceBox.getValue(), priority,
                            taskDescriptionField.getText(), LocalDateTime.of(deadlineDate.getValue(), LocalTime.parse(deadlineTime.getText()))
                            , LocalDateTime.of(startDateDate.getValue(), LocalTime.parse(startDateTime.getText())), category);
                }

                super.close(); //closing the stage
            }else{
                createWarningAlert(validateInput());
            }


        });

        cancelButton.setOnAction(actionEvent -> {
            super.close(); //closing the stage
        });

        saveButton.setOnAction(actionEvent -> {
            //checks what priority it needs to set
            int priority = 1;
            if (priorityChoiceBox.getValue().equals("High")) {
                priority = 3;
            } else if (priorityChoiceBox.getValue().equals("Medium")) {
                priority = 2;
            } else if (priorityChoiceBox.getValue().equals("Low")) {
                priority = 1;
            }

            //checking if the category is empty or not
            Category category = null;
            if(taskCategoryField.getText().isBlank()){
                category = new Category("Miscellaneous");
            } else {
                category = new Category(taskCategoryField.getText().trim());
            }

            if(validateInput().isBlank()){
                //checking whether the task has a deadline or not
                if(deadlineDate.getEditor().getText().isEmpty() && deadlineTime.getText().isBlank()){
                    existingTask.setDeadline(null);
                } else if (!(deadlineDate.getEditor().getText().isEmpty()) && deadlineTime.getText().isBlank()){
                    deadlineDate.setValue(deadlineDate.getConverter().fromString(deadlineDate.getEditor().getText()));
                    deadlineTime.setText("23:59");
                    existingTask.setDeadline(LocalDateTime.of(deadlineDate.getValue(), LocalTime.parse(deadlineTime.getText())));
                } else {
                    deadlineDate.setValue(deadlineDate.getConverter().fromString(deadlineDate.getEditor().getText()));
                    existingTask.setDeadline(LocalDateTime.of(deadlineDate.getValue(), LocalTime.parse(deadlineTime.getText())));
                }

                //updating all the information about the edited task
                existingTask.setPriority(priority);
                existingTask.setName(taskNameField.getText());
                existingTask.setDescription(taskDescriptionField.getText());
                existingTask.setStatus(statusChoiceBox.getValue());
                startDateDate.setValue(startDateDate.getConverter().fromString(startDateDate.getEditor().getText()));
                existingTask.setStartDate(LocalDateTime.of(startDateDate.getValue(), LocalTime.parse(startDateTime.getText())));
                existingTask.setCategory(category);

                super.close(); //closing the stage
            }else{
                createWarningAlert(validateInput());
            }

        });

        deleteButton.setOnAction(actionEvent -> {
            result = existingTask;
            super.close(); //closing the stage
        });
    }

    /**
     * method to validate if all the input fields are valid or not
     * @return - an empty string if it is valid, a non empty string if it is not valid
     */
    private String validateInput(){
        String explanation = "";

        //checking if task name is not filled out
        if (taskNameField.getText().isBlank()) {
            explanation += "Task must have a name.\n";
        }

        //checking if deadline date is written in wrong format
        try {
            if(!(deadlineDate.getEditor().getText().isEmpty())){
                deadlineDate.getConverter().fromString(deadlineDate.getEditor().getText());
            }
        } catch (DateTimeParseException e) {
            explanation += "Deadline must be written like 'dd/mm/yyyy'.\n";
        }

        //checking if startdate is written in wrong format
        try {
            startDateDate.getConverter().fromString(startDateDate.getEditor().getText());
        } catch (DateTimeParseException e){
            explanation += "Startdate must be written like 'dd/mm/yyyy'.\n";
        }

        //checking if startdate time is written in wrong format
        try{
            LocalTime.parse(startDateTime.getText());
        }catch(DateTimeParseException e){
            explanation += "Startdate time must be written like 'hh:mm'.\n";
        }

        //checking if deadline time is written in wrong format
        try{
            if (!deadlineTime.getText().isBlank()) {
                LocalTime.parse(deadlineTime.getText());
            }
        }catch(DateTimeParseException e){
            explanation += "Deadline time must be written like 'hh:mm'.\n";
        }

        //checking if the user selected a time but not a date for deadline
        if (deadlineDate.getEditor().getText().isEmpty() && !(deadlineTime.getText().isEmpty())) {
            explanation += "When a deadline time is selected, you must also select a date.\n";
        }

        //checking if the user selected a time but not a date startdate
        if (startDateDate.getEditor().getText().isEmpty() && !(startDateTime.getText().isEmpty())) {
            explanation += "When a startdate time is selected, you must also select a date.\n";
        }

        //checking if both the time and date is empty for startdate, which all tasks must have
        if (startDateDate.getEditor().getText().isEmpty() && startDateTime.getText().isEmpty()) {
            explanation += "Your task must have a startdate. \n";
        }

        return explanation;
    }

    /**
     * method to create an alert box of type warning
     * @param explanation - feedback to the user on what went wrong
     */
    private void createWarningAlert(String explanation){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid input");
        alert.setHeaderText("Invalid input");
        //setting the content that is displayed inside the alert
        alert.setContentText(explanation);
        alert.showAndWait();
    }
}

