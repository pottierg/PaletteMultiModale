/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.orders;

/**
 *
 * @author POTTIEGU
 */
public enum State {
    IDLE,
    INCOMPLETE_MOVE, MOUSE_DRAGGING, HIRO_INITIALIZE, HIRO_DRAGGING,
    INCOMPLETE_CREATE, PARTIAL_CREATE, CREATE_COLOR, CREATE_POSITION;
}
