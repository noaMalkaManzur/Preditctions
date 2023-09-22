package JavaFx.SubComponents.exeResults;

import Defenitions.EntityDefinitionDTO;
import Defenitions.simulationViewDTO;
import Instance.EntityPopGraphDTO;
import JavaFx.SubComponents.QuantitiesGraph.GraphScreenController;
import JavaFx.SubComponents.histogram.HistogramScreenController;
import JavaFx.SubComponents.resultTab.ResultTabController;
import histogramDTO.HistogramByPropertyEntitiesDTO;
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
    @FXML
    private GraphScreenController graphScreenComponentController;

    @FXML
    private ScrollPane histogramScreenComponent;
    @FXML
    private HistogramScreenController histogramScreenComponentController;

    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }
    @FXML
    public void initialize()
    {
        if(graphScreenComponentController != null && histogramScreenComponentController != null)
        {
            graphScreenComponentController.setExeResultsTab(this);
            histogramScreenComponentController.setExeResultsTab(this);
        }
    }

    public EntityPopGraphDTO getGraphDTO() {
        return resultTabController.getEngine().getGraphDTO(resultTabController.getSelectedGuid());
    }

    public Collection<String> getEntityStringDTO() {
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
                histogramScreenComponentController.populateView();
            }
            break;
        }
    }

    public Map<String, EntityDefinitionDTO> getEntityDTO() {
        return resultTabController.getEngine().getEntitiesDTO();
    }

    public HistogramByPropertyEntitiesDTO getHistogramByProp(String entName, String propName) {
        return resultTabController.getEngine().getHistogramByProp(entName,propName,resultTabController.getSelectedGuid());
    }
}
