/*
package Predictions.PredictionsUI;

import Defenitions.*;
import Defenitions.Actions.api.ActionDTO;
import Instance.ActiveEnvDTO;
import definition.property.api.PropertyType;
import engine.Validaton.api.ValidationEngine;
import engine.Validaton.impl.ValidationEngineImpl;
import engine.api.Engine;
import engine.impl.EngineImpl;
import exceptions.NoFileWasLoadedException;
import exceptions.NoSimulationWasRanException;
import histogramDTO.HistogramByAmountEntitiesDTO;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;
import simulationInfo.SimulationInfoDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PredictionsManagment {
    int userChoice;
    boolean Exit = false;
    Engine engine = new EngineImpl();
    ValidationEngine validation = new ValidationEngineImpl();
    Scanner scanner = new Scanner(System.in);
    boolean LoadedXMLFile = false;
    boolean ranSimulation = false;

    public void run() {
        String fileName;
        System.out.println("Hello There! Welcome to our Predictions simulation System!\n");
        while (!Exit) {
            printMenu();
            try {
                userChoice = scanner.nextInt();
                switch (userChoice) {
                    case 1:
                        fileName = getFileNameFromUser();
                        if (fileName.trim().isEmpty()) {
                            System.out.println("File path is empty please give valid file name.");
                        } else {
                            loadSimulationDetails(fileName);
                            LoadedXMLFile = true;
                            System.out.println("Successfully loaded file: " + fileName);
                            break;
                        }
                    case 2:
                        if (LoadedXMLFile) {
                            //SimulationInfoDTO simulationInfoDTO = engine.getSimulationInfo();
                            //printSimulation(simulationInfoDTO);
                        } else {
                            throw new NoFileWasLoadedException("The system doesn't have any XML file to run on!");
                        }
                        break;
                    case 3:
                        if (LoadedXMLFile) {
                            System.out.println("The Simulation has ended.\nSimulation Guid: "+ ExecuteSimulation() + "\n");
                            ranSimulation = true;
                        } else {
                            throw new NoFileWasLoadedException("The system doesn't have any XML file to run on!");
                        }
                        break;
                    case 4:
                        if (ranSimulation) {
                            showHistogram();
                        } else {
                            throw new NoSimulationWasRanException("No simulation was ran by the user cant show Histogram!");
                        }
                        break;

                    case 5:
                        System.out.println("Thank you for being with us!");
                        Exit = true;
                        break;
                    default:
                        System.out.println("Invalid Input please insert a number between 1-5\n");
                }
            } catch (Exception ex) {
                if(ex.getMessage() == null)
                {
                    System.out.println("Invalid input");
                    scanner.nextLine();
                }
                else {
                    System.out.println(ex.getMessage());
                }
            }
        }
        System.exit(0);
    }

    //region Common functions
    private void printMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("Please choose one of the following operations:").append(System.lineSeparator())
                .append("1. Load simulation details from xml file.").append(System.lineSeparator())
                .append("2. Show simulation's Specification.").append(System.lineSeparator())
                .append("3. Start Simulation.").append(System.lineSeparator())
                .append("4. Show past simulations.").append(System.lineSeparator())
                .append("5. Exit.").append(System.lineSeparator());
        System.out.println(menu);
    }
    //endregion
    //region Utility
    private String getFileNameFromUser()
    {
        System.out.println("Hello please insert full file path:");
        scanner.nextLine();
        return scanner.nextLine();
    }
    //endregion
    //region Command 1
    private void loadSimulationDetails(String fileName) {
        engine.loadXmlFiles(fileName);
    }
    //endregion
    //region Command 2
    private void printSimulation(SimulationInfoDTO simulationInfoDTO) {
        StringBuilder simulationInfo = new StringBuilder();
        gettingEntitiesInfo(simulationInfoDTO, simulationInfo);
        gettingRulesInfo(simulationInfoDTO, simulationInfo);
        gettingTerminationInfo(simulationInfoDTO, simulationInfo);
        System.out.println(simulationInfo);
    }
    private void gettingTerminationInfo(SimulationInfoDTO simulationInfoDTO, StringBuilder simulationInfo) {
        simulationInfo.append("Termination Terms:").append(System.lineSeparator());
        simulationInfo.append("     Termination by seconds: ").append(simulationInfoDTO.getTerms().getBySeconds()).append(System.lineSeparator());
        simulationInfo.append("     Termination by ticks: ").append(simulationInfoDTO.getTerms().getByTicks()).append(System.lineSeparator());
    }
    private void gettingRulesInfo(SimulationInfoDTO simulationInfoDTO, StringBuilder simulationInfo) {
        simulationInfo.append("Rules:").append(System.lineSeparator());
        for (RuleDTO ruleDTO : simulationInfoDTO.getRules().values()) {
            simulationInfo.append("     Rule name: ").append(ruleDTO.getName()).append(System.lineSeparator());
            simulationInfo.append("         Is applied by ticks: ").
                    append(ruleDTO.getActivation().getTicks()).append(" and by probability of: ").
                    append(ruleDTO.getActivation().getProbability()).append(System.lineSeparator());
            simulationInfo.append("         Amount of action for this role: ").append(ruleDTO.getActions().size()).append(System.lineSeparator());
            for (ActionDTO actionDTO : ruleDTO.getActions()) {
                simulationInfo.append("         Action type names: ").append(actionDTO.getType()).append(System.lineSeparator());
            }
        }
        simulationInfo.append(System.lineSeparator());
    }
    private void gettingEntitiesInfo(SimulationInfoDTO simulationInfoDTO, StringBuilder simulationInfo) {
        for (EntityDefinitionDTO entityDefDTO : simulationInfoDTO.getEntities().values()) {
            simulationInfo.append("Entity name: ").append(entityDefDTO.getName()).append(System.lineSeparator());
            simulationInfo.append("Population: ").append(entityDefDTO.getPopulation()).append(System.lineSeparator());
            simulationInfo.append("Properties: ").append("\n");

            for (EntityPropDefinitionDTO propertyDefDTO : entityDefDTO.getPropertyDefinition().values()) {
                simulationInfo.append("     Property name: ").append(propertyDefDTO.getName()).append(System.lineSeparator());
                simulationInfo.append("     Property type: ").append(propertyDefDTO.getType()).append(System.lineSeparator());


                if (propertyDefDTO.getRange() != null) {
                    simulationInfo.append("     Property range from: ")
                            .append(propertyDefDTO.getRange().getRangeFrom())
                            .append(" to: ")
                            .append(propertyDefDTO.getRange().getRangeTo())
                            .append(System.lineSeparator());
                }
                if (propertyDefDTO.isRandomInit()) {
                    simulationInfo.append("     Property is initialized randomly").append(System.lineSeparator());
                } else {
                    simulationInfo.append("     Property is not initialized randomly").append(System.lineSeparator());
                }
                simulationInfo.append(System.lineSeparator());

            }
        }
    }
    //endregion
    //region Command 3
    private void printEnvVarInfo(EnvPropertyDefinitionDTO envDefDTO) {
        StringBuilder envString = new StringBuilder();
        envString.append("Please insert value for the next environment variable").append(System.lineSeparator())
                .append("Name:").append(envDefDTO.getName()).append(System.lineSeparator())
                .append("Type:").append(envDefDTO.getType()).append(System.lineSeparator());
        if (envDefDTO.getRange() != null) {
            envString.append("Range:").append(envDefDTO.getRange().getRangeFrom()).append("-")
                    .append(envDefDTO.getRange().getRangeTo()).append(System.lineSeparator());
        }
        System.out.println(envString);
    }

    private String ExecuteSimulation() {
        EnvironmentDefinitionDTO myEnvDef = engine.getEnvDTO();
        letUserProvideValues(myEnvDef);
        return engine.runSimulation();
    }

    private Map<Integer,String>  printEnvVars(EnvironmentDefinitionDTO myEnvDef)
    {
        int index =1;
        StringBuilder myEnvVars = new StringBuilder();
        Map<Integer,String> envVarByID = new HashMap<>();
        myEnvVars.append("Please choose environment variable to insert value").append(System.lineSeparator())
                .append("You can also choose to start the simulation and we will provide values for you.").append(System.lineSeparator());
        for(String name :myEnvDef.getEnvProps().keySet())
        {
            myEnvVars.append(index).append(")").append(name).append(System.lineSeparator());
            envVarByID.put(index,name);
            index++;
        }
        myEnvVars.append(index).append(")").append("Start Simulation").append(System.lineSeparator());
        System.out.println(myEnvVars);
        return envVarByID;
    }
    private void letUserProvideValues(EnvironmentDefinitionDTO myEnvDef)
    {
        boolean runLoop = true;
        boolean isValidInput = false;
        engine.clearActiveEnv();
        while(runLoop) {
            Map<Integer, String> envVarByID = printEnvVars(myEnvDef);
            Object userValue = null;
            try {
                int userChoice = scanner.nextInt();
                if (userChoice >= 1 && userChoice <= envVarByID.size() + 1) {
                    if (userChoice == envVarByID.size() + 1) {
                        runLoop = false;
                        break;
                    } else {
                        EnvPropertyDefinitionDTO myEnvVar = myEnvDef.getEnvProps().get(envVarByID.get(userChoice));
                        printEnvVarInfo(myEnvDef.getEnvProps().get(envVarByID.get(userChoice)));
                        scanner.nextLine();
                        String userInput = scanner.nextLine();
                        switch (myEnvVar.getType()) {
                            case DECIMAL: {
                                isValidInput = validation.isValidIntegerVar(userInput, myEnvVar.getRange());
                                if (isValidInput) {
                                    userValue = PropertyType.DECIMAL.parse(userInput);
                                } else {
                                    System.out.println("Invalid input for value");
                                }
                                break;
                            }
                            case FLOAT:
                                isValidInput =validation.isValidDoubleVar(userInput, myEnvVar.getRange());
                                if (isValidInput) {
                                    userValue = PropertyType.FLOAT.parse(userInput);
                                } else {
                                    System.out.println("Invalid input for value");
                                }
                                break;
                            case BOOLEAN:
                                isValidInput = validation.isValidBooleanVar(userInput);
                                if (isValidInput) {
                                    userValue = PropertyType.BOOLEAN.parse(userInput);
                                } else {
                                    System.out.println("Invalid input for value");
                                }
                                break;
                            case STRING:
                                isValidInput = validation.isValidStringVar(userInput);
                                if (isValidInput) {
                                    userValue = PropertyType.STRING.convert(userInput);
                                } else {
                                    System.out.println("Invalid input for value");
                                }
                                break;
                            default:
                                break;
                        }
                        if (isValidInput)
                            engine.addEnvVarToActiveEnv(userValue, myEnvVar.getName());
                    }
                } else {
                    System.out.println("Invalid input.please enter valid input\n");
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid Input\n");
                scanner.nextLine();
            }
        }
        for(String envName: myEnvDef.getEnvProps().keySet())
            engine.initRandomEnvVars(envName);
        printActiveEnv(engine.ShowUserEnvVariables());
    }
    private void printActiveEnv(ActiveEnvDTO activeEnvDTO) {
        StringBuilder ActiveEnv = new StringBuilder();
        activeEnvDTO.getEnvPropInstances().forEach((key, value) ->
        {
            ActiveEnv.append("Name:").append(key).append("  ")
                    .append("Value:").append(value.getVal()).append(System.lineSeparator());
        });
        System.out.println(ActiveEnv);
    }
    //endregion
    private void showHistogram() {
        chooseOptionForInfoSimulation();
    }
  String getSelectedSimulationGuid() {
        int counter = 0;
        StringBuilder historySimulation = new StringBuilder();
        HistoryRunningSimulationDTO historyRunningSimulationDTO = engine.createHistoryRunningSimulationDTO();

        for (Map.Entry<String, String> entry : historyRunningSimulationDTO.getHistoryRunningSimulation().entrySet()) {
            historySimulation.append(counter + 1 +") ").append("Simulation guid: ").append(entry.getKey()).append(" | ").append("Simulation date:").append(entry.getValue()).append("\n");
            counter++;
        }
        System.out.println(historySimulation);
        scanner.nextLine(); //Clear Buffer.
        while (true) {
            System.out.println("Please choose a simulation");
            String userInputForProperty = scanner.nextLine();
            try {
                int userChoice = Integer.parseInt(userInputForProperty);
                if (userChoice >= 1 && userChoice <= (counter)) {
                    int index = userChoice - 1;
                    int currentIndex = 0;
                    for (Map.Entry<String, String> entry : historyRunningSimulationDTO.getHistoryRunningSimulation().entrySet()) {
                        if (currentIndex == index) {
                            return entry.getKey();
                        }
                        currentIndex++;
                    }
                } else {
                    System.out.println("Invalid user choice for simulation. Please enter a valid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
            }
        }
    }
   void chooseOptionForInfoSimulation(){
        boolean runLoop = true;
        String guid = getSelectedSimulationGuid();
        System.out.println("Please choose which way to see past running simulation:\n" +
                "1.Amount entites before and after simulation\n" +
                "2.Histogram per propery");
        while (runLoop) {
            String userInputForProperty = scanner.nextLine();
            try {
                int userChoice = Integer.parseInt(userInputForProperty);
                if (userChoice >= 1 && userChoice <= 2) {

                    if(userChoice == 1){
                        handlePrintingByAmount(guid);
                        runLoop = false;
                    }
                    else{
                        handlePrintingByProperty(guid);
                        runLoop = false;
                    }
                } else {
                    System.out.println("Invalid user choice for simulation. Please enter a valid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
            }
        }

    }

   private void handlePrintingByAmount(String guid) {

        Map<String,EntityDefinitionDTO> entityDefinitionDTOS = engine.getEntitiesDTO();
        String chosenEntity = printEntitiesList(entityDefinitionDTOS);
        HistogramByAmountEntitiesDTO histogram = engine.createHistogramByAmountEntitiesDTO(guid,chosenEntity);
        StringBuilder printByAmount = new StringBuilder();
        printByAmount.append("Entity:").append(histogram.getName()).append(System.lineSeparator())
                .append("Amount of population before starting simulation: ").append(histogram.getPopulationBeforeSimulation())
                .append(System.lineSeparator()).append("Amount of population after simulation: ")
                .append(histogram.getPopulationAfterSimulation()).append(System.lineSeparator());
        System.out.println(printByAmount);
    }

    private void handlePrintingByProperty(String guid) {
        Map<String,EntityDefinitionDTO> entityDefinitionDTOS = engine.getEntitiesDTO();
        String chosenEntity = printEntitiesList(entityDefinitionDTOS);
        EntityDefinitionDTO entityDefinitionDTO = entityDefinitionDTOS.get(chosenEntity);
        int size = entityDefinitionDTO.getPropertyDefinition().size();
        StringBuilder propertyToUser = new StringBuilder();
        Map<Integer,String> PropNameMap = new HashMap<>();
        boolean runLoop = true;

        System.out.println("Please select a property Name:");
        int index = 1;
        for(Map.Entry<String, EntityPropDefinitionDTO> propDef: entityDefinitionDTO.getPropertyDefinition().entrySet())
        {
            propertyToUser.append(index).append(")").append(propDef.getValue().getName()).append(System.lineSeparator());
            PropNameMap.put(index,propDef.getValue().getName());
            index++;
        }

        System.out.println(propertyToUser);

        while (runLoop) {

            String userInputForProperty = scanner.nextLine();
            try {
                int userChoice = Integer.parseInt(userInputForProperty);
                if (userChoice >= 1 && userChoice <= size) {
                    String nameProp = PropNameMap.get(userChoice);
                    printProperty(guid, nameProp);
                    runLoop = false;
                } else {
                    System.out.println("Invalid user choice for property name. Please enter a valid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
            }
        }
    }
    private String printEntitiesList(Map<String, EntityDefinitionDTO> entityDefinitionDTO) {
        StringBuilder entities = new StringBuilder();
        Map<Integer,String> EntityNameMap = new HashMap<>();
        int i = 1;
        entities.append("Please choose entity to display").append(System.lineSeparator());
        for(String name : entityDefinitionDTO.keySet())
        {
            entities.append(i).append(")").append(name).append(System.lineSeparator());
            EntityNameMap.put(i,name);
            i++;
        }
        System.out.println(entities);
        while(true)
        {
            String userInputForProperty = scanner.nextLine();
            try {
                int userChoice = Integer.parseInt(userInputForProperty);
                if (userChoice >= 1 && userChoice <= entityDefinitionDTO.keySet().size()) {
                    return EntityNameMap.get(userChoice);
                } else {
                    System.out.println("Invalid user choice for Entity name. Please enter a valid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
            }
        }
    }
    private void printProperty(String guid, String nameProp) {

        HistogramByPropertyEntitiesDTO histogramByPropertyEntitiesDTO = engine.setHistogramPerProperty(guid, nameProp);
        Map<Object,Integer> properties =  histogramByPropertyEntitiesDTO.getHistogramByProperty();
        if(properties.size() == 0)
        {
            System.out.println("No instances to show all instances killed in the simulation");
            return;
        }
        properties.forEach((propValue, counetrValue)->{
            System.out.println("Property Value: " + propValue +" counter: " + counetrValue);
        });
    }
}

*/
