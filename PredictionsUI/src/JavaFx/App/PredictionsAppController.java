package JavaFx.App;

import Defenitions.*;
import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.header.HeaderController;
import ThreadManager.ThreadManager;
import engine.api.Engine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class PredictionsAppController {
    private Engine engine;
    @FXML
    private ScrollPane headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private ScrollPane bodyComponent;
    @FXML
    private BodyController bodyComponentController;
    @FXML
    private ScrollPane predictionsAppScrollPane;
    private SimpleStringProperty selectedFile;
    private SimpleIntegerProperty queueSizeLabel;
    private SimpleIntegerProperty runningSimLabel;
    private SimpleIntegerProperty finishedSimLabel;


    @FXML
    public void initialize() {
        selectedFile = new SimpleStringProperty();
        queueSizeLabel = new SimpleIntegerProperty(0);
        runningSimLabel = new SimpleIntegerProperty(0);
        finishedSimLabel = new SimpleIntegerProperty(0);
        if (headerComponentController != null && bodyComponentController != null) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
            headerComponentController.bindHeaderToFullApp();

        }
    }
    public SimpleStringProperty selectedFileProperty() {
        return selectedFile;
    }
    public SimpleIntegerProperty getQueueSize() {
            return queueSizeLabel;
    }

    public void setQueueSizeLabel(int queueSizeLabel) {
        this.queueSizeLabel.set(queueSizeLabel);
    }

    public void setRunningSimLabel(int runningSimLabel) {
        this.runningSimLabel.set(runningSimLabel);
    }

    public void setFinishedSimLabel(int finishedSimLabel) {
        this.finishedSimLabel.set(finishedSimLabel);
    }

    public SimpleIntegerProperty getRunningSimLabel() {
        return runningSimLabel;
    }
    public SimpleIntegerProperty getFinishedSimLabel() {
        return finishedSimLabel;
    }

    public void readWorldData(String absolutePath) {
        try {
            engine.loadXmlFiles(absolutePath);
            selectedFile.set(absolutePath);
            bodyComponentController.populateTree();
            bodyComponentController.initNewExeScreen();
            bodyComponentController.enableTabPane();
            showAlert("Loaded file succeeded!",Alert.AlertType.INFORMATION,"Success");
        } catch (RuntimeException exception) {
            showAlert("Operation failed: " + exception.getMessage(), Alert.AlertType.ERROR,"Error");
        }
    }
    public void showAlert(String message, Alert.AlertType alertType, String Title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }

    public Map<String,EntityDefinitionDTO> getEntityDTO() {
        return engine.getEntitiesDTO();
    }

    public EnvironmentDefinitionDTO getEnvDTO() {
        return engine.getEnvDTO();
    }

    public RulesDTO getRulesDTO() {
        return engine.getRulesDTO();
    }

    public GridDTO getGridDTO() {
        return engine.getGridDTO();
    }

    public TerminationDTO getTerminationDTO() {
        return engine.getTerminationDTO();
    }

    public Integer getMaxPop() {
        return engine.getMaxPop();
    }

    public void setEntPop(String selectedItem, Integer value) {
        engine.setEntPop(selectedItem,value);
    }

    public boolean checkPopulation(Integer intValue, String selectedItem) {
        return engine.checkPopulation(intValue,selectedItem);
    }

    public Integer getSpaceLeft(String selectedItem) {
        return engine.getSpaceLeft(selectedItem);
    }

    public boolean isValidInput(EnvPropertyDefinitionDTO envPropertyDefinitionDTO, String userEnvInput) {
        return engine.getValidation().isValidUserInput(envPropertyDefinitionDTO,userEnvInput);
    }

    public void addEnvVarToActiveEnv(Object userValue, String name) {
        engine.addEnvVarToActiveEnv(userValue,name);
    }

    public void initRandomEnvVars(String envName) {
        engine.initRandomEnvVars(envName);
    }

    public void runSimulation() {
        engine.runSimulation();
    }
    private void updateLabels() {
        ExecutorService myService = engine.getThreadManager().getThreadExecutor();
        if (myService != null) {
            setQueueSizeLabel(((ThreadPoolExecutor)myService).getQueue().size());
            setRunningSimLabel(((ThreadPoolExecutor)myService).getActiveCount());
            setFinishedSimLabel((int) ((ThreadPoolExecutor)myService).getCompletedTaskCount());
        }
    }

    public ThreadManager getThreadManager() {
        return engine.getThreadManager();
    }
}
