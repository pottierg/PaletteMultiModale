/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.orders;

import java.awt.Point;
import palettegraphiquemultimodale.IvyDaemon;

/**
 *
 * @author POTTIEGU
 */
public class Order {
    private ActionsPossible action;
    private String color;
    // Point that has been designed on the palette
    private Point position;
    private Point releasedOn;
    private Point previousPoint;
    private Formes forme;
    // Name of the shape that has been clicked on
    private String designedShape;
    
    public Order() {
        position = null;
        releasedOn = null;
        forme = null;
        action = null;
        color = null;
        designedShape = null;
    }
    
    public boolean orderIsComplete() {
        return (action == ActionsPossible.CREATION && forme != null
                        && position != null && color != null)
            || (action == ActionsPossible.DEPLACEMENT && designedShape != null
                        && releasedOn != null);
    }
    
    public boolean orderHasEnoughInfo() {
        return (action == ActionsPossible.CREATION && forme != null)
            || (action == ActionsPossible.DEPLACEMENT && designedShape != null
                    && releasedOn != null);
    }
    
    public void execute() {
        if(!orderHasEnoughInfo()) {
            System.out.println("Order incomplete, cancelling");
            return;
        }
        System.out.println("Executing order : " + action);
        
        String fond = (color == null ? "" : color);
        int x = position != null ? position.x : 5;
        int y = position != null ? position.y : 5;
        int previousX = previousPoint != null ? previousPoint.x : 0;
        int previousY = previousPoint != null ? previousPoint.y : 0;
        
        try {
            switch(action) {
                case CREATION:
                    System.out.println(forme + " " + color);
                    switch(forme) {
                        case ELLIPSE:
                            IvyDaemon.getInstance().drawOval(x, y, 100, 100,  fond, "");
                            break;
                        case RECTANGLE:
                            IvyDaemon.getInstance().drawRectangle(x, y, 100, 100, fond, "");
                            break;
                    }
                    break;
                case DEPLACEMENT:
                    System.out.println(designedShape + " to " + x + ", " + y);
                    IvyDaemon.getInstance().moveObject(designedShape, x - previousX, y - previousY);
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public ActionsPossible getAction() {
        return action;
    }

    public void setAction(ActionsPossible action) {
        this.action = action;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        if(this.position != null)
            this.previousPoint = new Point(this.position);
        this.position = position;
    }

    public Formes getForme() {
        return forme;
    }

    public void setForme(Formes forme) {
        this.forme = forme;
    }

    public String getDesignedShape() {
        return designedShape;
    }

    public void setDesignedShape(String designedShape) {
        this.designedShape = designedShape;
    }

    public Point getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Point releasedOn) {
        this.releasedOn = releasedOn;
    }
}
