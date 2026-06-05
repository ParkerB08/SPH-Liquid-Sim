import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class Render extends JPanel {
    private final ArrayList<Particle> particles;

    public Render(ArrayList<Particle> particles) {
        this.particles = particles;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Smooth circle edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Particle p : particles) {
            g2.setColor(p.colour);
            g2.fillOval((int) p.pos[0], (int) p.pos[1], p.radius * 2, p.radius * 2);
        }
    }
}