package engine.api;

import Defenitions.EntityDefinitionDTO;
import Defenitions.EnvironmentDefinitionDTO;
import Defenitions.RulesDTO;
import Defenitions.TerminitionDTO;
import Instance.ActiveEnvDTO;
import definition.property.api.PropertyDefinition;
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
    Map<String, RulesDTO> getRulesDTO();
    TerminitionDTO getTerminationDTO();
    Map<String, EntityDefinitionDTO> getEntitiesDTO();
    public SimulationInfoDTO getSimulationInfo();
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

    PropertyDefinition propertyFromScratch(PropertyDefinition propertyDefinition);
    //endregion
}
