/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
    double HalfDiagonal = 0.5 * Math.sqrt(250.0 * 250.0 + 250.0 * 250.0);
    double AngleRange = 45.0;
    double AnglePrecision = 2.0;

    public List<Template> Templates = new ArrayList();

    public Result Recognize(Vector points) {
        points = Utils.Resample(points, Template.NUM_POINTS);
        points = Utils.RotateToZero(points);
        points = Utils.ScaleToSquare(points, Template.SQUARE_SIZE);
        points = Utils.TranslateToOrigin(points);

        int t = 0;
        double b = Double.MAX_VALUE;

        for (int i = 0; i < Templates.size(); i++) {
            double d = Utils.DistanceAtBestAngle(points, (Template) Templates.get(i), -AngleRange, AngleRange, AnglePrecision);
            if (d < b) {
                b = d;
                t = i;
            }
        }

        double score = 1.0 - (b / HalfDiagonal);

        return new Result(((Template) Templates.get(t)).Name, score, t, Utils.lastTheta);
    }
    
}
