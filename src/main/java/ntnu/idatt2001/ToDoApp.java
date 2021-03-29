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
import java.util.List;
import java.util.stream.Collectors;

public class ToDoApp extends Application {
    final ObservableList<Task> data = FXCollections.observableArrayList(
            new Task("test","todo",1," ", LocalDateTime.of(LocalDate.of(2021,03,20),LocalTime.of(20,00)),
                    LocalDateTime.now(), new Category("c", "")),
            new Task("test2","todo",1," ",LocalDateTime.of(LocalDate.of(2021,03,25), LocalTime.of(8,00))
                    ,LocalDateTime.now(),new Category("c", "")),
            new Task("test3","todo",1," ",LocalDateTime.of(LocalDate.of(2021,03,23),LocalTime.of(10,00)),
                    LocalDateTime.now(),new Category("c2", ""))
    );

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");

        stage.setTitle("To-Do application");
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(13,13,13,13));
        GridPane gpTop = new GridPane();
        GridPane gpTaskList = new GridPane();
        GridPane gpCategory = new GridPane();
        GridPane gpAddTask = new GridPane();

        Scene mainScene = new Scene(pane, 1080, 720);
        Scene addTaskScene = new Scene(gpAddTask, 1080, 720);

        gpTop.setPadding(new Insets(0,0,0,10));
        gpTaskList.setPadding(new Insets(10,10,10,10));
        gpCategory.setPadding(new Insets(50,20,20,20));
        gpAddTask.setPadding(new Insets(300,300,300,300));

        gpAddTask.setHgap(13);
        gpAddTask.setVgap(6);
        gpTop.setHgap(13);
        gpTop.setVgap(6);

        Text title = new Text("To-Do list");
        title.setFont(Font.font("Tohoma", FontWeight.EXTRA_BOLD, 40));

        Label test = new Label("test ");

        Button addTask = new Button("Add Task");
        Button sortBy = new Button("Sort by");

        gpTop.add(title,0,0,2,1);
        gpTop.add(addTask,3,1);
        gpTop.add(sortBy,4,1);

        TableView tableViewToDo = new TableView();
        TableColumn<Task, String> toDoListColumn = new TableColumn<>("To-do list");
        TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
        TableColumn<Task, String> taskNameColumn = new TableColumn<>("Task");
        toDoListColumn.getColumns().addAll(deadlineColumn,taskNameColumn);
        tableViewToDo.getColumns().addAll(toDoListColumn);
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewToDo.getItems().add(new Task("test","todo",1," ",
                LocalDateTime.of(LocalDate.of(2021,03,23),LocalTime.of(8,00)),LocalDateTime.now(),new Category("c", " ")));
        tableViewToDo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        gpTaskList.add(tableViewToDo,1,1);

        TableView tableViewDoing = new TableView();
        TableColumn<Task, String> doingColumn = new TableColumn<>("Doing list");
        TableColumn<Task, String> deadlineColumn1 = new TableColumn<>("Deadline1");
        TableColumn<Task, String> taskNameColumn1 = new TableColumn<>("Task1");
        doingColumn.getColumns().addAll(deadlineColumn1,taskNameColumn1);
        tableViewDoing.getColumns().addAll(doingColumn);
        deadlineColumn1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewDoing.setItems(data);
        tableViewDoing.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        gpTaskList.add(tableViewDoing,2,1);

        TableView tableViewDone = new TableView();
        TableColumn<Task, String> doneColumn = new TableColumn<>("Done list");
        TableColumn<Task, String> deadlineColumn2 = new TableColumn<>("Deadline2");
        TableColumn<Task, String> taskNameColumn2 = new TableColumn<>("Task2");
        doneColumn.getColumns().addAll(deadlineColumn2,taskNameColumn2);
        tableViewDone.getColumns().addAll(doneColumn);
        deadlineColumn2.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewDone.getItems().add(new Task("test3","todo",1," ",
                LocalDateTime.of(LocalDate.of(2021,03,23),LocalTime.of(8,00)),LocalDateTime.now(),new Category("c2", "")));
        tableViewDone.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        gpTaskList.add(tableViewDone,3,1);


        TableView tableViewCategory = new TableView();
        TableColumn<Task, String> categoryColumn = new TableColumn<>("Categories");
        tableViewCategory.getColumns().addAll(categoryColumn);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewCategory.setPrefSize(130,300);
        tableViewCategory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Category> categorylist = data.stream().map(Task::getCategory).distinct().collect(Collectors.toList());
        ObservableList<Category> categoryList = FXCollections.observableList(categorylist);
        tableViewCategory.setItems(categoryList);

        //after you press add task button the scene changes
        //all of the variables that are displayed
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
        //buttons that are displayed
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        HBox outsideBox = new HBox();
        outsideBox.setStyle("-fx-border-color: black");
        //Layout of the scene
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
            tableViewToDo.getItems().add(new Task(taskNameField.getText(),statusChoiceBox.getValue(),priority,
                    taskDescriptionField.getText(),LocalDateTime.of(deadlineDate.getValue(),LocalTime.parse(deadLineTime.getText()))
                    ,LocalDateTime.of(startDateDate.getValue(),LocalTime.parse(startDateTime.getText()))
                    , new Category(taskCategoryField.getText(),"")));
            stage.setScene(mainScene);
        });

        cancelButton.setOnAction(actionEvent -> stage.setScene(mainScene));


        gpCategory.add(tableViewCategory,0,0);

        pane.setTop(gpTop);
        pane.setCenter(gpTaskList);
        pane.setLeft(gpCategory);

        //gp.setGridLinesVisible(true);

        /*.table-view .scroll-bar * {
                -fx-min-width: 0;
        -fx-pref-width: 0;
        -fx-max-width: 0;

        -fx-min-height: 0;
        -fx-pref-height: 0;
        -fx-max-height: 0;}*/

        stage.setScene(mainScene);
        stage.show();

    }
}

