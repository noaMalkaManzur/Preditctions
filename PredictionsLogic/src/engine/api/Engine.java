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
    boolean isFileExist(String fileName) throws FileNotFoundException;
    boolean isXMLFile(String fileName) throws BadFileSuffixException;
    void loadXmlFiles(String fileName);

    EnvironmentDefinitionDTO getEnvDTO();

    boolean isValidIntegerVar(String userInput, Range range);

    boolean isValidDoubleVar(String userInput, Range range);

    boolean isValidBooleanVar(String userInput);

    boolean isValidStringVar(String userInput);

    void addEnvVarToActiveEnv(Object userValue, String name);
    Map<String, RulesDTO> getRulesDTO();
    TerminitionDTO getTerminationDTO();
    Map<String, EntityDefinitionDTO> getEntityDTO();
    public SimulationInfoDTO getSimulationInfo();

    ActiveEnvDTO ShowUserEnvVariables();

    String runSimulation();
    HistoryRunningSimulationDTO createHistoryRunningSimulationDTO();
    HistogramByAmountEntitiesDTO createHistogramByAmountEntitiesDTO(String guid);
    HistogramByPropertyEntitiesDTO setHistogramPerProperty(String guid, String propName);
}
