import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class Main {
    public static void main(String[] args) {

        Random rand = new Random();
        ArrayList<Particle> particles = new ArrayList<>();
        double[][] positions = new double[Config.particleAmount][2];

        for (int i = 0; i < Config.particleAmount; i++) {
            positions[i][0] = rand.nextInt(Config.frameWidth - Config.borderOffset);
            positions[i][1] = rand.nextInt(Config.frameHeight - Config.borderOffset);
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
        for (Particle p : particles) {

            //p.vel[1] += Config.grav;

            p.pos[0] += p.vel[0];
            p.pos[1] += p.vel[1];
            
            // border collision
            if (p.pos[1] > Config.frameHeight - Config.borderOffset) {
            p.pos[1] = Config.frameHeight - Config.borderOffset;
            p.vel[1] *= -Config.borderDamp;
            }
            if (p.pos[1] < Config.borderOffset) {
            p.pos[1] = Config.borderOffset;
            p.vel[1] *= -Config.borderDamp;
            }
            if (p.pos[0] > Config.frameWidth - Config.borderOffset) {
            p.pos[0] = Config.frameWidth - Config.borderOffset;
            p.vel[0] *= -Config.borderDamp;
            }
            if (p.pos[0] < Config.borderOffset) {
            p.pos[0] = Config.borderOffset;
            p.vel[0] *= -Config.borderDamp;
            }
        }
    }
}