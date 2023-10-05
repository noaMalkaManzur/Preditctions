package engine.api;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import Instance.EntityPopGraphDTO;
import ThreadManager.ThreadManager;
import definition.world.api.WorldDefinition;
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
    Map<String, EntityDefinitionDTO> getEntitiesDTO(WorldDefinition world);
    Map<String, SimulationManager> getSimulationInfo();
    Map<String, WorldDefinitionDTO> getWorldsDefinitionDTO();
    //endregion
    //region Command 3
    EnvironmentDefinitionDTO getEnvDTO(WorldDefinition world);

    void addEnvVarToActiveEnv(Object userValue, String name, WorldDefinition world);

    ActiveEnvDTO ShowUserEnvVariables();

    public void initRandomEnvVars(String name,WorldDefinition world);

    void clearActiveEnv();
    //endregion
    //region Command 4

    String runSimulation(WorldDefinition world);
    GridDTO getGridDTO( WorldDefinition world);

    Integer getMaxPop(WorldDefinition world);

    void setEntPop(String selectedItem, Integer value, WorldDefinition world);

    boolean checkPopulation(Integer intValue,String entName, WorldDefinition world);

    Integer getSpaceLeft(String selectedItem, WorldDefinition world);

    void initEnvVar(Object userInput,String selectedEnv, WorldDefinition world);

    ValidationEngine getValidation();

    ThreadManager getThreadManager();
    ProgressSimulationDTO getProgressDTO();
    List<simulationViewDTO> getSimulationsView();

    void resetSimVars(WorldDefinition world);

    RerunInfoDTO getReRunInfo(String selectedGuid);

    EntityPopGraphDTO getGraphDTO(String selectedGuid);

    HistogramByPropertyEntitiesDTO getHistogramByProp(String entName, String prop, String selectedGuid);

    StatisticsDTO getStatitsticDTO(String parent, String selectedItem, String selectedGuid);

    String getTerminationReason(String selectedGuid);


    //endregion
}
