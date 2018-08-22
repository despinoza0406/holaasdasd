package hubble.backend.providers.configurations.factories;

import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.TaskRunnerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskRunnerExecutionFactory {
    private final Logger logger = LoggerFactory.getLogger(TaskRunnerExecutionFactory.class);

    public TaskRunnerExecution createExecution(String provider,String applicationId, Results.RESULTS result, String description){
        logger.info("Ejecucion de " + provider + ". Resultado: " + result.toString());
        return new TaskRunnerExecution(provider,applicationId,result,description, DateHelper.getDateNow());
    }
}
