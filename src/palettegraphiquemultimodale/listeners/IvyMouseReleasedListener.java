/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.listeners;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Point;
import palettegraphiquemultimodale.orders.ActionsPossible;
import palettegraphiquemultimodale.orders.OrderManager;

/**
 *
 * @author POTTIEGU
 */
public class IvyMouseReleasedListener implements IvyMessageListener {

    @Override
    public void receive(IvyClient ic, String[] strings) { 
        System.out.println("Mouse got released on x="
                + strings[0] + ", y=" + strings[1]);
        
        ActionsPossible action = OrderManager.getInstance().typeOfCurrentOrder();
        int x = Integer.valueOf(strings[0]);
        int y = Integer.valueOf(strings[1]);
        
        if(action == ActionsPossible.DEPLACEMENT) {
            OrderManager.getInstance().orderReleasedOn(new Point(x, y));
        }
    }
    
}
