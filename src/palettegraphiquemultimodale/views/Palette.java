/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.views;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import palettegraphiquemultimodale.model.Model;

/**
 *
 * @author POTTIEGU
 */
public class Palette extends JComponent {
    
    public Palette (Model m) {
        JFrame jf = new JFrame("Palette multimodale");
        Panel sc = new Panel(m);
        sc.setPreferredSize(new Dimension(500, 500));
        
        jf.getContentPane().add(sc);
        jf.pack();
        jf.setResizable(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sc.setFocusable(true);
        sc.requestFocusInWindow();
        
        sc.repaint();
    }
    
}
