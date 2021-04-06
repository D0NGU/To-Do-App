package ntnu.idatt2001;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoApp extends Application {
    TaskList data = new TaskList();
    TableView tableViewToDo;
    TableView tableViewDoing;
    TableView tableViewDone;
    TableView tableViewCategory;

    //Variables for the add task scene
    //they are outside start method so it is possible to use them in private methods
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    TextField taskNameField = new TextField();
    TextField taskDescriptionField = new TextField();
    TextField deadLineTime = new TextField();
    TextField startDateTime = new TextField(LocalTime.now().format(timeFormat));
    DatePicker deadlineDate = new DatePicker();
    DatePicker startDateDate = new DatePicker(LocalDate.now());
    TextField taskCategoryField = new TextField();
    ChoiceBox<String> priorityChoiceBox= new ChoiceBox<>();
    ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();

    //Variables for show task scene
    TextField taskNameField1 = new TextField();
    TextField taskDescriptionField1 = new TextField();
    TextField deadLineTime1 = new TextField();
    TextField startDateTime1 = new TextField(LocalTime.now().format(timeFormat));
    DatePicker deadlineDate1 = new DatePicker();
    DatePicker startDateDate1 = new DatePicker(LocalDate.now());
    TextField taskCategoryField1 = new TextField();
    ChoiceBox<String> priorityChoiceBox1 = new ChoiceBox<>();
    ChoiceBox<String> statusChoiceBox1 = new ChoiceBox<>();


    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        //adding testdata to test the application
        fillWithTestData();

        //a stage to show the view task scene.
        //Makes a new window
        Stage stage2 = new Stage();

        //creating layoutpanes for the gui
        stage.setTitle("To-Do application");
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(13,13,13,13));
        GridPane gpTop = new GridPane();
        GridPane gpTaskList = new GridPane();
        GridPane gpCategory = new GridPane();
        GridPane gpAddTask = new GridPane();
        GridPane gpViewTask = new GridPane();

        Scene mainScene = new Scene(pane, 900, 600);
        Scene addTaskScene = new Scene(gpAddTask, 450, 240);
        Scene viewTaskScene = new Scene(gpViewTask, 450, 240);

        //adding paddings for the visuals (more space between layoutpane and its content)
        gpTop.setPadding(new Insets(0,0,0,10));
        gpTaskList.setPadding(new Insets(10,10,10,10));
        gpCategory.setPadding(new Insets(50,20,20,20));
        gpAddTask.setPadding(new Insets(10,10,10,10));
        gpViewTask.setPadding(new Insets(10,10,10,10));

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
        TableColumn taskNameColumn = new TableColumn<>("Task");
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
        TableColumn taskNameColumn1 = new TableColumn<>("Task1");
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
        TableColumn taskNameColumn2 = new TableColumn<>("Task2");
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

        //add task button is pressed
        addTask.setOnAction(actionEvent -> {
            stage2.setScene(addTaskScene);
            stage2.show();
            resetAddTaskValues();
                });


        addTaskScene(gpAddTask);
        //buttons that are displayed inn add task scene
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");
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
            data.addTask(new Task(taskNameField.getText(),statusChoiceBox.getValue(),priority,
                    taskDescriptionField.getText(),LocalDateTime.of(deadlineDate.getValue(),LocalTime.parse(deadLineTime.getText()))
                    ,LocalDateTime.of(startDateDate.getValue(),LocalTime.parse(startDateTime.getText()))
                    , new Category(taskCategoryField.getText(),"")));
            stage2.close();
            update();
            resetAddTaskValues();

        });

        //what happens when you click on the cancel button
        cancelButton.setOnAction(actionEvent -> {
            stage2.close();
            //stage.show();
            resetAddTaskValues();
        });

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

        //Buttons save and delete are added to viewTaskScene
        viewTaskScene(gpViewTask);
        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");
        //Colors are added to the buttons
        deleteButton.setStyle("-fx-background-color: red");
        saveButton.setStyle("-fx-background-color: lightgreen");

        gpViewTask.add(saveButton,5,8);
        gpViewTask.add(deleteButton,1,8);
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        GridPane.setHalignment(deleteButton, HPos.LEFT);

        //Handles the actions "View task", "Delete Task"
        //"Save" button is yet to be implemented
        //some code from http://java-buddy.blogspot.com/2013/05/detect-mouse-click-on-javafx-tableview.html?m=1
        //implementing a method to allow customization of the cells in columns.
        //Callback is an interface that implements a method "call"
        // and the body of this method can be called on all cells in columns that have gotten this method.
        Callback<TableColumn, TableCell> stringCellFactory =
                p -> {
                    //a class "MyStringTableCell" is implemented at the end of this page
                    MyStringTableCell cell = new MyStringTableCell();
                    //addEventFilter takes in 2 arguments:
                    // 1) the action (mouse click)
                    // 2) EventHandler which is directly implemented here with lambda
                    cell.addEventFilter(MouseEvent.MOUSE_CLICKED, t -> {

                        TableCell c = (TableCell) t.getSource();
                        int index = c.getIndex();
                        //check if the cell selected is in to-do column, doing column or done column.
                        if (c.getTableView().getColumns().get(0) == tableViewToDo.getColumns().get(0)){
                            //stores the task selected in a variable "tasktoView"
                            Task taskToView = data.getToDoList().get(index);
                            //this method fills in the text fields of the viewTaskScene with
                            // the information of the selected task
                            fillViewTask(taskToView);
                            //opens a new window to view the selected task
                            stage2.setScene(viewTaskScene);
                            stage2.show();

                            //deletes the task we are working with here (taskToView)
                            // if the button "delete" is clicked
                            deleteButton.setOnAction(actionEvent -> {
                                data.removeTask(taskToView);
                                //removes the task and returns to mainScene
                                stage2.close();
                                //updates the table after deleting a task
                                update();
                            });

                            //saves the editted informasjon of the task
                            saveButton.setOnAction(actionEvent -> {
                                //a help method to update the information of the task
                                editTaskUpdate(taskToView);

                                //This will make the name of the task appear on the table at once
                                // without having to reopen the app
                                cell.updateItem(taskToView.getName(), false);

                                stage2.close();
                                update();
                            });

                        } else if (c.getTableView().getColumns().get(0) == tableViewDoing.getColumns().get(0)){
                            stage2.setScene(viewTaskScene);
                            Task taskToView = data.getDoingList().get(index);
                            fillViewTask(taskToView);
                            stage2.show();

                            deleteButton.setOnAction(actionEvent -> {
                                data.removeTask(taskToView);
                                stage2.close();
                                update();
                            });

                            saveButton.setOnAction(actionEvent -> {
                                editTaskUpdate(taskToView);
                                cell.updateItem(taskToView.getName(), false);

                                stage2.close();
                                update();
                            });

                        } else if (c.getTableView().getColumns().get(0) == tableViewDone.getColumns().get(0)){
                            stage2.setScene(viewTaskScene);
                            Task taskToView = data.getDoneList().get(index);
                            fillViewTask(taskToView);
                            stage2.show();

                            deleteButton.setOnAction(actionEvent -> {
                                data.removeTask(taskToView);
                                stage2.close();
                                update();
                            });

                            saveButton.setOnAction(actionEvent -> {
                                editTaskUpdate(taskToView);
                                cell.updateItem(taskToView.getName(), false);

                                stage2.close();
                                update();
                            });
                        }

                    });
                    return cell;
                };

        //cells in the to-do, doing and done columns will be able to perform the actions
        // implemented in the stringCellFactory variable (view and delete tasks)
        taskNameColumn.setCellFactory(stringCellFactory);
        taskNameColumn1.setCellFactory(stringCellFactory);
        taskNameColumn2.setCellFactory(stringCellFactory);

        //setting the scene and showing it
        stage.setScene(mainScene);
        stage.show();

    }

    private void editTaskUpdate(Task task){
        //checks what priority it needs to set
        //this code is not working, need to find how to fix this.
        // priority and status needs to be fixed
        int priority = 1;
        if(priorityChoiceBox.getValue().equals("High")){
            priority = 3;
        }else if(priorityChoiceBox.getValue().equals("Medium")){
            priority = 2;
        }else if(priorityChoiceBox.getValue().equals("Low")){
            priority = 1;
        }
        task.setPriority(priority);

        task.setName(taskNameField1.getText());
        task.setDescription(taskDescriptionField1.getText());
        task.setStatus(statusChoiceBox1.getValue());
        task.setDeadline(LocalDateTime.of(deadlineDate1.getValue(),LocalTime.parse(deadLineTime1.getText())));
        task.setStartDate(LocalDateTime.of(startDateDate1.getValue(),LocalTime.parse(startDateTime1.getText())));

        //list of current categories
        List<Category> categoryList = data.getAllTasks().stream().map(Task::getCategory).distinct().collect(Collectors.toList());

        //checking if category exists from before
        boolean categoryExists = false;
        for (Category c: categoryList) {
            if (taskCategoryField1.getText().equals(c.getName())){
                //if category exist we update the task with it
                categoryExists = true;
                task.setCategory(c);
            }
        }

        //if category name does not exist from before, we add the category to the list and update the task
        if (categoryExists == false){
            task.setCategory(new Category(taskCategoryField1.getText(), ""));
            categoryList.add(new Category(taskCategoryField1.getText(), ""));
        }

        //update the table so that it shows all the categories
        ObservableList<Category> categoryObservableList = FXCollections.observableList(categoryList);
        tableViewCategory.setItems(categoryObservableList);

    }

    private GridPane viewTaskScene(GridPane gpViewTask){
        HBox outsideBox = new HBox();
        outsideBox.setStyle("-fx-border-color: black");
        taskNameField1.setPromptText("Task name");
        taskCategoryField1.setPromptText("Category");
        taskDescriptionField1.setPromptText("Task description");
        deadLineTime1.setPromptText("hh:mm");
        deadLineTime1.setPrefWidth(70);
        startDateTime1.setPrefWidth(70);
        deadlineDate1.setPrefWidth(110);
        startDateDate1.setPrefWidth(110);
        taskCategoryField1.setPrefWidth(110);
        priorityChoiceBox1.getItems().addAll("High", "Medium", "Low");
        priorityChoiceBox1.setValue("High");
        statusChoiceBox1.getItems().addAll("to do", "doing");
        statusChoiceBox1.setValue("to do");

        gpViewTask.add(outsideBox,0,0,7,10);
        gpViewTask.add(taskNameField1,1,1,5,1);
        gpViewTask.add(taskDescriptionField1,2,6,4,2);
        gpViewTask.add(new Label("Description:"), 1,6);
        gpViewTask.add(new Label("Deadline:"), 1,4);
        gpViewTask.add(deadlineDate1,2,4);
        gpViewTask.add(deadLineTime1,3,4);
        gpViewTask.add(new Label("Startdate"), 1,3);
        gpViewTask.add(startDateDate1,2,3);
        gpViewTask.add(startDateTime1,3,3);
        gpViewTask.add(new Label("Category:"),1,5);
        gpViewTask.add(taskCategoryField1,2,5);
        gpViewTask.add(new Label("Priority:"),4,3);
        gpViewTask.add(priorityChoiceBox1,5,3);
        gpViewTask.add(new Label("Status:"),4,4);
        gpViewTask.add(statusChoiceBox1,5,4);

        return gpViewTask;
    }

    //method that creates add task scene
    private GridPane addTaskScene(GridPane gpAddTask){

        HBox outsideBox = new HBox();
        outsideBox.setStyle("-fx-border-color: black");
        taskNameField.setPromptText("Task name");
        taskCategoryField.setPromptText("Category");
        taskDescriptionField.setPromptText("Task description");
        deadLineTime.setPromptText("hh:mm");
        deadLineTime.setPrefWidth(70);
        startDateTime.setPrefWidth(70);
        deadlineDate.setPrefWidth(110);
        startDateDate.setPrefWidth(110);
        taskCategoryField.setPrefWidth(110);
        priorityChoiceBox.getItems().addAll("High", "Medium", "Low");
        priorityChoiceBox.setValue("High");
        statusChoiceBox.getItems().addAll("to do", "doing");
        statusChoiceBox.setValue("to do");

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
        return gpAddTask;
    }
    private void resetAddTaskValues(){
        taskDescriptionField.setText("");
        taskNameField.setText("");
        taskCategoryField.setText("");
        startDateTime.setText(LocalTime.now().format(timeFormat));
        startDateDate.setValue(LocalDate.now());
        deadlineDate.setValue(null);
        deadLineTime.setText("");
    }

    private void update(){
        tableViewToDo.setItems(data.getToDoList());
        tableViewDoing.setItems(data.getDoingList());
        tableViewDone.setItems(data.getDoneList());
        List<Category> categoryList = data.getAllTasks().stream().map(Task::getCategory).distinct().collect(Collectors.toList());
        ObservableList<Category> categoryObservableList = FXCollections.observableList(categoryList);
        tableViewCategory.setItems(categoryObservableList);
    }

    private void fillWithTestData(){
        data.addTask(new Task("test","to do",1,"something to test ", LocalDateTime.of(LocalDate.of(2021,03,20), LocalTime.of(20,00)),
                LocalDateTime.now(), new Category("c", "")));
        data.addTask(new Task("test2","doing",2," ",LocalDateTime.of(LocalDate.of(2021,03,25), LocalTime.of(8,00))
                ,LocalDateTime.now(),new Category("c", "")));
        data.addTask(new Task("test3","done",1," ",LocalDateTime.of(LocalDate.of(2021,03,23),LocalTime.of(10,00)),
                LocalDateTime.now(),new Category("c2", "")));
    }

    private void fillViewTask(Task task){
        taskDescriptionField1.setText(task.getDescription());
        taskNameField1.setText(task.getName());
        taskCategoryField1.setText(task.getCategory().getName());
        startDateTime1.setText(task.getStartDate().toLocalTime().format(timeFormat));
        startDateDate1.setValue(task.getStartDate().toLocalDate());
        deadlineDate1.setValue(task.getDeadline().toLocalDate());
        deadLineTime1.setText(task.getDeadline().toLocalTime().format(timeFormat));
    }

    @Override
    public void stop() throws Exception {
        //Serializing the TaskList object "data" when application stops
        //In other words: saving all the information in TaskList object to a file
        try(FileOutputStream outputStream = new FileOutputStream("data.ser");
            ObjectOutputStream out = new ObjectOutputStream(outputStream)){
            out.writeObject(data);
        }catch(IOException ioe){
            System.out.println("IO-failure");
        }catch (Exception e){
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
        }catch(FileNotFoundException e){
            //System.out.println("File not found");
        }catch(EOFException e){
            //System.out.println("File found but empty");
        }catch(IOException ioe){
            System.out.println("IO-failure");
        }catch (Exception e){
            System.out.println("Something other than IO failure");
        }
        super.init();
    }
}
//code from http://java-buddy.blogspot.com/2013/05/detect-mouse-click-on-javafx-tableview.html?m=1
class MyStringTableCell extends TableCell<Task, String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : getString());
        setGraphic(null);
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}

