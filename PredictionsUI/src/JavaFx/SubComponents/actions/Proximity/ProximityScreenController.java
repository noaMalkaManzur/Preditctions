/*
package JavaFx.SubComponents.actions.Proximity;

import Defenitions.Actions.Condition.impl.SingleDTO;
import Defenitions.Actions.Proximity.ProximityDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProximityScreenController {
    private DetailsTabController detailsTabController;

    @FXML
    private TableView<ProximityDTO> tableViewComponent;

    @FXML
    private TableColumn<ProximityDTO, String> typeCol;

    @FXML
    private TableColumn<ProximityDTO, String> primaryEntCol;

    @FXML
    private TableColumn<ProximityDTO, String> secondaryEntCol;

    @FXML
    private TableColumn<ProximityDTO, String> sourceCol;

    @FXML
    private TableColumn<ProximityDTO, String> targetCol;

    @FXML
    private TableColumn<ProximityDTO, String> depthCol;

    @FXML
    private TableColumn<ProximityDTO, Integer> actionsCol;

    public void initializeTableView() {
        typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType().toString()));

        primaryEntCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrimaryEntityName()));

        secondaryEntCol.setCellValueFactory(data -> {
            if (data.getValue().getSecondaryEntityName() != null) {
                return new SimpleObjectProperty<>(data.getValue().getSecondaryEntityName());
            } else {
                return new SimpleObjectProperty<>(null); // Display null for null range
            }
        });
        sourceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSource()));
        targetCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTarget()));
        depthCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepth()));
        actionsCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNumActions()));


    }
    public void updateTableView(ProximityDTO proximityDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<ProximityDTO> data = FXCollections.observableArrayList(proximityDTO);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }

}
*/
