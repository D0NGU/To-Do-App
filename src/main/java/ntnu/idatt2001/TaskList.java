package ntnu.idatt2001;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for one tasklist
 * A tasklist contains all tasks in a given situation (ex. application)
 */
public class TaskList implements java.io.Serializable{
    private ArrayList<Task> allTasks;
    private String titleName;

    /**
     * constructor that creates a new tasklist
     */
    public TaskList() {
        allTasks = new ArrayList<>();
        titleName = "To-Do List";
    }

    /**
     * method to get all of the tasks belonging to a given tasklist
     * @return a list of all the tasks
     */
    public ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    /**
     *
     * @return the name of the title to the app
     */
    public String getTitleName(){
        return titleName;
    }

    /**
     * Takes in a parameter title and sets the titleName to the given title
     * @param title sets the given title as the title to the app
     */
    public void setTitleName(String title){
        titleName = title;
    }

    /**
     * method to only get the tasks with the status "to do"
     * @return a list of all the tasks with the status "to do"
     */
    public ObservableList<Task> getToDoList() {
        List<Task> toDoList = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("to do")).collect(Collectors.toList());
        return FXCollections.observableList(toDoList);
    }

    /**
     * method to only get the tasks with the status "doing"
     * @return a list of all the tasks with the status "doing"
     */
    public ObservableList<Task> getDoingList() {
        List<Task> doingList = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("doing")).collect(Collectors.toList());
        return FXCollections.observableList(doingList);
    }

    /**
     * method to only get the tasks with the status "done"
     * @return a list of all the tasks with the status "done"
     */
    public ObservableList<Task> getDoneList() {
        List<Task> doneList = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("done")).collect(Collectors.toList());
        return FXCollections.observableList(doneList);
    }

    /**
     * method to only get the categories of all tasks
     * @return a list of all categories of all tasks
     */
    public ObservableList<Category> getCategoryList(){
        List<Category> categoryList = allTasks.stream().map(Task::getCategory).distinct().collect(Collectors.toList());
        return FXCollections.observableList(categoryList);
    }
    /**
     * method to add a new task to the given tasklist
     * @param task - the new task
     * @return true if it was registered (the task was not registered already),
     *         false if the task object was already registered
     */
    public boolean addTask(Task task){
        if(!(allTasks.contains(task))){
            allTasks.add(task);
            return true;
        }
        return false;
    }

    /**
     * method to remove a task from the given tasklist
     * @param task - the task that shall be removed
     * @return true if the task was successfully removed from the tasklist, false if otherwise
     */
    public boolean removeTask(Task task){
        return allTasks.remove(task);
    }

    /**
     * method to sort the tasklist after deadline or priority
     * @param sortBy - what the list shall be sorted after (either deadline og priority)
     */
    public void sortLists(String sortBy){
        if (sortBy.equalsIgnoreCase("deadline")){
            Collections.sort(allTasks, new TaskComparator());
        } else if (sortBy.equalsIgnoreCase("priority")){
            allTasks.sort(Comparator.comparing(Task::getPriority).reversed());
        }
    }

    /**
     * method that returns all the information belonging to a given tasklist
     * @return all the contents to the tasklist (the list is sorted after status)
     */
    @Override
    public String toString() {
        return "TaskList{" +
                "toDoList=" + getToDoList() +
                ", doingList=" + getDoingList() +
                ", doneList=" + getDoneList() +
                '}';
    }
}
