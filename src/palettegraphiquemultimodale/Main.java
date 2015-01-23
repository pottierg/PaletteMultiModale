/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale;

import fr.dgac.ivy.IvyException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
    }
}
