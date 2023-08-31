package JavaFx.SubComponents.body;

import Defenitions.EntityDefinitionDTO;
import Defenitions.EnvPropertyDefinitionDTO;
import Defenitions.EnvironmentDefinitionDTO;
import JavaFx.App.PredictionsAppController;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.List;
import java.util.Map;

public class BodyController {
    private PredictionsAppController mainController;

    @FXML
    private ScrollPane detailsTabComponent;
    @FXML
    private DetailsTabController detailsTabComponentController;

    public void setMainController(PredictionsAppController mainController) {
        this.mainController = mainController;
    }
    @FXML
    public void initialize()
    {
        if(detailsTabComponentController != null)
            detailsTabComponentController.setBodyController(this);
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
}
