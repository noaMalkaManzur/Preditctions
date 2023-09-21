package simulation.api;

import Defenitions.ProgressSimulationDTO;
import Defenitions.RerunInfoDTO;
import Instance.EntityPopGraphDTO;
import execution.context.Context;
import execution.instance.environment.api.ActiveEnvironment;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;

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

    RerunInfoDTO getReRunInfoDTO();
    HistoryRunningSimulationDTO createHistoryRunningSimulationDTO();
    HistogramByPropertyEntitiesDTO setHistogramPerProperty(String guid, String propName);


}
