package ParticleSimulator.Service;

import ParticleSimulator.Model.Particle;
import ParticleSimulator.Model.SimulationState;
import ParticleSimulator.Model.State;
import ParticleSimulator.Model.Vector2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class SimulationEngine {
    private ArrayList<Particle> particles;
    private GraphicsContext gc;
    private Thread simulationThread;
    private SimulationState simulationState;
    private StateManager stateManager;

    public SimulationEngine(GraphicsContext gc) {
        this.particles = new ArrayList<Particle>();
        this.gc = gc;
        this.stateManager = new StateManager();
    }

    public SimulationEngine(ArrayList<Particle> particles) {
        this.stateManager = new StateManager();
        this.particles = particles;
    }

    public SimulationState getSimulationState() {
        return simulationState;
    }

    public void startSimulation() {
        Runnable simulationRunnable = this::simulationThreadMethod;
        simulationThread = new Thread(simulationRunnable);
        simulationState = SimulationState.RUNNING;
        simulationThread.start();
    }

    private void simulationThreadMethod() {
        // Simulation settings
        final int targetFPS = 60;
        final int delay = 1000000000 / targetFPS;

        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        int fps = 0;

        double x = 0;
        double y = 0;

        while(true) {
            long timestamp = System.nanoTime();
            long updateLength = timestamp - lastLoopTime;

            lastLoopTime = timestamp;
            double delta = updateLength / ((double)delay);

            lastFpsTime += updateLength;
            fps++;

            if(lastFpsTime >= 1000000000){
                System.out.println("(FPS: "+fps+")");
                System.out.println(simulationState);
                lastFpsTime = 0;
                fps = 0;
            }

            // Update and redraw (quite self-explanatory I guess) //
            update();
            redraw();

            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + delay) / 1000000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Game loop ended");
    }

    public void stopSimulation() {
        if (simulationState != SimulationState.RUNNING) return;
        simulationThread.interrupt();
        particles.clear();
        simulationState = SimulationState.STOPPED;
    }

    private void update() {
        for (Particle p : particles) {
            collisionCheck(p);
            p.updatePosition();
        }
    }

    private void redraw() {
        gc.clearRect(0, 0, 690, 660);
        for (Particle p : particles) {
            gc.strokeOval(
                    p.getPosition().getX(),
                    p.getPosition().getY(),
                    p.getRadius()*2, p.getRadius()*2);
        }
    }

    private void collisionCheck(Particle particle) {
        // Walls collision
        if ((particle.getPosition().getX() <= 0) || (particle.getPosition().getX() + 2 * particle.getRadius() > 690)) {
            particle.setSpeed(new Vector2D(
                    -particle.getSpeed().getX(),
                     particle.getSpeed().getY()));
            // UGLYYYY but idk how to do it
//            if (!(particle.getPosition().getX() + 2 * particle.getRadius() > 690)){
//                particle.setPosition(new Vector2D(
//                        1,
//                        particle.getPosition().getY()
//                ));
//            }
//            else {
//                particle.setPosition(new Vector2D(
//                        685 - 2 * particle.getRadius(),
//                        particle.getPosition().getY()
//                ));
//            }
            particle.updatePosition();

        }

        if ((particle.getPosition().getY() <= 0) || (particle.getPosition().getY() + 2 * particle.getRadius() > 660)) {
            particle.setSpeed(new Vector2D(
                    particle.getSpeed().getX(),
                    -particle.getSpeed().getY()));
            // UGLYYYY but idk how to do it
//            if(particle.getPosition().getY() <= 0) {
//                particle.setPosition(new Vector2D(
//                        particle.getPosition().getX(),
//                        1
//                ));
//            }
//            else {
//                particle.setPosition(new Vector2D(
//                        particle.getPosition().getX(),
//                        655 - 2 * particle.getRadius()
//                ));
//            }

            particle.updatePosition();
        }

        // Particles collision
        for (Particle p : particles) {
            if (p == particle) continue;
            Vector2D centerPos = new Vector2D(
                    particle.getPosition().getX() + particle.getRadius(),
                    particle.getPosition().getY() - particle.getRadius());
            Vector2D pCenterPos = new Vector2D(
                    p.getPosition().getX() + p.getRadius(),
                    p.getPosition().getY() - p.getRadius());

            double distance = Math.sqrt(
                    Math.pow((pCenterPos.getX() - centerPos.getX()), 2)
                            + Math.pow((pCenterPos.getY() - centerPos.getY()), 2));

            if (distance < p.getRadius() + particle.getRadius()) {
                Vector2D tmp = p.getSpeed();
                p.setSpeed(particle.getSpeed());
                particle.setSpeed(tmp);

                // Update position to prevent glitches
                particle.updatePosition();
                p.updatePosition();
            }

        }

    }

    public void generateParticles(int amount) {
        Random randEngine = new Random();
        for (int i = 0; i < amount; i++) {
            double x;
            double y;
            double radius;
            double velX;
            double velY;

            x = randEngine.nextInt(300) + 200;
            y = randEngine.nextInt(300) + 200;
            radius = randEngine.nextInt(10) + 10;
            velX = randEngine.nextInt(15) - 7;
            velY = randEngine.nextInt(15) - 7;

            Particle particle = new Particle(
                    new Vector2D(x, y),
                    radius,
                    new Vector2D(velX, velY));

            particles.add(particle);
        }
    }

    public void saveSimulation() {
        stateManager.saveSimulation(particles);
    }

    public void loadSimulation() {
        this.particles = stateManager.loadSimulation();
    }
}
