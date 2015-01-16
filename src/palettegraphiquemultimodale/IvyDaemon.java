/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import palettegraphiquemultimodale.listeners.*;

/**
 *
 * @author POTTIEGU
 */
public class IvyDaemon {
    private Ivy bus;
    
    public IvyDaemon() throws IvyException {
        bus = new Ivy("Multimodal Daemon", "started", null);
        
        bus.bindMsg("^Palette:MousePressed x=(.*) y=(.*)$", new IvyMousePressedListener());
        bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)$", new IvyMouseReleasedListener());
        
        bus.start("127.255.255.255:2010");
    }
}
