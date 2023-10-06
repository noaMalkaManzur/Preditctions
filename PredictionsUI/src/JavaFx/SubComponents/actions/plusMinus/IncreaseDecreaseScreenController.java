/*
package JavaFx.SubComponents.actions.plusMinus;

import Defenitions.Actions.IncreaseDecrease.IncreaseDecreaseDTO;
import Defenitions.Actions.api.ActionDTO;
import Defenitions.EntityPropDefinitionDTO;
import Defenitions.EnvPropertyDefinitionDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import definition.property.api.Range;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Map;

public class IncreaseDecreaseScreenController {
    DetailsTabController detailsTabController;

    @FXML
    private TableView<IncreaseDecreaseDTO> tableViewComponent;
    @FXML
    private TableColumn<IncreaseDecreaseDTO, String> typeCol;
    @FXML
    private TableColumn<IncreaseDecreaseDTO, String> primaryEntCol;
    @FXML
    private TableColumn<IncreaseDecreaseDTO, String> secondaryEntCol;
    @FXML
    private TableColumn<IncreaseDecreaseDTO, String> propCol;
    @FXML
    private TableColumn<IncreaseDecreaseDTO, String> byCol;

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
        byCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getByExpression()));
    }
    public void updateTableView(IncreaseDecreaseDTO increaseDecreaseDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<IncreaseDecreaseDTO> data = FXCollections.observableArrayList(increaseDecreaseDTO);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }

}
*/
