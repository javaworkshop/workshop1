
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Arrays;

/**
 * Class to store the results of queries made using the free sql input function of the. A 
 * QueryResult object stores query results in the form of a table. Data are stored in String format 
 * as the class is meant to be used for displaying query results to the user.
 */
public class QueryResult {
    private LinkedHashMap<String, List<String>> columns;
    private int rowCount;
    
    public QueryResult() {
        columns = new LinkedHashMap<>();
        rowCount = 0;
    }
    
    public String[] columnNames() {
        Set keys = columns.keySet();
        String[] columnNames = new String[keys.size()];
        keys.toArray(columnNames);
        return columnNames;
    }
    
    public int columnCount() {
        return columns.keySet().size();
    }
    
    public int rowCount() {
        return rowCount;
    }
    
    public List<String> getCellValues(String column) {
        return columns.get(column);
    }
    
    public String getCellValue(String column, int row) {
        return columns.get(column).get(row - 1);
    }
    
    public void addColumn(String label, String[] cellValues) throws IllegalArgumentException {
        if(columns.isEmpty())
            rowCount = cellValues.length;        
        else if(cellValues.length != rowCount)
            throw new IllegalArgumentException("The number of rows in the column to be added has "
                    + "to match the number of rows of the column(s) already stored in the current "
                    + "QueryResult object.");
        
        columns.put(label, Arrays.asList(cellValues));
    }
    
    public void addColumn(String label, ArrayList<String> cellValues) 
            throws IllegalArgumentException {
        if(columns.isEmpty())
            rowCount = cellValues.size();        
        else if(cellValues.size() != rowCount)
            throw new IllegalArgumentException("The number of rows in the column to be added has "
                    + "to match the number of rows of the column(s) already stored in the current "
                    + "QueryResult object.");
        
        columns.put(label, cellValues);
    }

    public boolean isEmpty() {
        return columns.isEmpty();
    }
}
