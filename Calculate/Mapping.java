package Calculate;

import java.util.ArrayList;

import Configure.Config;
import Configure.Particle;

public class Mapping {

    private static int cols = Config.frameWidth / Config.smoothingRadius + 1;
    private static int rows = Config.frameHeight / Config.smoothingRadius + 1;
    private static ArrayList<Particle>[] grid = new ArrayList[cols * rows];

    static {
        for (int i = 0; i < grid.length; i++)
            grid[i] = new ArrayList<>();
    }

    public static void updateGrid(ArrayList<Particle> particles) {

        for (ArrayList<Particle> cell : grid) {
            cell.clear();
        }

        for (Particle p : particles) {
            p.cellX = (int)(p.pos[0] / Config.smoothingRadius);
            p.cellY = (int)(p.pos[1] / Config.smoothingRadius);
            grid[p.cellY * cols + p.cellX].add(p);
        }
    }
        
    public static ArrayList<Particle> neighborhoodSearch(double x, double y, ArrayList<Particle> result) {

        result.clear();
        int cx =  (int) (x / Config.smoothingRadius);
        int cy =  (int) (y / Config.smoothingRadius);

        for (int dy = -1; dy < 2; dy++){
            for (int dx = -1; dx < 2; dx++){

                int nx = cx + dx;
                int ny = cy + dy;

                if (nx >= 0 && nx < cols && ny >= 0 && ny < rows) {
                    for (Particle p : grid[ny * cols + nx]) {
                        result.add(p);
                    }
                }
            }
        }

        return result;
    }
}
