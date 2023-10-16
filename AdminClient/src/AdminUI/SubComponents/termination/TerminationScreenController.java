package AdminUI.SubComponents.termination;

import Defenitions.TerminationDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TerminationScreenController {


    @FXML
    private TableView<TerminationDTO> tableViewComponent;

    @FXML
    private TableColumn<TerminationDTO, Integer> ticksCol;

    @FXML
    private TableColumn<TerminationDTO, Integer> secCol;

    @FXML
    private TableColumn<TerminationDTO, Boolean> byUserCol;

    public void initializeTableView() {
        ticksCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getByTicks()));
        secCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBySeconds()));
        byUserCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().getByUser()));
    }
    public void updateTableView(TerminationDTO terminitionDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<TerminationDTO> data = FXCollections.observableArrayList(terminitionDTO);
        tableViewComponent.setItems(data);
    }

}
