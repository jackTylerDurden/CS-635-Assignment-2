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
    public static Boolean isEquationView = false;

    public static void setupGrid() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Frame Title
        frame.setTitle("Postfix Evaluator");
        // Initializing the JTable
        clientTable = new JTable(dataModel);
        clientTable.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(clientTable);

        JButton button = new JButton("Toogle");
        button.setBounds(40, 95, 90, 50);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isEquationView){
                    Context.switchToEquationView();
                    isEquationView = true;
                }else{
                    Context.switchToValueView();
                    isEquationView = false;
                }
            }
            
        });
        frame.add(button);
        frame.add(sp);
        
                

        // Frame Size 
        frame.setSize(500, 200); 
        // Frame Visible = true 
        frame.setVisible(true); 
    } 
}