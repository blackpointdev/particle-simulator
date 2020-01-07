package ParticleSimulator.Service;

import ParticleSimulator.Model.Particle;
import ParticleSimulator.Model.State;

import java.util.ArrayList;

public class StateManager {
    private ArrayList<Particle> particles;
    private ArrayList<State> stateRepository;

    public StateManager() {
        stateRepository = new ArrayList<State>();
    }

    public StateManager(ArrayList<State> stateRepository) {
        this.stateRepository = stateRepository;
    }

    public void saveSimulation(ArrayList<Particle> particles) {
        for (Particle particle : particles) {
            stateRepository.add(particle.saveState());
        }
    }

    public ArrayList<Particle> loadSimulation() {
        // this.stateRepository = stateRepository;
        ArrayList<Particle> particles = new ArrayList<Particle>();
        for (State state : stateRepository) {
            particles.add(state.deserialize());
        }

        return particles;
    }

}
