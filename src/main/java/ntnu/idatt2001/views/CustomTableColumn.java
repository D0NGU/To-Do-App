package ntnu.idatt2001;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * class for creating a custom table column (column to hold a button etc.)
 */
public class CustomTableColumn extends TableColumn {
    ToDoApp app;

    /**
     * constructor for creating a custom checkbox or priority column
     * @param checkboxColumn - true if the column is a checkboxcolum
     * @param app - the application that uses the method
     */
    public CustomTableColumn(Boolean checkboxColumn, ToDoApp app) {
        super();
        this.app = app;
        if(checkboxColumn){
            checkBoxColumnCreator();
        }else{
            priorityColumnCreator();
        }
    }

    /**
     * constructor for creating a custom date column
     * @param typeOfDate - what type of date is shown in the column, either "Deadline" or "Finish date"
     * @param app - the application that uses the method
     */
    public CustomTableColumn(String typeOfDate, ToDoApp app){
        super(typeOfDate);
        this.app = app;
        if(typeOfDate.equalsIgnoreCase("Deadline")){
            dateColumnCreator(typeOfDate);
        }else if(typeOfDate.equalsIgnoreCase("Finish date")){
            dateColumnCreator(typeOfDate);
        }else if (typeOfDate.equalsIgnoreCase("Task")){
            taskColumnCreater();
        }
    }

    /**
     * constructor for creating a custom button column
     * @param typeOfTable - the name of the table that will contain the column (to do, doingLeft, doingRight, done)
     * @param typeOfColumn -
     * @param app - the application that uses the method
     */
    public CustomTableColumn(String typeOfTable, String typeOfColumn, ToDoApp app){
        super();
        this.app = app;
        if(typeOfTable.equalsIgnoreCase("to do")){
            buttonColumnCreator("to do");
        }else if(typeOfTable.equalsIgnoreCase("doingLeft")){
            buttonColumnCreator("doingLeft");
        }else if(typeOfTable.equalsIgnoreCase("doingRight")){
            buttonColumnCreator("doingRight");
        }
        else if(typeOfTable.equalsIgnoreCase("done")){
            buttonColumnCreator("done");
        }
    }

    private void categoryColumnCreator(){
        //changing the width of the column
        super.setPrefWidth(100);
        //making it so that the user cant move the columns around or resize the
        super.setReorderable(false);
        super.setResizable(false);
        //deciding what the cells in this column will contain
        super.setCellFactory(column -> new TableCell<Category, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { //If the cell is empty
                    setText(null);
                    setStyle("");
                } else { //If the cell is not empty
                    Category category = getTableView().getItems().get(getIndex());
                    setTextFill(Color.BLACK);
                    setText(category.getName());
                }
            }
        });
    }

    private void taskColumnCreater(){
        super.setCellFactory(column -> new TableCell<Task, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty); //This is mandatory

                if (empty) { //If the cell is empty
                    setText(null);
                    setStyle("");
                } else { //If the cell is not empty
                    Task task = getTableView().getItems().get(getIndex());
                    setText(task.getName());
                    setAlignment(Pos.CENTER);

                    Tooltip tooltip = new Tooltip("Double click to view more");
                    tooltip.setShowDelay(Duration.millis(200));
                    setTooltip(tooltip);
                }
            }
        });
    }

    /**
     * method that creates a new checkbox column
     */
    private void checkBoxColumnCreator(){
        //the table that will contain the checkbox
        super.setPrefWidth(30);
        //making it not possible for user to resize the column or reorder it
        super.setResizable(false);
        super.setReorderable(false);
        super.setCellFactory(column -> new TableCell<Category, Void>() {

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                CheckBox checkBox = new CheckBox();
                {
                    checkBox.setSelected(true);
                    app.update();
                    checkBox.setOnAction((ActionEvent event) -> {
                        Category category = getTableView().getItems().get(getIndex());
                        Tooltip tooltip = new Tooltip();
                        if(!checkBox.isSelected()){
                            category.setShowing(false);
                            app.getData().getAllTasks().stream().filter(t -> t.getCategory().getName().equals(category.getName())).forEach(t -> t.getCategory().setShowing(false));
                            app.update();
                            tooltip = new Tooltip("Show tasks of this category");
                        }else if(checkBox.isSelected()){
                            category.setShowing(true);
                            app.getData().getAllTasks().stream().filter(t -> t.getCategory().getName().equals(category.getName())).forEach(t -> t.getCategory().setShowing(true));
                            app.update();
                            tooltip = new Tooltip("Hide tasks of this category");
                        }
                        tooltip.setShowDelay(Duration.millis(300));
                        checkBox.setTooltip(tooltip);
                    });
                    Tooltip tooltip = new Tooltip("Hide tasks of this category");
                    tooltip.setShowDelay(Duration.millis(300));
                    checkBox.setTooltip(tooltip);
                }
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(checkBox);
                }
            }
        });
    }

    /**
     * method that creates a new priority column
     */
    private void priorityColumnCreator() {
        super.setPrefWidth(20);
        super.setResizable(false);
        super.setReorderable(false);

        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty); //This is mandatory

                if (empty) { //If the cell is empty
                    setText(null);
                    setStyle("");
                } else { //If the cell is not empty
                    Task task = getTableView().getItems().get(getIndex());

                    if (task.getPriority() == 1) {

                        ImageView i = new ImageView(new Image("LowPriority.png"));
                        i.setFitHeight(20);
                        i.setFitWidth(20);
                        setAlignment(Pos.CENTER);
                        setGraphic(i);

                    } else if (task.getPriority() == 2){

                        ImageView i = new ImageView(new Image("MediumPriority.png"));
                        i.setFitHeight(20);
                        i.setFitWidth(20);
                        setAlignment(Pos.CENTER);
                        setGraphic(i);

                    }else if (task.getPriority() == 3){

                        ImageView i = new ImageView(new Image("HighPriority.png"));
                        i.setFitHeight(20);
                        i.setFitWidth(20);
                        setAlignment(Pos.CENTER);
                        setGraphic(i);

                    }
                }
            }
        });

    }

    /**
     * method that creates a new date column
     * @param typeOfDate - what sort of date the column will contain, either deadline og finishdate
     */
    private void dateColumnCreator(String typeOfDate){
        super.setPrefWidth(85);
        super.setResizable(false);
        super.setReorderable(false);

        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty); //This is mandatory

                if (empty) { //If the cell is empty
                    setText(null);
                    setStyle("");
                } else { //If the cell is not empty
                    Task task = getTableView().getItems().get(getIndex());
                    if(typeOfDate.equalsIgnoreCase("Deadline")){
                        LocalDateTime taskDeadline = task.getDeadline();
                        if(taskDeadline == null){
                            setText("No deadline");
                            setTextFill(Color.BLACK);
                        }else{
                            String textDate = taskDeadline.getDayOfMonth()+"/"+taskDeadline.getMonthValue()+"/"+taskDeadline.getYear();
                            String textTime = taskDeadline.getHour()+":"+new DecimalFormat("00").format(taskDeadline.getMinute());
                            setText(textDate+"\n"+textTime);

                            if(taskDeadline.isBefore(LocalDateTime.now())){
                                setText("! "+textDate+"\n"+textTime);
                                setTextFill(Color.RED);
                                Tooltip tooltip = new Tooltip("Deadline has passed");
                                tooltip.setShowDelay(Duration.millis(200));
                                setTooltip(tooltip);
                            }else{
                                setTextFill(Color.BLACK);
                            }
                        }

                    }else if(typeOfDate.equalsIgnoreCase("Finish date")){
                        LocalDateTime taskFinishDate = task.getFinishDate();

                        String textDate = taskFinishDate.getDayOfMonth()+"/"+taskFinishDate.getMonthValue()+"/"+taskFinishDate.getYear();
                        String textTime = taskFinishDate.getHour()+":"+new DecimalFormat("00").format(taskFinishDate.getMinute());

                        setText(textDate+"\n"+textTime);
                    }

                }
            }
        });
    }

    /**
     * method to create a new button column
     * @param tableName - the name of the table that will contain the column (to do, doingLeft, doingRight, done)
     */
    private void buttonColumnCreator(String tableName){
        super.setPrefWidth(40);
        //making it not possible for user to resize the column or reorder it
        super.setResizable(false);
        super.setReorderable(false);

        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                //button that pushes task to the right
                Button btn = new Button();
                ImageView rightArrow = new ImageView(new Image("ArrowToRight.png"));
                rightArrow.setFitHeight(20);
                rightArrow.setFitWidth(20);
                btn.setGraphic(rightArrow);
                btn.getStyleClass().add("arrow-buttons");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        Task data = getTableView().getItems().get(getIndex());
                        //checking what status the task is in, so that the status can be changed correctly
                        //after clicking the -> button
                        if(data.getStatus().equals("to do")){
                            data.setStatus("doing");
                        }else if(data.getStatus().equals("doing")){
                            data.setStatus("done");
                        }
                        //updating the tasklist to contain the changed task
                        app.update();
                    });
                    Tooltip tooltip = new Tooltip("Move task to right");
                    tooltip.setShowDelay(Duration.millis(300));
                    btn.setTooltip(tooltip);
                }

                //button that pushes task to the left
                Button btn2 = new Button();
                ImageView leftArrow = new ImageView(new Image("ArrowToLeft.png"));
                leftArrow.setFitHeight(20);
                leftArrow.setFitWidth(20);
                btn2.setGraphic(leftArrow);
                btn2.getStyleClass().add("arrow-buttons");

                {
                    btn2.setOnAction((ActionEvent event) -> {
                        Task data = getTableView().getItems().get(getIndex());
                        //checking what status the task is in, so that the status can be changed correctly
                        //after clicking the <- button
                        if(data.getStatus().equals("doing")){
                            data.setStatus("to do");
                        }else if(data.getStatus().equals("done")){
                            data.setStatus("doing");
                        }
                        //updating the tasklist to contain the changed task
                        app.update();
                    });
                    Tooltip tooltip = new Tooltip("Move task to left");
                    tooltip.setShowDelay(Duration.millis(300));
                    btn2.setTooltip(tooltip);
                }
                if (empty) {
                    setGraphic(null);
                } else {
                    //adding the -> button to only to do and doing column
                    if(tableName.equals("to do") || tableName.equals("doingRight")){
                        setGraphic(btn);
                        //adding the <- button to only doing and done column
                    }else if(tableName.equals("doingLeft") || tableName.equals("done")){
                        setGraphic(btn2);
                    }
                }
            }
        });
    }
}

