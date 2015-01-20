/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComponent;
import palettegraphiquemultimodale.model.Dessin;
import palettegraphiquemultimodale.model.Model;
import palettegraphiquemultimodale.model.Point;
import palettegraphiquemultimodale.utils.Recognizer;
import palettegraphiquemultimodale.utils.Result;
import palettegraphiquemultimodale.utils.Template;

/**
 *
 * @author POTTIEGU
 */
public class Panel extends JComponent {
    private Model model;
    private Dessin dessin;
    private JButton registerAsTemplateButton;
    private JButton recognizeButton;
    
    public Panel(Model m) {
        super();
        
        registerAsTemplateButton = new JButton("Ajouter aux templates");
        recognizeButton = new JButton("Reconnaître");
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
        for(Point p : dessin.getVector())
            g.fillOval((int) p.X, (int) p.Y, 4, 4);
        
        this.paintChildren(g_);
        /*
        g.setColor(Color.GRAY);
        for(Dessin d : model.getDessins())
            for(Point p : d.getDessin())
                g.fillOval((int) p.X, (int) p.Y, 4, 4);
        */
    }
    
    public final void init() {
        registerAsTemplateButton.setBounds(5, 5, 180, 25);
        registerAsTemplateButton.setEnabled(false);
        this.add(registerAsTemplateButton);
        
        registerAsTemplateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(dessin.getVector().isEmpty())
                    return;
                
                model.addTemplate(new Template(Calendar.getInstance().getTime().toString(), dessin.getVector()));
                
                registerAsTemplateButton.setEnabled(false);
                recognizeButton.setEnabled(false);
                
                dessin = new Dessin();
                repaint();
            }
            
        });
        
        recognizeButton.setBounds(190, 5, 120, 25);
        recognizeButton.setEnabled(false);
        this.add(recognizeButton);
        
        recognizeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(dessin.getVector().isEmpty())
                    return;
                
                Recognizer r = new Recognizer(model.getTemplates());
                Result res = r.Recognize(dessin.getVector());
                System.out.println("Template reconnu : " + res.Name + ", score:" + res.Score);
                
                registerAsTemplateButton.setEnabled(false);
                recognizeButton.setEnabled(false);
                
                dessin = new Dessin();
                repaint();
            }
        });
        
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
                if(dessin.getVector().isEmpty())
                    return;
                
                //model.addDessin(dessin);
                
                registerAsTemplateButton.setEnabled(true);
                recognizeButton.setEnabled(true);
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
