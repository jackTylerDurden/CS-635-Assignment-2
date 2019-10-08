public class Memento{
    private Cell cell = new Cell("");

    public Memento(Cell savedCell){
        cell.value = savedCell.value;
        cell.colIndex = savedCell.colIndex;
    }

    public Cell getSavedCell(){
        return cell;
    }
}