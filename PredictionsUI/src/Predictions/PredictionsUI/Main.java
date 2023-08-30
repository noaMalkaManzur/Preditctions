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
import expression.impl.TickFunction;
import rule.Activation;
import rule.ActivationImpl;
import rule.Rule;
import rule.RuleImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //properties Entity 1:

        IntegerPropertyDefinition e1= new IntegerPropertyDefinition("e1", PropertyType.DECIMAL, ValueGeneratorFactory.createRandomInteger(15, 50) , new Range(10, 100), false);
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
        expressionList.add(new TickFunction("p1"));
        expressionList.add(new EvaluateExpression(ent1.getName(), p1.getName()));
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

        int ticks = 0;
        boolean isTerminated = false;
        while (!isTerminated) {
            int finalTicks = ticks;
            context.getEntityManager().getInstances().forEach(entityInstance -> {
                context.setPrimaryInstacne(1);
                if (rule1.getActivation().isActive(30)) {
                    rule1.getActionsToPerform().forEach(action -> {
                        action.invoke(context, finalTicks);
                    });
                }
            });
            ticks++;
            context.setCurrTick(ticks);
            if (ticks==30) {
                isTerminated = true;
            }
        }
    }

   /* public static void main(String[] args)
    {
        PredictionsManagment predictionsManagment = new PredictionsManagment();
        predictionsManagment.run();
        //launch(PredictionsApp.class);
    }*/
}

