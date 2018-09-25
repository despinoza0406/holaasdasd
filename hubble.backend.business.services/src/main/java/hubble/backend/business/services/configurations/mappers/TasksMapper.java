package hubble.backend.business.services.configurations.mappers;

import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.models.tables.TasksTable;

public class TasksMapper {

    public TasksMapper(){}

    public TasksTable mapWorkItemToTasksTable(WorkItem workItem){
        TasksTable tasksTable = new TasksTable();
        tasksTable.setOrigen(workItem.getProviderOrigin());
        tasksTable.setId(Integer.toString(workItem.getExternalId()));
        tasksTable.setTitulo(workItem.getTitle());
        tasksTable.setFechaDeAlta(workItem.getRegisteredDate().toString());
        tasksTable.setAsignadoA(workItem.getAssignee());
        tasksTable.setEstado(workItem.getState());
        tasksTable.setDescripcion(workItem.getDescription());
        tasksTable.setFechadDeRealizacion(workItem.getDueDate().toString());
        tasksTable.setDiasDesviados(Long.toString(workItem.getDeflectionDays()));

        return tasksTable;
    }

}
