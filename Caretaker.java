import java.util.*;
public class Caretaker{
    ArrayList<Memento> savedContexts = new ArrayList<Memento>();

    public void addMemento(Memento m){
        savedContexts.add(m);        
    }

    public Memento fetchMemento(int colIndex){
        System.out.println("colIndex------__>>>"+colIndex);
        return savedContexts.get(colIndex);
    }
}