/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import palettegraphiquemultimodale.model.Dessin;
import palettegraphiquemultimodale.model.Model;
import palettegraphiquemultimodale.model.Point;

/**
 *
 * @author POTTIEGU
 */
public class Panel extends JComponent {
    Model model;
    Dessin dessin;
    
    public Panel(Model m) {
        super();
        dessin = new Dessin();
        model = m;
        init();
    }
    
    @Override
    public void paint(Graphics g_) {
        Graphics2D g = (Graphics2D) g_;
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.RED);
        for(Point p : dessin.getDessin())
            g.fillOval((int) p.X, (int) p.Y, 4, 4);
        
        g.setColor(Color.GRAY);
        for(Dessin d : model.getDessins())
            for(Point p : d.getDessin())
                g.fillOval((int) p.X, (int) p.Y, 4, 4);
    }
    
    public void init() {
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // void
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dessin = new Dessin();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                model.addDessin(dessin);
                dessin = new Dessin();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // void
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // void
            }
        
        });
        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                dessin.addPoint(new Point(e.getX(), e.getY()));
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // void
            }
            
        });
    }
    
    
}
