package hubble.backend.storage.repositories;

import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.operations.TaskRunnerOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class TaskRunnerRepositoryImpl implements TaskRunnerOperations {

    @Autowired
    MongoOperations mongo;

    public boolean exist(TaskRunnerExecution taskRunnerExecution){

        Criteria isSameTimeStamp = Criteria.where("timeStamp").is(taskRunnerExecution.getTimestamp());
        Criteria isSameId = Criteria.where("id").is(taskRunnerExecution.getId());
        Criteria isSameProvider = Criteria.where("provider").is(taskRunnerExecution.getProvider());

        List<TaskRunnerExecution> executions = mongo
                .find(Query.query(isSameTimeStamp.andOperator(isSameProvider,isSameId)),
                        TaskRunnerExecution.class);

        return !executions.isEmpty();

    }

}
