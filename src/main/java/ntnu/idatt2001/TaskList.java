package ntnu.idatt2001;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskList {

    private ArrayList<Task> toDoList;
    private ArrayList<Task> doingList;
    private ArrayList<Task> doneList;

    public TaskList() {
        toDoList = new ArrayList<>();
        doingList = new ArrayList<>();
        doneList = new ArrayList<>();
    }

    public ArrayList<Task> getToDoList() {
        return toDoList;
    }

    public ArrayList<Task> getDoingList() {
        return doingList;
    }

    public ArrayList<Task> getDoneList() {
        return doneList;
    }

    public boolean addTask(Task task){
        if (task.getStatus().equals("to do")){
            toDoList.add(task);
            return true;
        } else if (task.getStatus().equals("doing")){
            doingList.add(task);
            return true;
        } else if (task.getStatus().equals("done")){
            doneList.add(task);
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
        if (task.getStatus().equals("to do")){
            return toDoList.remove(task);
        } else if (task.getStatus().equals("doing")){
            return doingList.remove(task);
        } else if (task.getStatus().equals("done")) {
            return doneList.remove(task);
        }
        return false;
    }

    public void sortLists(String sortBy){
        if (sortBy.equals("deadline")){
            toDoList.sort(Comparator.comparing(Task::getDeadline));
            doingList.sort(Comparator.comparing(Task::getDeadline));
            doneList.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortBy.equals("priority")){
            toDoList.sort(Comparator.comparing(Task::getPriority));
            doingList.sort(Comparator.comparing(Task::getPriority));
            doneList.sort(Comparator.comparing(Task::getPriority));
        }
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "toDoList=" + toDoList +
                ", doingList=" + doingList +
                ", doneList=" + doneList +
                '}';
    }
}
