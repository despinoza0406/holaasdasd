package hubble.backend.tasksrunner.communication.controllers;

import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.repositories.TaskRunnerRepository;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ExecutionController {

    @Autowired
    SchedulerMediator schedulerMediator;

    @Autowired
    TaskRunnerRepository taskRunnerRepository;

    @RequestMapping(value = "/execute")
    public void execute(@RequestParam(value = "id",defaultValue = "") String id){
        schedulerMediator.execute(id);
    }
}
