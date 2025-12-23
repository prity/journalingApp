package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Scheduler.JobScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JobSchedulerTests {

    @Autowired
    private JobScheduler jobScheduler;

    @Test
    public void scheduleMailTest()
    {
        jobScheduler.scheduleMail();
    }

}
