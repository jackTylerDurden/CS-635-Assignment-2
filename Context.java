import java.util.*;
public class Context {
    LinkedHashMap<String,Cell> values = new LinkedHashMap<String,Cell>();
    LinkedHashMap<String,String> colIndexToExpressionMap = new LinkedHashMap<String,String>();
    Set<String> dependentCellsSet = new HashSet<String>();
    Caretaker caretaker = new Caretaker();
    Originator originator = new Originator();
    int saveCount = 0;
    int currentCellState = 0;
    public String getValue(String key){
        if(values.get(key) == null){
            Cell cell = new Cell("");
            values.put(key,cell);
        }            
        return values.get(key).getCellValue();
    }

    public void setValue(String key,String value){
        Cell cellToAssign = values.get(key);
        cellToAssign.setCellValue(value,Integer.valueOf(key));                
    }
    
    public static void switchToValueView(){        
        for(String cell : Client.con.values.keySet()){            
            String val = Client.con.getValue(cell);
            val = val.trim();                        
            if(!val.isEmpty()){                
                if(!val.matches(Expression.numberRegex)){
                    Client.con.colIndexToExpressionMap.put(cell,val);                
                    Cell cellToAssign = Client.con.values.get(cell);
                    Client.con.values.put(cell,cellToAssign);        
                    cellToAssign.setCellValue(cellToAssign.getReferredCell(), Integer.valueOf(cell));
                    cellToAssign.evaluate();
                    Client.con.dependentCellsSet = new HashSet<String>();                     
                }                
            }
        }
    }
    
    public static void switchToEquationView(){
        for(String cell : Client.con.values.keySet()){            
            String expression = Client.con.colIndexToExpressionMap.get(cell);
            // System.out.println("expression=========>>"+expression);
            if(expression != null){
                Cell cellToAssign = Client.con.values.get(cell);
                cellToAssign.setCellValue(expression,Integer.valueOf(cell));
                Client.con.values.put(cell,cellToAssign);                                    
                Client.dataModel.setValueAt(expression, 0, Integer.valueOf(cell));
            }            
        }
    }        
}