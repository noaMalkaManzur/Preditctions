package Predictions.PredictionsUI;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import definition.property.api.PropertyType;
import engine.api.Engine;
import engine.impl.EngineImpl;
import simulationInfo.SimulationInfoDTO;

import java.util.Scanner;

public class PredictionsManagment
{
    int userChoice;
    boolean Exit = false;
    Engine engine = new EngineImpl();
    Scanner scanner = new Scanner(System.in);
    public void run()
    {
        //ToDo: Implement user input method.
        String fileName = "ex1-cigarets.xml";
        System.out.println("Hello There! Welcome to our Predictions simulation System!\n");
        while(!Exit) {
            printMenu();
            try {
                userChoice = scanner.nextInt();
                switch (userChoice) {
                    case 1:
                        loadSimulationDetails(fileName);
                        System.out.println("Successfully loaded file:"+fileName);
                        break;
                    case 2:
                        loadSimulationDetails(fileName);
                        SimulationInfoDTO simulationInfoDTO = engine.getSimulationInfo();
                        printSimulation(simulationInfoDTO);
                        break;
                    case 3:
                        ExecuteSimulation();
                    case 4:
                        //Handle case 4
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
    private void printMenu()
    {
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
        for(RulesDTO ruleDTO:simulationInfoDTO.getRules().values()){
            simulationInfo.append("Rules:").append("\n");
            simulationInfo.append("Rule name: ").append(ruleDTO.getName()).append("\n");
            simulationInfo.append("Is applied by ticks: ").
                    append(ruleDTO.getActivation().getTicks()).append(" and by: ").
                    append(ruleDTO.getActivation().getProbabilty()).append("\n");
            simulationInfo.append("Amount of action for this role: ").append(ruleDTO.getActions().size()).append("\n");
            for(ActionDTO actionDTO: ruleDTO.getActions()){
                simulationInfo.append("Action type names: ").append(actionDTO.getType()).append("\n");
            }
        }
    }

    private void gettingEntitiesInfo(SimulationInfoDTO simulationInfoDTO,StringBuilder simulationInfo ) {
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
                .append("Type:").append(envDefDTO.getType()).append(System.lineSeparator())
                .append("Range:").append(envDefDTO.getRange().getRangeFrom()).append("-")
                .append(envDefDTO.getRange().getRangeTo()).append(System.lineSeparator());
        System.out.println(envString);
    }
    private void ExecuteSimulation() {
        EnvironmentDefinitionDTO myEnvDef = engine.getEnvDTO();
        getUserEnvValues(myEnvDef);
        engine.runSimulation();
    }
    private void getUserEnvValues(EnvironmentDefinitionDTO myEnvDef) {
        scanner.nextLine();
        myEnvDef.getEnvProps().values().forEach(envDef -> {
            printEnvVarInfo(envDef);
            Object userValue = null;
            boolean isValidInput = false;
            while(!isValidInput) {
                String userInput = scanner.nextLine();
                if (!userInput.isEmpty()) {
                    switch (envDef.getType()) {
                        case DECIMAL: {
                            isValidInput = engine.isValidIntegerVar(userInput, envDef.getRange());
                            if(isValidInput) {
                                userValue = PropertyType.DECIMAL.parse(userInput);
                            }
                            break;
                        }
                        case FLOAT:
                            isValidInput = engine.isValidDoubleVar(userInput, envDef.getRange());
                            if(isValidInput) {
                                userValue = PropertyType.FLOAT.parse(userInput);
                            }
                            break;
                        case BOOLEAN:
                            isValidInput = engine.isValidBooleanVar(userInput);
                            if(isValidInput) {
                                userValue = PropertyType.BOOLEAN.parse(userInput);
                            }
                            break;
                        case STRING:
                            isValidInput = engine.isValidStringVar(userInput);
                            if(isValidInput) {
                                userValue = PropertyType.STRING.convert(userInput);
                            }
                            break;
                        default:
                            break;
                    }

                }
                else
                {
                    isValidInput = true;
                }
                if(isValidInput)
                    engine.addEnvVarToActiveEnv(userValue,envDef.getName());
                else
                {
                    System.out.println("Please insert a valid input\n");
                }
            }
        });
        printActiveEnv(engine.ShowUserEnvVariables());
    }

    private void printActiveEnv(ActiveEnvDTO activeEnvDTO)
    {
        StringBuilder ActiveEnv = new StringBuilder();
        activeEnvDTO.getEnvPropInstances().forEach((key,value)->
                {
                    ActiveEnv.append("Name:").append(key).append("  ")
                            .append("Value:").append(value.getVal()).append(System.lineSeparator());
                });
        System.out.println(ActiveEnv);
    }
    //endregion

}
