package hubble.backend.business.services.configurations.mappers;

import hubble.backend.business.services.implementations.services.IssueServiceImpl;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.tables.IssuesTable;

public class IssuesMapper {

    public IssuesMapper(){}

    public IssuesTable mapIssueToIssuesTable(Issue issue){
        IssuesTable issuesTable = new IssuesTable();
        issuesTable.setOrigen(issue.getProviderOrigin());
        issuesTable.setId(Integer.toString(issue.getExternalId()));
        issuesTable.setTitulo(issue.getTitle());
        issuesTable.setFechaDeAlta(issue.getRegisteredDate().toString());
        issuesTable.setRegistradoPor(issue.getDetectedBy());
        issuesTable.setAsignadoA(issue.getAssignee());
        issuesTable.setEstado(issue.getState());
        issuesTable.setDescripcion(issue.getDescription());
        issuesTable.setPrioridad(Integer.toString(issue.getPriority()));
        issuesTable.setSeveridad(Integer.toString(issue.getSeverity()));
        issuesTable.setCriticidad(Integer.toString((this.getPriority(issue)+this.getSeverity(issue))/2));

        return issuesTable;

    }

    private Integer getPriority(Issue issue){
        int priority = issue.getPriority();
        if(issue.getProviderOrigin().equalsIgnoreCase("ALM")){
            if (priority >= 3){
                return 3;
            }else {
                return priority;
            }
        }else {
            if (priority >= 4){
                return 1;
            }
            if (priority >= 3){
                return 2;
            }else {
                return 3;
            }
        }
    }

    private Integer getSeverity(Issue issue){
        int severity = issue.getSeverity();
        if(issue.getProviderOrigin().equalsIgnoreCase("ALM")){
            if (severity >= 3){
                return 3;
            }else {
                return severity;
            }
        }else {
            return severity;
        }
    }
}
