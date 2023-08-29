package Predictions.PredictionsUI;


import action.api.Action;
import action.impl.IncreaseAction;
import action.impl.ProximityAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.environment.impl.EnvVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.BooleanPropertyDefinition;
import definition.property.impl.IntegerPropertyDefinition;
import definition.value.generator.api.ValueGeneratorFactory;
import definition.world.impl.Coordinate;
import execution.context.Context;
import execution.context.ContextImpl;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.enitty.manager.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.environment.impl.ActiveEnvironmentImpl;
import execution.instance.property.PropertyInstanceImpl;
import expression.api.Expression;
import expression.impl.EnvironmentFunction;
import expression.impl.EvaluateExpression;
import expression.impl.PercentExpression;
import rule.Activation;
import rule.ActivationImpl;
import rule.Rule;
import rule.RuleImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //properties Entity 1:

        IntegerPropertyDefinition e1= new IntegerPropertyDefinition("e1", PropertyType.DECIMAL,ValueGeneratorFactory.createRandomInteger(15, 50) , new Range(10, 100), false);
        BooleanPropertyDefinition e2= new BooleanPropertyDefinition("e2", PropertyType.BOOLEAN,ValueGeneratorFactory.createRandomBoolean() ,false);

        EntityDefinition ent1= new EntityDefinitionImpl("ent-1", 10);
        PropertyDefinition p1 = new IntegerPropertyDefinition("p1", PropertyType.DECIMAL,ValueGeneratorFactory.createRandomInteger(0, 100), new Range(0, 100) , true);
        PropertyDefinition p3 = new BooleanPropertyDefinition("p3", PropertyType.BOOLEAN, ValueGeneratorFactory.createRandomBoolean(), true);
        ent1.getProps().put("p1",p1);
        ent1.getProps().put("p3", p3);
        //entity 2:
        PropertyDefinition p11 = new IntegerPropertyDefinition("p11", PropertyType.DECIMAL,ValueGeneratorFactory.createRandomInteger(0, 60), new Range(0, 100) , true);
        PropertyDefinition p22 = new BooleanPropertyDefinition("p22", PropertyType.BOOLEAN, ValueGeneratorFactory.createRandomBoolean(), false);
        EntityDefinition ent2= new EntityDefinitionImpl("ent-2", 5);
        ent2.getProps().put("p11", p11);
        ent2.getProps().put("p22", p22);
        //need to add the e1 to the environment manager
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new EvaluateExpression(p1.getName()));
        expressionList.add(new PercentExpression(new EvaluateExpression(p1.getName()),new EnvironmentFunction("e1")));
        Activation activation = new ActivationImpl(1, 0.9);
        Rule rule1 = new RuleImpl("rule 1", activation);
        List<Action> actionList = new ArrayList<>();
        actionList.add(new IncreaseAction(ent1,expressionList,p1.getName()));

        rule1.addAction(new ProximityAction(ent1,expressionList,actionList));

        EnvVariablesManager envVariablesManager = new EnvVariableManagerImpl();
        envVariablesManager.addEnvironmentVariable(e1);
        envVariablesManager.addEnvironmentVariable(e2);

        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        ActiveEnvironment activeEnvironment = new ActiveEnvironmentImpl();
        for (int i = 0; i < ent1.getPopulation(); i++) {
            entityInstanceManager.create(ent1);
            // create env variable instance

            activeEnvironment = envVariablesManager.createActiveEnvironment();
            int valueFromUser = 54;
            int anotherValue = 10;
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(e1, valueFromUser));
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(e2, anotherValue));
            }

        EntityInstance primaryEntityInstance = entityInstanceManager.getInstances().get(0);
        primaryEntityInstance.setCoordinate(new Coordinate(1,2));
        EntityInstance secoundaryEntityInstance = entityInstanceManager.getInstances().get(1);
        secoundaryEntityInstance.setCoordinate(new Coordinate(2,2));
        Context context = new ContextImpl(primaryEntityInstance,entityInstanceManager,activeEnvironment, secoundaryEntityInstance, 4, 4);

        if (rule1.getActivation().isActive(30)) {
            rule1
                    .getActionsToPerform()
                    .forEach(action ->
                            action.invoke(context, 1));
        }

    }


/*    public static void main(String[] args)
    {
        PredictionsManagment predictionsManagment = new PredictionsManagment();
        predictionsManagment.run();
        launch(PredictionsApp.class);
    }*/
}

/*IntegerPropertyDefinition e1 = new IntegerPropertyDefinition("cigarets-increase-non-smoker", ValueGeneratorFactory.createRandomInteger(0, 10),new Range(0,10));
        IntegerPropertyDefinition cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition = new IntegerPropertyDefinition("cigarets-increase-already-smoker", ValueGeneratorFactory.createRandomInteger(10, 100),new Range(10,100));



        PropertyDefinition lungCancerProgress = new IntegerPropertyDefinition("lung-cancer-progress", ValueGeneratorFactory.createFixed(0),new Range(0,100));
        IntegerPropertyDefinition agePropertyDefinition = new IntegerPropertyDefinition("age", ValueGeneratorFactory.createRandomInteger(15, 50),new Range(15,50));
        PropertyDefinition cigaretsPerMonthPropertyDefinition = new IntegerPropertyDefinition("cigarets-per-month", ValueGeneratorFactory.createRandomInteger(0,500),new Range(0,500));
        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 5);
        smokerEntityDefinition.getProps().add(agePropertyDefinition);
        smokerEntityDefinition.getProps().add(cigaretsPerMonthPropertyDefinition);
        smokerEntityDefinition.getProps().add(lungCancerProgress);
        List<Expression> list = new ArrayList<>();
        list.add(new RandomFunction("8"));
        list.add(new RandomFunction("9"));
        Rule rule1 = new RuleImpl("rule 1");
        rule1.addAction(new MultiplyAction(ActionType.DECREASE,smokerEntityDefinition, list, lungCancerProgress.getName()));
        EnvVariablesManager envVariablesManager = new EnvVariableManagerImpl();
        IntegerPropertyDefinition cigaretsIncreaseNonSmokerEnvironmentVariablePropertyDefinition = new IntegerPropertyDefinition("cigarets-increase-non-smoker", ValueGeneratorFactory.createRandomInteger(0, 10),new Range(0,10));
        IntegerPropertyDefinition cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition = new IntegerPropertyDefinition("cigarets-increase-already-smoker", ValueGeneratorFactory.createRandomInteger(10, 100),new Range(10,100));

        envVariablesManager.addEnvironmentVariable(cigaretsIncreaseNonSmokerEnvironmentVariablePropertyDefinition);
        envVariablesManager.addEnvironmentVariable(cigaretsIncreaseAlreadySmokerEnvironmentVariablePropertyDefinition);

        // creating entity instance manager
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();

        // create 3 instance of the smokerEntityDefinition smoker
        for (int i = 0; i < smokerEntityDefinition.getPopulation(); i++) {
            entityInstanceManager.create(smokerEntityDefinition);
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
            list2.add(new GeneralExpression(PropertyType.DECIMAL, "2"));
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
            System.out.println(context.getPrimaryEntityInstance().getPropertyByName(cigaretsPerMonthPropertyDefinition.getName()).getValue().toString());*/