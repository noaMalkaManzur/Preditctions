/*
package JavaFx.SubComponents.body;

import Defenitions.*;
import Instance.ActiveEnvDTO;
import JavaFx.App.PredictionsAppController;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import JavaFx.SubComponents.newExeTab.NewExeScreenController;
import JavaFx.SubComponents.resultTab.ResultTabController;
import engine.api.Engine;
import exceptions.NoChosenSimException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import simulation.api.SimulationManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @FXML
    private ScrollPane resultTabComponent;

    @FXML
    private ResultTabController resultTabComponentController;
    private Boolean updateListStarted =false;

    @FXML
    public void initialize()
    {
        if(detailsTabComponentController != null && newExeTabComponentController != null
            && resultTabComponentController != null)
        {
            detailsTabComponentController.setBodyController(this);
            newExeTabComponentController.setBodyController(this);
            resultTabComponentController.setBodyController(this);
        }
    }
    public void setMainController(PredictionsAppController mainController) {
        this.mainController = mainController;
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

    public TerminationDTO getTerminationDTO() {
        return mainController.getTerminationDTO();
    }

    public void enableTabPane()
    {
        tabPaneComponent.setDisable(false);
    }
    public void initNewExeScreen()
    {
        newExeTabComponentController.initNewExeScreen();
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

    public void runSimulation(String ValuesString) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Values Confirmation");
        alert.setContentText(ValuesString);

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(selectedButton -> {
            if (selectedButton == ButtonType.OK) {
                tabPaneComponent.getSelectionModel().select(2);
                if (!updateListStarted) {
                    resultTabComponentController.startUpdateListViewTask();
                    updateListStarted = true;
                }
                mainController.runSimulation();
                newExeTabComponentController.onClearBtnClicked();
                resetSimVars();
            }
        });

    }

    public Engine getEngine() {
        return mainController.getEngine();
    }

    public void setPause(String selectedGuid, boolean b) {
        mainController.setPause(selectedGuid,b);
    }

    public void setTerminated(String selectedGuid) {
        mainController.setTerminated(selectedGuid);
    }

    public void resetSimVars() {
        mainController.resetSimVars();
    }

    public void onReRun(String selectedGuid) {
        try{
            newExeTabComponentController.setOnReRun(getEngine().getReRunInfo(selectedGuid));
            tabPaneComponent.getSelectionModel().select(1);
            showAlert("To start the simulation with the same values please click Set All Values Button!", Alert.AlertType.valueOf("INFORMATION"),"Information");
        }catch (Exception e)
        {
            throw new NoChosenSimException("No Selected Simulation!");
        }
    }

    public ActiveEnvDTO getActiveEndDTO() {
        return mainController.getActiveEnvDTO();
    }

    public void clearResScreen() {
        tabPaneComponent.getSelectionModel().select(0);
        resultTabComponentController.clearLstView();
    }
}
*/
