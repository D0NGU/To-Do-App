package ntnu.idatt2001.controllers;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import ntnu.idatt2001.models.Category;
import ntnu.idatt2001.models.Task;
import ntnu.idatt2001.views.ToDoApp;

/**
 * A controller class which communicates between CustomTableColumn, ToDoApp (view) and the models
 */
public class CustomTableColumnController {
    ToDoApp app;

    /**
     * constructor for creating a new controller
     * @param app - the application
     */
    public CustomTableColumnController(ToDoApp app) {
        this.app = app;
    }

    /**
     * method that decides what happens when you click on a checkbox
     * @param checkBox - the checkbox that was clicked on
     * @param category - the category the checkbox belongs to
     */
    public void checkboxOnClick(CheckBox checkBox, Category category){
        //creating a new tooltip, so that an unchecked and checked checkbox can show different information
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(300));

        if(!checkBox.isSelected()){ //if the checkbox is not selected, then the category is not to be shown
            //setting all the categories with the same name, to not be shown
            app.getData().getAllTasks().stream().filter(t -> t.getCategory().getName().equals(category.getName()))
               .forEach(t -> t.getCategory().setShowing(false));
            //updating the application so that its only showing wanted tasks of a specific category
            app.update();
            //creating the custom tooltip for unchecked
            tooltip = new Tooltip("Show tasks of this category");
        }else if(checkBox.isSelected()){ //if the checkbox is selected, then the category is to be shown
            //changing all the categories with the same name, to be shown
            app.getData().getAllTasks().stream().filter(t -> t.getCategory().getName().equals(category.getName()))
               .forEach(t -> t.getCategory().setShowing(true));
            //updating the application so that its only showing wanted tasks of a specific category
            app.update();
            //creating the custom tooltip for unchecked
            tooltip = new Tooltip("Hide tasks of this category");
        }
        //adding the tooltip to the checkbox
        checkBox.setTooltip(tooltip);
    }

    /**
     * method that decides what happens when you click on a arrow button (change status button)
     * @param direction - the direction the arrow button is pointing
     * @param task - the task that the arrow button belongs to
     */
    public void arrowButtonOnClick(String direction, Task task){
        //checking which direction the button is pointing towards, to change the status correctly
        if(direction.equalsIgnoreCase("right")){
            //checking what status the task is in, so that the status can be changed correctly
            if(task.getStatus().equals("to do")){
                task.setStatus("doing");
            }else if(task.getStatus().equals("doing")){
                task.setStatus("done");
            }
        }else if(direction.equalsIgnoreCase("left")){
            //checking what status the task is in, so that the status can be changed correctly
            if(task.getStatus().equals("doing")){
                task.setStatus("to do");
            }else if(task.getStatus().equals("done")){
                task.setStatus("doing");
            }
        }
        //updating the tasklist to contain the changed task
        app.update();
    }
}
