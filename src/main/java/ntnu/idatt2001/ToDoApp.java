package ntnu.idatt2001;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ToDoApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("To-Do application");
        BorderPane pane = new BorderPane();
        GridPane gp = new GridPane();

        Text title = new Text("To-Do list");
        title.setFont(Font.font("Tohoma", FontWeight.EXTRA_BOLD, 40));


        StackPane sp = new StackPane();
        sp.setStyle("-fx-border-color: black");
        sp.setMinSize(300,200);

        StackPane toDoTitle = new StackPane();
        toDoTitle.setStyle("-fx-border-color: black");
        toDoTitle.setMinSize(300,20);

        StackPane toDoList = new StackPane();
        toDoList.setStyle("-fx-border-color: red");
        toDoList.setMinSize(300,200);

        StackPane toDoDead = new StackPane();
        toDoDead.setStyle("-fx-border-color: black");
        toDoDead.setMinSize(300,20);

        StackPane doingTitle = new StackPane();
        doingTitle.setStyle("-fx-border-color: black");
        doingTitle.setMinSize(300,20);

        StackPane doingDead = new StackPane();
        doingDead.setStyle("-fx-border-color: black");
        doingDead.setMinSize(300,20);

        StackPane doneTitle = new StackPane();
        doneTitle.setStyle("-fx-border-color: black");
        doneTitle.setMinSize(300,20);


        StackPane doingList = new StackPane();
        doingList.setStyle("-fx-border-color: black");
        doingList.setMinSize(300,200);

        StackPane doneList = new StackPane();
        doneList.setStyle("-fx-border-color: black");
        doneList.setMinSize(300,200);

        Button addTask = new Button("Add Task");
        Button sortBy = new Button("Sort by");

        gp.add(addTask,0,0);
        gp.add(sortBy,2,0);
        gp.add(toDoTitle,0,2,4,1);
        gp.add(toDoDead,0,3,1,10);
        gp.add(toDoList,1,3,3,10);


        gp.add(doingTitle,5,2,4,1);
        gp.add(doingList,6,3,3,10);
        gp.add(doingDead,6,3,1,10);

        gp.add(doneTitle,10,2,4,1);
        gp.add(doneList,10,3,3,10);

        pane.setTop(title);
        pane.setCenter(gp);
        //pane.setLeft(sp);

        gp.setGridLinesVisible(true);

        stage.setScene(new Scene(pane, 1100, 900));
        stage.show();

    }
}
