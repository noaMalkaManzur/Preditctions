package JavaFx.SubComponents.resultTab;

import Defenitions.simulationViewDTO;
import JavaFx.SubComponents.body.BodyController;
import JavaFx.SubComponents.exeDetails.ExeDetailsController;
import JavaFx.SubComponents.exeResults.ExeResultsController;
import JavaFx.Tasks.UpdateListViewTask;
import com.sun.javafx.collections.ObservableListWrapper;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import simulation.api.SimulationState;

import java.util.LinkedList;
import java.util.function.Consumer;

public class ResultTabController
{
    private BodyController bodyController;

    @FXML
    private ListView<simulationViewDTO> executionsLstComponent;

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
    private final ObservableList<simulationViewDTO> executions = new ObservableListWrapper<>(new LinkedList<>());
    private Consumer<simulationViewDTO> simulationViewDTOConsumer;


    @FXML
    public void initialize() {
        if (exeDetailsScreenComponentController != null && exeResultsScreenComponentController != null) {
            exeDetailsScreenComponentController.setResultTabController(this);
            exeResultsScreenComponentController.setResultTabController(this);
            exeDetailsScreenComponentController.initializeTableView();
            exeResultsScreenComponent.setDisable(true);
        }
        this.simulationViewDTOConsumer = (s) ->
        {
            Platform.runLater(() -> {
                refreshListView(s);
                if(s.getState().equals(SimulationState.FINISHED))
                    bodyController.showAlert("Simulation: " + s.getGuid() + "\nFinished its run!\nTermination Reason:" +
                            getEngine().getTerminationReason(s.getGuid()), Alert.AlertType.INFORMATION,"Info");
            });
        };

        executionsLstComponent.setCellFactory(list -> new ListCell<simulationViewDTO>() {

            @Override
            protected void updateItem(simulationViewDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.getGuid()).append(System.lineSeparator()).append(item.getState().toString());
                    setText(sb.toString());
                }
            }
        });
        executionsLstComponent.setItems(executions);

    }

    private void refreshListView(simulationViewDTO s) {
        for(int i = 0; i<executionsLstComponent.getItems().size();i++)
        {
            if(s.getGuid().equals(executionsLstComponent.getItems().get(i).getGuid()))
            {
                executionsLstComponent.getItems().set(i,s);
            }
        }
    }

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
    public void startUpdateListViewTask() {
        if (bodyController != null) {
            updateListViewTask = new UpdateListViewTask(executions,getEngine(),simulationViewDTOConsumer);
            Thread taskThread = new Thread(updateListViewTask);
            taskThread.setDaemon(true);
            taskThread.start();
        }
    }

    public Engine getEngine() {
        return bodyController.getEngine();
    }

    public String getSelectedGuid() {
        if (executionsLstComponent.getSelectionModel().getSelectedItem() != null)
            return executionsLstComponent.getSelectionModel().getSelectedItem().getGuid();
        return null;
    }
    public void selectedItem()
    {
        String SelectedItem = executionsLstComponent.getSelectionModel().getSelectedItem().getGuid();
        if(SelectedItem != null)
        {
            if (!isLiveUpdateStarted) {
                exeDetailsScreenComponentController.startUpdateTableTask();
                isLiveUpdateStarted = false;
            }

            for (int i = 0; i < executionsLstComponent.getItems().size(); i++) {
                if (getSelectedGuid().equals(executionsLstComponent.getItems().get(i).getGuid())) {
                    exeDetailsScreenComponentController.SetUIButtons(executionsLstComponent.getItems().get(i));
                    exeResultsScreenComponentController.SetUI(executionsLstComponent.getItems().get(i));
                }
            }
        }

    }
    public void setPause(String selectedGuid, boolean b) {
        bodyController.setPause(selectedGuid,b);
    }

    public void setTerminated(String selectedGuid) {
        bodyController.setTerminated(selectedGuid);
    }

    public void onReRun() {
        bodyController.onReRun(getSelectedGuid());
    }
    public void clearLstView()
    {
        executionsLstComponent.getItems().clear();
        exeDetailsScreenComponentController.clearScreen();
    }
}
