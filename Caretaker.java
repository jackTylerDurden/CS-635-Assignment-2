import java.util.*;
public class Caretaker{
    ArrayList<Memento> savedCells = new ArrayList<Memento>();

    public void addMemento(Memento m){
        savedCells.add(m);
    }

    public Memento fetchMemento(int colIndex){
        for(Memento meme : savedCells){
            System.out.println("meme.cell.value---------->>>"+meme.getSavedCell().value);
        }
        return savedCells.get(colIndex);
    }
}