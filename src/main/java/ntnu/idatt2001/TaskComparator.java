package ntnu.idatt2001;

import java.time.LocalDateTime;
import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    public int compare(Task left, Task right) {
        // compares if left and right is null
        if (left.getDeadline() == null) return right.getDeadline() == null ? 0 : 1;
        if (right.getDeadline() == null) return -1;
        // you are now guaranteed that neither left nor right are null.

        // if neither left nor right are null
        return left.getDeadline().compareTo(right.getDeadline());
    }
}
