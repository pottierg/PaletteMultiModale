/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale;

import fr.dgac.ivy.IvyException;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import nyartoolkit.CameroHiroHandler;
import palettegraphiquemultimodale.model.Model;
import palettegraphiquemultimodale.views.Palette;

/**
 *
 * @author POTTIEGU
 */
public class Main {
    
    public static void main (String args[]) {
        Model m = new Model();
        
        IvyDaemon i = null;
        try {
            i = IvyDaemon.getInstance();
        } catch (IvyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Palette p = new Palette(m);
        p.setVisible(true);
        
        CameroHiroHandler frame;
        try {
            frame = new CameroHiroHandler();
            frame.setVisible(true);
            Insets ins = frame.getInsets();
            frame.setSize(320 + ins.left + ins.right, 240 + ins.top + ins.bottom);
            frame.startCapture();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
