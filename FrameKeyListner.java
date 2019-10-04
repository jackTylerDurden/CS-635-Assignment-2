
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class FrameKeyListner extends JFrame implements KeyListener {    
    @Override
    public void keyReleased(KeyEvent e){
        System.out.println("Key pressed-------_>>>"+e.getID());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}