import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import core.Particle;
import core.Config;
import calculations.Mapping;
import calculations.Physics;
import render.Render;

public class Main {

    private static ArrayList<Particle> particles = new ArrayList<>();
    public static void main(String[] args) {

        Random rand = new Random();
        double[][] positions = new double[Config.particleAmount][2];

        // spawn particles
        for (int i = 0; i < Config.particleAmount; i++) {
            positions[i][0] = Config.borderOffset + rand.nextInt(Config.frameWidth - 2 * Config.borderOffset);
            positions[i][1] = Config.borderOffset + rand.nextInt(Config.frameHeight - 2 * Config.borderOffset);
        }
        for (int i = 0; i < Config.particleAmount; i++) {
            particles.add(new Particle(positions[i][0], positions[i][1], Color.BLUE));
        }

        JFrame frame = new JFrame("SPH Liquid Sim");
        Render render = new Render(particles);
        render.setPreferredSize(new java.awt.Dimension(Config.frameWidth, Config.frameHeight));
        frame.add(render);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Timer timer = new Timer(16, e -> {update(particles); render.repaint();});
        timer.start();
    }

    public static void update(ArrayList<Particle> particles){

        // updates
        Mapping.updateGrid(particles);
        for (Particle p : particles) {
            Mapping.neighborhoodSearch(p.pos[0], p.pos[1], p.neighbors); 
        }
        updateDensities();
        updatePressures();
        double dt = Config.timeStep;

        for (Particle p : particles) {
            
            // forces
            double[] pressure = Physics.calculatePressure(p.pos[0], p.pos[1], p.pressure, p.density, p.neighbors);

            p.vel[0] += -pressure[0] * dt;
            p.vel[1] += (-pressure[1] + Config.grav) * dt;

            p.vel[0] *= Config.velDamp;
            p.vel[1] *= Config.velDamp;

            p.pos[0] += p.vel[0];
            p.pos[1] += p.vel[1];
            
            // border collision
            if (p.pos[1] > Config.frameHeight - Config.borderOffset) {
                p.pos[1] = Config.frameHeight - Config.borderOffset;
                p.vel[1] *= -Config.borderDamp;
                p.vel[0] *= Config.friction;
            }
            if (p.pos[1] < Config.borderOffset) {
                p.pos[1] = Config.borderOffset;
                p.vel[1] *= -Config.borderDamp;
                p.vel[0] *= Config.friction;
            }
            if (p.pos[0] > Config.frameWidth - Config.borderOffset) {
                p.pos[0] = Config.frameWidth - Config.borderOffset;
                p.vel[0] *= -Config.borderDamp;
                p.vel[1] *= Config.friction;
            }
            if (p.pos[0] < Config.borderOffset) {
                p.pos[0] = Config.borderOffset;
                p.vel[0] *= -Config.borderDamp;
                p.vel[1] *= Config.friction;
            }
        }
    }

    public static ArrayList<Particle> getParticles() {
        return particles;
    }

    public static void updateDensities(){
        for (Particle p : particles) {
            p.density = Physics.calculateDensity(p.pos[0], p.pos[1], p.neighbors);
        }
    }

    public static void updatePressures(){
        for (Particle p : particles) {
            p.pressure = Physics.densityToPressure(p.density);
        }
    }
}