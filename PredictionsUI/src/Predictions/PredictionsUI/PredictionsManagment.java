package Predictions.PredictionsUI;

import Defenitions.*;
import definition.property.api.PropertyType;
import definition.world.api.WorldDefinition;
import definition.world.impl.WorldImpl;
import engine.api.Engine;
import engine.impl.EngineImpl;
import simulationInfo.SimulationInfoDTO;

import java.util.Scanner;

public class PredictionsManagment
{
    int userChoice;
    WorldDefinition myWorld = new WorldImpl();
    Engine engine = new EngineImpl();
    private static Scanner scanner = new Scanner(System.in);
    public void run()
    {
        //ToDo: Implement user input method.
        String fileName = "ex1-cigarets.xml";
        System.out.println("Hello There! Welcome to our Predictions simulation System!\n");
        System.out.println("Please choose command from the list!");
        printMenu();
        try {
            userChoice = 1;
            switch (userChoice)
            {
                case 1:
                    engine.loadXmlFiles(fileName);
                    EnvironmentDefinitionDTO myEnvDef =  engine.getEnvDTO();
                    getUserEnvValues(myEnvDef);
                    break;
                case 2:
                    SimulationInfoDTO simulationInfoDTO = new SimulationInfoDTO(engine.getEntityDTO(),engine.getRulesDTO(),engine.getTerminationDTO());
                    printSimulation(simulationInfoDTO);
                    break;
                case 3:
                    //EnvironmentDefinitionDTO myEnvDef =  engine.getEnvDTO();


            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }



    private void getUserEnvValues(EnvironmentDefinitionDTO myEnvDef) {
        myEnvDef.getEnvProps().values().forEach(envDef -> {
            System.out.println("Please insert Environment variable value:");
            Object userValue = null;
            boolean isValidInput = false;
            while(!isValidInput) {
                String userInput = scanner.nextLine();
                if (!userInput.isEmpty()) {
                    switch (envDef.getType()) {
                        //ToDo: Implement validation funcs
                        case DECIMAL: {
                            isValidInput = engine.isValidIntegerVar(userInput, envDef.getRange());
                            if(isValidInput) {
                                userValue = PropertyType.DECIMAL.parse(userInput);
                            }
                        }
                        case FLOAT:
                            isValidInput = engine.isValidDoubleVar(userInput, envDef.getRange());
                            if(isValidInput) {
                                userValue = PropertyType.FLOAT.parse(userInput);
                            }
                        case BOOLEAN:
                            isValidInput = engine.isValidBooleanVar(userInput);
                            if(isValidInput) {
                                userValue = PropertyType.BOOLEAN.parse(userInput);
                            }
                        case STRING:
                            isValidInput = engine.isValidStringVar(userInput);
                            if(isValidInput) {
                                userValue = PropertyType.STRING.convert(userInput);
                            }
                        default:
                            userValue = null;
                    }
                }
                engine.addEnvVarToActiveEnv(userValue,envDef.getName());
                isValidInput = true;


            }
        });
    }

    public void printMenu()
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
            simulationInfo.append("Rule name:").append(ruleDTO.getName()).append("\n");
            simulationInfo.append("Is applied by ticks: ").
                    append(ruleDTO.getActivation().getTicks()).append("and by: ").
                    append(ruleDTO.getActivation().getProbabilty()).append("\n");
            simulationInfo.append("Amount of action: ").append(ruleDTO.getActions().size()).append("\n");
            for(ActionDTO actionDTO: ruleDTO.getActions()){
                simulationInfo.append("Action type names:").append(actionDTO.getType());
            }
        }
    }

    void gettingEntitiesInfo(SimulationInfoDTO simulationInfoDTO,StringBuilder simulationInfo ) {
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
}
