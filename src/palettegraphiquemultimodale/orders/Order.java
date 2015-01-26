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
    private Point position;
    private Formes forme;
    
    public Order() {
        position = null;
        forme = null;
        action = null;
        color = null;
    }
    
    public boolean orderIsComplete() {
        return action != null && forme != null && position != null && color != null;
    }
    
    public boolean orderHasEnoughInfo() {
        return action != null && forme != null;
    }
    
    public void execute() {
        // TODO
        if(!orderHasEnoughInfo()) {
            System.out.println("Order incomplete, cancelling");
            return;
        }
        System.out.println("Executing order : " + action + " " + forme + " " + color);
        
        String fond = (color == null ? "" : color);
        int x = position != null ? position.x : 5;
        int y = position != null ? position.y : 5;
        
        try {
            switch(action) {
                case CREATION:
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
        this.position = position;
    }

    public Formes getForme() {
        return forme;
    }

    public void setForme(Formes forme) {
        this.forme = forme;
    }
    
    
    
}
