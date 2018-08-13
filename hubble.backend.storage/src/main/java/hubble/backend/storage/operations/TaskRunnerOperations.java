package hubble.backend.storage.operations;

import hubble.backend.storage.models.TaskRunnerExecution;

public interface TaskRunnerOperations {

    boolean exist(TaskRunnerExecution taskRunnerExecution);


}
