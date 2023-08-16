package Predictions.PredictionsUI;


public class Main {

    /*public static void main(String[] args) {

        // definition phase - happens as part of file read and validity checks
        PropertyDefinition lungCancerProgress = new IntegerPropertyDefinition("lung-cancer-progress", ValueGeneratorFactory.createFixed(0),new Range(0,100));
        IntegerPropertyDefinition agePropertyDefinition = new IntegerPropertyDefinition("age", ValueGeneratorFactory.createRandomInteger(15, 50),new Range(15,50));
        PropertyDefinition cigaretsPerMonthPropertyDefinition = new IntegerPropertyDefinition("cigarets-per-month", ValueGeneratorFactory.createRandomInteger(0,500),new Range(0,500));


        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 5);
        smokerEntityDefinition.getProps().add(agePropertyDefinition);
        smokerEntityDefinition.getProps().add(cigaretsPerMonthPropertyDefinition);
        smokerEntityDefinition.getProps().add(lungCancerProgress);


//        List<Expression> list = new ArrayList<>();
//        list.add(new RandomFunction("8"));
//        list.add(new RandomFunction("9"));
//
        Rule rule1 = new RuleImpl("rule 1");
//        rule1.addAction(new MultiplyAction(ActionType.DECREASE,smokerEntityDefinition, list, lungCancerProgress.getName()));



        EnvVariablesManager envVariablesManager = new EnvVariableManagerImpl();
        IntegerPropertyDefinition cigaretsIncreaseNonSmokerEnvironmentVariablePropertyDefinition = new IntegerPropertyDefinition("cigarets-increase-non-smoker", ValueGeneratorFactory.createRandomInteger(0, 10),new Range(0,10));
        IntegerPropertyDefinition cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition = new IntegerPropertyDefinition("cigarets-increase-already-smoker", ValueGeneratorFactory.createRandomInteger(10, 100),new Range(10,100));

        envVariablesManager.addEnvironmentVariable(cigaretsIncreaseNonSmokerEnvironmentVariablePropertyDefinition);
        envVariablesManager.addEnvironmentVariable(cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition);



        // execution phase - happens upon command 3

        // initialization phase

        // creating entity instance manager
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();

        // create 3 instance of the smokerEntityDefinition smoker
        for (int i = 0; i < smokerEntityDefinition.getPopulation(); i++) {
            entityInstanceManager.create(smokerEntityDefinition);
        }

        // create env variable instance
        ActiveEnvironment activeEnvironment = envVariablesManager.createActiveEnvironment();
        // all available environment variable with their definition
        // for (PropertyDefinition propertyDefinition : envVariablesManager.getEnvVariables()) {

        // collect value from user...
        int valueFromUser = 54;
        int anotherValue = 10;
        activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition, valueFromUser));
        activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(cigaretsIncreaseNonSmokerEnvironmentVariablePropertyDefinition, anotherValue));

        // }


        //list.add(new EnvironmentFunction("10",activeEnvironment));
        List<Expression> list3 = new ArrayList<>();
        list3.add(new EnvironmentFunction(cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition.getName(),activeEnvironment));
        list3.add(new RandomFunction("5"));
//        rule1.addAction(new MultiplyAction(ActionType.DECREASE, smokerEntityDefinition, list3, cigaretsPerMonthPropertyDefinition.getName()));
//        // all env variable not inserted by user, needs to be generated randomly. lucky we have all data needed for it...
//        //Integer randomEnvVariableValue = taxAmountEnvironmentVariablePropertyDefinition.generateValue();
//        //activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(taxAmountEnvironmentVariablePropertyDefinition, randomEnvVariableValue));
//
//        //checking condition:
//        System.out.println();
        List<Expression> list4 = new ArrayList<>();
        list4.add(new EnvironmentFunction(cigaretsIncreaseNonSmokerEnvironmentVariablePropertyDefinition.getName(),activeEnvironment));
        //list4.add(new GeneralExpression("40",PropertyType.DECIMAL));
        List<Action> actionList = new ArrayList<>();
        actionList.add(new IncreaseAction(ActionType.DECREASE, smokerEntityDefinition, list3, lungCancerProgress.getName()));
        actionList.add(new DecreaseAction(ActionType.DECREASE, smokerEntityDefinition, list3, lungCancerProgress.getName()));
        List<ConditionAction> conditionList = new ArrayList<>();

        conditionList.add(new SingleAction(ActionType.CONDITION, smokerEntityDefinition, list4, "age", actionList, "bt"));
        conditionList.add(new SingleAction(ActionType.CONDITION, smokerEntityDefinition, list4, cigaretsPerMonthPropertyDefinition.getName(), actionList, "bt"));
        rule1.addAction(new MultipleAction(ActionType.CONDITION,smokerEntityDefinition,list4,"and", lungCancerProgress.getName(),actionList,conditionList));
//        actionList.add(new IncreaseAction(ActionType.DECREASE, smokerEntityDefinition, list3, lungCancerProgress.getName()));
//        rule1.addAction(new SingleAction(ActionType.CONDITION, smokerEntityDefinition, list4, "age", actionList, "bt"));




        //List<ConditionAction> conditionActionList = new ArrayList<>();
        //ConditionAction conditionAction1 = new SingleAction(ActionType.CONDITION, smokerEntityDefinition, list4, "single", actionList, "Bt");



        // during a tick...

// given an instance...
        // create a context (per instance)
        Context context = new ContextImpl(entityInstance, entityInstanceManager, activeEnvironment);
        List<Expression> list2 = new ArrayList<>();
        list2.add(new GeneralExpression("2",PropertyType.DECIMAL));
        list2.add(new RandomFunction("9"));
        rule1.addAction(new MultiplyAction(ActionType.DECREASE, smokerEntityDefinition, list2, agePropertyDefinition.getName()));
        System.out.println(context.getPrimaryEntityInstance().getPropertyByName(lungCancerProgress.getName()).getValue().toString());
        System.out.println(context.getPrimaryEntityInstance().getPropertyByName(agePropertyDefinition.getName()).getValue().toString());
        System.out.println(context.getPrimaryEntityInstance().getPropertyByName(cigaretsPerMonthPropertyDefinition.getName()).getValue().toString());

         if (rule1.getActivation().isActive(1)) {
             rule1
                     .getActionsToPerform()
                     .forEach(action ->
                             action.invoke(context));
         }
        System.out.println("\n\n");

        System.out.println(context.getPrimaryEntityInstance().getPropertyByName(lungCancerProgress.getName()).getValue().toString());
        System.out.println(context.getPrimaryEntityInstance().getPropertyByName(agePropertyDefinition.getName()).getValue().toString());
        System.out.println(context.getPrimaryEntityInstance().getPropertyByName(cigaretsPerMonthPropertyDefinition.getName()).getValue().toString());

    }*/

    public static void main(String[] args)
    {
        PredictionsManagment predictionsManagment = new PredictionsManagment();
        predictionsManagment.run();
    }

}