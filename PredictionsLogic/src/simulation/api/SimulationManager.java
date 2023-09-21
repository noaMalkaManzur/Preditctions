package simulation.api;

import Defenitions.ProgressSimulationDTO;
import Instance.EntityPopGraphDTO;
import execution.context.Context;
import execution.instance.environment.api.ActiveEnvironment;

import java.time.Instant;

public interface SimulationManager extends Runnable {

    Context getContext();
    String getGuid();
    Instant getStartTime();
    Integer getCurrTick();
    long getCurrentSecond();
    Boolean getIsTerminated();
    String getSimulationEndReason();
    ActiveEnvironment getSimEnvironment();
    void setSimEnvironment(ActiveEnvironment simEnvironment);
    ProgressSimulationDTO getProgressDTO();
    EntityPopGraphDTO getGraphDTO();
    SimulationState getState();
    void setPause(Boolean pause);
    void setIsTerminated();


}
