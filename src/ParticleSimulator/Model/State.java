package ParticleSimulator.Model;

public class State {
    private String data;

    public State() { }
    public State(String serializedData) {
        data = serializedData;
    }

    public String serialize(Particle particle) {
        return particle.getPosition().getX() + ";" + particle.getPosition().getY() + ";" +
                particle.getSpeed().getX() + ";" + particle.getSpeed().getY() + ";" +
                particle.getRadius();
    }

    public Particle deserialize() {
        /*
        This array should look like this:
        0 - 1: position vector;
        2 - 3: velocity vector
        4: radius double value
         */
        String[] dataArray = data.split(";");
        Vector2D position = new Vector2D(
                Double.parseDouble(dataArray[0]),
                Double.parseDouble(dataArray[1])
        );

        Vector2D speed = new Vector2D(
                Double.parseDouble(dataArray[2]),
                Double.parseDouble(dataArray[3])
        );

        double radius = Double.parseDouble(dataArray[4]);

        return new Particle(position, radius, speed);
    }

    public void setState(String state) {
        data = state;
    }
}
