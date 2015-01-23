/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.model;

import java.io.Serializable;

/**
 *
 * @author POTTIEGU
 */
public class Point implements Serializable{
    public double X, Y;
    
    public Point(double x, double y){
        this.X = x;
        this.Y = y;
    }
    
    public void copy(Point src) {
        X = src.X;
        Y = src.Y;
    }
}
