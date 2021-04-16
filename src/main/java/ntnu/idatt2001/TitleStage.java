package ntnu.idatt2001;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TitleStage extends Stage {

    private Text existingTitle = null;
    private String result;

    public TitleStage(Text title){
        super();
        existingTitle = title;
        createStage();
    }

    public String getResult() {
        return result;
    }
    private void createStage() {
        GridPane titlePane = new GridPane();
        titlePane.setHgap(13);
        titlePane.setVgap(6);
        titlePane.setPadding(new Insets(10, 10, 10, 10));

        TextField setTitleField = new TextField(existingTitle.getText());
        Button saveTitle = new Button("Save");
        Text write = new Text("Edit the title here: ");

        titlePane.add(write, 1,1);
        titlePane.add(setTitleField, 1,2);
        titlePane.add(saveTitle, 1,3);

        Scene titleScene = new Scene(titlePane, 200,130);
        super.setScene(titleScene);
        saveTitle.setOnAction(event -> {
            result = setTitleField.getText();
            super.close();
        });

    }

}
