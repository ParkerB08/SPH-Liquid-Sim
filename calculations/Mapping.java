package calculations;

import java.util.ArrayList;
import core.Config;
import core.Particle;

public class Mapping {

    private static int cols = Config.frameWidth / Config.smoothingRadius + 1;
    private static int rows = Config.frameHeight / Config.smoothingRadius + 1;


    private static ArrayList<Particle>[] flatGrid = new ArrayList[cols * rows];
    static {
        for (int i = 0; i < flatGrid.length; i++)
            flatGrid[i] = new ArrayList<>();
    }

    public static void updateGrid(ArrayList<Particle> particles) {

        for (ArrayList<Particle> cell : flatGrid) {
            cell.clear();
        }

        for (Particle p : particles) {
            p.cellX = (int)(p.pos[0] / Config.smoothingRadius);
            p.cellY = (int)(p.pos[1] / Config.smoothingRadius);
            flatGrid[p.cellY * cols + p.cellX].add(p);
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
                    result.addAll(flatGrid[ny * cols + nx]);
                }
            }
        }

        return result;
    }
}
