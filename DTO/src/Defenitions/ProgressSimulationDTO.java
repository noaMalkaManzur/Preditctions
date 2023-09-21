package Defenitions;

import simulation.api.SimulationState;

import java.util.List;
import java.util.Map;

public class ProgressSimulationDTO {
    private final long seconds;
    private final Integer currTick;
    private List<EntPopDTO> entPopDTO;
    private SimulationState simulationState;

    public ProgressSimulationDTO(long bySeconds, Integer byTicks, List<EntPopDTO> entPopDTO,SimulationState state) {
        this.seconds = bySeconds;
        this.currTick = byTicks;
        this.entPopDTO = entPopDTO;
        this.simulationState = state;

    }
    public long getSeconds(){
        return seconds;
    }
    public Integer getCurrTick(){
        return currTick;
    }
    public List<EntPopDTO> getEntitiesUpdateData(){return entPopDTO;}

    public SimulationState getSimulationState() {
        return simulationState;
    }
}
