package engine.api;

import Defenitions.EntityDefinitionDTO;
import Defenitions.EnvironmentDefinitionDTO;
import Defenitions.RulesDTO;
import Defenitions.TerminitionDTO;
import Instance.ActiveEnvDTO;
import definition.property.api.Range;
import exceptions.BadFileSuffixException;
import histogramDTO.HistogramByAmountEntitiesDTO;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;
import simulationInfo.SimulationInfoDTO;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Engine
{
    //region Command 1
    boolean isFileExist(String fileName) throws FileNotFoundException;
    boolean isXMLFile(String fileName) throws BadFileSuffixException;
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

    boolean isValidIntegerVar(String userInput, Range range);

    boolean isValidDoubleVar(String userInput, Range range);

    boolean isValidBooleanVar(String userInput);

    boolean isValidStringVar(String userInput);

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
    //endregion
}
