/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.model;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author POTTIEGU
 */
public class Dessin {
    private Vector<Point> dessin;
    
    public Dessin() {
        dessin = new Vector<>();
    }
    
    public void addPoint(Point p) {
        dessin.add(p);
    }

    public List<Point> getDessin() {
        return dessin;
    }
    
    
}
