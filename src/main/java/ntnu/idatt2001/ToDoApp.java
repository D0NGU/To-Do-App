package ntnu.idatt2001;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoApp extends Application {
    TaskList data2 = new TaskList();
    ArrayList<Task> allTasks = data2.getAllTasks();
    TableView tableViewToDo;
    TableView tableViewDoing;
    TableView tableViewDone;
    TableView tableViewCategory;


    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        //adding testdata to test the application
        fillWithTestData();

        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");

        //creating layoutpanes for the gui
        stage.setTitle("To-Do application");
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(13,13,13,13));
        GridPane gpTop = new GridPane();
        GridPane gpTaskList = new GridPane();
        GridPane gpCategory = new GridPane();
        GridPane gpAddTask = new GridPane();

        Scene mainScene = new Scene(pane, 1080, 720);
        Scene addTaskScene = new Scene(gpAddTask, 1080, 720);

        //adding paddings for the visuals (more space between layoutpane and its content)
        gpTop.setPadding(new Insets(0,0,0,10));
        gpTaskList.setPadding(new Insets(10,10,10,10));
        gpCategory.setPadding(new Insets(50,20,20,20));
        gpAddTask.setPadding(new Insets(300,300,300,300));

        //adding the spacing between all contents inn the gridpanes
        gpAddTask.setHgap(13);
        gpAddTask.setVgap(6);
        gpTop.setHgap(13);
        gpTop.setVgap(6);

        //adding the title
        Text title = new Text("To-Do list");
        title.setFont(Font.font("Tohoma", FontWeight.EXTRA_BOLD, 40));

        Label test = new Label("test ");

        //adding two buttons
        Button addTask = new Button("Add Task");
        Button sortBy = new Button("Sort by");

        //adding the buttons and title to their gridpane
        gpTop.add(title,0,0,2,1);
        gpTop.add(addTask,3,1);
        gpTop.add(sortBy,4,1);

        //creating the table for the todo list
        tableViewToDo = new TableView();
        //creating the columns in the table
        TableColumn<Task, String> toDoListColumn = new TableColumn<>("To-do list");
        TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
        TableColumn<Task, String> taskNameColumn = new TableColumn<>("Task");
        //adding the deadline and taskname column to the todolist column to create nested columns
        toDoListColumn.getColumns().addAll(deadlineColumn,taskNameColumn);
        //added all the columns to the table
        tableViewToDo.getColumns().addAll(toDoListColumn);
        //setting what the values of the columns will be
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //making the table only show the columns that i added
        tableViewToDo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the table to the gridpane
        gpTaskList.add(tableViewToDo,1,1);

        //creating the table for the doing list
        tableViewDoing = new TableView();
        TableColumn<Task, String> doingColumn = new TableColumn<>("Doing list");
        TableColumn<Task, String> deadlineColumn1 = new TableColumn<>("Deadline1");
        TableColumn<Task, String> taskNameColumn1 = new TableColumn<>("Task1");
        doingColumn.getColumns().addAll(deadlineColumn1,taskNameColumn1);
        tableViewDoing.getColumns().addAll(doingColumn);
        deadlineColumn1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewDoing.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the todolist table to gridpane
        gpTaskList.add(tableViewDoing,2,1);

        //creating the table for done list
        tableViewDone = new TableView();
        TableColumn<Task, String> doneColumn = new TableColumn<>("Done list");
        TableColumn<Task, String> deadlineColumn2 = new TableColumn<>("Deadline2");
        TableColumn<Task, String> taskNameColumn2 = new TableColumn<>("Task2");
        doneColumn.getColumns().addAll(deadlineColumn2,taskNameColumn2);
        tableViewDone.getColumns().addAll(doneColumn);
        deadlineColumn2.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewDone.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //adding the done table to gridpane
        gpTaskList.add(tableViewDone,3,1);

        //creating the category table
        tableViewCategory = new TableView();
        TableColumn<Task, String> categoryColumn = new TableColumn<>("Categories");
        tableViewCategory.getColumns().addAll(categoryColumn);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //deciding what size the table will be
        tableViewCategory.setPrefSize(130,300);
        tableViewCategory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gpCategory.add(tableViewCategory,0,0);

        //adding all the task data to the tables
        update();

        //all of the variables that are displayed in add task scene
        addTask.setOnAction(actionEvent -> stage.setScene(addTaskScene));
        TextField taskNameField = new TextField("Task");
        TextField taskDescriptionField = new TextField("Task description");
        TextField deadLineTime = new TextField();
        deadLineTime.setPromptText("hh:mm");
        deadLineTime.setPrefWidth(70);
        TextField startDateTime = new TextField(LocalTime.now().format(timeformat));
        startDateTime.setPrefWidth(70);
        DatePicker deadlineDate = new DatePicker();
        deadlineDate.setPrefWidth(110);
        DatePicker startDateDate = new DatePicker(LocalDate.now());
        startDateDate.setPrefWidth(110);
        TextField taskCategoryField = new TextField();
        taskCategoryField.setPrefWidth(110);
        ChoiceBox<String> priorityChoiceBox= new ChoiceBox<>();
        priorityChoiceBox.getItems().addAll("High", "Medium", "Low");
        priorityChoiceBox.setValue("High");
        ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
        statusChoiceBox.getItems().addAll("to do", "doing");
        statusChoiceBox.setValue("to do");
        //buttons that are displayed inn add task scene
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        HBox outsideBox = new HBox();
        outsideBox.setStyle("-fx-border-color: black");


        //Layout of the add task scene
        gpAddTask.add(outsideBox,0,0,7,10);
        gpAddTask.add(taskNameField,1,1,5,1);
        gpAddTask.add(taskDescriptionField,2,6,4,2);
        gpAddTask.add(new Label("Description:"), 1,6);

        gpAddTask.add(new Label("Deadline:"), 1,4);
        gpAddTask.add(deadlineDate,2,4);
        gpAddTask.add(deadLineTime,3,4);

        gpAddTask.add(new Label("Startdate"), 1,3);
        gpAddTask.add(startDateDate,2,3);
        gpAddTask.add(startDateTime,3,3);

        gpAddTask.add(new Label("Category:"),1,5);
        gpAddTask.add(taskCategoryField,2,5);

        gpAddTask.add(new Label("Priority:"),4,3);
        gpAddTask.add(priorityChoiceBox,5,3);

        gpAddTask.add(new Label("Status:"),4,4);
        gpAddTask.add(statusChoiceBox,5,4);

        gpAddTask.add(addButton,5,8);
        gpAddTask.add(cancelButton,1,8);
        GridPane.setHalignment(addButton, HPos.RIGHT);
        //the add button is pressed
        addButton.setOnAction(actionEvent -> {
            //checks what priority it needs to set
            int priority = 1;
            if(priorityChoiceBox.getValue().equals("High")){
                priority = 3;
            }else if(priorityChoiceBox.getValue().equals("Medium")){
                priority = 2;
            }else if(priorityChoiceBox.getValue().equals("Low")){
                priority = 1;
            }
            data2.addTask(new Task(taskNameField.getText(),statusChoiceBox.getValue(),priority,
                    taskDescriptionField.getText(),LocalDateTime.of(deadlineDate.getValue(),LocalTime.parse(deadLineTime.getText()))
                    ,LocalDateTime.of(startDateDate.getValue(),LocalTime.parse(startDateTime.getText()))
                    , new Category(taskCategoryField.getText(),"")));
            stage.setScene(mainScene);
            update();
        });

        //what happens when you click on the cancelbutton
        cancelButton.setOnAction(actionEvent -> stage.setScene(mainScene));

        //adding all the gridpanes to the main layoutpane
        pane.setTop(gpTop);
        pane.setCenter(gpTaskList);
        pane.setLeft(gpCategory);

        //css code to remove scroll bar at the bottom of the tableviews
        /*.table-view .scroll-bar * {
                -fx-min-width: 0;
        -fx-pref-width: 0;
        -fx-max-width: 0;

        -fx-min-height: 0;
        -fx-pref-height: 0;
        -fx-max-height: 0;}*/

        //setting the scene and showing it
        stage.setScene(mainScene);
        stage.show();

    }

    private void update(){
        tableViewToDo.setItems(data2.getToDoList());
        tableViewDoing.setItems(data2.getDoingList());
        tableViewDone.setItems(data2.getDoneList());
        List<Category> categorylist = allTasks.stream().map(Task::getCategory).distinct().collect(Collectors.toList());
        ObservableList<Category> categoryList = FXCollections.observableList(categorylist);
        tableViewCategory.setItems(categoryList);
    }

    private void fillWithTestData(){
        data2.addTask(new Task("test","to do",1," ", LocalDateTime.of(LocalDate.of(2021,03,20),LocalTime.of(20,00)),
                LocalDateTime.now(), new Category("c", "")));
        data2.addTask(new Task("test2","doing",2," ",LocalDateTime.of(LocalDate.of(2021,03,25), LocalTime.of(8,00))
                ,LocalDateTime.now(),new Category("c", "")));
        data2.addTask(new Task("test3","done",1," ",LocalDateTime.of(LocalDate.of(2021,03,23),LocalTime.of(10,00)),
                LocalDateTime.now(),new Category("c2", "")));
    }

}

