public class Physics { 

    public static double smoothingKernel(double radius, double distance){
        double influence = radius - distance;
        if (influence > 0){
            double volume = 10 / (Math.PI * Math.pow(radius, 5));
            return volume * Math.pow(influence, 3);
        }
        return 0;
    }

    public static double derivativeKernel(double radius, double distance){

        double influence = radius - distance;

        if (influence > 0){
            return (30 / (Math.PI * Math.pow(radius, 5)) * Math.pow(influence, 2));
        }
        return 0;
    }

    public static double calculateDensity(double x,double y){
        double density = 0;
        for (Particle p : Main.getParticles()){
            double distance = Math.pow(Math.pow((p.pos[0] - x), 2) + Math.pow((p.pos[1] - y), 2), 0.5);
            density += Config.mass * smoothingKernel(Config.smoothingRadius, distance);
        }
        return density;
    }

    public static double[] calculatePressure(double x, double y, double pressure, double density) {

        double[] pressureForce = {0, 0};
        double[] direction = {0, 0};
        double sharedPressure;
        double influence;

        for (Particle p : Main.getParticles()) {
            double distance = Math.hypot(p.pos[0] - x, p.pos[1] - y);
            if (distance == 0) continue;

            direction[0] = (x - p.pos[0]) / distance;
            direction[1] = (y - p.pos[1]) / distance;

            influence = derivativeKernel(Config.smoothingRadius, distance);
            sharedPressure = pressure / Math.pow(density, 2) + p.pressure / Math.pow(p.density, 2);

            pressureForce[0] += sharedPressure * Config.mass * influence * direction[0];
            pressureForce[1] += sharedPressure * Config.mass * influence * direction[1];
        }
        return pressureForce;
    }

    public static double densityToPressure(double density){
        return Config.pressureMultiplier * (density - Config.targetDensity);
    }
}