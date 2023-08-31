package Defenitions.Actions.Calculation;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;
import Enums.MathActionDTO;

public class Calculation extends ActionDTO {
    public String getResult_Prop() {
        return result_Prop;
    }

    public String getFirstArg() {
        return firstArg;
    }

    public String getSecondArg() {
        return secondArg;
    }

    public MathActionDTO getMathType() {
        return mathType;
    }

    private final String result_Prop;
    private final String firstArg;
    private final String secondArg;
    private final MathActionDTO mathType;
    public Calculation(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, String resultProp, String firstArg, String secondArg, MathActionDTO mathType) {
        super(type, primaryEntityName, secondaryEntityName);
        result_Prop = resultProp;
        this.firstArg = firstArg;
        this.secondArg = secondArg;
        this.mathType = mathType;
    }
}
