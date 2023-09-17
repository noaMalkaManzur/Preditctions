package JavaFx.SubComponents.exeDetails;

import Instance.EntityMangerDTO;
import JavaFx.SubComponents.resultTab.ResultTabController;
import execution.instance.enitty.EntityInstance;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExeDetailsController {
    private ResultTabController resultTabController;
    @FXML
    private TableColumn<EntityMangerDTO, Integer> instancesCol;

    @FXML
    private Button StopBtn;

    @FXML
    private TextField currTickText;

    @FXML
    private TableColumn<EntityMangerDTO, String> entNameCol;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button rerunBtn;

    @FXML
    private Button resumeBtn;

    @FXML
    private TextField secondsCountText;

    @FXML
    private TableView<EntityMangerDTO> tableViewComponent;



    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }
    public void initializeTableView() {
        entNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInstances().keySet().toString()));
        instancesCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getInstances().keySet().size()));
    }
}
