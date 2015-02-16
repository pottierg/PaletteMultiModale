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
    private ActionPossible action;
    private String color;
    // Point that has been designed on the palette
    private Point position;
    private Point releasedOn;
    private Forme forme;
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
        return (action == ActionPossible.CREATION && forme != null
                        && position != null && color != null)
            || (action == ActionPossible.DEPLACEMENT && designedShape != null
                        && releasedOn != null);
    }
    
    public boolean orderHasEnoughInfo() {
        return (action == ActionPossible.CREATION && forme != null)
            || (action == ActionPossible.DEPLACEMENT && designedShape != null
                    && releasedOn != null);
    }
    
    public void execute() {
        if(!orderHasEnoughInfo()) {
            System.out.println("Order incomplete, cancelling");
            System.out.println("Order infos : " + this.toString());
            return;
        }
        System.out.println("Executing order : " + action);
        
        String fond = (color == null ? "" : color);
        int x = position != null ? position.x : 5;
        int y = position != null ? position.y : 5;
        int releasedOnX = releasedOn != null ? releasedOn.x : 0;
        int releasedOnY = releasedOn != null ? releasedOn.y : 0;
        
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
                    System.out.println(designedShape + " to " + (releasedOnX - x) + ", " + (releasedOnY - y));
                    IvyDaemon.getInstance().moveObject(designedShape, (releasedOnX - x), (releasedOnY - y));
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public ActionPossible getAction() {
        return action;
    }

    public void setAction(ActionPossible action) {
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
        this.position = position;
    }

    public Forme getForme() {
        return forme;
    }

    public void setForme(Forme forme) {
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
    
    public String toString() {
        if(this.action == ActionPossible.DEPLACEMENT)
            return  "Deplacement " + this.designedShape + " to " + this.releasedOn;
        else
            return "Création " + this.forme + " " + this.color + " " + this.position;
    }
}
