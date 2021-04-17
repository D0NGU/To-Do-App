package ntnu.idatt2001.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for one task
 * A task has a name, status, priority, description, deadline, startdate, finishdate and category
 */
public class Task implements java.io.Serializable{
    private String name;
    private String status;
    private int priority;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private Category category;


    /**
     * constructor that creates a new task with a deadline
     * @param name - the name of the task
     * @param status - the status of the task
     * @param priority - the priority of the task
     * @param description - the description of the task
     * @param deadline - the deadline of the task
     * @param startDate - the startdate of the task
     * @param category - the category of the task
     * @throws IllegalArgumentException if any of the input is not valid
     */
    public Task(String name, String status, int priority, String description, LocalDateTime deadline,
                LocalDateTime startDate, Category category) {
        if(name.isBlank()){
            throw new IllegalArgumentException("A task must have a name.");
        }
        if(status.isBlank()){
            throw new IllegalArgumentException("A task must have a status.");
        }
        if(priority < 1 || priority > 3){
            throw new IllegalArgumentException("Priority has to be either 1, 2 or 3.");
        }
        if(startDate == null){
            throw new IllegalArgumentException("A task must have a start date.");
        }
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.description = description;
        this.deadline = deadline;
        this.startDate = startDate;
        this.category = category;
    }

    /**
     * constructor that creates a new task without a deadline
     * @param name - the name of the task
     * @param status - the status of the task
     * @param priority - the priority of the task
     * @param description - the description of the task
     * @param startDate - the startdate of the task
     * @param category - the category of the task
     * @throws IllegalArgumentException if the any of the input is not valid
     */
    public Task(String name, String status, int priority, String description,
                LocalDateTime startDate, Category category) {
        if(name.isBlank()){
            throw new IllegalArgumentException("A task must have a name.");
        }
        if(status.isBlank()){
            throw new IllegalArgumentException("A task must have a status.");
        }
        if(priority < 1 || priority > 3){
            throw new IllegalArgumentException("Priority has to be either 1, 2 or 3.");
        }
        if(startDate == null){
            throw new IllegalArgumentException("A task must have a start date.");
        }
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.description = description;
        this.startDate = startDate;
        this.category = category;
    }

    /**
     * method to get the name of the given task
     * @return the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * method to get the status of the given task
     * @return the status of the task
     */
    public String getStatus() {
        return status;
    }

    /**
     * method to change the status of a given task
     * @param status - the new status
     */
    public void setStatus(String status) {
        if(status.equalsIgnoreCase("done")){
            finishDate = LocalDateTime.now();
        }
        this.status = status;
    }

    /**
     * method to get the priority of the given task
     * @return the priority of the task
     */
    public int getPriority() {
        return priority;
    }

    /**
     * method to change the name of a given task
     * @param name - the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method to change the priority of a given task
     * @param priority - the new priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * method to get the description of the given task
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * method to change the description of a given task
     * @param description - the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * method to get the deadline of the given task
     * @return the deadline of the task
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * method to change the deadline of a given task
     * @param deadline - the new deadline
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * method to get the startdate of the given task
     * @return the startdate of the task
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * method to change the startdate of a given task
     * @param startDate - the new startdate
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * method to get the finishdate of the given task
     * @return the finishdate of the task
     */
    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    /**
     * method to change the finishdate of a given task
     * @param finishDate - the new finishdate
     */
    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * method to get the category of the given task
     * @return the category of the task
     */
    public Category getCategory() {
        return category;
    }

    /**
     * method to change the category of a given task
     * @param category - the new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * method that checks whether or not two task objects are the same. They are the same if either:
     * 1. the task name, description and category are the same
     * 2. both the objects are the same
     * @param o - the other object you want to compare with
     * @return true if the task objects are the same or false if they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(category, task.category);
    }

    /**
     * method that creates a unique id for a task object
     * @return the unique id for a task
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, category);
    }

    /**
     * method that returns all the information belonging to a given task
     * @return all the contents to the task
     */
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", category=" + category +
                '}';
    }
}
