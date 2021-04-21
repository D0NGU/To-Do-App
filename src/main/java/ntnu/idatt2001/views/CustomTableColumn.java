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
import ntnu.idatt2001.controllers.CustomTableColumnController;
import ntnu.idatt2001.models.Task;
import ntnu.idatt2001.models.Category;

/**
 * class for creating a custom table column (column to hold a button etc.)
 */
public class CustomTableColumn extends TableColumn {
    ToDoApp app;
    CustomTableColumnController controller;

    /**
     * constructor for creating a custom checkbox or priority column without a header name
     * @param checkboxColumn - true if the column is a checkboxcolumn
     * @param app - the application that uses the method
     */
    public CustomTableColumn(Boolean checkboxColumn, ToDoApp app) {
        super();
        this.app = app;
        controller = new CustomTableColumnController(app);
        //checking whether the column is a checkbox column or priority column
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
        controller = new CustomTableColumnController(app);
        //checking what sort of column it is
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
        controller = new CustomTableColumnController(app);
        //checking what sort of column it is and which direction the button should point towards
        if(typeOfTable.equalsIgnoreCase("to do")){
            buttonColumnCreator( "right");
        }else if(typeOfTable.equalsIgnoreCase("doing")
                && typeOfColumn.equalsIgnoreCase("buttonLeft")){
            buttonColumnCreator("left");
        }else if(typeOfTable.equalsIgnoreCase("doing")
                && typeOfColumn.equalsIgnoreCase("buttonRight")){
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
                if (empty) { //If the cell is empty, it should not display anything
                    setText(null);
                } else { //If the cell is not empty, it should display the name of the category
                    //the category that belongs to the cell
                    Category category = getTableView().getItems().get(getIndex());
                    //putting the category name in the cell
                    setText(category.getName());
                    //setting the color of the text
                    setTextFill(Color.BLACK);
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

                if (empty) { //If the cell is empty, it should not display anything
                    setText(null);
                } else { //If the cell is not empty, it should display the name of the task
                    //the task that belongs to the cell
                    Task task = getTableView().getItems().get(getIndex());

                    //putting the name of the task in the cell
                    setText(task.getName());
                    //setting the color of the text to be black and in the center of the cell
                    setTextFill(Color.BLACK);
                    setAlignment(Pos.CENTER);

                    //if the task is finished, adding a cross out style so its easier
                    //for the user to see that the task is completed
                    if (task.getStatus().equalsIgnoreCase("done")){
                        getStyleClass().add("cross-out");
                    }

                    //adding a tooltip (hover over effect) over the task name cell
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

                //creating the checkbox that the column will contain
                CheckBox checkBox = new CheckBox();
                //all categories are set to be shown when the program starts
                checkBox.setSelected(true);
                //creating a tooltip and adding it to the checkbox
                Tooltip tooltip = new Tooltip("Hide tasks of this category");
                tooltip.setShowDelay(Duration.millis(300));
                checkBox.setTooltip(tooltip);
                //what happens when you click on the checkbox
                checkBox.setOnAction((ActionEvent event) -> controller.checkboxOnClick(checkBox, getTableView().getItems().get(getIndex())));

                if (empty) { //If the cell is empty, it should not display anything
                    setGraphic(null);
                } else { //If the cell is not empty, it should display the checkbox
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

                if (empty) { //If the cell is empty, it should not display anything
                    setText(null);
                } else { //If the cell is not empty, it should display the priority
                    //the task that belongs to the cell
                    Task task = getTableView().getItems().get(getIndex());

                    //checking what priority the task has, to add the right graphics (photo) to the cell
                    if (task.getPriority() == 1) {
                        //the image that will be displayed in the cell
                        ImageView i = new ImageView(new Image("LowPriority.png"));
                        //the size of the image
                        i.setFitHeight(20);
                        i.setFitWidth(20);
                        //setting the image to be in the middle of the cell
                        setAlignment(Pos.CENTER);
                        setGraphic(i);
                    } else if (task.getPriority() == 2){
                        //the image that will be displayed in the cell
                        ImageView i = new ImageView(new Image("MediumPriority.png"));
                        //the size of the image
                        i.setFitHeight(20);
                        i.setFitWidth(20);
                        //setting the image to be in the middle of the cell
                        setAlignment(Pos.CENTER);
                        //adding the image to the cell
                        setGraphic(i);
                    }else if (task.getPriority() == 3){
                        //the image that will be displayed in the cell
                        ImageView i = new ImageView(new Image("HighPriority.png"));
                        //the size of the image
                        i.setFitHeight(20);
                        i.setFitWidth(20);
                        //setting the image to be in the middle of the cell
                        setAlignment(Pos.CENTER);
                        //adding the image to the cell
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

                if (empty) { //If the cell is empty, it should not display anything
                    setText(null);
                } else { //If the cell is not empty, it should display the date
                    //the task that belongs to the cell
                    Task task = getTableView().getItems().get(getIndex());

                    //checking what type of date is showed in the column (deadline or finish date)
                    if(typeOfDate.equalsIgnoreCase("Deadline")){
                        LocalDateTime taskDeadline = task.getDeadline();
                        setTextFill(Color.BLACK);
                        if(taskDeadline == null){ //if the task does not have a deadline
                            setText("No deadline");
                        }else{ //if the task has a deadline
                            //formatting the date and time so it can be easy to read
                            String textDate = taskDeadline.getDayOfMonth()+"/"+taskDeadline.getMonthValue()
                                    +"/"+taskDeadline.getYear();
                            String textTime = taskDeadline.getHour()+":"
                                    +new DecimalFormat("00").format(taskDeadline.getMinute());
                            //adding the date and time to the cell
                            setText(textDate+"\n"+textTime);

                            if(taskDeadline.isBefore(LocalDateTime.now())){ //if the deadline has passed
                                //adding a "!" to make it easier for user to understand that
                                //its something special about this deadline
                                setText("! "+textDate+"\n"+textTime);
                                //setting the color of the text to red
                                setStyle("-fx-text-fill: #ad0606");
                                //adding a tooltip explaining the meaning of the red color and "!"
                                Tooltip tooltip = new Tooltip("Deadline has passed");
                                tooltip.setShowDelay(Duration.millis(200));
                                setTooltip(tooltip);
                            }
                        }
                    }else if(typeOfDate.equalsIgnoreCase("Finish date")){
                        LocalDateTime taskFinishDate = task.getFinishDate();
                        //formatting the date and time so it can be easy to read
                        String textDate = taskFinishDate.getDayOfMonth()+"/"
                                +taskFinishDate.getMonthValue()+"/"+taskFinishDate.getYear();
                        String textTime = taskFinishDate.getHour()+":"
                                +new DecimalFormat("00").format(taskFinishDate.getMinute());
                        //adding the date and time to the cell
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
        super.setPrefWidth(60);
        //making it not possible for user to resize the column or reorder it
        super.setResizable(false);
        super.setReorderable(false);
        //deciding what the cells in this column will contain
        super.setCellFactory(column -> new TableCell<Task, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                //creating the button that pushes task to the right
                Button btnRight = new Button();
                //getting the image that the button will display and setting its size
                ImageView rightArrow = new ImageView(new Image("ArrowToRight.png"));
                rightArrow.setFitHeight(20);
                rightArrow.setFitWidth(20);
                //adding the image to the button and a custom styling
                btnRight.setGraphic(rightArrow);
                btnRight.getStyleClass().add("arrow-buttons");
                //adding a tooltip explaining the functionality of the button
                Tooltip tooltipBtnRight = new Tooltip("Move task to right");
                tooltipBtnRight.setShowDelay(Duration.millis(300));
                btnRight.setTooltip(tooltipBtnRight);
                //what happens when you click on the button
                btnRight.setOnAction((ActionEvent event) -> controller.arrowButtonOnClick("right", getTableView().getItems().get(getIndex())));

                //creating the button that pushes task to the left
                Button btnLeft = new Button();
                //getting the image that the button will display and setting its size
                ImageView leftArrow = new ImageView(new Image("ArrowToLeft.png"));
                leftArrow.setFitHeight(20);
                leftArrow.setFitWidth(20);
                //adding the image to the button and a custom styling
                btnLeft.setGraphic(leftArrow);
                btnLeft.getStyleClass().add("arrow-buttons");
                //adding a tooltip explaining the functionality of the button
                Tooltip tooltipBtnLeft = new Tooltip("Move task to left");
                tooltipBtnLeft.setShowDelay(Duration.millis(300));
                btnLeft.setTooltip(tooltipBtnLeft);
                //what happens when you click on the button
                btnLeft.setOnAction((ActionEvent event) -> controller.arrowButtonOnClick("left", getTableView().getItems().get(getIndex())));

                if (empty) { //If the cell is empty, it should not display anything
                    setGraphic(null);
                } else { //If the cell is not empty, it should the button
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

