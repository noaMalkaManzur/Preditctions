/*
package JavaFx.SubComponents.entProperty;

import Defenitions.EntityDefinitionDTO;
import Defenitions.EntityPropDefinitionDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityScreenController {
    DetailsTabController detailsTabController;

    @FXML
    private TableView<EntityPropDefinitionDTO> tableViewComponent;

    @FXML
    private TableColumn<EntityPropDefinitionDTO, String> nameCol;

    @FXML
    private TableColumn<EntityPropDefinitionDTO, String> typeCol;

    @FXML
    private TableColumn<EntityPropDefinitionDTO, String> rangeCol;

    @FXML
    private TableColumn<EntityPropDefinitionDTO, Double> fromCol;

    @FXML
    private TableColumn<EntityPropDefinitionDTO, Double> toCol;

    @FXML
    private TableColumn<EntityPropDefinitionDTO, Boolean> randomInitCol;

    // This method initializes and populates the TableView and its columns
    public void initializeTableView() {
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType().toString()));

        fromCol.setCellValueFactory(data -> {
            Range range = data.getValue().getRange();
            if (range != null) {
                return new SimpleObjectProperty<>(range.getRangeFrom());
            } else {
                return new SimpleObjectProperty<>(null); // Display null for null range
            }
        });

        toCol.setCellValueFactory(data -> {
            Range range = data.getValue().getRange();
            if (range != null) {
                return new SimpleObjectProperty<>(range.getRangeTo());
            } else {
                return new SimpleObjectProperty<>(null); // Display null for null range
            }
        });

        randomInitCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isRandomInit()));

    }
    public void updateTableView(EntityDefinitionDTO selectedEntity) {
        tableViewComponent.getItems().clear();
        Map<String, EntityPropDefinitionDTO> propertyDefinition = selectedEntity.getPropertyDefinition();
        List<EntityPropDefinitionDTO> propList = new ArrayList<>(propertyDefinition.values());
        ObservableList<EntityPropDefinitionDTO> data = FXCollections.observableArrayList(propList);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }
}
*/
