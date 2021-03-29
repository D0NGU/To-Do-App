package ntnu.idatt2001;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String status;
    private int priority;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private Category category;

    public Task(String name, String status, int priority, String description, LocalDateTime deadline,
                LocalDateTime startDate, Category category) {
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.description = description;
        this.deadline = deadline;
        this.startDate = startDate;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(category, task.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, category);
    }

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
