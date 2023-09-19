package JavaFx.SubComponents.detailsTab;

import Defenitions.*;
import Defenitions.Actions.Calculation.CalculationDTO;
import Defenitions.Actions.Condition.api.ConditionDTO;
import Defenitions.Actions.Condition.impl.MultipleDTO;
import Defenitions.Actions.Condition.impl.SingleDTO;
import Defenitions.Actions.IncreaseDecrease.IncreaseDecreaseDTO;
import Defenitions.Actions.Kill.KillDTO;
import Defenitions.Actions.Proximity.ProximityDTO;
import Defenitions.Actions.Replace.ReplaceDTO;
import Defenitions.Actions.Set.SetDTO;
import Defenitions.Actions.api.ActionDTO;
import JavaFx.SubComponents.actions.Calculation.CalculationScreenController;
import JavaFx.SubComponents.actions.Condition.MultipleScreenController;
import JavaFx.SubComponents.actions.Condition.SingleScreenController;
import JavaFx.SubComponents.actions.Kill.KillScreenController;
import JavaFx.SubComponents.actions.Proximity.ProximityScreenController;
import JavaFx.SubComponents.actions.Replace.ReplaceScreenController;
import JavaFx.SubComponents.actions.Set.SetScreenController;
import JavaFx.SubComponents.actions.plusMinus.IncreaseDecreaseScreenController;
import JavaFx.SubComponents.activation.ActivationScreenController;
import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.entProperty.EntityScreenController;
import JavaFx.SubComponents.envProperty.EnvScreenController;
import JavaFx.SubComponents.grid.GridScreenController;
import JavaFx.SubComponents.termination.TerminationScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

import java.util.*;

public class DetailsTabController{
    BodyController bodyController;
    @FXML
    public ScrollPane detailsComponent;
    @FXML
    public StackPane stackPaneComponent;
    @FXML
    private TreeView<String> treeViewComponent;
    @FXML
    private ScrollPane entPropScreenComponent;
    @FXML
    private EntityScreenController entPropScreenComponentController;
    @FXML
    private ScrollPane envPropScreenComponent;
    @FXML
    private EnvScreenController envPropScreenComponentController;
    @FXML
    private ScrollPane plusMinusScreenComponent;
    @FXML
    private IncreaseDecreaseScreenController plusMinusScreenComponentController;
    @FXML
    private ScrollPane calcScreenComponent;
    @FXML
    private CalculationScreenController calcScreenComponentController;
    @FXML
    private ScrollPane singleScreenComponent;
    @FXML
    private SingleScreenController singleScreenComponentController;
    @FXML
    private ScrollPane multipleScreenComponent;
    @FXML
    private MultipleScreenController multipleScreenComponentController;
    @FXML
    private ScrollPane setScreenComponent;
    @FXML
    private SetScreenController setScreenComponentController;
    @FXML
    private ScrollPane killScreenComponent;
    @FXML
    private KillScreenController killScreenComponentController;
    @FXML
    private ScrollPane proximityScreenComponent;
    @FXML
    private ProximityScreenController proximityScreenComponentController;
    @FXML
    private ScrollPane replaceScreenComponent;
    @FXML
    private ReplaceScreenController replaceScreenComponentController;
    @FXML
    private ScrollPane activationScreenComponent;
    @FXML
    private ActivationScreenController activationScreenComponentController;
    @FXML
    private ScrollPane gridScreenComponent;
    @FXML
    private GridScreenController gridScreenComponentController;
    @FXML
    private ScrollPane terminationScreenComponent;
    @FXML
    private TerminationScreenController terminationScreenComponentController;

    @FXML
    public void initialize() {
        if(entPropScreenComponentController != null && envPropScreenComponentController != null
                && plusMinusScreenComponentController != null && calcScreenComponentController != null
                && singleScreenComponentController != null && multipleScreenComponentController != null
                && setScreenComponentController != null && proximityScreenComponentController != null
                && killScreenComponentController!= null && replaceScreenComponentController!= null
                && activationScreenComponentController != null && gridScreenComponentController != null
                && terminationScreenComponentController != null)
        {
            entPropScreenComponentController.setDetailsTabController(this);
            entPropScreenComponentController.initializeTableView();

            envPropScreenComponentController.setDetailsTabController(this);
            envPropScreenComponentController.initializeTableView();

            plusMinusScreenComponentController.setDetailsTabController(this);
            plusMinusScreenComponentController.initializeTableView();

            calcScreenComponentController.setDetailsTabController(this);
            calcScreenComponentController.initializeTableView();

            singleScreenComponentController.setDetailsTabController(this);
            singleScreenComponentController.initializeTableView();

            multipleScreenComponentController.setDetailsTabController(this);
            multipleScreenComponentController.initializeTableView();

            setScreenComponentController.setDetailsTabController(this);
            setScreenComponentController.initializeTableView();

            proximityScreenComponentController.setDetailsTabController(this);
            proximityScreenComponentController.initializeTableView();

            killScreenComponentController.setDetailsTabController(this);
            killScreenComponentController.initializeTableView();

            replaceScreenComponentController.setDetailsTabController(this);
            replaceScreenComponentController.initializeTableView();

            activationScreenComponentController.setDetailsTabController(this);
            activationScreenComponentController.initializeTableView();

            gridScreenComponentController.setDetailsTabController(this);
            gridScreenComponentController.initializeTableView();

            terminationScreenComponentController.setDetailsTabController(this);
            terminationScreenComponentController.initializeTableView();

        }
    }

    public void selectedItem()
    {
        TreeItem<String> selectedItem = treeViewComponent.getSelectionModel().getSelectedItem();
        if(selectedItem != null && selectedItem != treeViewComponent.getRoot()) {
            resetViews();
            if(selectedItem.getParent().getValue().equals("Entities")) {
                entPropScreenComponent.setVisible(true);
                String selectedEntityName = selectedItem.getValue();
                EntityDefinitionDTO selectedEntity = bodyController.getEntityDTO().get(selectedEntityName);
                entPropScreenComponentController.updateTableView(selectedEntity);
            }
            if(selectedItem.getValue().equals("Environment")) {
                envPropScreenComponent.setVisible(true);
                String selectedEnvVarName = selectedItem.getValue();
                //EnvPropertyDefinitionDTO selectedEnvVar = bodyController.getEnvDTO().getEnvProps().get(selectedEnvVarName);
                envPropScreenComponentController.updateTableView(bodyController.getEnvDTO().getEnvProps());
            }
            if(selectedItem.getParent().getValue().equals("Actions")) {
                if (selectedItem.getValue().equals("INCREASE") || selectedItem.getValue().equals("DECREASE")) {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    plusMinusScreenComponent.setVisible(true);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    IncreaseDecreaseDTO myAct = (IncreaseDecreaseDTO) rule.getActions().get(selectedIndex);
                    plusMinusScreenComponentController.updateTableView(myAct);
                }
                else if(selectedItem.getValue().equals("CALCULATION"))
                {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    calcScreenComponent.setVisible(true);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    CalculationDTO myAct = (CalculationDTO) rule.getActions().get(selectedIndex);
                    calcScreenComponentController.updateTableView(myAct);
                }
                else if(selectedItem.getValue().equals("CONDITION"))
                {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    ConditionDTO myAct = (ConditionDTO) rule.getActions().get(selectedIndex);
                    if(myAct instanceof SingleDTO)
                    {
                        singleScreenComponent.setVisible(true);
                        singleScreenComponentController.updateTableView((SingleDTO) myAct);
                    }
                    else
                    {
                        multipleScreenComponent.setVisible(true);
                        multipleScreenComponentController.updateTableView((MultipleDTO) myAct);
                    }
                }
                else if(selectedItem.getValue().equals("SET"))
                {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    setScreenComponent.setVisible(true);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    SetDTO myAct = (SetDTO) rule.getActions().get(selectedIndex);
                    setScreenComponentController.updateTableView(myAct);
                }
                else if(selectedItem.getValue().equals("PROXIMITY"))
                {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    proximityScreenComponent.setVisible(true);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    ProximityDTO myAct = (ProximityDTO) rule.getActions().get(selectedIndex);
                    proximityScreenComponentController.updateTableView(myAct);
                } else if (selectedItem.getValue().equals("KILL")) {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    killScreenComponent.setVisible(true);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    KillDTO myAct = (KillDTO) rule.getActions().get(selectedIndex);
                    killScreenComponentController.updateTableView(myAct);
                }
                else if(selectedItem.getValue().equals("REPLACE"))
                {
                    int selectedIndex = selectedItem.getParent().getChildren().indexOf(selectedItem);
                    replaceScreenComponent.setVisible(true);
                    RuleDTO rule = getRule(selectedItem.getParent().getParent().getValue());
                    ReplaceDTO myAct = (ReplaceDTO) rule.getActions().get(selectedIndex);
                    replaceScreenComponentController.updateTableView(myAct);
                }
            }
            if(selectedItem.getValue().equals("Activation"))
            {
                RuleDTO rule = getRule(selectedItem.getParent().getValue());
                activationScreenComponent.setVisible(true);
                ActivationDTO activationDTO = rule.getActivation();
                activationScreenComponentController.updateTableView(activationDTO);
            }
            if(selectedItem.getValue().equals("Grid"))
            {
                gridScreenComponent.setVisible(true);
                GridDTO gridDTO = bodyController.getGridDTO();
                gridScreenComponentController.updateTableView(gridDTO);
            }
            if(selectedItem.getValue().equals("Termination"))
            {
                terminationScreenComponent.setVisible(true);
                TerminationDTO terminationDTO = bodyController.getTerminationDTO();
                terminationScreenComponentController.updateTableView(terminationDTO);
            }
        }
    }

    private RuleDTO getRule(String selectedEnvVarName) {
        RulesDTO rules = bodyController.getRulesDTO();
        return rules.getRuleDTOList()
                .stream()
                .filter(ruleDTO -> ruleDTO.getName().equals(selectedEnvVarName))
                .findFirst() // Get the first matching RuleDTO, or null if none match
                .orElse(null);
    }
    public void resetViews()
    {
        entPropScreenComponent.setVisible(false);
        envPropScreenComponent.setVisible(false);
        plusMinusScreenComponent.setVisible(false);
        calcScreenComponent.setVisible(false);
        singleScreenComponent.setVisible(false);
        multipleScreenComponent.setVisible(false);
        setScreenComponent.setVisible(false);
        proximityScreenComponent.setVisible(false);
        killScreenComponent.setVisible(false);
        replaceScreenComponent.setVisible(false);
        activationScreenComponent.setVisible(false);
        gridScreenComponent.setVisible(false);
        terminationScreenComponent.setVisible(false);

    }

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
    public void populateTree()
    {
        stackPaneComponent.setVisible(true);
        TreeItem<String> root = new TreeItem<>("World");
        TreeItem<String> entityBranch = PopulateEntityBranch();
        TreeItem<String> environmentBranch =  new TreeItem<>("Environment");
        TreeItem<String> RulesBranch = PopulateRulesBranch();
        TreeItem<String> GridBranch = new TreeItem<>("Grid");
        TreeItem<String> TerminationBranch = new TreeItem<>("Termination");
        root.getChildren().addAll(entityBranch, environmentBranch, RulesBranch, GridBranch, TerminationBranch);
        treeViewComponent.setRoot(root);

    }
    public TreeItem<String> PopulateEntityBranch()
    {
        TreeItem<String> entityBranch = new TreeItem<>("Entities");
        Map<String, EntityDefinitionDTO> EntitiesDTO = bodyController.getEntityDTO();
        List<String> keys = new ArrayList<>(EntitiesDTO.keySet());
        Collections.reverse(keys);
        keys.forEach((String name) -> entityBranch.getChildren().add(new TreeItem<>(name)));
        return entityBranch;
    }
    public TreeItem<String> PopulateEnvironmentBranch()
    {
        TreeItem<String> envBranch =  new TreeItem<>("Environment");
        Map<String,EnvPropertyDefinitionDTO> envVars = bodyController.getEnvDTO().getEnvProps();
        List<String> keys = new ArrayList<>(envVars.keySet());
        //Collections.reverse(keys);
        keys.forEach((String name) -> envBranch.getChildren().add(new TreeItem<>(name)));
        return envBranch;
    }
    public TreeItem<String> PopulateRulesBranch()
    {
        TreeItem<String> RulesBranch = new TreeItem<>("Rules");
        RulesDTO rulesDTO = bodyController.getRulesDTO();
        int index = 0;
        rulesDTO.getRuleDTOList().forEach((RuleDTO rule)->
        {
            TreeItem<String> currRule = new TreeItem<>(rule.getName());
            currRule.getChildren().add(new TreeItem<>("Activation"));
            currRule.getChildren().add(PopulateRulesAction(rule.getActions()));
            RulesBranch.getChildren().add(currRule);
        });
        return RulesBranch;
    }
    public TreeItem<String> PopulateRulesAction(List<ActionDTO> actionList)
    {
        TreeItem<String> ActionBranch = new TreeItem<>("Actions");
        actionList.forEach((ActionDTO action)->
        {
            ActionBranch.getChildren().add(new TreeItem<>(action.getType().toString()));
        });
        return ActionBranch;
    }

    public void clearTree()
    {
        if (treeViewComponent.getRoot() != null) {
            treeViewComponent.getRoot().getChildren().clear();
            treeViewComponent.setRoot(null);
        }
    }
}
