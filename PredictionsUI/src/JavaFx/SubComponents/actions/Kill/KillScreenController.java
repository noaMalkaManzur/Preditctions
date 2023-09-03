package JavaFx.SubComponents.actions.Kill;

import Defenitions.Actions.Kill.KillDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class KillScreenController {
    private DetailsTabController detailsTabController;
    @FXML
    private TableView<KillDTO> tableViewComponent;

    @FXML
    private TableColumn<KillDTO, String> typeCol;

    @FXML
    private TableColumn<KillDTO, String> primaryEntCol;

    @FXML
    private TableColumn<KillDTO,String> secondaryEntCol;
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


    }
    public void updateTableView(KillDTO killDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<KillDTO> data = FXCollections.observableArrayList(killDTO);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }
}
