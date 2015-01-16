/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import palettegraphiquemultimodale.listeners.*;

/**
 *
 * @author POTTIEGU
 */
public class IvyDaemon {
    private Ivy bus;
    
    public IvyDaemon() throws IvyException {
        bus = new Ivy("Multimodal Daemon", "started", null);
        
        bus.bindMsg("^Palette:MousePressed x=(.*) y=(.*)$",
                new IvyMousePressedListener());
        bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)$",
                new IvyMouseReleasedListener());
        
        bus.start("127.255.255.255:2010");
    }
    
    public void drawOval(int x, int y, int l, int h,
            Color fond, Color contour) {
        String s = "CreerEllipse x=" + x + " y=" + y + " longueur=" + l + 
                " hauteur=" + h + " couleurFond=" + fond +
                " couleurContour=" + contour;
        
        send(s);
    }
    
    public void drawRectangle(int x, int y, int l, int h,
            String fond, String contour) {
        String s = "CreerRectangle x=" + x + " y=" + y + " longueur=" + l + 
                " hauteur=" + h + " couleurFond=" + fond +
                " couleurContour=" + contour;
        
        send(s);
    }
    
    public void moveObject(String name, int x, int y) {
        String s = "DeplacerObjet nom=" + name + " x=" + x + " y=" + y;
        
        send(s);
    }
    
    public void askInfo(String name) {
        String s = "";
        
        send(s);
    }
    
    public void send(String s) {
        try {
            bus.sendMsg(s);
        } catch (IvyException ex) {
            Logger.getLogger("Ivy").log(Level.SEVERE, null, ex);
        }
    }
}
