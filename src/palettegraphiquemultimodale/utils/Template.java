package palettegraphiquemultimodale.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;

/**
 *
 * @author VIGUIELO
 */
public class Template {
    public static final int NUM_POINTS = 64;
    public static final double SQUARE_SIZE = 250.0;

    String Name;
    Vector Points;

    public Template(String name, Vector points) {
        this.Name = name;
        this.Points = Utils.Resample(points, NUM_POINTS);
        this.Points = Utils.RotateToZero(this.Points);
        this.Points = Utils.ScaleToSquare(this.Points, SQUARE_SIZE);
        this.Points = Utils.TranslateToOrigin(this.Points);
    }

    public String getName() {
        return Name;
    }

    public Vector getPoints() {
        return Points;
    }
    
    
}
