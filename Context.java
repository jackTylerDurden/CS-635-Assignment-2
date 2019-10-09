import java.util.*;
public class Context {
    LinkedHashMap<String,Cell> values = new LinkedHashMap<String,Cell>();    
    LinkedHashMap<String,String> colIndexToExpressionMap = new LinkedHashMap<String,String>();
    Set<String> dependentCellsSet = new HashSet<String>();
    Caretaker caretakerForEquationView = new Caretaker();
    Originator originatorForEquationView = new Originator();
    Caretaker caretakerForValueView = new Caretaker();
    Originator originatorForValueView = new Originator();    
    int currentCellStateForEquationView = -1;
    int currentCellStateForValueView = -1;

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
        if(Client.isValueView){
            Client.con.originatorForValueView.set(contextStateMap);
            Client.con.caretakerForValueView.addMemento(Client.con.originatorForValueView.storeInMemento());            
            Client.con.currentCellStateForValueView++;
            System.out.println("Client.con.currentCellState--------->>>>"+Client.con.currentCellStateForValueView);            
            System.out.println("");
        }else if(!Client.isValueView){
            Client.con.originatorForEquationView.set(contextStateMap);
            Client.con.caretakerForEquationView.addMemento(Client.con.originatorForEquationView.storeInMemento());            
            Client.con.currentCellStateForEquationView++;
            System.out.println("Client.con.currentCellState--------->>>>"+Client.con.currentCellStateForEquationView);            
            System.out.println("");
        }        

    }

    public void restoreState(){                        
            LinkedHashMap<String,String> contextAfterUndo = new LinkedHashMap<>();
            if(Client.isValueView){
                if(Client.con.currentCellStateForValueView >= 1){
                    Client.con.currentCellStateForValueView--;
                    contextAfterUndo = Client.con.originatorForValueView.restoreFromMemento(Client.con.caretakerForValueView.fetchMemento(Client.con.currentCellStateForValueView));    
                }
            }else{
                if(Client.con.currentCellStateForEquationView >= 1){
                    Client.con.currentCellStateForEquationView--;
                    contextAfterUndo = Client.con.originatorForEquationView.restoreFromMemento(Client.con.caretakerForEquationView.fetchMemento(Client.con.currentCellStateForEquationView));
                }
            }
            
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
        Client.con.saveState();
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