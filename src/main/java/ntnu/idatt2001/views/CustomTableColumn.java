package ntnu.idatt2001.views;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ntnu.idatt2001.models.Task;
import ntnu.idatt2001.models.Category;

/**
 * class for creating a custom table column (column to hold a button etc.)
 */
public class CustomTableColumn extends TableColumn {
    ToDoApp app;

    /**
     * constructor for creating a custom checkbox or priority column without a header name
     * @param checkboxColumn - true if the column is a checkboxcolumn
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
     * constructor for creating a custom date column with a header name
     * @param typeOfColumn - the column type and also its name
     * @param app - the application that uses the method
     */
    public CustomTableColumn(String typeOfColumn, ToDoApp app){
        super(typeOfColumn);
        this.app = app;
        if(typeOfColumn.equalsIgnoreCase("Deadline")){
            dateColumnCreator(typeOfColumn);
        }else if(typeOfColumn.equalsIgnoreCase("Finish date")){
            dateColumnCreator(typeOfColumn);
        }else if (typeOfColumn.equalsIgnoreCase("Task")){
            taskColumnCreator();
        }else if (typeOfColumn.equalsIgnoreCase("Category")){
            categoryColumnCreator();
        }
    }

    /**
     * constructor for creating a custom button column without a header name
     * @param typeOfTable - the name of the table that will contain the column (to do, doing, done)
     * @param typeOfColumn - what type of column (ex. button left = button that pushes task to the left table)
     * @param app - the application that uses the method
     */
    public CustomTableColumn(String typeOfTable, String typeOfColumn, ToDoApp app){
        super();
        this.app = app;
        if(typeOfTable.equalsIgnoreCase("to do")){
            buttonColumnCreator( "right");
        }else if(typeOfTable.equalsIgnoreCase("doing") && typeOfColumn.equalsIgnoreCase("buttonLeft")){
            buttonColumnCreator("left");
        }else if(typeOfTable.equalsIgnoreCase("doing") && typeOfColumn.equalsIgnoreCase("buttonRight")){
            buttonColumnCreator("right");
        }
        else if(typeOfTable.equalsIgnoreCase("done")){
            buttonColumnCreator("left");
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

    private void taskColumnCreator(){
        //changing the width of the column
        super.setPrefWidth(170);
        //making it so that the user cant move the columns around or resize the
        super.setResizable(false);
        super.setReorderable(false);
        //deciding what the cells in this column will contain
        super.setCellFactory(column -> new TableCell<Task, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) { //If the cell is empty
                    setText(null);
                    setStyle("");
                } else { //If the cell is not empty
                    Task task = getTableView().getItems().get(getIndex());
                    setTextFill(Color.BLACK);
                    setText(task.getName());
                    setAlignment(Pos.CENTER);

                    if (task.getStatus().equalsIgnoreCase("done")){
                        getStyleClass().add("cross-out");
                    }

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
        //changing the width of the column
        super.setPrefWidth(30);
        //making it so that the user cant move the columns around or resize the
        super.setResizable(false);
        super.setReorderable(false);
        //deciding what the cells in this column will contain
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
        //changing the width of the column
        super.setPrefWidth(20);
        //making it so that the user cant move the columns around or resize the
        super.setResizable(false);
        super.setReorderable(false);
        //deciding what the cells in this column will contain
        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

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
        //changing the width of the column
        super.setPrefWidth(85);
        //making it so that the user cant move the columns around or resize the
        super.setResizable(false);
        super.setReorderable(false);
        //deciding what the cells in this column will contain
        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

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
     * @param direction - what direction the button will push the task
     */
    private void buttonColumnCreator(String direction){
        //changing the width of the column
        super.setPrefWidth(40);
        //making it not possible for user to resize the column or reorder it
        super.setResizable(false);
        super.setReorderable(false);
        //deciding what the cells in this column will contain
        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                //button that pushes task to the right
                Button btnRight = new Button();
                ImageView rightArrow = new ImageView(new Image("ArrowToRight.png"));
                rightArrow.setFitHeight(20);
                rightArrow.setFitWidth(20);
                btnRight.setGraphic(rightArrow);
                btnRight.getStyleClass().add("arrow-buttons");

                {
                    btnRight.setOnAction((ActionEvent event) -> {
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
                    btnRight.setTooltip(tooltip);
                }

                //button that pushes task to the left
                Button btnLeft = new Button();
                ImageView leftArrow = new ImageView(new Image("ArrowToLeft.png"));
                leftArrow.setFitHeight(20);
                leftArrow.setFitWidth(20);
                btnLeft.setGraphic(leftArrow);
                btnLeft.getStyleClass().add("arrow-buttons");

                {
                    btnLeft.setOnAction((ActionEvent event) -> {
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
                    btnLeft.setTooltip(tooltip);
                }
                if (empty) {
                    setGraphic(null);
                } else {
                    //adding the -> button to the columns that will push the task to the right
                    if(direction.equals("right")){
                        setGraphic(btnRight);
                        //adding the <- button to the columns that will push the task to the left
                    }else if(direction.equals("left")){
                        setGraphic(btnLeft);
                    }
                }
            }
        });
    }
}

