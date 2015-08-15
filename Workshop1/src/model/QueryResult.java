
package model;

import java.util.ArrayList;
import java.util.LinkedHashSet;


/**
 * Class to store the results of queries made using the free sql input function of the crud. A 
 * QueryResult object can store all possible values returned by a query by using objects of the 
 * implementations of the abstract data class. Rows are represented by QueryResultRow objects. A 
 * QueryResult object keeps track of the names of the columns that are stored and also of the type 
 * that was originally stored in a column.
 */

public class QueryResult {
    private ArrayList<QueryResultRow> rows;
    private LinkedHashSet<String> columnNames;
    
    public QueryResult() {
        rows = new ArrayList<>();
        columnNames = new LinkedHashSet<>();
    }
    
    public void addRow(QueryResultRow qrr) {
        rows.add(qrr);
    }
    
    public QueryResultRow getRow(int index) {
        return rows.get(index);       
    }
    
    public ArrayList<QueryResultRow> getRows() {
        return rows;
    }
    
    public int rowCount() {
        return rows.size();
    }
    
    public void addColumnName(String name) {
        columnNames.add(name);
    }
    
    public String[] getColumnNames() {
        String[] columnNamesArray = new String[columnNames.size()];
        columnNames.toArray(columnNamesArray);
        return columnNamesArray;
    }
    
    // todo...
    @Override
    public int hashCode() {
        return 0;
    }
    
    // todo...
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

