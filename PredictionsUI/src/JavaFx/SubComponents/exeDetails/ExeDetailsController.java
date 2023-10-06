/*
package JavaFx.SubComponents.exeDetails;

import Defenitions.*;
import JavaFx.SubComponents.resultTab.ResultTabController;
import JavaFx.Tasks.UpdateExeDetailsTask;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import simulation.api.SimulationState;

import java.util.LinkedList;

public class ExeDetailsController {
    private ResultTabController resultTabController;

    @FXML
    private TableColumn<EntPopDTO, String> entNameCol;

    @FXML
    private Button StopBtn;

    @FXML
    private TextField currTickText;

    @FXML
    private TableColumn<EntPopDTO, Integer> instancesCol;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button rerunBtn;

    @FXML
    private Button resumeBtn;

    @FXML
    private TextField secondsCountText;

    @FXML
    private TableView<EntPopDTO> tableViewComponent;
    private UpdateExeDetailsTask updateExeDetailsTask;

    private final ObservableList<EntPopDTO> data = new ObservableListWrapper<>(new LinkedList<>());

    @FXML
    public void initialize()
    {
        StopBtn.setDisable(true);
        pauseBtn.setDisable(true);
        resumeBtn.setDisable(true);
        rerunBtn.setDisable(true);
        tableViewComponent.setItems(data);
    }
    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }
    public void initializeTableView() {
        entNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        instancesCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPop()));
    }
    public void startUpdateTableTask() {
        updateExeDetailsTask = new UpdateExeDetailsTask(resultTabController);
        new Thread(updateExeDetailsTask).start();

        updateExeDetailsTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    try {
                        if (!newValue.getSimulationState().equals(SimulationState.PENDING)) {
                            currTickText.setText(newValue.getCurrTick().toString());
                            secondsCountText.setText(String.valueOf(newValue.getSeconds()));
                            data.clear();
                            data.addAll(newValue.getEntitiesUpdateData());
                        } else {
                            currTickText.setText(newValue.getCurrTick().toString());
                            secondsCountText.setText(String.valueOf(newValue.getSeconds()));
                            data.clear();
                        }
                    }
                    catch (Exception ignored)
                    {}
                });
            }
        });
    }
    public void onPauseBtnClicked()
    {
        String selectedGuid = resultTabController.getSelectedGuid();
        resultTabController.setPause(selectedGuid,true);
    }
    public void onStopBtnClicked() {
        String selectedGuid = resultTabController.getSelectedGuid();
        resultTabController.setTerminated(selectedGuid);
    }

    public void onResumeBtnClicked() {
        String selectedGuid = resultTabController.getSelectedGuid();
        resultTabController.setPause(selectedGuid,false);
    }

    public void onRerunBtnClicked() {
        resultTabController.onReRun();
    }
    public void SetUIButtons(simulationViewDTO simulationViewDTO)
    {
        switch(simulationViewDTO.getState())
        {
            case PENDING:
            {
                StopBtn.setDisable(true);
                pauseBtn.setDisable(true);
                resumeBtn.setDisable(true);
                rerunBtn.setDisable(true);
            }
            break;
            case RUNNING:
            {
                StopBtn.setDisable(false);
                pauseBtn.setDisable(false);
                resumeBtn.setDisable(false);
                rerunBtn.setDisable(true);
            }
            break;
            case FINISHED:
            {
                StopBtn.setDisable(true);
                pauseBtn.setDisable(true);
                resumeBtn.setDisable(true);
                rerunBtn.setDisable(false);
            }
            break;
        }
    }

    public void clearScreen() {
        currTickText.clear();
        secondsCountText.clear();
        data.clear();
    }
}

*/
