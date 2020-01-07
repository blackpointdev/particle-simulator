package ParticleSimulator.Model;

import static ParticleSimulator.Model.Vector2D.add;

public class Particle {
    Vector2D position;
    Vector2D speed;
    double radius;

    public Particle(Vector2D position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    public Particle(Vector2D position, double radius, Vector2D speed) {
        this.position = position;
        this.speed = speed;
        this.radius = radius;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D speed) {
        this.speed = speed;
    }

    public double getRadius() {
        return radius;
    }

    public void updatePosition() {
        position = add(position, speed);
    }

    public State saveState() {
        State state = new State();
        String serializedData = state.serialize(this);
        state.setState(serializedData);
        return state;
    }

    public void recoverState() {
        //TODO recoverState
    }
}
