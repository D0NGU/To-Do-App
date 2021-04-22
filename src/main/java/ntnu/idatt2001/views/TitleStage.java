package ntnu.idatt2001.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * class that represents edit title stage
 */
public class TitleStage extends Stage {

    private Text existingTitle = null;
    private String result;

    /**
     * Constructor that creates title stage
     * @param title -title that is saved from before
     */
    public TitleStage(Text title){
        super();
        existingTitle = title;
        createStage();
    }

    /**
     * Method that returns the new title
     * @return the new title that is saved in createStage() method
     */
    public String getResult() {
        return result;
    }

    /**
     * Method that creates the edit title stage
     */
    private void createStage() {
        super.initModality(Modality.APPLICATION_MODAL);
        GridPane titlePane = new GridPane();
        titlePane.setHgap(13);
        titlePane.setVgap(6);
        titlePane.setPadding(new Insets(10, 10, 10, 10));

        TextField setTitleField = new TextField(existingTitle.getText());
        Button saveTitle = new Button("Save");
        saveTitle.setStyle("-fx-background-color: #62a36d");
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
