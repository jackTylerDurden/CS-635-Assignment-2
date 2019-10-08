import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.TableModel;

public class Client {
    public static JFrame frame;
    public static ClientTableModel dataModel = new ClientTableModel();
    public static JTable clientTable;
    public static Context con = new Context();
    public static Boolean isValueView = false;
    public static Boolean isToggled = false;

    public static void setupGrid() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Frame Title
        frame.setTitle("Equation View");
        // Initializing the JTable
        clientTable = new JTable(dataModel);
        clientTable.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(clientTable);

        JButton toggleButton = new JButton("Toggle");
        toggleButton.setBounds(40, 95, 90, 50);
        toggleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isToggled = true;
                if(!isValueView){
                    isValueView = true;                    
                    frame.setTitle("Value View");
                    Context.switchToValueView();                                        
                }else{
                    isValueView = false;
                    frame.setTitle("Equation View");
                    Context.switchToEquationView();                    
                }
                isToggled = false;
            }
            
        });
        frame.add(toggleButton);


        JButton undoButton = new JButton("Undo");
        undoButton.setBounds(150, 95, 90, 50);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                if(con.currentCellState >= 1){
                    System.out.println("on.currentCellState before--------------->>>"+con.currentCellState);
                    con.currentCellState--;                    
                    System.out.println("on.currentCellState after--------------->>>"+con.currentCellState);                    
                    Cell cellAfterUndo = con.originator.restoreFromMemento(con.caretaker.fetchMemento(con.currentCellState));
                    System.out.println("cellAfterUndo--------------->>>"+cellAfterUndo.value);
                    con.setValue(String.valueOf(cellAfterUndo.colIndex), cellAfterUndo.value);
                    cellAfterUndo.evaluate();
                }
            }
            
        });
        frame.add(undoButton);
        frame.add(sp);
        
                

        // Frame Size 
        frame.setSize(500, 200); 
        // Frame Visible = true 
        frame.setVisible(true); 
    } 
}