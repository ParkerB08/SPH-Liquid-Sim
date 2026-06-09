package render;

import java.awt.Color;

import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.JFrame;

import core.Config;
import core.Particle;

public class Render {

    private static ArrayList<Particle> particles = new ArrayList<>();

    public static void render () {

        double[][] positions = new double[Config.particleAmount][2];

        // create grid in center
        int gridSize = (int) Math.ceil(Math.sqrt(Config.particleAmount));
        double spacing = 15;
        double gridWidth = gridSize * spacing;
        double gridHeight = gridSize * spacing;
        
        double centerX = Config.frameWidth / 2.0;
        double centerY = Config.frameHeight / 2.0;
        double startX = centerX - gridWidth / 2.0;
        double startY = centerY - gridHeight / 2.0;
        
        int index = 0;
        for (int row = 0; row < gridSize && index < Config.particleAmount; row++) {
            for (int col = 0; col < gridSize && index < Config.particleAmount; col++) {
                positions[index][0] = startX + col * spacing;
                positions[index][1] = startY + row * spacing;
                index++;
            }
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
