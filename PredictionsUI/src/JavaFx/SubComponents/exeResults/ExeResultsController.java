package JavaFx.SubComponents.exeResults;

import Defenitions.simulationViewDTO;
import Instance.EntityPopGraphDTO;
import JavaFx.SubComponents.QuantitiesGraph.GraphScreenController;
import JavaFx.SubComponents.resultTab.ResultTabController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.Collection;
import java.util.Map;

public class ExeResultsController {
    private ResultTabController resultTabController;
    @FXML
    private ScrollPane exeResComponent;

    @FXML
    private ScrollPane graphScreenComponent;
    private GraphScreenController graphScreenComponentController;

    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }
    @FXML
    public void initialize()
    {
        if(graphScreenComponentController != null)
        {
            graphScreenComponentController.setExeResultsTab(this);
        }
    }

    public EntityPopGraphDTO getGraphDTO() {
        return resultTabController.getEngine().getGraphDTO(resultTabController.getSelectedGuid());
    }

    public Collection<String> getEntityDTO() {
        return resultTabController.getEngine().getEntitiesDTO().keySet();
    }

    public void SetUI(simulationViewDTO simulationViewDTO) {
        switch(simulationViewDTO.getState()) {
            case PENDING:
            case RUNNING:
                exeResComponent.setDisable(true);
                break;
            case FINISHED: {
                exeResComponent.setDisable(false);
                graphScreenComponentController.initEntList();
            }
            break;
        }
    }
}
