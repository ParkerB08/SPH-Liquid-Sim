import javax.swing.JFrame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random rand = new Random();
        ArrayList<Particle> particles = new ArrayList<>();
        int[][] positions = new int[Config.particleAmount][2];

        for (int i = 0; i < Config.particleAmount; i++){
            positions[i][0] = rand.nextInt(Config.frameWidth);
            positions[i][1] = rand.nextInt(Config.frameWidth);
        }

        for (int i = 0; i < Config.particleAmount; i++){
            particles.add(new Particle(positions[i], Color.BLUE, 5));
        }

        JFrame frame = new JFrame("SPH Liquid Sim");
        Render render = new Render(particles);

        render.setPreferredSize(new java.awt.Dimension(Config.frameWidth, Config.frameHeight));

        frame.add(render);
        frame.pack(); // automatically sizes frame

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}