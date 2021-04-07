package ntnu.idatt2001;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for one tasklist
 * A tasklist contains all tasks in a given situation (ex. application)
 */
public class TaskList implements java.io.Serializable{
    private ArrayList<Task> allTasks;

    /**
     * constructor that creates a new tasklist
     */
    public TaskList() {
        allTasks = new ArrayList<>();
    }

    /**
     * method to get all of the tasks belonging to a given tasklist
     * @return a list of all the tasks
     */
    public ArrayList<Task> getAllTasks() {
        return allTasks;
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
     * method to change the status of one task
     * @param task - the task that will have its status changed
     * @param status - the new status to the task
     * @return true if the task was successfully added, false otherwise
     */
    //consider removing this method
    public boolean changeStatus(Task task, String status){
        removeTask(task);
        task.setStatus(status);
        return addTask(task);
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
        if (sortBy.equals("deadline")){
            allTasks.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortBy.equals("priority")){
            allTasks.sort(Comparator.comparing(Task::getPriority));
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
