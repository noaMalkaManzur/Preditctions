package engine.api;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import ThreadManager.ThreadManager;
import engine.Validaton.api.ValidationEngine;
import histogramDTO.HistogramByAmountEntitiesDTO;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;
import simulation.api.SimulationManager;
import simulationInfo.SimulationInfoDTO;

import java.util.List;
import java.util.Map;

public interface Engine
{
    //region Command 1
    void loadXmlFiles(String fileName);
    //endregion
    //region Command 2
    RulesDTO getRulesDTO();
    TerminationDTO getTerminationDTO();
    Map<String, EntityDefinitionDTO> getEntitiesDTO();
    Map<String, SimulationManager> getSimulationInfo();
    WorldDefinitionDTO getWorldDefinitionDTO();
    //endregion
    //region Command 3
    EnvironmentDefinitionDTO getEnvDTO();

    void addEnvVarToActiveEnv(Object userValue, String name);

    ActiveEnvDTO ShowUserEnvVariables();

    public void initRandomEnvVars(String name);

    void clearActiveEnv();
    //endregion
    //region Command 4
    HistoryRunningSimulationDTO createHistoryRunningSimulationDTO();
  
    HistogramByAmountEntitiesDTO createHistogramByAmountEntitiesDTO(String guid,String name);

    String runSimulation();

    HistogramByPropertyEntitiesDTO setHistogramPerProperty(String guid, String propName);

    GridDTO getGridDTO();

    Integer getMaxPop();

    void setEntPop(String selectedItem, Integer value);

    boolean checkPopulation(Integer intValue,String entName);

    Integer getSpaceLeft(String selectedItem);

    void initEnvVar(Object userInput,String selectedEnv);

    ValidationEngine getValidation();

    ThreadManager getThreadManager();
    ProgressSimulationDTO getProgressDTO();
    List<simulationViewDTO> getSimulationsView();

    void resetSimVars();

    //endregion
}
