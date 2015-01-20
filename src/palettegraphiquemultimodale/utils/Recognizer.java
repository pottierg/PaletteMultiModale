/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | templates
 * and open the template in the editor.
 */
package palettegraphiquemultimodale.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author VIGUIELO
 */
public class Recognizer {
    double halfDiagonal = 0.5 * Math.sqrt(250.0 * 250.0 + 250.0 * 250.0);
    double angleRange = 45.0;
    double anglePrecision = 2.0;

    public List<Template> templates;
    
    public Recognizer(List<Template> l) {
        templates = l;
    }

    public Result Recognize(Vector points) {
        points = Utils.Resample(points, Template.NUM_POINTS);
        points = Utils.RotateToZero(points);
        points = Utils.ScaleToSquare(points, Template.SQUARE_SIZE);
        points = Utils.TranslateToOrigin(points);

        int t = 0;
        double b = Double.MAX_VALUE;

        for (int i = 0; i < templates.size(); i++) {
            double d = Utils.DistanceAtBestAngle(points, (Template) templates.get(i), -angleRange, angleRange, anglePrecision);
            if (d < b) {
                b = d;
                t = i;
            }
        }

        double score = 1.0 - (b / halfDiagonal);

        return new Result(((Template) templates.get(t)).Name, score, t, Utils.lastTheta);
    }
    
}
