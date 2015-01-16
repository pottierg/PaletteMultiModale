/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author VIGUIELO
 */
public class Result {

    public String Name;
    public double Score;
    public int Index;
    public double Theta;

    public Result(String name, double score, int index) {
        this.Name = name;
        this.Score = score;
        this.Index = index;
        Theta = 0;
    }

    public Result(String name, double score, int index, double theta) {
        this.Name = name;
        this.Score = score;
        this.Index = index;
        this.Theta = theta;
    }
}
