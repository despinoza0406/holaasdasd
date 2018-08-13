package hubble.backend.providers.configurations.factories;

import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.TaskRunnerExecution;
import org.springframework.stereotype.Component;

@Component
public class TaskRunnerExecutionFactory {

    public TaskRunnerExecution createExecution(String provider, Results.RESULTS result, String description){
        return new TaskRunnerExecution(provider,result,description, DateHelper.getDateNow());
    }
}
