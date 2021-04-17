package ntnu.idatt2001.views;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ntnu.idatt2001.controllers.MainController;
import ntnu.idatt2001.models.Category;
import ntnu.idatt2001.models.Task;
import ntnu.idatt2001.models.TaskList;

/**
 * Class for the application "To-Do-App"
 */
public class ToDoApp extends Application {
    TaskList data = new TaskList();
    TableView<Task> tableViewToDo;
    TableView<Task> tableViewDoing;
    TableView<Task> tableViewDone;
    TableView<Category> tableViewCategory;
    TableColumn<Task, String> toDoListColumn;
    TableColumn<Task, String> doingColumn;
    TableColumn<Task, String> doneColumn;
    ComboBox sortBy;
    GridPane gpTaskList;
    GridPane gpCategory;
    Text title;

    //the controller which communicates with the models for to do app
    MainController mainController = new MainController(this);

    //to run the application
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * method that runs after the init method
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        //creating layoutpanes for the gui
        stage.setTitle("To-Do application");
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(13, 13, 13, 13));
        GridPane gpTop = new GridPane();
        gpTaskList = new GridPane();
        gpCategory = new GridPane();

        //creating the main scene, and how big it will be (the main scene = the window that shows all the tables)
        Scene mainScene = new Scene(pane, 1400, 600);

        //adding a stylesheet to the main scene
        mainScene.getStylesheets().add("/stylesheet.css");

        //adding paddings for the visuals (more space between layoutpane and its content)
        gpTaskList.setPadding(new Insets(10, 10, 10, 10));
        gpCategory.setPadding(new Insets(50, 20, 20, 20));

        //adding the spacing between all contents inn the gridpanes
        gpTop.setHgap(13);
        gpTop.setVgap(6);
        gpTaskList.setHgap(13);
        gpTaskList.setVgap(6);

        //adding the title
        title = new Text(data.getTitleName());
        title.setFont(Font.font("Tohoma", FontWeight.EXTRA_BOLD, 40));

        //adding the add task button
        Button addTask = new Button("Add Task");
        addTask.setStyle("-fx-background-color: #a3ffb3");
        //add task button is pressed
        addTask.setOnAction(actionEvent -> mainController.addTask());

        //creating the sort by option
        sortBy = new ComboBox();
        sortBy.getItems().addAll("Deadline", "Priority");
        sortBy.setPromptText("Sort tasks by");
        //what happens when you change the sort by combobox
        sortBy.setOnAction(actionEvent -> mainController.sortTasks( ));

        //creating the edit title button
        Button setTitle = new Button();
        ImageView editImage = new ImageView(new Image("editTitle.png"));
        editImage.setFitHeight(15);
        editImage.setFitWidth(15);
        setTitle.setGraphic(editImage);
        setTitle.getStyleClass().add("title-edit-button");
        //information to show on hover over for edit title button
        Tooltip tooltip = new Tooltip("Edit title");
        tooltip.setShowDelay(Duration.millis(200));
        setTitle.setTooltip(tooltip);
        //edit title button is pressed
        setTitle.setOnAction(actionEvent -> mainController.editTitle());

        //adding the buttons and title to their gridpane
        gpTop.add(title, 1, 1, 2, 2);
        gpTop.add(setTitle, 3, 1);
        gpTaskList.add(addTask, 1, 0);
        gpTaskList.add(sortBy, 2, 0);

        //creating all the tableviews (to do, doing, done and category)
        createTableViews();

        //if one of the rows in one of the tables is clicked on, the method taskOnClick is run
        tableViewToDo.setOnMouseClicked((MouseEvent event) -> mainController.taskOnClick(event, "toDo"));
        tableViewDoing.setOnMouseClicked((MouseEvent event) -> mainController.taskOnClick(event, "doing"));
        tableViewDone.setOnMouseClicked((MouseEvent event) -> mainController.taskOnClick(event, "done"));

        //when the user opens the application, all the categories will be showing
        data.getAllTasks().stream().forEach(t -> t.getCategory().setShowing(true));

        //updating the lists of tasks that the tables shows
        update();

        //adding all the gridpanes to the main layoutpane
        pane.setTop(gpTop);
        pane.setCenter(gpTaskList);
        pane.setLeft(gpCategory);

        //setting the scene and showing it
        stage.setScene(mainScene);
        stage.show();

    }

    /**
     * method that returns the tasklist object
     * @return tasklist
     */
    public TaskList getData() {
        return data;
    }

    /**
     * method get the sort by combobox
     * @return the sort by combobox
     */
    public ComboBox getSortBy(){
        return sortBy;
    }

    /**
     * method to get the to-do tableView
     * @return to-do TableView
     */
    public TableView<Task> getToDoTableView(){
        return tableViewToDo;
    }

    /**
     * method to get the doing tableView
     * @return doing TableView
     */
    public TableView<Task> getDoingTableView(){
        return tableViewDoing;
    }

    /**
     * method to get the done tableView
     * @return done TableView
     */
    public TableView<Task> getDoneTableView(){
        return tableViewDone;
    }

    /**
     * method that updates all the lists of tasks that the table shows, so that all information is updated
     */
    public void update() {
        //setting the items that the tableview for category will contain
        tableViewCategory.setItems(data.getCategoryList());
        //getting the to do, doing and done lists and making them only contain the categories that are supposed to be showing
        List<Task> toDoList = data.getToDoList().stream().filter(t -> t.getCategory().isShowing()).collect(Collectors.toList());
        List<Task> doingList = data.getDoingList().stream().filter(t -> t.getCategory().isShowing()).collect(Collectors.toList());
        List<Task> doneList = data.getDoneList().stream().filter(t -> t.getCategory().isShowing()).collect(Collectors.toList());
        //making all of the lists to observable lists so they can be added to the tableviews
        ObservableList<Task> toDoObservableList = FXCollections.observableList(toDoList);
        ObservableList<Task> doingObservableList = FXCollections.observableList(doingList);
        ObservableList<Task> doneObservableList = FXCollections.observableList(doneList);
        //adding the to do, doing and done lists to their tableview
        tableViewToDo.setItems(toDoObservableList);
        tableViewDoing.setItems(doingObservableList);
        tableViewDone.setItems(doneObservableList);
        //refreshing all of the tables
        tableViewToDo.refresh();
        tableViewDoing.refresh();
        tableViewDone.refresh();
        //updating the name in case the user changed the title name
        title.setText(data.getTitleName());
    }

    /**
     * method that creates all the tables (to do, doing, done and category) and adds them to the gridpane
     */
    private void createTableViews(){
        //creating the table for the to do list
        tableViewToDo = new TableView();
        //creating the columns in the table
        toDoListColumn = new TableColumn<>("To-do");
        TableColumn toDoDeadlineColumn = new CustomTableColumn("Deadline",this);
        TableColumn toDoTaskColumn = new CustomTableColumn("Task", this);
        TableColumn toDoPriorityColumn = new CustomTableColumn(false, this);
        TableColumn toDoButtonColumn = new CustomTableColumn("to do","buttonRight", this);
        //making it so that the user cant move the columns around
        toDoListColumn.setReorderable(false);
        //adding the deadline, taskname, priority and button column to the todolist column to create nested columns
        toDoListColumn.getColumns().addAll(toDoDeadlineColumn, toDoPriorityColumn, toDoTaskColumn,toDoButtonColumn);
        //adding all the columns to the table
        tableViewToDo.getColumns().addAll(toDoListColumn);
        //making the table only show the columns that i added
        tableViewToDo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the table to the gridpane
        gpTaskList.add(tableViewToDo, 1, 1,2,1);
        //making the table editable
        tableViewToDo.setEditable(true);

        //creating the table for the doing list
        tableViewDoing = new TableView();
        //creating the columns that the table will contain
        doingColumn = new TableColumn<>("Doing");
        TableColumn doingDeadlineColumn = new CustomTableColumn("Deadline",this);
        TableColumn doingTaskColumn = new CustomTableColumn("Task", this);
        TableColumn doingPriorityColumn = new CustomTableColumn(false, this);
        TableColumn doingButtonLeftColumn = new CustomTableColumn("doing","buttonLeft", this);
        TableColumn doingButtonRightColumn = new CustomTableColumn("doing","buttonRight", this);
        //making it so that the user cant move the columns around
        doingColumn.setReorderable(false);
        //adding the deadline, taskname, priorty and button column to the doing column to create nested columns
        doingColumn.getColumns().addAll(doingDeadlineColumn, doingPriorityColumn,doingTaskColumn,doingButtonLeftColumn,doingButtonRightColumn);
        //adding all the columns to the table
        tableViewDoing.getColumns().addAll(doingColumn);
        //making the table only show the columns that i added
        tableViewDoing.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the todolist table to gridpane
        gpTaskList.add(tableViewDoing, 3, 1);
        //making the table editable
        tableViewDoing.setEditable(true);

        //creating the table for done list
        tableViewDone = new TableView();
        //creating the columns that the table will contain
        doneColumn = new TableColumn<>("Completed");
        TableColumn doneFinishDateColumn = new CustomTableColumn("Finish date",this);
        TableColumn doneTaskColumn = new CustomTableColumn("Task", this);
        TableColumn donePriorityColumn = new CustomTableColumn(false, this);
        TableColumn doneButtonColumn = new CustomTableColumn("done","buttonLeft", this);
        //making it so that the user cant move the columns around
        doneColumn.setReorderable(false);
        //adding the deadline and taskname column to the done column to create nested columns
        doneColumn.getColumns().addAll(doneFinishDateColumn , donePriorityColumn, doneTaskColumn,doneButtonColumn);
        //adding all the columns to the table
        tableViewDone.getColumns().addAll(doneColumn);
        //making the table only show the columns that i added
        tableViewDone.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the done table to gridpane
        gpTaskList.add(tableViewDone, 4, 1);
        //making the table editable
        tableViewDone.setEditable(true);

        //creating the category table
        tableViewCategory = new TableView();
        //creating the columns that the table will contain
        TableColumn<Category, String> categoryColumn = new CustomTableColumn("Category", this);
        TableColumn<Category, Void> checkboxColumn = new CustomTableColumn(true, this);
        //adding the columns to the category table
        tableViewCategory.getColumns().addAll(checkboxColumn, categoryColumn);
        //deciding what size the table will be
        tableViewCategory.setPrefSize(135, 300);
        tableViewCategory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the category table to gridpane
        gpCategory.add(tableViewCategory, 0, 0);
    }

    /**
     * method that specifies what happens when the user closes the program
     * @throws Exception
     */
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

    /**
     * method that specifies what happens when the user starts the program
     * @throws Exception
     */
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