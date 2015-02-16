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
    private TimerTask currentTask;
    private boolean timerIsRunning;
    private State state;
    
    private OrderManager() {
        order = null;
        timer = new Timer();
        instance = this;
        currentTask = new TimerHandler();
        timerIsRunning = false;
        state = State.IDLE;
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
        order = new Order();
    }
    
    public boolean isCreatingOrder() {
        return order != null;
    }
    
    public ActionPossible typeOfCurrentOrder() {
        if(isCreatingOrder())
            return order.getAction();
        return null;
    }
    
    /**
     * Sets the position of a new shape or initializes
     * shape dragging.
     * @param p Point clicked on
     */
    public void orderPressedOn(Point p) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                // void
                break;
                
            // Set or reset the position of a new shape
            case INCOMPLETE_CREATE :
                break;
            case PARTIAL_CREATE :
                order.setPosition(p);
                startTimer();
                state = State.CREATE_POSITION;
                break;
            case CREATE_COLOR :
                order.setPosition(p);
                // Order is complete in this case : finalize
                finalizeOrder();
                startTimer();
                state = State.IDLE;
                break;
            case CREATE_POSITION :
                // Resets the position
                order.setPosition(p);
                startTimer();
                break;
            
            case INCOMPLETE_MOVE :
                // orderDesignedShape handles the Press/InShape event
                // But needs the original position
                order.setPosition(p);
                break;
            case MOUSE_DRAGGING :
                // forbidden
                break;
            case HIRO_INITIALIZE :
                break;
            case HIRO_DRAGGING :
                // void
                break;
        }
    }
    
    /**
     * Set the color for a shape
     * @param c name of the color as recognized by the palette
     */
    public void orderColor(String c) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                // void
                break;
                
            case INCOMPLETE_CREATE :
                // void
                break;
            case PARTIAL_CREATE :
                order.setColor(c);
                startTimer();
                state = State.CREATE_COLOR;
                break;
            case CREATE_COLOR :
                // Resets the color
                order.setColor(c);
                startTimer();
                break;
            case CREATE_POSITION :
                order.setColor(c);
                // Order is complete in this case : draw it
                finalizeOrder();
                startTimer();
                state = State.IDLE;
                break;
            
            case INCOMPLETE_MOVE :
                // May be used for designation
                break;
            case MOUSE_DRAGGING :
                // void
                break;
            case HIRO_INITIALIZE :
                break;
            case HIRO_DRAGGING :
                // void
                break;
        }
    }
    
    /**
     * Set the initial purpose of the order : moving or creating
     * @param a ActionPossible for moving or creating
     */
    public void orderAction(ActionPossible a) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                order.setAction(a);
                startTimer();
                state = a == ActionPossible.CREATION ?
                        State.PARTIAL_CREATE : State.INCOMPLETE_MOVE;
                break;
                
            case INCOMPLETE_CREATE :
                break;
            case PARTIAL_CREATE :
                break;
            case CREATE_COLOR :
                break;
            case CREATE_POSITION :
                break;
            
            case INCOMPLETE_MOVE :
                break;
            case MOUSE_DRAGGING :
                break;
            case HIRO_DRAGGING :
                break;
        }
    }
    
    /**
     * Set the shape for an order
     * @param f 
     */
    public void orderForme(Forme f) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                break;
                
            case INCOMPLETE_CREATE :
                order.setForme(f);
                startTimer();
                state = State.PARTIAL_CREATE;
                break;
            case PARTIAL_CREATE :
            case CREATE_COLOR :
            case CREATE_POSITION :
                // Resets the shape
                order.setForme(f);
                startTimer();
                break;
            
            case INCOMPLETE_MOVE :
                // May be used for designation
                break;
            case MOUSE_DRAGGING :
                break;
            case HIRO_INITIALIZE :
                break;
            case HIRO_DRAGGING :
                break;
        }
    }
    
    /**
     * Equals to the Press / InShape event.
     * Happens if you click on a shape in the palette.
     * Sets a specific shape for future events.
     * @param nom name of the shape according to the palette
     */
    public void orderDesignedShape(String nom) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                // Eventually going to "move" state without voice commands
                // to go mouse dragging directly
                break;
            
            case INCOMPLETE_CREATE :
                break;
            case PARTIAL_CREATE :
                break;
            case CREATE_COLOR :
                break;
            case CREATE_POSITION :
                break;
            
            case INCOMPLETE_MOVE :
                order.setDesignedShape(nom);
                state = State.MOUSE_DRAGGING;
                break;
            case MOUSE_DRAGGING :
                break;
            case HIRO_INITIALIZE :
                order.setDesignedShape(nom);
                state = State.HIRO_DRAGGING;
                break;
            case HIRO_DRAGGING :
                break;
        }
        startTimer();
    }
    
    public void orderReleasedOn(Point p) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                break;
                
            case INCOMPLETE_CREATE :
                // void
                break;
            case PARTIAL_CREATE :
                // void
                break;
            case CREATE_COLOR :
                // void
                break;
            case CREATE_POSITION :
                // void
                break;
            
            case INCOMPLETE_MOVE :
                // void
                break;
            case MOUSE_DRAGGING :
                System.out.println("mouse released");
                order.setReleasedOn(p);
                state = State.IDLE;
                // Order is complete, finalize
                finalizeOrder();
                break;
            case HIRO_INITIALIZE :
                // void
                break;
            case HIRO_DRAGGING :
                // void
                break;
        }
    }
    
    public void orderHiroHasShownUp(Point p) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                break;
                
            case INCOMPLETE_CREATE :
                // void
                break;
            case PARTIAL_CREATE :
                // void
                break;
            case CREATE_COLOR :
                // void
                break;
            case CREATE_POSITION :
                // void
                break;
            
            case INCOMPLETE_MOVE :
                // Set the original position
                System.out.println("Hiro shown up");
                order.setPosition(p);
                state = State.HIRO_INITIALIZE;
                break;
            case MOUSE_DRAGGING :
                // void
                break;
            case HIRO_INITIALIZE :
                // void
                break;
            case HIRO_DRAGGING :
                // void
                break;
        }
    }
    
    public void orderHiroMovesTo(Point p) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                break;
                
            case INCOMPLETE_CREATE :
                // void
                break;
            case PARTIAL_CREATE :
                // void
                break;
            case CREATE_COLOR :
                // void
                break;
            case CREATE_POSITION :
                // void
                break;
            
            case INCOMPLETE_MOVE :
                // void
                break;
            case MOUSE_DRAGGING :
                // void
                break;
            case HIRO_INITIALIZE :
                // void
                break;
            case HIRO_DRAGGING :
                // Without this line, there is a "joystick" effect, may be interesting
                order.setPosition(order.getReleasedOn());
                order.setReleasedOn(p);
                order.execute();
                break;
        }
    }
    
    public void orderHiroHiddenOn(Point p) {
        if(order == null) {
            createOrder();
        }
        
        switch (state) {
            case IDLE :
                break;
                
            case INCOMPLETE_CREATE :
                // void
                break;
            case PARTIAL_CREATE :
                // void
                break;
            case CREATE_COLOR :
                // void
                break;
            case CREATE_POSITION :
                // void
                break;
            
            case INCOMPLETE_MOVE :
                // void
                break;
            case MOUSE_DRAGGING :
                // void
                break;
            case HIRO_INITIALIZE :
                // void
                break;
            case HIRO_DRAGGING :
                order = null;
                state = State.IDLE;
                break;
        }
    }
    
    private class TimerHandler extends TimerTask {
        @Override
        public void run() {
            System.out.println("tick : state=" + state);
            switch (state) {
                case IDLE :
                    break;

                case INCOMPLETE_CREATE :
                    order = null;
                    cancelTimer();
                    state = State.IDLE;
                    break;
                case PARTIAL_CREATE :
                    order.execute();
                    order = null;
                    cancelTimer();
                    state = State.IDLE;
                    break;
                case CREATE_COLOR :
                    order.execute();
                    order = null;
                    cancelTimer();
                    state = State.IDLE;
                    break;
                case CREATE_POSITION :
                    order.execute();
                    order = null;
                    cancelTimer();
                    state = State.IDLE;
                    break;

                case INCOMPLETE_MOVE :
                    order = null;
                    cancelTimer();
                    state = State.IDLE;
                    break;
                case MOUSE_DRAGGING :
                    // void
                    break;
                case HIRO_INITIALIZE :
                    // void
                    break;
                case HIRO_DRAGGING :
                    // void
                    break;
            }
        }
    }
    
    private void finalizeOrder() {
        cancelTimer();
        order.execute();
        order = null;
    }
    
    private void startTimer() {
        if(timerIsRunning) {
            // New piece of order received : new timer
            cancelTimer();
        }
        timerIsRunning = true;
        currentTask = new TimerHandler();
        timer.schedule(currentTask, ORDER_DELAY);
    }
    
    private void cancelTimer() {
        timerIsRunning = false;
        currentTask.cancel();
        timer.cancel();
        timer.purge();
        timer = new Timer();
    }
}
