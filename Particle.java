import java.awt.Color;

public class Particle {
    public double[] pos, vel;       
    public Color colour;
    public double density, pressure;

    public Particle(double x, double y, Color colour) {
        this.pos = new double[]{x, y};
        this.vel = new double[]{0, 0};
        this.colour = colour;
        this.density = 0;
    }
}