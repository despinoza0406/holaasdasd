package hubble.backend.tasksrunner.communication.controllers;

import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RescheduleController {

    @Autowired
    SchedulerMediator scheduler;

    @RequestMapping("/reschedule")
    public void reschedule(@RequestParam(value="id", defaultValue="") String id) {
        scheduler.reschedule(id);
    }
}
