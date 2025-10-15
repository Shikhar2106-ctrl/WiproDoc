package com.wipro.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import com.wipro.task.DailyTasks;

public class TestCheckPresence {

    @Test
    public void testCheckPresenceTrue() {
        DailyTasks obj = new DailyTasks();
        boolean result = obj.checkPresence("Hello World", "World");
        assertTrue(result);
    }

    @Test
    public void testCheckPresenceFalse() {
        DailyTasks obj = new DailyTasks();
        boolean result = obj.checkPresence("Hello World", "Java");
        assertFalse(result);
    }
}
