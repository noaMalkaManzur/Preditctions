package engine.api;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import Instance.EntityPopGraphDTO;
import ThreadManager.ThreadManager;
import engine.Validaton.api.ValidationEngine;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import simulation.api.SimulationManager;

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

    String runSimulation();
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

    RerunInfoDTO getReRunInfo(String selectedGuid);

    EntityPopGraphDTO getGraphDTO(String selectedGuid);

    HistogramByPropertyEntitiesDTO getHistogramByProp(String entName, String prop, String selectedGuid);

    StatisticsDTO getStatitsticDTO(String parent, String selectedItem, String selectedGuid);

    String getTerminationReason(String selectedGuid);


    //endregion
}
