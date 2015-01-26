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
 * @author pottiegu
 */
public class IvyMicColorListener implements IvyMessageListener {

    @Override
    public void receive(IvyClient ic, String[] strings) {
        System.out.println("Message vocal reçu : " + strings[0]);
        
        String color = strings[0].equals("bleu") ? "blue" : 
                strings[0].equals("rouge") ? "red" : "green";
        
        OrderManager.getInstance().orderColor(color);
    }
    
}
