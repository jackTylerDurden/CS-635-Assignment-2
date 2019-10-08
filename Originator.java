public class Originator{
    private Cell cell = new Cell("");

    public void set(Cell newCell){
        System.out.println("newCell-------------???"+newCell.value);
        cell.value = newCell.value;
        cell.colIndex = newCell.colIndex;
    }

    public Memento storeInMemento(){
        return new Memento(cell);
    }

    public Cell restoreFromMemento(Memento memento){
        Cell savedCell = memento.getSavedCell();
        return savedCell;
    }
}