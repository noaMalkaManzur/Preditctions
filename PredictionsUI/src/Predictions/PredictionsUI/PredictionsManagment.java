package Predictions.PredictionsUI;

import Defenitions.EnvPropertyDefinitionDTO;
import Defenitions.EnvironmentDefinitionDTO;
import definition.property.api.PropertyType;
import definition.world.api.WorldDefinition;
import definition.world.impl.WorldImpl;
import engine.api.*;
import engine.impl.*;
import exceptions.BadFileSuffixException;

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
            Object userValue;
            boolean isValidInput = false;
            while(!isValidInput) {
                String userInput = scanner.nextLine();
                if (!userInput.isEmpty()) {
                    switch (envDef.getType()) {
                        //ToDo: Implement validation funcs
                        case DECIMAL: {
                            isValidInput = engine.isValidIntegerVar(userInput, envDef.getRange());
                            if(isValidInput) {
                                userValue = PropertyType.DECIMAL.convert(userInput);
                            }
                        }
                        case FLOAT:
                            isValidInput = engine.isValidDoubleVar(userInput, envDef.getRange());
                            if(isValidInput) {
                                userValue = PropertyType.FLOAT.convert(userInput);
                            }
                        case BOOLEAN:
                            isValidInput = engine.isValidBooleanVar(userInput);
                            if(isValidInput) {
                                userValue = PropertyType.BOOLEAN.convert(userInput);
                            }
                        case STRING:
                            isValidInput = engine.isValidStringVar(userInput);
                            if(isValidInput) {
                                userValue = PropertyType.STRING.convert(userInput);
                            }
                        default:
                            userValue = null;
                    }
                    engine.addEnvVarToActiveEnv(userValue,envDef.getName());

                }
                else {

                }
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
}
