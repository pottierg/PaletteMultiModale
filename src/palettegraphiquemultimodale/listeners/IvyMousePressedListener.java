/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.listeners;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import palettegraphiquemultimodale.IvyDaemon;
import palettegraphiquemultimodale.orders.ActionsPossible;
import palettegraphiquemultimodale.orders.OrderManager;

/**
 *
 * @author POTTIEGU
 */
public class IvyMousePressedListener implements IvyMessageListener {
    
    @Override
    public void receive(IvyClient ic, String[] strings) {
        ActionsPossible action = OrderManager.getInstance().typeOfCurrentOrder();
        int x = Integer.valueOf(strings[0]);
        int y = Integer.valueOf(strings[1]);
        
        System.out.println("Mouse got pressed on x="
                + x + ", y=" + y);
        
        try {
            // Request the name of the designed shape if needed
            IvyDaemon.getInstance().testPoint(x, y);
        } catch (IvyException ex) {
            Logger.getLogger(IvyMousePressedListener.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        OrderManager.getInstance().orderPosition(
                new Point(x, y));
    }
    
}
