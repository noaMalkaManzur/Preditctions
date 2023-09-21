package Defenitions;

import simulation.api.SimulationState;

import java.time.Instant;

public class simulationViewDTO {
    private final String guid;
    private  SimulationState state;
    private final Instant time;

    public simulationViewDTO(String guid, SimulationState state, Instant time) {
        this.guid = guid;
        this.state = state;
        this.time = time;
    }

    public String getGuid() {
        return guid;
    }

    public SimulationState getState() {
        return state;
    }

    public Instant getTime() {
        return time;
    }

    public void setState(SimulationState state) {
        this.state = state;
    }
}
