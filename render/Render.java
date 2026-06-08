package render;

import javax.swing.JPanel;
import core.Particle;
import core.Config;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class Render extends JPanel {
    private final ArrayList<Particle> particles;

    public Render(ArrayList<Particle> particles) {
        this.particles = particles;
        setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Smooth circle edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Particle p : particles) {
            g2.setColor(p.colour);
            g2.fillOval((int) (p.pos[0] - Config.particleRadius), (int) (p.pos[1] - Config.particleRadius), Config.particleRadius * 2, Config.particleRadius * 2);
        }
    }
}