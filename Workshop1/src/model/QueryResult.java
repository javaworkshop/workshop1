
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
    private final ArrayList<QueryResultRow> rows;
    private final LinkedHashSet<String> columnNames;
    
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
    
    public boolean isEmpty() {
        return rows.isEmpty();
    }
    
    @Override
    public int hashCode() {
        return rows.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        QueryResult qr = (QueryResult)obj;
        return qr.rows.equals(this.rows);
    }

    @Override
    public String toString() {
        return "QueryResult{" + "rows=" + rows + ", columnNames=" + columnNames + '}';
    }
}

