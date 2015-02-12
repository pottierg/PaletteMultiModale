/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.orders;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author POTTIEGU
 */
public class OrderManager {
    public static final int ORDER_DELAY = 3000;
    
    private static Order order;
    private static Timer timer;
    private static OrderManager instance;
    private boolean timerIsRunning;
    
    private OrderManager() {
        order = null;
        timer = new Timer();
        instance = this;
        timerIsRunning = false;
    }
    
    public static OrderManager getInstance() {
        if (OrderManager.instance == null) {
            // Pattern singleton
            synchronized(OrderManager.class) {
              if (OrderManager.instance == null) {
                OrderManager.instance = new OrderManager();
              }
            }
         }
        
         return OrderManager.instance;
    }
    
    private void createOrder() {
        System.out.println("OrderManager : Creating order");
        order = new Order();
        timerIsRunning = true;
        
        // Timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timeout : Running or cancelling order");
                // If the order has enough info to do something
                if(order != null)
                    if(order.orderIsComplete())
                        order.execute();
                
                // Stop the task anyway
                timerIsRunning = false;
                order = null;
            }
        }, ORDER_DELAY);
    }
    
    public boolean isCreatingOrder() {
        return order != null;
    }
    
    public ActionsPossible typeOfCurrentOrder() {
        if(isCreatingOrder())
            return order.getAction();
        return null;
    }
    
    public void orderPosition(Point p) {
        System.out.println("OrderManager : setting position");
        if(order == null) {
            createOrder();
        }
        
        if(order.getAction() == null && order.getDesignedShape() != null) {
            System.out.println("OrderManager : Setting move mode");
            order.setAction(ActionsPossible.DEPLACEMENT);
        }
        order.setPosition(p);
        finalizeOrder();
    }
    
    public void orderColor(String c) {
        if(order == null) {
            createOrder();
        }
        
        order.setColor(c);
        finalizeOrder();
    }
    
    public void orderAction(ActionsPossible a) {
        if(order == null) {
            createOrder();
        }
        
        order.setAction(a);
        finalizeOrder();
    }
    
    public void orderForme(Formes f) {
        if(order == null) {
            createOrder();
        }
        order.setForme(f);
        finalizeOrder();
    }
    
    public void orderDesignedShape(String nom) {
        if(order == null) {
            createOrder();
        }
        order.setDesignedShape(nom);
        finalizeOrder();
    }
    
    public void orderReleasedOn(Point p) {
        if(order == null) {
            createOrder();
        }
        order.setReleasedOn(p);
        finalizeOrder();
    }
    
    private void finalizeOrder() {
        if(order.orderIsComplete()) {
            order.execute();
            order = null;
            timer.cancel();
            timer = new Timer();
            return;
        }
        
        if(timerIsRunning) {
            // New piece of order received : new timer
            timer.cancel();
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timeout : Running or cancelling order");
                // If the order has enough info to do something
                if(order != null)
                    order.execute();
                
                // Stop the task anyway
                timerIsRunning = false;
                order = null;
            }
        }, ORDER_DELAY);
    }
}
