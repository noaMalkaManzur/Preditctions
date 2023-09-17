package JavaFx.SubComponents.resultTab;

import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.exeDetails.ExeDetailsController;
import JavaFx.SubComponents.exeResults.ExeResultsController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

public class ResultTabController
{
    private BodyController bodyController;

    @FXML
    private ListView<String> executionsLstComponent;

    @FXML
    private ScrollPane exeDetailsScreenComponent;

    @FXML
    private ExeDetailsController exeDetailsScreenComponentController;

    @FXML
    private ScrollPane exeResultsScreenComponent;

    @FXML
    private ExeResultsController exeResultsScreenComponentController;

    @FXML
    public void initialize() {
        if(exeDetailsScreenComponentController != null && exeResultsScreenComponentController != null)
        {
            exeDetailsScreenComponentController.setResultTabController(this);
            exeResultsScreenComponentController.setResultTabController(this);
        }
    }
    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
}
