package hubble.backend.tasksrunner.communication.controllers;

import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RescheduleController {

    @Autowired
    SchedulerMediator scheduler;

    @RequestMapping(value = "/reschedule",method = RequestMethod.PATCH)
    public ResponseEntity<Void> reschedule(@RequestParam(value="id", defaultValue="") String id) {
        HttpStatus httpStatus;
        if(scheduler.reschedule(id)){
            httpStatus = HttpStatus.OK;
        }else{
            httpStatus = HttpStatus.I_AM_A_TEAPOT;
        }

        return new ResponseEntity<>(httpStatus);

    }
}
