package ntnu.idatt2001;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList implements java.io.Serializable{
    private ArrayList<Task> allTasks;
    //private ArrayList<Task> toDoList;
    //private ArrayList<Task> doingList;
    //private ArrayList<Task> doneList;

    public TaskList() {
        allTasks = new ArrayList<>();
    }

    public ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    public ObservableList<Task> getToDoList() {
        List<Task> toDoList = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("to do")).collect(Collectors.toList());
        return FXCollections.observableList(toDoList);
    }

    public ObservableList<Task> getDoingList() {
        List<Task> doingList = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("doing")).collect(Collectors.toList());
        return FXCollections.observableList(doingList);
    }

    public ObservableList<Task> getDoneList() {
        List<Task> doneList = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("done")).collect(Collectors.toList());
        return FXCollections.observableList(doneList);
    }

    public boolean addTask(Task task){
        if(!(allTasks.contains(task))){
            allTasks.add(task);
            return true;
        }
        return false;
    }

    public boolean changeStatus(Task task, String status){
        removeTask(task);
        task.setStatus(status);
        return addTask(task);
    }

    public boolean removeTask(Task task){
        return allTasks.remove(task);
    }

    public void sortLists(String sortBy){
        if (sortBy.equals("deadline")){
            allTasks.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortBy.equals("priority")){
            allTasks.sort(Comparator.comparing(Task::getPriority));
        }
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "toDoList=" + getToDoList() +
                ", doingList=" + getDoingList() +
                ", doneList=" + getDoneList() +
                '}';
    }
}
