/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.listeners;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;
import palettegraphiquemultimodale.orders.Formes;
import palettegraphiquemultimodale.orders.OrderManager;

/**
 *
 * @author pottiegu
 */
public class IvyMicShapeListener implements IvyMessageListener {

    @Override
    public void receive(IvyClient ic, String[] strings) {
        System.out.println("Message vocal reçu : " + strings[0]);
        
        OrderManager.getInstance().orderForme(strings[0].equals("ellipse") ?
                Formes.ELLIPSE : Formes.RECTANGLE);
    }
    
}
