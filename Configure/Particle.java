package Configure;

import java.awt.Color;

import java.util.ArrayList;

public class Particle {
    public double[] pos, vel;       
    public Color colour;
    public double density, pressure;
    public int cellX, cellY;
    public ArrayList<Particle> neighbors;

    public Particle(double x, double y, Color colour) {
        this.pos = new double[]{x, y};
        this.vel = new double[]{0, 0};
        this.neighbors = new ArrayList<>(64);
        this.colour = colour;
        this.density = 0;
    }
}