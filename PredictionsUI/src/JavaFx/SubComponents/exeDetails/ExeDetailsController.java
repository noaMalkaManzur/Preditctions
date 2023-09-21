package JavaFx.SubComponents.exeDetails;

import Defenitions.EntPopDTO;
import Defenitions.EntityDefinitionDTO;
import Defenitions.EntityPropDefinitionDTO;
import Defenitions.ProgressSimulationDTO;
import Instance.EntityMangerDTO;
import JavaFx.SubComponents.resultTab.ResultTabController;
import JavaFx.Tasks.UpdateExeDetailsTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }
    public void initializeTableView() {
        entNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        instancesCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPop()));
    }
    public void startUpdateTableTask() {
        String selectedGuid = resultTabController.getSelectedGuid();
        if (selectedGuid != null) {
            updateExeDetailsTask = new UpdateExeDetailsTask(resultTabController.getEngine(), selectedGuid);
            new Thread(updateExeDetailsTask).start();

            updateExeDetailsTask.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Platform.runLater(() -> {
                        currTickText.setText(newValue.getCurrTick().toString());
                        secondsCountText.setText(String.valueOf(newValue.getSeconds()));
                        ObservableList<EntPopDTO> data = FXCollections.observableArrayList(newValue.getEntitiesUpdateData());
                        tableViewComponent.setItems(data);
                    });
                }
            });
        }
    }


}

