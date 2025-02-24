package task;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void deadlineDateSaved() {
        Deadline deadline = new Deadline("Project Work by 12/12/2222 1600");
        assertEquals("12 Dec 2222", deadline.getDeadlineDate());
    }
    @Test
    public void deadlineTimeSaved() {
        Deadline deadline = new Deadline("Project Work by 12/12/2222 1600");
        assertEquals("4PM", deadline.getDeadlineTime().toUpperCase());
    }
}
