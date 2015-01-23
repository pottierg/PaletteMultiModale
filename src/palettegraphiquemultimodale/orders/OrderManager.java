/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.orders;

import java.awt.Color;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author POTTIEGU
 */
public class OrderManager {
    public static final int ORDER_DELAY = 10000;
    
    private static Order order;
    private static Timer timer;
    private static OrderManager instance;
    
    private OrderManager() {
        order = null;
        timer = new Timer();
        instance = this;
    }
    
    public static OrderManager getInstance() {
        if (OrderManager.instance == null) {
            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
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
        
        // Timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Running order");
                // If the order has enough info to do something
                if(order.orderIsComplete())
                    order.execute();
                
                // Stop the task anyway
                order = null;
            }
        }, ORDER_DELAY);
        
    }
    
    public boolean isCreatingOrder() {
        return order != null;
    }
    
    public void orderPosition(Point p) {
        System.out.println("OrderManager : setting position");
        if(order == null) {
            createOrder();
        }
        
        order.setPosition(p);
        if(order.orderIsComplete())
            order.execute();
    }
    
    public void orderColor(String c) {
        if(order == null) {
            createOrder();
        }
        
        order.setColor(c);
        if(order.orderIsComplete())
            order.execute();
    }
    
    public void orderAction(ActionsPossible a) {
        if(order == null) {
            createOrder();
        }
        
        order.setAction(a);
        if(order.orderIsComplete())
            order.execute();
    }
    
    public void orderForme(Formes f) {
        if(order == null) {
            createOrder();
        }
        
        order.setForme(f);
        if(order.orderIsComplete())
            order.execute();
    }
}
