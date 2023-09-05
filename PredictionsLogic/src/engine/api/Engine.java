package engine.api;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import histogramDTO.HistogramByAmountEntitiesDTO;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;
import simulationInfo.SimulationInfoDTO;

import java.util.Map;

public interface Engine
{
    //region Command 1
    void loadXmlFiles(String fileName);
    //endregion
    //region Command 2
    RulesDTO getRulesDTO();
    TerminitionDTO getTerminationDTO();
    Map<String, EntityDefinitionDTO> getEntitiesDTO();
    SimulationInfoDTO getSimulationInfo();
    WorldDefinitionDTO getWorldDefinitionDTO();
    //endregion
    //region Command 3
    EnvironmentDefinitionDTO getEnvDTO();

    void addEnvVarToActiveEnv(Object userValue, String name);

    ActiveEnvDTO ShowUserEnvVariables();

    String runSimulation();

    public void initRandomEnvVars(String name);

    void clearActiveEnv();
    //endregion
    //region Command 4
    HistoryRunningSimulationDTO createHistoryRunningSimulationDTO();
  
    HistogramByAmountEntitiesDTO createHistogramByAmountEntitiesDTO(String guid,String name);

    HistogramByPropertyEntitiesDTO setHistogramPerProperty(String guid, String propName);

    GridDTO getGridDTO();


    //endregion
}
