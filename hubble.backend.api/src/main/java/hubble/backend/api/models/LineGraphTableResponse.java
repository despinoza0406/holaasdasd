package hubble.backend.api.models;

import hubble.backend.business.services.models.tables.FrontEndTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LineGraphTableResponse {
    private List<String> columnTitles;
    private List<FrontEndTable> response;


    public LineGraphTableResponse(){}

    public LineGraphTableResponse(List<FrontEndTable> frontEndTables,List<String> columnTitles){
        this.columnTitles = columnTitles;
        this.response = frontEndTables;
    }

    public List<String> getColumnTitles() {
        return columnTitles;
    }

    public void setColumnTitles(List<String> columnTitles) {
        this.columnTitles = columnTitles;
    }

    public List<FrontEndTable> getResponse() {
        return response;
    }

    public void setResponse(List<FrontEndTable> response) {
        this.response = response;
    }

}
