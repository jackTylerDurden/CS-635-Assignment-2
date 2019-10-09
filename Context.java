import java.util.*;
public class Context {
    LinkedHashMap<String,Cell> values = new LinkedHashMap<String,Cell>();    
    LinkedHashMap<String,String> colIndexToExpressionMap = new LinkedHashMap<String,String>();
    Set<String> dependentCellsSet = new HashSet<String>();
    Caretaker caretaker = new Caretaker();
    Originator originator = new Originator();
    int saveCount = 0;
    int currentCellState = -1;
    public String getValue(String key){
        if(values.get(key) == null){
            Cell cell = new Cell("");            
            saveState();
            values.put(key,cell);
        }            
        return values.get(key).getCellValue();
    }

    public void setValue(String key,String value){        
        Cell cellToAssign = values.get(key);        
        cellToAssign.setCellValue(value,Integer.valueOf(key));
        if(!Client.undoStarted && !cellToAssign.isObserver){            //!Client.undoStarted
            saveState();
        }        
    }

    public void saveState(){
        LinkedHashMap<String,String> contextStateMap = new LinkedHashMap<>();
        Boolean proceedWithSave = false;
        for(String key : Client.con.values.keySet()){
            Cell presentCell = Client.con.values.get(key);
            if(presentCell.value != "")
                System.out.println("value while saving state--------->>>>"+presentCell.value);
            if(Client.isValueView && !presentCell.isObserver){                
                contextStateMap.put(key,presentCell.value);
                proceedWithSave = true;
            }else if(!Client.isValueView){                
                contextStateMap.put(key,presentCell.value);
                proceedWithSave = true;
            }            
        }
        if(true){
            Client.con.originator.set(contextStateMap);
            Client.con.caretaker.addMemento(Client.con.originator.storeInMemento());
            Client.con.saveCount++;
            Client.con.currentCellState++;
            System.out.println("Client.con.currentCellState--------->>>>"+Client.con.currentCellState);            
            System.out.println("");
        }        
    }

    public void restoreState(){                
        if(Client.con.currentCellState >= 1){                                    
            Client.con.currentCellState--;
            LinkedHashMap<String,String> contextAfterUndo = Client.con.originator.restoreFromMemento(Client.con.caretaker.fetchMemento(Client.con.currentCellState));
            for(String cellKey : contextAfterUndo.keySet()){                
                String pastCellVal = contextAfterUndo.get(cellKey);
                Cell presentCell = Client.con.values.get(cellKey);
                if(!Client.isValueView && !pastCellVal.equals(presentCell.value)){ //differnetial update
                    presentCell.setCellValue(pastCellVal, Integer.valueOf(cellKey));
                    Client.dataModel.setValueAt(pastCellVal, 0, Integer.valueOf(cellKey));
                }else if(Client.isValueView && !presentCell.isObserver && !pastCellVal.equals(presentCell.value)){
                    presentCell.setCellValue(pastCellVal, Integer.valueOf(cellKey));
                    Client.dataModel.setValueAt(pastCellVal, 0, Integer.valueOf(cellKey));
                }
            }
        }
    }
    
    public static void switchToValueView(){        
        for(String cell : Client.con.values.keySet()){            
            String val = Client.con.getValue(cell);
            val = val.trim();                        
            if(!val.isEmpty()){
                Cell cellToAssign = Client.con.values.get(cell);
                if(!val.matches(Expression.numberRegex)){
                    Client.con.colIndexToExpressionMap.put(cell,val);                                                        
                    Client.con.values.put(cell,cellToAssign);        
                    cellToAssign.setCellValue(cellToAssign.getReferredCell(), Integer.valueOf(cell));
                    cellToAssign.evaluate();
                    Client.con.dependentCellsSet = new HashSet<String>();                     
                }else{                    
                    // Client.con.colIndexToExpressionMap.put(cell,"");
                }                
            }
        }
    }
    
    public static void switchToEquationView(){
        for(String cell : Client.con.values.keySet()){            
            String expression = Client.con.colIndexToExpressionMap.get(cell);            
            Cell cellToAssign = Client.con.values.get(cell);
            if(expression != null){                
                cellToAssign.setCellValue(expression,Integer.valueOf(cell));
                Client.con.values.put(cell,cellToAssign);                                    
                Client.dataModel.setValueAt(expression, 0, Integer.valueOf(cell));
            }else{
                Client.dataModel.setValueAt(cellToAssign.value, 0, Integer.valueOf(cell));
            }            
        }
    }        
}