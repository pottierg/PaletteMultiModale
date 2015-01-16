/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.utils;

/**
 *
 * @author VIGUIELO
 */
public class Rectangle {

    public double X, Y, Width, Height;

    Rectangle(double x, double y, double width, double height) // constructor
    {
        this.X = x;
        this.Y = y;
        this.Width = width;
        this.Height = height;
    }

    public void copy(Rectangle src) {
        X = src.X;
        Y = src.Y;
        Width = src.Width;
        Height = src.Height;
    }
}
