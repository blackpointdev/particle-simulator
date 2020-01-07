package ParticleSimulator.Model;

public class Vector2D {
    private double x_pos;
    private double y_pos;

    public Vector2D(double x_pos, double y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public double getX() {
        return x_pos;
    }

    public void setX(double x_pos) {
        this.x_pos = x_pos;
    }

    public double getY() {
        return y_pos;
    }

    public void setY(double y_pos) {
        this.y_pos = y_pos;
    }

    public static Vector2D add (Vector2D vector1, Vector2D vector2) {
        return new Vector2D(vector1.getX() + vector2.getX(),
                            vector1.getY() + vector2.getY());
    }

    public static Vector2D multiply (Vector2D vector2D, double multiplier) {
        return new Vector2D(vector2D.getX() * multiplier,
                            vector2D.getY() * multiplier);
    }
}
