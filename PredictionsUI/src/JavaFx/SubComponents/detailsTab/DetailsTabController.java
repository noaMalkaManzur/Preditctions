package JavaFx.SubComponents.detailsTab;

import Defenitions.EntityDefinitionDTO;
import Defenitions.EnvPropertyDefinitionDTO;
import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.entProperty.EntityScreenController;
import JavaFx.SubComponents.envProperty.EnvScreenController;
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
    public void initialize() {
        if(entPropScreenComponentController != null && envPropScreenComponentController != null)
        {
            entPropScreenComponentController.setDetailsTabController(this);
            entPropScreenComponentController.initializeTableView();

            envPropScreenComponentController.setDetailsTabController(this);
            envPropScreenComponentController.initializeTableView();

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
        }
    }
    public void resetViews()
    {
        entPropScreenComponent.setVisible(false);
        envPropScreenComponent.setVisible(false);
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
        TreeItem<String> RulesBranch = new TreeItem<>("Rules");
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
}
