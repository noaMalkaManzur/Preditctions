package JavaFx.SubComponents.termination;

import Defenitions.GridDTO;
import Defenitions.TerminitionDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import com.sun.xml.internal.fastinfoset.DecoderStateTables;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TerminationScreenController {

    private DetailsTabController detailsTabController;

    @FXML
    private TableView<TerminitionDTO> tableViewComponent;

    @FXML
    private TableColumn<TerminitionDTO, Integer> ticksCol;

    @FXML
    private TableColumn<TerminitionDTO, Integer> secCol;

    @FXML
    private TableColumn<TerminitionDTO, Boolean> byUserCol;

    public void initializeTableView() {
        ticksCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getByTicks()));
        secCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBySeconds()));
        byUserCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().getByUser()));
    }
    public void updateTableView(TerminitionDTO terminitionDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<TerminitionDTO> data = FXCollections.observableArrayList(terminitionDTO);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }

}
