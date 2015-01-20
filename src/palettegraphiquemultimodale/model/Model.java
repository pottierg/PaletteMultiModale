/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.model;

import java.util.ArrayList;
import java.util.List;
import palettegraphiquemultimodale.utils.Template;

/**
 *
 * @author POTTIEGU
 */
public class Model {
    private List<Dessin> dessins;
    private List<Template> templates;
    
    public Model() {
        dessins = new ArrayList<>();
        templates = new ArrayList<>();
    }

    public List<Dessin> getDessins() {
        return dessins;
    }
    
    public void addDessin(Dessin d) {
        dessins.add(d);
    }
    
    public void addTemplate(Template t) {
        templates.add(t);
        System.out.println(t.toString());
    }

    public List<Template> getTemplates() {
        return templates;
    }
    
    
}
