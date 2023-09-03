package JavaFx.SubComponents.actions.Set;

import Defenitions.Actions.Condition.impl.SingleDTO;
import Defenitions.Actions.Set.SetDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SetScreenController {
    private DetailsTabController detailsTabController;

    @FXML
    private TableView<SetDTO> tableViewComponent;

    @FXML
    private TableColumn<SetDTO, String> typeCol;

    @FXML
    private TableColumn<SetDTO, String> primaryEntCol;

    @FXML
    private TableColumn<SetDTO, String> secondaryEntCol;

    @FXML
    private TableColumn<SetDTO, String> propCol;

    @FXML
    private TableColumn<SetDTO, String> valCol;

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
        propCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPropertyName()));
        valCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
    }
    public void updateTableView(SetDTO setDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<SetDTO> data = FXCollections.observableArrayList(setDTO);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }

}
