package render;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JFrame;

import core.Config;
import core.Particle;

public class Render {

    private static ArrayList<Particle> particles = new ArrayList<>();

    public static void render () {

        Random rand = new Random();
        double[][] positions = new double[Config.particleAmount][2];

        // assign random particle pos
        for (int i = 0; i < Config.particleAmount; i++) {
            positions[i][0] = Config.borderOffset + rand.nextInt(Config.frameWidth / 2 - 2 * Config.borderOffset);
            positions[i][1] = Config.borderOffset + rand.nextInt(Config.frameHeight - 2 * Config.borderOffset);
        }

        // add to array list
        for (int i = 0; i < Config.particleAmount; i++) {
            particles.add(new Particle(positions[i][0], positions[i][1], Color.BLUE));
        }

        // initialize frame/particles
        JFrame frame = new JFrame("SPH Liquid Sim");
        DrawParticles sim = new DrawParticles(particles);
        
        // frame/particle config
        sim.setPreferredSize(new java.awt.Dimension(Config.frameWidth, Config.frameHeight));
        frame.add(sim);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // start simulation
        Timer timer = new Timer(16, e -> {Update.update(particles); sim.repaint();});
        timer.start();
    }
}
