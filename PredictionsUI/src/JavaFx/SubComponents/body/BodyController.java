package JavaFx.SubComponents.body;

import Defenitions.*;
import JavaFx.App.PredictionsAppController;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import JavaFx.SubComponents.newExeTab.NewExeScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;

import java.util.Map;

public class BodyController {
    private PredictionsAppController mainController;

    @FXML
    private TabPane tabPaneComponent;

    @FXML
    private ScrollPane detailsTabComponent;

    @FXML
    private DetailsTabController detailsTabComponentController;

    @FXML
    private ScrollPane newExeTabComponent;

    @FXML
    private NewExeScreenController newExeTabComponentController;

    public void setMainController(PredictionsAppController mainController) {
        this.mainController = mainController;
    }
    @FXML
    public void initialize()
    {
        if(detailsTabComponentController != null && newExeTabComponentController != null)
        {
            detailsTabComponentController.setBodyController(this);
            newExeTabComponentController.setBodyController(this);
        }
    }
    public Map<String, EntityDefinitionDTO> getEntityDTO()
    {
        return mainController.getEntityDTO();
    }
    public void populateTree() {
        detailsTabComponentController.populateTree();
    }

    public EnvironmentDefinitionDTO getEnvDTO() {
        return mainController.getEnvDTO();
    }
    public RulesDTO getRulesDTO(){return mainController.getRulesDTO();}

    public GridDTO getGridDTO() {
        return mainController.getGridDTO();
    }

    public TerminitionDTO getTerminationDTO() {
        return mainController.getTerminationDTO();
    }

    public void clearTree() {
        detailsTabComponentController.clearTree();
    }
    public void enableTabPane()
    {
        tabPaneComponent.setDisable(false);
    }
    public void initNewExeScreen()
    {
        newExeTabComponentController.initNewExeScreen();
    }
    public void clearNewExeScreen()
    {
        newExeTabComponentController.clearNewExeScreen();
    }

    public Integer getMaxPop() {
        return mainController.getMaxPop();
    }

    public void setEntPop(String selectedItem, Integer value) {
        mainController.setEntPop(selectedItem,value);
    }

    public boolean checkPopulation(Integer intValue, String selectedItem) {
        return mainController.checkPopulation(intValue,selectedItem);
    }

    public Integer getSpaceLeft(String selectedItem) {
        return mainController.getSpaceLeft(selectedItem);
    }

    public boolean isValidInput(EnvPropertyDefinitionDTO envPropertyDefinitionDTO, String userEnvInput) {
        return mainController.isValidInput(envPropertyDefinitionDTO,userEnvInput);
    }

    public void addEnvVarToActiveEnv(Object userValue, String name) {
        mainController.addEnvVarToActiveEnv(userValue,name);
    }

    public void showAlert(String message, Alert.AlertType alertType, String title) {
        mainController.showAlert(message,alertType,title);
    }

    public void initRandomEnvVars(String envName) {
        mainController.initRandomEnvVars(envName);
    }

    public void runSimulation() {
        mainController.runSimulation();
    }
}
