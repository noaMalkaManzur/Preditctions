package userManager.servlet.Api;

public class ParametersToSimulation {
    private final String simulationName;
    private final int numSimulationToRun;

    public ParametersToSimulation(String simulationName, int numSimulationToRun) {
        this.simulationName = simulationName;
        this.numSimulationToRun = numSimulationToRun;
    }
}
