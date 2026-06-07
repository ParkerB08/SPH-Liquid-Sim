import java.util.ArrayList;

public class Mapping {

    public static ArrayList<ArrayList<ArrayList<Particle>>> updateGrid() {

        int cols = Config.frameWidth / Config.smoothingRadius + 1;
        int rows = Config.frameHeight / Config.smoothingRadius + 1;
        ArrayList<ArrayList<ArrayList<Particle>>> grid = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            ArrayList<ArrayList<Particle>> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(new ArrayList<>());
            }
            grid.add(row);
        }

        for (Particle p : Main.getParticles()) {

            p.cellX = (int)(p.pos[0] / Config.smoothingRadius);
            p.cellY = (int)(p.pos[1] / Config.smoothingRadius);

            grid.get(p.cellY).get(p.cellX).add(p);
        }
        return grid;
    }

    public static ArrayList<Particle> neighborhoodSearch(ArrayList<ArrayList<ArrayList<Particle>>> grid, double x, double y) {

        int cx =  (int) (x / Config.smoothingRadius);
        int cy =  (int) (y / Config.smoothingRadius);

        ArrayList<Particle> neighbors = new ArrayList<>();

        for (int dy = -1; dy < 2; dy++){
            for (int dx = -1; dx < 2; dx++){

                int nx = cx + dx;
                int ny = cy + dy;

                if (nx >= 0 && nx < grid.get(0).size() && ny >= 0 && ny < grid.size()) {
                    neighbors.addAll(grid.get(ny).get(nx));
                }
            }
        }

        return neighbors;
    }
}
