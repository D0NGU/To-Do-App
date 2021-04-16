package ntnu.idatt2001;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
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
    TitleStage stageTitle;

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

        Scene mainScene = new Scene(pane, 1400, 600);
        mainScene.getStylesheets().add("/stylesheet.css");

        //adding paddings for the visuals (more space between layoutpane and its content)
        gpTaskList.setPadding(new Insets(10, 10, 10, 10));
        gpCategory.setPadding(new Insets(50, 20, 20, 20));
        gpAddTask.setPadding(new Insets(10, 10, 10, 10));
        gpViewTask.setPadding(new Insets(10, 10, 10, 10));

        //adding the spacing between all contents inn the gridpanes
        gpTop.setHgap(13);
        gpTop.setVgap(6);
        gpTaskList.setHgap(13);
        gpTaskList.setVgap(6);

        //adding the title
        Text title = new Text("To-Do list");
        title.setFont(Font.font("Tohoma", FontWeight.EXTRA_BOLD, 40));


        //adding three buttons
        Button addTask = new Button("Add Task");
        addTask.setStyle("-fx-background-color: #a3ffb3");
        sortBy = new ComboBox();
        sortBy.getItems().addAll("Deadline","Priority");
        sortBy.setPromptText("Sort tasks by");

        //set edit title button to an image
        Button setTitle = new Button();
        ImageView editImage = new ImageView(new Image("editTitle.png"));
        editImage.setFitHeight(15);
        editImage.setFitWidth(15);
        setTitle.setGraphic(editImage);
        setTitle.getStyleClass().add("title-edit-button");

        //information to show on hover over for edit button
        Tooltip tooltip = new Tooltip("Edit title");
        tooltip.setShowDelay(Duration.millis(200));
        setTitle.setTooltip(tooltip);

        //adding the buttons and title to their gridpane
        gpTop.add(title, 1, 1, 2, 2);
        gpTop.add(setTitle,3,1);
        //gpTop.add(addTask, 3, 2);
        //gpTop.add(sortBy, 4, 2);
        gpTaskList.add(addTask,1,0);
        gpTaskList.add(sortBy,2,0);

        //creating the table for the to do list
        tableViewToDo = new TableView();
        //creating the columns in the table
        toDoListColumn = new TableColumn<>("To-do");
        TableColumn deadlineColumn = new CustomTableColumn("Deadline",this);
        TableColumn taskNameColumn = new CustomTableColumn("Task", this);
        TableColumn priorityColumn = new CustomTableColumn(false, this);
        TableColumn buttonColumn = new CustomTableColumn("to do","", this);
        //changing the width of each column
        taskNameColumn.setPrefWidth(170);
        //making it so that the user cant move the columns around or resize them
        toDoListColumn.setReorderable(false);
        taskNameColumn.setResizable(false);
        taskNameColumn.setReorderable(false);
        //adding the deadline and taskname column to the todolist column to create nested columns
        toDoListColumn.getColumns().addAll(deadlineColumn, priorityColumn, taskNameColumn,buttonColumn);
        toDoListColumn.setId("TO-DO-HEADER");
        //toDoListColumn.getStyleClass().add("column-header-top");
        //adding the button columns to the to do table
        //adding all the columns to the table
        tableViewToDo.getColumns().addAll(toDoListColumn);
        //setting what the values of the columns will be
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //making the table only show the columns that i added
        tableViewToDo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the table to the gridpane
        gpTaskList.add(tableViewToDo, 1, 1,2,1);
        //making the table editable
        tableViewToDo.setEditable(true);

        //creating the table for the doing list
        tableViewDoing = new TableView();
        doingColumn = new TableColumn<>("Doing");
        TableColumn deadlineColumn1 = new CustomTableColumn("Deadline",this);
        TableColumn taskNameColumn1 = new CustomTableColumn("Task", this);
        TableColumn priorityColumn1 = new CustomTableColumn(false, this);
        TableColumn buttonLeftColumn = new CustomTableColumn("doingLeft","", this);
        TableColumn buttonRightColumn = new CustomTableColumn("doingRight","", this);
        //changing the width of each column
        taskNameColumn1.setPrefWidth(170);
        //making it so that the user cant move the columns around or resize the
        taskNameColumn1.setResizable(false);
        doingColumn.setReorderable(false);
        taskNameColumn1.setReorderable(false);
        //adding the deadline and taskname column to the doing column to create nested columns
        doingColumn.getColumns().addAll(deadlineColumn1, priorityColumn1,taskNameColumn1,buttonLeftColumn,buttonRightColumn);
        //doingColumn.getStyleClass().add("column-header-top");
        //adding the button columns to the doing table
        //adding all the columns to the table
        tableViewDoing.getColumns().addAll(doingColumn);
        //setting what the values of the columns will be
        taskNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        //making the table only show the columns that i added
        tableViewDoing.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the todolist table to gridpane
        gpTaskList.add(tableViewDoing, 3, 1);
        //making the table editable
        tableViewDoing.setEditable(true);

        //creating the table for done list
        tableViewDone = new TableView();
        doneColumn = new TableColumn<>("Completed");
        TableColumn finishDateColumn = new CustomTableColumn("Finish date",this);
        TableColumn taskNameColumn2 = new CustomTableColumn("Task", this);
        TableColumn priorityColumn2 = new CustomTableColumn(false, this);
        TableColumn buttonDoneColumn = new CustomTableColumn("done","", this);
        //changing the width of each column
        taskNameColumn2.setPrefWidth(170);
        //making it so that the user cant move the columns around or resize the
        taskNameColumn2.setResizable(false);
        doneColumn.setReorderable(false);
        taskNameColumn2.setReorderable(false);
        //adding the deadline and taskname column to the done column to create nested columns
        doneColumn.getColumns().addAll(finishDateColumn , priorityColumn2, taskNameColumn2,buttonDoneColumn);
        //doneColumn.getStyleClass().add("column-header-top");
        //adding the button columns to the done table
        //adding all the columns to the table
        tableViewDone.getColumns().addAll(doneColumn);
        //setting what the values of the columns will be
        taskNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        //making the table only show the columns that i added
        tableViewDone.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the done table to gridpane
        gpTaskList.add(tableViewDone, 4, 1);
        //making the table editable
        tableViewDone.setEditable(true);

        //creating the category table
        tableViewCategory = new TableView();
        TableColumn<Category, String> categoryColumn = new TableColumn<>("Categories");
        //categoryColumn.getStyleClass().add("column-header-top");
        categoryColumn.setReorderable(false);
        categoryColumn.setResizable(false);
        categoryColumn.setPrefWidth(100);
        //adding the columns to the category table
        TableColumn<Category, Void> checkboxColumn = new CustomTableColumn(true, this);
        tableViewCategory.getColumns().addAll(checkboxColumn, categoryColumn);
        //setting what the values of the columns will be
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //deciding what size the table will be
        tableViewCategory.setPrefSize(135, 300);
        tableViewCategory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the category table to gridpane
        gpCategory.add(tableViewCategory, 0, 0);

        data.getAllTasks().stream().forEach( t -> t.getCategory().setShowing(true));

        update();

        //edit title button is pressed
        setTitle.setOnAction(actionEvent ->{
            stageTitle = new TitleStage(title);
            stageTitle.setTitle("Edit title name");
            stageTitle.showAndWait();

            if(stageTitle.getResult() != null){
                title.setText(stageTitle.getResult());
                data.setTitleName(stageTitle.getResult());
            }

        });

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

    public TaskList getData() {
        return data;
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

    public void update() {
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
}