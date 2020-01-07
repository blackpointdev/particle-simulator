package ParticleSimulator.Window;

import ParticleSimulator.Model.SimulationState;
import ParticleSimulator.Service.SimulationEngine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Canvas particleBoard;

    private GraphicsContext gc;
    private SimulationEngine simulationEngine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Basic config
        this.gc = particleBoard.getGraphicsContext2D();
        this.gc.setStroke(Color.rgb(115, 140, 217));

        // Setting simulation engine
        simulationEngine = new SimulationEngine(gc);
    }

    public void startSimulationClicked(MouseEvent mouseEvent) {
        // Check if simulation isn't already running, we don't want to create more than one thread
        if (simulationEngine.getSimulationState() == SimulationState.RUNNING) return;
        simulationEngine.generateParticles(10);
        simulationEngine.startSimulation();
    }

    public void stopSimulationClicked(MouseEvent mouseEvent) {
        simulationEngine.stopSimulation();
    }

    public void saveSimulationClicked(MouseEvent mouseEvent) { simulationEngine.saveSimulation(); }

    public void loadSimulationClicked(MouseEvent mouseEvent) { simulationEngine.loadSimulation(); }
}
