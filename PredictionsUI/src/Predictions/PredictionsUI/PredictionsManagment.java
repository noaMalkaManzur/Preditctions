package Predictions.PredictionsUI;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import engine.api.Engine;
import engine.impl.EngineImpl;
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
    Scanner scanner = new Scanner(System.in);

    public void run() {
        //ToDo: Implement user input method.
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
                            System.out.println("Successfully loaded file: " + fileName);
                            break;
                        }
                    case 2:
                        //loadSimulationDetails(fileName);
                        SimulationInfoDTO simulationInfoDTO = engine.getSimulationInfo();
                        printSimulation(simulationInfoDTO);
                        break;
                    case 3:
                        System.out.println(ExecuteSimulation() + "\n");
                        break;
                    case 4:
                        showHistogram();
                        break;
                    case 5:
                        System.out.println("Thank you for being with us!");
                        Exit = true;
                        break;


                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
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
    private String getFileNameFromUser()
    {
        System.out.println("Hello please insert full file path:");
        scanner.nextLine(); // Empty the buffer.
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

        simulationInfo.append("The first term of use is by seconds: ").append(simulationInfoDTO.getTerms().getBySeconds()).append("\n");
        simulationInfo.append("The second term of use is by ticks: ").append(simulationInfoDTO.getTerms().getByTicks()).append("\n");
    }

    private void gettingRulesInfo(SimulationInfoDTO simulationInfoDTO, StringBuilder simulationInfo) {
        for (RulesDTO ruleDTO : simulationInfoDTO.getRules().values()) {
            simulationInfo.append("Rules:").append("\n");
            simulationInfo.append("Rule name: ").append(ruleDTO.getName()).append("\n");
            simulationInfo.append("Is applied by ticks: ").
                    append(ruleDTO.getActivation().getTicks()).append(" and by: ").
                    append(ruleDTO.getActivation().getProbabilty()).append("\n");
            simulationInfo.append("Amount of action for this role: ").append(ruleDTO.getActions().size()).append("\n");
            for (ActionDTO actionDTO : ruleDTO.getActions()) {
                simulationInfo.append("Action type names: ").append(actionDTO.getType()).append("\n");
            }
        }
    }

    private void gettingEntitiesInfo(SimulationInfoDTO simulationInfoDTO, StringBuilder simulationInfo) {
        for (EntityDefinitionDTO entityDefDTO : simulationInfoDTO.getEntities().values()) {
            simulationInfo.append("Entity name: ").append(entityDefDTO.getName()).append("\n");
            simulationInfo.append("Population: ").append(entityDefDTO.getPopulation()).append("\n");
            simulationInfo.append("Properties: ").append("\n");

            for (EntityPropDefinitionDTO propertyDefDTO : entityDefDTO.getPropertyDefinition().values()) {
                simulationInfo.append("Property name: ").append(propertyDefDTO.getName()).append("\n");
                simulationInfo.append("Property type: ").append(propertyDefDTO.getType()).append("\n");


                if (propertyDefDTO.getRange() != null) {
                    simulationInfo.append("Property range from: ")
                            .append(propertyDefDTO.getRange().getRangeFrom())
                            .append(" to: ")
                            .append(propertyDefDTO.getRange().getRangeTo())
                            .append("\n");
                }
                if (propertyDefDTO.isRandomInit()) {
                    simulationInfo.append("Property is initialized randomly\n");
                } else {
                    simulationInfo.append("Property is not initialized randomly\n");
                }

            }
        }
    }

    //endregion
    //region Command 3
    private void printEnvVarInfo(EnvPropertyDefinitionDTO envDefDTO)
    {
        StringBuilder envString = new StringBuilder();
        envString.append("Please insert value for the next environment variable, " +
                        "if you don't want to insert a value just press enter and we will make a random value for you").append(System.lineSeparator())
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
        getUserEnvValues(myEnvDef);
        return engine.runSimulation();
    }

    private void getUserEnvValues(EnvironmentDefinitionDTO myEnvDef) {
        scanner.nextLine();
        myEnvDef.getEnvProps().values().forEach(envDef -> {
            printEnvVarInfo(envDef);
            Object userValue = null;
            boolean isValidInput = false;
            while (!isValidInput) {
                String userInput = scanner.nextLine();
                if (!userInput.isEmpty()) {
                    switch (envDef.getType()) {
                        case DECIMAL: {
                            isValidInput = engine.isValidIntegerVar(userInput, envDef.getRange());
                            if (isValidInput) {
                                userValue = PropertyType.DECIMAL.parse(userInput);
                            }
                            break;
                        }
                        case FLOAT:
                            isValidInput = engine.isValidDoubleVar(userInput, envDef.getRange());
                            if (isValidInput) {
                                userValue = PropertyType.FLOAT.parse(userInput);
                            }
                            break;
                        case BOOLEAN:
                            isValidInput = engine.isValidBooleanVar(userInput);
                            if (isValidInput) {
                                userValue = PropertyType.BOOLEAN.parse(userInput);
                            }
                            break;
                        case STRING:
                            isValidInput = engine.isValidStringVar(userInput);
                            if (isValidInput) {
                                userValue = PropertyType.STRING.convert(userInput);
                            }
                            break;
                        default:
                            break;
                    }

                } else {
                    isValidInput = true;
                }
                if (isValidInput)
                    engine.addEnvVarToActiveEnv(userValue, envDef.getName());
                else {
                    System.out.println("Please insert a valid input\n");
                }
            }
        });
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
        for(String name : entityDefinitionDTO.keySet())
        {
            entities.append(i).append(")").append(name).append(System.lineSeparator());
            EntityNameMap.put(i,name);
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
        properties.forEach((propValue, counetrValue)->{
            System.out.println("Property Value: " + propValue +" counter: " + counetrValue);
        });
    }
}

