package JavaFx.SubComponents.resultTab;

import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.exeDetails.ExeDetailsController;
import JavaFx.SubComponents.exeResults.ExeResultsController;
import JavaFx.Tasks.UpdateListViewTask;
import engine.api.Engine;
import javafx.concurrent.Task;
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
    private UpdateListViewTask updateListViewTask;
    private Boolean isLiveUpdateStarted = false;


    @FXML
    public void initialize() {
        if(exeDetailsScreenComponentController != null && exeResultsScreenComponentController != null)
        {
            exeDetailsScreenComponentController.setResultTabController(this);
            exeResultsScreenComponentController.setResultTabController(this);
            exeDetailsScreenComponentController.initializeTableView();
        }

    }
    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
    public void startUpdateListViewTask() {
        if (bodyController != null) {
            updateListViewTask = new UpdateListViewTask(executionsLstComponent, bodyController.getSimulationMap());

            // Start the task in a background thread
            Thread taskThread = new Thread(updateListViewTask);
            taskThread.setDaemon(true); // Set it as a daemon thread so it stops when the application exits
            taskThread.start();
        }
    }

    public Engine getEngine() {
        return bodyController.getEngine();
    }

    public String getSelectedGuid() {
        return executionsLstComponent.getSelectionModel().getSelectedItem();
    }
    public void selectedItem()
    {
        exeDetailsScreenComponentController.startUpdateTableTask();
    }
}
