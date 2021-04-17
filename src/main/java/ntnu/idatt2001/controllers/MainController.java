package ntnu.idatt2001.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import ntnu.idatt2001.models.*;
import ntnu.idatt2001.views.*;
import java.util.Optional;

/**
 * A controller class which communicates with both the views and models classes
 */
public class MainController {
    private ToDoApp app;

    /**
     * constructor that creates a new controller
     * @param app - the application that created the controller
     */
    public MainController(ToDoApp app) {
        this.app = app;
    }

    /**
     * method to add a new task
     */
    public void addTask(){
        //creating the new window and waiting for the users choice
        TaskStage taskStage = new TaskStage();
        taskStage.setTitle("Add Task");
        taskStage.showAndWait();

        //if the result does not return null, it means the user clicked add task
        Task result = taskStage.getResult();
        if (result != null) {
            //if the addTask method returns false, then the task already exists
            if(!(app.getData().addTask(result))){
                showFailedAddTaskDialog();
            }
        }

        //checks if sortby has been picked and sorts after the value
        sortTasks();

        //updating the application so that it shows the new list
        app.update();

    }

    /**
     * method to sort the task lists
     */
    public void sortTasks() {
        //if the user has picked something to sort the lists by, then the value will not be null
        if(app.getSortBy().getValue() != null){
            //sorting the lists by the wanted value
            app.getData().sortLists(app.getSortBy().getValue().toString());
        }
        //updating the application to show the updated information
        app.update();
    }

    /**
     * method to edit the main title of the application
     */
    public void editTitle(){
        //creating the new window and waiting for the users choice
        TitleStage stageTitle = new TitleStage(new Text(app.getData().getTitleName()));
        stageTitle.setTitle("Edit title name");
        stageTitle.showAndWait();

        //if the result does not return null, it means the user clicked on edit title
        if(stageTitle.getResult() != null){
            //setting the title of the application
            app.getData().setTitleName(stageTitle.getResult());
            //updating the application to show the updated information
            app.update();
        }
    }

    /**
     * method to view a selected task
     * @param event - the mouse event that caused the call to this method
     * @param tableName - the name of the table which caused the call to this method
     */
    public void taskOnClick(MouseEvent event, String tableName) {
        Task taskToView = null;
        boolean notEmpty = false;
        //can decide how many clicks must happen before a window is opened
        if (event.getClickCount() > 1) {
            //checking whether the table clicked on is empty, because then no window should be shown
            if (app.getToDoTableView().getSelectionModel().getSelectedItem() == null && tableName.equalsIgnoreCase("toDo")) {
                notEmpty = true;
            } else if (app.getDoingTableView().getSelectionModel().getSelectedItem() == null && tableName.equalsIgnoreCase("doing")) {
                notEmpty = true;
            } else if (app.getDoneTableView().getSelectionModel().getSelectedItem() == null && tableName.equalsIgnoreCase("done")) {
                notEmpty = true;
            }

            //if table is not empty, then the user clicked on a task
            if (!notEmpty) {
                if (tableName.equalsIgnoreCase("toDo")) {
                    taskToView = app.getToDoTableView().getSelectionModel().getSelectedItem();
                } else if (tableName.equalsIgnoreCase("doing")) {
                    taskToView = app.getDoingTableView().getSelectionModel().getSelectedItem();

                } else if (tableName.equalsIgnoreCase("done")) {
                    taskToView = app.getDoneTableView().getSelectionModel().getSelectedItem();
                }

                //opens a new window to view the selected task
                TaskStage taskStage = new TaskStage(taskToView);
                taskStage.showAndWait();

                //get result returns a task, if its not null means the user clicked remove task
                if(taskStage.getResult() != null){
                    //checking if the user actually wants to delete the task
                    if (showDeleteConfirmationDialog()) {
                        app.getData().removeTask(taskStage.getResult());
                    }
                }

                //sort the list
                sortTasks();
                app.update();
            }
        }
    }

    /**
     * method to display a delete confirmation
     * @return true if the user confirms to delete the task
     */
    private boolean showDeleteConfirmationDialog() {
        boolean deleteConfirmed = false;

        //creating a new confirmation alert to check if the user wants to delete the chosen task or not
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Delete confirmation");
        //setting the content that is displayed inside the alert
        alert.setContentText("Are you sure you want to delete this task?");

        //showing the alert and waiting for the user to respond
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            //if the user clicked on the ok button, then the delete action is confirmed
            deleteConfirmed = (result.get() == ButtonType.OK);
        }
        return deleteConfirmed;
    }

    /**
     * method to display a dialog box telling the user that the task could not be added
     */
    private void showFailedAddTaskDialog(){
        //creating a new confirmation alert to tell the user that adding task failed
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Failure");
        alert.setHeaderText("Failure in adding a task");
        //setting the content that is displayed inside the alert
        String text = "The task already exists, and could therefore not be added. \n \n" +
                      "Two tasks are the same if they have the same name, description, category, " +
                      "startdate and deadline.";
        alert.setContentText(text);
        //showing the alert and waiting for the user to respond
        alert.showAndWait();
    }
}
