import java.awt.Color;

public class Particle {
    public double[] pos;  
    public double[] vel;     
    public Color colour;

    public Particle(double x, double y, Color colour) {
        this.pos = new double[]{x, y};
        this.vel = new double[]{0, 0};
        this.colour = colour;
    }
}