package JavaFx.App;

import Defenitions.EntityDefinitionDTO;
import Defenitions.EnvironmentDefinitionDTO;
import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.header.HeaderController;
import engine.api.Engine;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.Map;

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

    private SimpleBooleanProperty isFileSelected;
    private SimpleStringProperty selectedFile;


    @FXML
    public void initialize() {
        if (headerComponentController != null && bodyComponentController != null) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
        }
        selectedFile = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        headerComponentController.bindHeaderToFullApp();
    }

    public SimpleStringProperty selectedFileProperty() {
        return selectedFile;
    }

    public void readWorldData(String absolutePath) {
        selectedFile.set(absolutePath);
        engine.loadXmlFiles(absolutePath);
        bodyComponentController.populateTree();
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
}
