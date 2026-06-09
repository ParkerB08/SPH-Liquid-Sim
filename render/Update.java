package render;

import java.util.ArrayList;

import calculations.Mapping;
import calculations.Physics;

import core.Config;
import core.Particle;

public class Update {
    
    public static void update(ArrayList<Particle> particles){

        // updates
        Mapping.updateGrid(particles);
        for (Particle p : particles) {
            Mapping.neighborhoodSearch(p.pos[0], p.pos[1], p.neighbors); 
        }
        updateDensities(particles);
        updatePressures(particles);
        
        double dt = Config.timeStep;

        for (Particle p : particles) {
            
            // forces
            double[] pressure = Physics.calculatePressure(p.pos[0], p.pos[1], p.pressure, p.density, p.neighbors);
            double[] viscosity = Physics.calculateViscosity(p.pos[0], p.pos[1], p.vel, p.neighbors);

            for (int i = 0; i < viscosity.length; i++){
                viscosity[i] *= Config.viscosityCoeffiecient;
            }
            
            p.vel[0] += (-pressure[0] + viscosity[0])* dt;
            p.vel[1] += (-pressure[1] + viscosity[1] + Config.grav) * dt;

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

    public static void updateDensities(ArrayList<Particle> particles){
        for (Particle p : particles) {
            p.density = Physics.calculateDensity(p.pos[0], p.pos[1], p.neighbors);
        }
    }

    public static void updatePressures(ArrayList<Particle> particles){
        for (Particle p : particles) {
            p.pressure = Physics.densityToPressure(p.density);
        }
    }
}
