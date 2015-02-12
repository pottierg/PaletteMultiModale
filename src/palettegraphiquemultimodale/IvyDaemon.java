/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import palettegraphiquemultimodale.listeners.*;

/**
 *
 * @author POTTIEGU
 */
public class IvyDaemon {
    private Ivy bus;
    private static IvyDaemon instance;
    
    
    private IvyDaemon() throws IvyException {
        bus = new Ivy("Multimodal Daemon", "started", null);
        
        bus.bindMsg("^Palette:MousePressed x=(.*) y=(.*)$",
                new IvyMousePressedListener());
        
        bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)$",
                new IvyMouseReleasedListener());
        
        bus.bindMsg("^sra5 Parsed=Action:couleur (.*) Confidence=0,[8-9].*",
                new IvyMicColorListener());
        
        bus.bindMsg("^sra5 Parsed=Action:forme (.*) Confidence=0,[8-9].*",
                new IvyMicShapeListener());
        
        bus.bindMsg("^sra5 Parsed=Action:deplacer (.*) (.*) Confidence=0,[8-9].*",
                new IvyMicDirectionListener());
        
        bus.bindMsg("^sra5 Parsed=Action: (.*) Confidence=0,[8-9].*",
                new IvyMicActionListener());
                
        bus.bindMsg("^Palette:ResultatTesterPoint x=(.*) y=(.*) nom=(.*)",
                new IvyTestPointListener());
        
        bus.start("127.255.255.255:2010");
    }
    
    public static IvyDaemon getInstance() throws IvyException {
        if (IvyDaemon.instance == null) {
            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized(IvyDaemon.class) {
              if (IvyDaemon.instance == null) {
                IvyDaemon.instance = new IvyDaemon();
              }
            }
         }
        
         return IvyDaemon.instance;
    }
    
    public void drawOval(int x, int y, int l, int h,
            String fond, String contour) {
        String s = "Palette:CreerEllipse x=" + x + " y=" + y + " longueur=" + l + 
                " hauteur=" + h;
        
        if(fond.length() > 0)
            s += " couleurFond=" + fond;
        
        if(contour.length() > 0)
            s += " couleurContour=" + contour;
        
        send(s);
    }
    
    public void drawRectangle(int x, int y, int l, int h,
            String fond, String contour) {
        String s = "Palette:CreerRectangle x=" + x + " y=" + y + " longueur=" + l + 
                " hauteur=" + h;
        
        if(fond.length() > 0)
            s += " couleurFond=" + fond;
        
        if(contour.length() > 0)
            s += " couleurContour=" + contour;
        
        send(s);
    }
    
    public void moveObject(String name, int x, int y) {
        String s = "Palette:DeplacerObjet nom=" + name + " x=" + x + " y=" + y;
        
        send(s);
    }
    
    public void askInfo(String name) {
        String s = "Palette:DemanderInfo nom=" + name;
        
        send(s);
    }
    
    public void testPoint(int x, int y) {
        String s = "Palette:TesterPoint x=" + x + " y=" + y;
        
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
