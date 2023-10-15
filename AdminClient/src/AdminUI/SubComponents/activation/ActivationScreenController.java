package AdminUI.SubComponents.activation;


import Defenitions.ActivationDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ActivationScreenController {

    @FXML
    private TableView<ActivationDTO> tableViewComponent;

    @FXML
    private TableColumn<ActivationDTO,Double> probCol;

    @FXML
    private TableColumn<ActivationDTO,Integer> ticksCol;

    public void initializeTableView() {

        ticksCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTicks()));
        probCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getProbability()));

    }
    public void updateTableView(ActivationDTO activationDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<ActivationDTO> data = FXCollections.observableArrayList(activationDTO);
        tableViewComponent.setItems(data);
    }
}
