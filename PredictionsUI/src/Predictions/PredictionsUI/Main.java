package Predictions.PredictionsUI;


import action.api.ActionType;
import action.impl.IncreaseAction;
import action.impl.KillAction;
import action.impl.calculation.impl.MultiplyAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.environment.impl.EnvVariableManagerImpl;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.IntegerPropertyDefinition;
import definition.value.generator.api.ValueGeneratorFactory;
import execution.context.Context;
import execution.context.ContextImpl;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.enitty.manager.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.PropertyInstanceImpl;
import expression.api.eExpression;
import expression.impl.GeneralExpression;
import expression.impl.function.RandomFunction;
import rule.Rule;
import rule.RuleImpl;

public class Main {

    public static void main(String[] args) {

        // definition phase - happens as part of file read and validity checks
        IntegerPropertyDefinition agePropertyDefinition = new IntegerPropertyDefinition("age", ValueGeneratorFactory.createRandomInteger(10, 50),new Range(10,50));
        IntegerPropertyDefinition smokingInDayPropertyDefinition = new IntegerPropertyDefinition("smokingInDay", ValueGeneratorFactory.createFixed(10),null);

        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 100);
        smokerEntityDefinition.getProps().add(agePropertyDefinition);
        smokerEntityDefinition.getProps().add(smokingInDayPropertyDefinition);

        // define rules by creating instances of actions
        Rule rule1 = new RuleImpl("rule 1");
        rule1.addAction(new MultiplyAction(ActionType.CALCULATION,smokerEntityDefinition, "age", "1","6",new GeneralExpression(eExpression.GENERAL, PropertyType.DECIMAL),new RandomFunction("10")));
        RandomFunction expression = new RandomFunction("10");

        //rule1.addAction(new IncreaseAction(smokerEntityDefinition, "age", new RandomFunction("10").toString()));

        Object res = expression.calculateExpression("10");
        System.out.println(res +"    noa");
        System.out.println(res.toString());

        //rule1.addAction(new IncreaseAction(smokerEntityDefinition, "smokingInDay", expression.toString()));
        rule1.addAction(new KillAction(smokerEntityDefinition));

        EnvVariablesManager envVariablesManager = new EnvVariableManagerImpl();
        IntegerPropertyDefinition taxAmountEnvironmentVariablePropertyDefinition = new IntegerPropertyDefinition("tax-amount", ValueGeneratorFactory.createRandomInteger(10, 100),new Range(10,100));
        envVariablesManager.addEnvironmentVariable(taxAmountEnvironmentVariablePropertyDefinition);


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
//        for (PropertyDefinition propertyDefinition : envVariablesManager.getEnvVariables()) {

        // collect value from user...
        int valueFromUser = 54;
        activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(taxAmountEnvironmentVariablePropertyDefinition, valueFromUser));
//        }

        // all env variable not inserted by user, needs to be generated randomly. lucky we have all data needed for it...
        //Integer randomEnvVariableValue = taxAmountEnvironmentVariablePropertyDefinition.generateValue();
        //activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(taxAmountEnvironmentVariablePropertyDefinition, randomEnvVariableValue));

        // during a tick...

        // given an instance...
        EntityInstance entityInstance = entityInstanceManager.getInstances().get(0);
        // create a context (per instance)
        Context context = new ContextImpl(entityInstance, entityInstanceManager, activeEnvironment);
//        if (rule1.getActivation().isActive(1)) {
//            rule1
//                    .getActionsToPerform()
//                    .forEach(action ->
//                            action.invoke(context));
//        }
        rule1.getActionsToPerform().forEach(action -> action.invoke(context));
    }
}

