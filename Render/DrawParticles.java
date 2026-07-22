package Render;

import javax.swing.JPanel;

import Configure.Config;
import Configure.Particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.ArrayList;

public class DrawParticles extends JPanel {
    private final ArrayList<Particle> particles;

    public DrawParticles(ArrayList<Particle> particles) {
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

    public static void colorParticles(ArrayList<Particle> particles) {

        double maxSpeed = 5;

        for (Particle p : particles){
            double speed = Math.sqrt(p.vel[0] * p.vel[0] + p.vel[1] * p.vel[1]);
            double influence = Math.min(speed / maxSpeed, 1.0);

            int r;
            int g;
            int b;

            if (influence < 0.33) {
                double t = influence / 0.33;
                r = 0;
                g = (int) (128 + t * 127);
                b = 255;
            } else if (influence < 0.66) {
                double t = (influence - 0.33) / 0.33;
                r = (int) (t * 255);
                g = 255;
                b = (int) (255 - t * 200);
            } else {
                double t = (influence - 0.66) / 0.34;
                r = 255;
                g = (int) (255 - t * 190);
                b = (int) (55 - t * 55);
            }

            p.colour = new Color(r, g, b);
        }
    }
}
