/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.listeners;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;

/**
 *
 * @author pottiegu
 */
public class IvyMicDirectionListener implements IvyMessageListener {
    
    @Override
    public void receive(IvyClient ic, String[] strings) {
        System.out.println("Message vocal reçu : " + strings[0] + " " + strings[1]);
    }
    
}
