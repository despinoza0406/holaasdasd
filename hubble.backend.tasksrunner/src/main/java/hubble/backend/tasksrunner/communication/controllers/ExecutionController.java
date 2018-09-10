package hubble.backend.tasksrunner.communication.controllers;

import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.repositories.TaskRunnerRepository;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/execute",method = RequestMethod.POST)
    public ResponseEntity<Void> execute(@RequestParam(value = "id",defaultValue = "") String id){
        HttpStatus httpStatus;
        if(schedulerMediator.execute(id)){
            httpStatus = HttpStatus.OK;
        }else{
            httpStatus = HttpStatus.I_AM_A_TEAPOT;
        }

        return new ResponseEntity<>(httpStatus);
    }
}
