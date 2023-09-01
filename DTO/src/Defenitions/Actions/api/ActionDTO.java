package Defenitions.Actions.api;

import Enums.ActionTypeDTO;
import Enums.MathActionDTO;
import Enums.ModesDTO;

public class ActionDTO {
    private final ActionTypeDTO type;
    private final String primaryEntityName;
    private final String SecondaryEntityName;
//    private final MathActionDTO mathType;
//    private final String PropertyName;
//    private final String ByExpression;
//    private final String result_Prop;
//    private final String firstArg;
//    private final String secondArg;
//    private final String singularity;
//    private final int thenSize;
//    private final int elseSize;
//    private final String Operator;
//    private final String Value;
//    private final String logic;
//    private final int condNum;
//    private final String source;
//    private final String target;
//    private final int depth;
//    private final int numActions;
//    private final String toKill;
//    private final String toCreate;
//    private final ModesDTO mode;

    public ActionDTO(ActionTypeDTO type, String primaryEntityName,
                     String secondaryEntityName) {
        this.type = type;
        this.primaryEntityName = primaryEntityName;
        SecondaryEntityName = secondaryEntityName;
//        PropertyName = propertyName;
//        ByExpression = byExpression;
//        result_Prop = resultProp;
//        this.firstArg = firstArg;
//        this.secondArg = secondArg;
//        this.mathType = mathType;
//        this.singularity = singularity;
//        this.thenSize = thenSize;
//        this.elseSize = elseSize;
//        Operator = operator;
//        Value = value;
//        this.logic = logic;
//        this.condNum = condNum;
//        this.source = source;
//        this.target = target;
//        this.depth = depth;
//        this.numActions = numActions;
//        this.toKill = toKill;
//        this.toCreate = toCreate;
//        this.mode = mode;
    }

    public ActionTypeDTO getType() {
        return type;
    }

    public String getPrimaryEntityName() {
        return primaryEntityName;
    }

    public String getSecondaryEntityName() {
        return SecondaryEntityName;
    }
//
//    public String getPropertyName() {
//        return PropertyName;
//    }
//
//    public String getByExpression() {
//        return ByExpression;
//    }
//
//    public String getResult_Prop() {
//        return result_Prop;
//    }
//
//    public String getFirstArg() {
//        return firstArg;
//    }
//
//    public String getSecondArg() {
//        return secondArg;
//    }
//
//    public MathActionDTO getMathType() {
//        return mathType;
//    }
//
//    public String getSingularity() {
//        return singularity;
//    }
//
//    public int getThenSize() {
//        return thenSize;
//    }
//
//    public int getElseSize() {
//        return elseSize;
//    }
//
//    public String getOperator() {
//        return Operator;
//    }
//
//    public String getValue() {
//        return Value;
//    }
//
//    public String getLogic() {
//        return logic;
//    }
//
//    public int getCondNum() {
//        return condNum;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public String getTarget() {
//        return target;
//    }
//
//    public int getDepth() {
//        return depth;
//    }
//
//    public int getNumActions() {
//        return numActions;
//    }
//
//    public String getToKill() {
//        return toKill;
//    }
//
//    public String getToCreate() {
//        return toCreate;
//    }
//
//    public ModesDTO getMode() {
//        return mode;
//    }
}
