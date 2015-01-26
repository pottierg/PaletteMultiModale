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
 * @author pottiegu
 */
public class IvyMicActionListener implements IvyMessageListener {

    @Override
    public void receive(IvyClient ic, String[] strings) { 
        System.out.println("Action commandée : " + strings[0]);
        OrderManager.getInstance().orderAction(strings[0].equals("deplacer") ?
                ActionsPossible.DEPLACEMENT : ActionsPossible.CREATION);
        
    }
    
}
