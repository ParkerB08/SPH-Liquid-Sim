package Calculate;

import java.util.ArrayList;

import Configure.Config;
import Configure.Particle;

public class Physics { 

    static final double r = Config.smoothingRadius;
    static final double m = Config.mass;
    static final double targetDensity = Config.targetDensity;
    static final double pressureMultiplier = Config.pressureMultiplier;

    public static double smoothingKernel(double d) {

        double influence = r - d;

        if (influence > 0){
            double volume = 10 / (Math.PI * (r * r * r * r * r));
            return volume * (influence * influence * influence);
        }
        return 0;
    }

    public static double derivativeKernel(double d) {

        double influence = r - d;

        if (influence > 0){
            return (30 / (Math.PI * (r * r * r * r * r)) * (influence * influence));
        }
        return 0;
    }

    public static double laplacianKernel(double d){

        double influence = r - d;

        if (influence > 0){
            return (40 / (Math.PI * r * r * r * r * r)) * influence;
        }
        return 0;
    }

    public static double calculateDensity(double x, double y, ArrayList<Particle> neighbors) {

        double density = 0;
         
        for (Particle p : neighbors){
            double dx = p.pos[0] - x;
            double dy = p.pos[1] - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            density += m * smoothingKernel(distance);
        }
        return density;
    }

    public static double[] calculatePressure(double x, double y, double pressure, double density, ArrayList<Particle> neighbors) {

        double[] pressureForce = {0, 0};
        double[] direction = {0, 0};
        double sharedPressure;
        double influence;

        for (Particle p : neighbors) {

            double dx = p.pos[0] - x;
            double dy = p.pos[1] - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance == 0) continue;

            direction[0] = (x - p.pos[0]) / distance;
            direction[1] = (y - p.pos[1]) / distance;

            influence = derivativeKernel(distance);
            sharedPressure = pressure / (density * density) + p.pressure / (p.density * p.density);

            pressureForce[0] -= sharedPressure * Config.mass * influence * direction[0];
            pressureForce[1] -= sharedPressure * Config.mass * influence * direction[1];
        }
        return pressureForce;
    }

    public static double[] calculateViscosity(double x, double y, double[] velocity, ArrayList<Particle> neighbors){

        double[] viscosityForce = {0, 0};
        
        for (Particle p : neighbors) {

            double dx = p.pos[0] - x;
            double dy = p.pos[1] - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance == 0){
                continue;
            }

            double influence = laplacianKernel(distance);
            viscosityForce[0] += m * influence * (p.vel[0] - velocity[0]) / p.density;
            viscosityForce[1] += m * influence * (p.vel[1] - velocity[1]) / p.density;
        }
        return viscosityForce;
    }

    public static double densityToPressure(double density){
        return pressureMultiplier * (density - targetDensity);
    }
}