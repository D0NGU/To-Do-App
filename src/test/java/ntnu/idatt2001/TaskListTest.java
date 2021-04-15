package ntnu.idatt2001;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    TaskList list;
    Task t;
    Task t2;

    @BeforeEach
    void initAll(){
        list = new TaskList();
        t = new Task("test", "to do", 1, "something to test ", LocalDateTime.of(LocalDate.of(2021, 03, 20), LocalTime.of(20, 00)),
                LocalDateTime.now(), new Category("c"));
        t2 = new Task("test2", "doing", 2, " ", LocalDateTime.of(LocalDate.of(2021, 03, 25), LocalTime.of(8, 00))
                , LocalDateTime.now(), new Category("c"));
    }

    @Nested
    @DisplayName("Add task")
    class AddTaskTest {
        @Test
        @DisplayName("Add not already existing task")
        void addNotExistingTask() {
            //adding a task that has not been added before
            assertTrue(list.addTask(t));
            //checking if the TaskList object has actually added the task after using the addTask method
            assertTrue(list.getAllTasks().contains(t));
        }

        @Test
        @DisplayName("Add already existing task")
        void addExistingTask() {
            //adding a task
            list.addTask(t);
            //trying to add that task again, which should not work (return false)
            assertFalse(list.addTask(t));
        }
    }

    @Nested
    @DisplayName("Remove task")
    class RemoveTaskTest {
        @Test
        @DisplayName("Remove already existing task")
        void removeExistingTask() {
            //adding a task
            list.addTask(t);
            //trying to remove the task that was added, which should return true
            assertTrue(list.removeTask(t));
            //checking if the TaskList object contains the task still, which should be false
            assertFalse(list.getAllTasks().contains(t));
        }

        @Test
        @DisplayName("Remove a non-existent task")
        void removeNotExistingTask() {
            //trying to remove a task that was never added, which should return false
            assertFalse(list.removeTask(t2));
        }
    }

    @Nested
    @DisplayName("Sort list")
    class SortListTest{
        @Test
        @DisplayName("Sort tasks by deadline")
        void sortListsByDeadline() {
            //adding two tasks
            list.addTask(t2);
            list.addTask(t);
            //checking if the task were added in the correct order
            assertTrue(list.getAllTasks().get(0).equals(t2));
            //sorting list by deadline
            list.sortLists("deadline");
            //checking if the list was sorted correctly
            assertTrue(list.getAllTasks().get(0).equals(t));
        }

        @Test
        @DisplayName("Sort tasks by priority")
        void sortListsByPriority() {
            //adding two tasks
            list.addTask(t);
            list.addTask(t2);
            //checking if the task were added in the correct order
            assertTrue(list.getAllTasks().get(0).equals(t));
            //sorting list by priority
            list.sortLists("priority");
            //checking if the list was sorted correctly
            assertTrue(list.getAllTasks().get(0).equals(t2));
        }
    }
}