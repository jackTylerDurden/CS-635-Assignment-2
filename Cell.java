import java.util.*;
public class Cell{
    String value="";
    int colIndex;
    int isVisited=0;
    HashSet<Cell> observerList;

    public Cell(String val){
        value = val;
        observerList = new HashSet<Cell>();
    }

    public void notifyObserver(){        
        for(Cell observer : observerList){            
            observer.update();
        }
    }

    public void register(Cell observer){
        observerList.add(observer);        
    }

    public void unregister(Cell observer){
        observerList.remove(observer);        
    }

    public void update(){
        value = Client.con.colIndexToExpressionMap.get(String.valueOf(colIndex));        
        Client.con.dependentCellsSet = new HashSet<String>();
        value = getReferredCell();
        evaluate();
    }

    public String getCellValue(){
        return value;
    }

    public void setCellValue(String cellVal,int col){
        value = cellVal;
        colIndex = col;                 
        if(Client.isValueView){            //&& cellVal != "error"
            if(!Client.isToggled){
                notifyObserver();
            }            
        }else{
            Client.con.dependentCellsSet = new HashSet<String>();
            this.observerList = new HashSet<Cell>();
        }
        saveCellState(this);
    }

    public void saveCellState(Cell cellToSave){
        Client.con.originator.set(cellToSave);
        Client.con.caretaker.addMemento(Client.con.originator.storeInMemento());
        Client.con.saveCount++;
        Client.con.currentCellState++;
    }

    
    public void evaluate(){
        value =  PostfixEvaluator.evaluateExpression(value);        
        Client.dataModel.setValueAt(value, 0, colIndex);
    }
    public String getReferredCell(){         
        List<String> resultList = new ArrayList<String>();                
        if(value != null && !value.isBlank()){            
            System.out.println("value-----------_>>>"+value);
            if(value.matches(Expression.numberRegex)){
                isVisited = 0;                
                Client.con.dependentCellsSet.remove(String.valueOf(this.colIndex));                
                resultList.add(value);                
                return String.join(" ", resultList);

            }            
            if(Client.con.dependentCellsSet.contains(String.valueOf(this.colIndex))){
                return "error";
            }else{
                Client.con.dependentCellsSet.add(String.valueOf(this.colIndex));
            }
            for(String term : value.split(" ")){
                term = term.toLowerCase().trim();
                if(term.matches("[a-z]")){
                    int col = (int) term.toCharArray()[0];                                        
                    col = col - Expression.firstAlphabetAscii;                    
                    Cell referredCell = Client.con.values.get(String.valueOf(col));
                    // System.out.println("term  "+term+" ");
                    referredCell.register(this);                                        
                    term = referredCell.getReferredCell();
                    if(term == "error")
                        return "error";
                    resultList.add(term);
                }else{
                    resultList.add(term);
                }
            }
            return String.join(" ", resultList);            
        }
        return value;
        
    }

}