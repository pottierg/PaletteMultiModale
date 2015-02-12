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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComponent;
import palettegraphiquemultimodale.model.Dessin;
import palettegraphiquemultimodale.model.Model;
import palettegraphiquemultimodale.model.Point;
import palettegraphiquemultimodale.orders.ActionsPossible;
import palettegraphiquemultimodale.orders.Formes;
import palettegraphiquemultimodale.orders.OrderManager;
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
        
        // Loading serialized templates rectangle and ellipse
        ObjectInputStream ois = null;
        
        try {
          final FileInputStream rectangleFile = new FileInputStream("Rectangle.ser");
          final FileInputStream antiRectangleFile = new FileInputStream("AntiRectangle.ser");
          final FileInputStream ellipseFile = new FileInputStream("Ellipse.ser");
          final FileInputStream antiEllipseFile = new FileInputStream("AntiEllipse.ser");
          

          ois = new ObjectInputStream(rectangleFile);
          final Template rectangle = (Template) ois.readObject();
          model.addTemplate(rectangle);
          
          ois = new ObjectInputStream(antiRectangleFile);
          final Template antiRectangle = (Template) ois.readObject();
          model.addTemplate(antiRectangle);

          ois = new ObjectInputStream(ellipseFile);
          final Template ellipse = (Template) ois.readObject();
          model.addTemplate(ellipse);
          
          ois = new ObjectInputStream(antiEllipseFile);
          final Template antiEllipse = (Template) ois.readObject();
          model.addTemplate(antiEllipse);

        } catch (final java.io.IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
              if (ois != null) {
                ois.close();
              }
            } catch (final IOException ex) {
              ex.printStackTrace();
            }
        }
        
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
                
                Template t = new Template(Calendar.getInstance().getTime().toString(), dessin.getVector());
                //serializeTemplate(t);
                model.addTemplate(t);
                
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
                
                if(res.Score > 0.8) {
                    OrderManager.getInstance().orderAction(ActionsPossible.CREATION);
                    OrderManager.getInstance().orderForme(res.Name.equals("Ellipse") ?
                            Formes.ELLIPSE : Formes.RECTANGLE);
                }
                
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
    
    protected void serializeTemplate(Template t) {
        // Serialization of templates
        ObjectOutputStream oos = null;

        try {
          final FileOutputStream fichier = new FileOutputStream(t.getName() + ".ser");
          oos = new ObjectOutputStream(fichier);

          oos.writeObject(t);
        } catch (final java.io.IOException ex) {
          ex.printStackTrace();
        } finally {
          try {
            if (oos != null) {
              oos.flush();
              oos.close();
            }
          } catch (final IOException ex) {
            ex.printStackTrace();
          }
        }
    }
    
}
