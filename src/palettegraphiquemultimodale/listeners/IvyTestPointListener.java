/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.listeners;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;
import palettegraphiquemultimodale.orders.OrderManager;

/**
 *
 * @author POTTIEGU
 */
public class IvyTestPointListener implements IvyMessageListener {

    @Override
    public void receive(IvyClient ic, String[] strings) {
        String nom = strings[2];
        System.out.println("You clicked on : " + nom);
        OrderManager.getInstance().orderDesignedShape(nom);
    }
    
}
