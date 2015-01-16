/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author POTTIEGU
 */
public class Model {
    private List<Dessin> dessins;
    
    public Model() {
        dessins = new ArrayList<>();
    }

    public List<Dessin> getDessins() {
        return dessins;
    }
    
    public void addDessin(Dessin d) {
        dessins.add(d);
    }
}
