package hubble.backend.storage.operations;

import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.Date;
import java.util.List;

public interface TaskRunnerOperations {

    boolean exist(TaskRunnerExecution taskRunnerExecution);

    List<TaskRunnerExecution> findExecutionsByProviderAndApplicationIdAndPeriod(String id,String applicationId, Date startDate,Date endDate);


}
