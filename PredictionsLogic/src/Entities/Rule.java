package Entities;
import Actions.*;

import java.util.List;

public class Rule
{
    private String name;
    private List<Expression> ruleActionList;
    private Activation ActivationTerms;

    private float probability;
}
