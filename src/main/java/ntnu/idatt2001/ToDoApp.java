package ntnu.idatt2001;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class for the application "To-Do-App"
 */
public class ToDoApp extends Application {
    TaskList data = new TaskList();
    TableView<Task> tableViewToDo;
    TableView<Task> tableViewDoing;
    TableView<Task> tableViewDone;
    TableView<Category> tableViewCategory;
    Task taskToView;
    TableColumn<Task, String> toDoListColumn;
    TableColumn<Task, String> doingColumn;
    TableColumn<Task, String> doneColumn;
    ComboBox sortBy;

    TaskStage stage2;

    //to run the application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //creating layoutpanes for the gui
        stage.setTitle("To-Do application");
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(13, 13, 13, 13));
        GridPane gpTop = new GridPane();
        GridPane gpTaskList = new GridPane();
        GridPane gpCategory = new GridPane();
        GridPane gpAddTask = new GridPane();
        GridPane gpViewTask = new GridPane();

        Scene mainScene = new Scene(pane, 1400, 600);

        //adding paddings for the visuals (more space between layoutpane and its content)
        gpTop.setPadding(new Insets(0, 0, 0, 10));
        gpTaskList.setPadding(new Insets(10, 10, 10, 10));
        gpCategory.setPadding(new Insets(50, 20, 20, 20));
        gpAddTask.setPadding(new Insets(10, 10, 10, 10));
        gpViewTask.setPadding(new Insets(10, 10, 10, 10));

        //adding the spacing between all contents inn the gridpanes
        gpAddTask.setHgap(13);
        gpAddTask.setVgap(6);
        gpTop.setHgap(13);
        gpTop.setVgap(6);
        gpViewTask.setHgap(13);
        gpViewTask.setVgap(6);

        //adding the title
        Text title = new Text("To-Do list");
        title.setFont(Font.font("Tohoma", FontWeight.EXTRA_BOLD, 40));

        //adding two buttons
        Button addTask = new Button("Add Task");
        addTask.setStyle("-fx-background-color: #a3ffb3");
        sortBy = new ComboBox();
        sortBy.getItems().addAll("Deadline","Priority");
        sortBy.setPromptText("Sort tasks by");
        sortBy.setStyle("-fx-background-color: #86cffc");

        //adding the buttons and title to their gridpane
        gpTop.add(title, 0, 0, 2, 1);
        gpTop.add(addTask, 3, 1);
        gpTop.add(sortBy, 4, 1);

        //creating the table for the to do list
        tableViewToDo = new TableView();
        //creating the columns in the table
        toDoListColumn = new TableColumn<>("To-do");
        TableColumn deadlineColumn = new TableColumn<>("Deadline");
        TableColumn taskNameColumn = new TableColumn<>("Task");
        TableColumn priorityColumn = new TableColumn<>();
        //changing the width of each column
        deadlineColumn.setPrefWidth(75);
        taskNameColumn.setPrefWidth(170);
        priorityColumn.setPrefWidth(20);
        //making it so that the user cant move the columns around or resize them
        toDoListColumn.setReorderable(false);
        deadlineColumn.setResizable(false);
        priorityColumn.setResizable(false);
        deadlineColumn.setReorderable(false);
        taskNameColumn.setResizable(false);
        taskNameColumn.setReorderable(false);
        priorityColumn.setReorderable(false);
        //adding the deadline and taskname column to the todolist column to create nested columns
        toDoListColumn.getColumns().addAll(deadlineColumn, priorityColumn, taskNameColumn);
        //adding the button columns to the to do table
        addButtonToTable("to do");
        //adding all the columns to the table
        tableViewToDo.getColumns().addAll(toDoListColumn);
        //setting what the values of the columns will be
        deadlineColumn.setCellFactory(column -> formattingDate("deadline"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityColumn.setCellFactory(column -> addPriorityColors());
        //making the table only show the columns that i added
        tableViewToDo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the table to the gridpane
        gpTaskList.add(tableViewToDo, 1, 1);
        //making the table editable
        tableViewToDo.setEditable(true);

        //creating the table for the doing list
        tableViewDoing = new TableView();
        doingColumn = new TableColumn<>("Doing");
        TableColumn deadlineColumn1 = new TableColumn<>("Deadline");
        TableColumn taskNameColumn1 = new TableColumn<>("Task");
        TableColumn priorityColumn1 = new TableColumn<>();
        //changing the width of each column
        deadlineColumn1.setPrefWidth(75);
        taskNameColumn1.setPrefWidth(170);
        priorityColumn1.setPrefWidth(20);
        //making it so that the user cant move the columns around or resize the
        deadlineColumn1.setResizable(false);
        taskNameColumn1.setResizable(false);
        priorityColumn1.setResizable(false);
        doingColumn.setReorderable(false);
        deadlineColumn1.setReorderable(false);
        taskNameColumn1.setReorderable(false);
        priorityColumn1.setReorderable(false);
        //adding the deadline and taskname column to the doing column to create nested columns
        doingColumn.getColumns().addAll(deadlineColumn1, priorityColumn1,taskNameColumn1);
        //adding the button columns to the doing table
        addButtonToTable("doing2");
        addButtonToTable("doing1");
        //adding all the columns to the table
        tableViewDoing.getColumns().addAll(doingColumn);
        //setting what the values of the columns will be
        deadlineColumn1.setCellFactory(column -> formattingDate("deadline"));
        taskNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityColumn1.setCellFactory(column -> addPriorityColors());
        //making the table only show the columns that i added
        tableViewDoing.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the todolist table to gridpane
        gpTaskList.add(tableViewDoing, 2, 1);
        //making the table editable
        tableViewDoing.setEditable(true);

        //creating the table for done list
        tableViewDone = new TableView();
        doneColumn = new TableColumn<>("Completed");
        TableColumn finishDateColumn = new TableColumn<>("Finish date");
        TableColumn taskNameColumn2 = new TableColumn<>("Task");
        TableColumn priorityColumn2 = new TableColumn<>();
        //changing the width of each column
        finishDateColumn.setPrefWidth(75);
        taskNameColumn2.setPrefWidth(170);
        priorityColumn2.setPrefWidth(20);
        //making it so that the user cant move the columns around or resize the
        finishDateColumn.setResizable(false);
        taskNameColumn2.setResizable(false);
        doneColumn.setReorderable(false);
        finishDateColumn.setReorderable(false);
        taskNameColumn2.setReorderable(false);
        priorityColumn2.setResizable(false);
        priorityColumn2.setReorderable(false);
        //adding the deadline and taskname column to the done column to create nested columns
        doneColumn.getColumns().addAll(finishDateColumn , priorityColumn2, taskNameColumn2);
        //adding the button columns to the done table
        addButtonToTable("done");
        //adding all the columns to the table
        tableViewDone.getColumns().addAll(doneColumn);
        //setting what the values of the columns will be
        finishDateColumn.setCellFactory(column -> formattingDate("finishdate"));
        taskNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityColumn2.setCellFactory(column -> addPriorityColors());
        //making the table only show the columns that i added
        tableViewDone.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the done table to gridpane
        gpTaskList.add(tableViewDone, 3, 1);
        //making the table editable
        tableViewDone.setEditable(true);

        //creating the category table
        tableViewCategory = new TableView();
        TableColumn<Category, String> categoryColumn = new TableColumn<>("Categories");
        categoryColumn.setReorderable(false);
        categoryColumn.setResizable(false);
        //adding the columns to the category table
        addCheckBoxToTable();
        tableViewCategory.getColumns().add(categoryColumn);
        //setting what the values of the columns will be
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //deciding what size the table will be
        tableViewCategory.setPrefSize(130, 300);
        tableViewCategory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the category table to gridpane
        gpCategory.add(tableViewCategory, 0, 0);

        data.getAllTasks().stream().forEach( t -> t.getCategory().setShowing(true));

        update();

        //add task button is pressed
        addTask.setOnAction(actionEvent -> {
            stage2 = new TaskStage();
            stage2.setTitle("Add Task");
            stage2.showAndWait();

            //if the getResult does not return null, means the user clicked add task
            if(stage2.getResult() != null){
                data.addTask(stage2.getResult());
            }

            if(sortBy.getValue() != null){
                data.sortLists(sortBy.getValue().toString());
            }

            update();
        });


        //what happens when you change the sort by combobox
        sortBy.setOnAction(actionEvent -> {
            data.sortLists(sortBy.getValue().toString());
            sortBy.setPlaceholder(new Text("Sort tasks by"));
            update();
        });

        //adding all the gridpanes to the main layoutpane
        pane.setTop(gpTop);
        pane.setCenter(gpTaskList);
        pane.setLeft(gpCategory);

        //if one of the rows in one of the tables is clicked on, the method taskOnClick is run
        tableViewToDo.setOnMouseClicked((MouseEvent event) -> taskOnClick(event, "toDo"));
        tableViewDoing.setOnMouseClicked((MouseEvent event) -> taskOnClick(event, "doing"));
        tableViewDone.setOnMouseClicked((MouseEvent event) -> taskOnClick(event, "done"));

        //setting the scene and showing it
        stage.setScene(mainScene);
        stage.show();

    }

    private void taskOnClick(MouseEvent event, String tableName) {
        boolean notEmpty = false;
        //can decide how many clicks must happen before a window is opened
        if (event.getClickCount() > 1) {
            //checking whether the table clicked on is empty, because then no window should be shown
            if (tableViewToDo.getSelectionModel().getSelectedItem() == null && tableName.equalsIgnoreCase("toDo")) {
                notEmpty = true;
            } else if (tableViewDoing.getSelectionModel().getSelectedItem() == null && tableName.equalsIgnoreCase("doing")) {
                notEmpty = true;
            } else if (tableViewDone.getSelectionModel().getSelectedItem() == null && tableName.equalsIgnoreCase("done")) {
                notEmpty = true;
            }

            //if table is not empty, then the user clicked on a task
            if (!notEmpty) {
                if (tableName.equalsIgnoreCase("toDo")) {
                    taskToView = tableViewToDo.getSelectionModel().getSelectedItem();
                } else if (tableName.equalsIgnoreCase("doing")) {
                    taskToView = tableViewDoing.getSelectionModel().getSelectedItem();

                } else if (tableName.equalsIgnoreCase("done")) {
                    taskToView = tableViewDone.getSelectionModel().getSelectedItem();
                }

                //opens a new window to view the selected task
                stage2 = new TaskStage(taskToView);
                stage2.showAndWait();

                //get result returns a task, if its not null means the user clicked remove task
                if(stage2.getResult() != null){
                    //checking if the user actually wants to delete the task
                    if (showDeleteConfirmationDialog()) {
                        data.removeTask(stage2.getResult());
                    }
                }
                update();
            }
        }
    }

    private void update() {
        tableViewCategory.setItems(data.getCategoryList());
        List<Task> toDoList = data.getToDoList().stream().filter(t -> t.getCategory().isShowing()).collect(Collectors.toList());
        List<Task> doingList = data.getDoingList().stream().filter(t -> t.getCategory().isShowing()).collect(Collectors.toList());
        List<Task> doneList = data.getDoneList().stream().filter(t -> t.getCategory().isShowing()).collect(Collectors.toList());
        ObservableList<Task> toDoObservableList = FXCollections.observableList(toDoList);
        ObservableList<Task> doingObservableList = FXCollections.observableList(doingList);
        ObservableList<Task> doneObservableList = FXCollections.observableList(doneList);
        tableViewToDo.setItems(toDoObservableList);
        tableViewDoing.setItems(doingObservableList);
        tableViewDone.setItems(doneObservableList);
        //refreshing all
        tableViewToDo.refresh();
        tableViewDoing.refresh();
        tableViewDone.refresh();
    }

    /**
     * method to display a delete confirmation
     * @return true if the user confirms to delete the task
     */
    public boolean showDeleteConfirmationDialog() {
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

    @Override
    public void stop() throws Exception {
        //Serializing the TaskList object "data" when application stops
        //In other words: saving all the information in TaskList object to a file
        try (FileOutputStream outputStream = new FileOutputStream("data.ser");
             ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
            out.writeObject(data);
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (Exception e) {
            System.out.println("Something other than IO failure");
        }
        super.stop();
    }

    @Override
    public void init() throws Exception {
        //Opening the file containing the serialized TaskList object when the application starts
        try (FileInputStream inputStream = new FileInputStream("data.ser");
             ObjectInputStream in = new ObjectInputStream(inputStream)) {
            data = (TaskList) in.readObject();
        } catch (FileNotFoundException e) {
            //
        } catch (EOFException e) {
            //System.out.println("File found but empty");
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (Exception e) {
            System.out.println("Something other than IO failure");
        }
        super.init();
    }

    //method to create a tablecolumn that contains a button
    //the methods take in the tablename: "to do", "doing1", "doing2", "done"
    //"doing1" and "doing2" is because doing column requires two buttoncolumns
    private void addButtonToTable(String tableName) {
        //the table that will contain the button
        TableColumn<Task, Void> colBtn = new TableColumn();
        colBtn.setPrefWidth(40);
        //making it not possible for user to resize the column or reorder it
        colBtn.setResizable(false);
        colBtn.setReorderable(false);

        //creating a custom cellFactory, so it can contain a button
        Callback<TableColumn<Task, Void>, TableCell<Task, Void>> cellFactory = new Callback<TableColumn<Task, Void>, TableCell<Task, Void>>() {
            @Override
            public TableCell<Task, Void> call(final TableColumn<Task, Void> param) {
                final TableCell<Task, Void> cell = new TableCell<Task, Void>() {

                    //button that pushes task to the right
                    private final Button btn = new Button("->");
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
                            update();
                        });
                        Tooltip tooltip = new Tooltip("Move task to right");
                        tooltip.setShowDelay(Duration.millis(300));
                        btn.setTooltip(tooltip);
                    }

                    //button that pushes task to the left
                    private final Button btn2 = new Button("<-");
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
                            update();
                        });
                        Tooltip tooltip = new Tooltip("Move task to left");
                        tooltip.setShowDelay(Duration.millis(300));
                        btn2.setTooltip(tooltip);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //adding the -> button to only to do and doing column
                            if(tableName.equals("to do") || tableName.equals("doing1")){
                                setGraphic(btn);
                                //adding the <- button to only doing and done column
                            }else if(tableName.equals("doing2") || tableName.equals("done")){
                                setGraphic(btn2);
                            }
                        }
                    }
                };
                return cell;
            }
        };

        //setting the factory of colBtn to the custom cellfactory created
        colBtn.setCellFactory(cellFactory);

        //Sets the correct buttons to "to do", "doing" and "done" columns
        if(tableName.equals("to do")){
            toDoListColumn.getColumns().add(colBtn);
        }else if(tableName.equals("doing1") || tableName.equals("doing2")){
            doingColumn.getColumns().add(colBtn);
        } else if(tableName.equals("done")) {
            doneColumn.getColumns().add(colBtn);
        }

    }

    private void addCheckBoxToTable(){
        //the table that will contain the checkbox
        TableColumn<Category, Void> colCB = new TableColumn();
        colCB.setPrefWidth(40);
        //making it not possible for user to resize the column or reorder it
        colCB.setResizable(false);
        colCB.setReorderable(false);

        //creating a custom cellFactory, so it can contain a checkbox
        Callback<TableColumn<Category, Void>, TableCell<Category, Void>> cellFactory = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                final TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    private final CheckBox checkBox = new CheckBox();
                    {
                        checkBox.setSelected(true);
                        update();
                        checkBox.setOnAction((ActionEvent event) -> {
                            Category category = getTableView().getItems().get(getIndex());
                            Tooltip tooltip = new Tooltip();
                            if(!checkBox.isSelected()){
                                category.setShowing(false);
                                data.getAllTasks().stream().filter(t -> t.getCategory().getName().equals(category.getName())).forEach(t -> t.getCategory().setShowing(false));
                                update();
                                tooltip = new Tooltip("Show tasks of this category");
                            }else if(checkBox.isSelected()){
                                category.setShowing(true);
                                data.getAllTasks().stream().filter(t -> t.getCategory().getName().equals(category.getName())).forEach(t -> t.getCategory().setShowing(true));
                                update();
                                tooltip = new Tooltip("Hide tasks of this category");
                            }
                            tooltip.setShowDelay(Duration.millis(300));
                            checkBox.setTooltip(tooltip);
                        });
                        Tooltip tooltip = new Tooltip("Hide tasks of this category");
                        tooltip.setShowDelay(Duration.millis(300));
                        checkBox.setTooltip(tooltip);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(checkBox);
                        }
                    }
                };
                return cell;
            }
        };

        //
        colCB.setCellFactory(cellFactory);
        colCB.setPrefWidth(30);

        //
        tableViewCategory.getColumns().add(colCB);
    }

    private TableCell<Task, Void> addPriorityColors(){
        {
            return new TableCell<Task, Void>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty
                        Task task = getTableView().getItems().get(getIndex());

                        if (task.getPriority() == 1) {
                            setText("L");
                            setStyle("-fx-background-color: limegreen");
                        } else if (task.getPriority() == 2){
                            setText("M");
                            setStyle("-fx-background-color: orange");
                        }else if (task.getPriority() == 3){
                            setText("H");
                            setStyle("-fx-background-color: red");
                        }
                    }
                }
            };
        }
    }

    private TableCell<Task, Void> formattingDate(String typeOfDate){
        {
            return new TableCell<Task, Void>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty
                        Task task = getTableView().getItems().get(getIndex());
                        if(typeOfDate.equalsIgnoreCase("deadline")){
                            LocalDateTime taskDeadline = task.getDeadline();

                            String textDate = taskDeadline.getDayOfMonth()+"/"+taskDeadline.getMonthValue()+"/"+taskDeadline.getYear();
                            String textTime = taskDeadline.getHour()+":"+new DecimalFormat("00").format(taskDeadline.getMinute());

                            setText(textDate+"\n"+textTime);

                            if(taskDeadline.isBefore(LocalDateTime.now())){
                                setTextFill(Color.RED);
                                Tooltip tooltip = new Tooltip("Deadline has passed");
                                tooltip.setShowDelay(Duration.millis(200));
                                setTooltip(tooltip);
                            }
                        }else if(typeOfDate.equalsIgnoreCase("finishdate")){
                            LocalDateTime taskFinishDate = task.getFinishDate();

                            String textDate = taskFinishDate.getDayOfMonth()+"/"+taskFinishDate.getMonthValue()+"/"+taskFinishDate.getYear();
                            String textTime = taskFinishDate.getHour()+":"+new DecimalFormat("00").format(taskFinishDate.getMinute());

                            setText(textDate+"\n"+textTime);
                        }

                    }
                }
            };
        }
    }
}