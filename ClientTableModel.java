import javax.swing.table.AbstractTableModel;

public class ClientTableModel extends AbstractTableModel {    
    private static final long serialVersionUID = 1L;
    public static final int numberOfCells = 9;    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
        
    @Override
    public Object getValueAt(int row, int col) {
        return Client.con.getValue(String.valueOf(col)) != null ? Client.con.getValue(String.valueOf(col)) : "";
    }

    @Override    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {        
        Client.con.setValue(String.valueOf(columnIndex), String.valueOf(aValue)); 
        fireTableCellUpdated(rowIndex, columnIndex);       
    }
    
    @Override
    public int getRowCount() {        
        return 1;
    }
    
    @Override
    public int getColumnCount() {        
        return numberOfCells;
    }
}