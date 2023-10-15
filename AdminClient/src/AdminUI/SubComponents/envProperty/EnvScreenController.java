package AdminUI.SubComponents.envProperty;

import Defenitions.EnvPropertyDefinitionDTO;
import definition.property.api.Range;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Map;

public class EnvScreenController {

    @FXML
    private TableView<EnvPropertyDefinitionDTO> tableViewComponent;

    @FXML
    private TableColumn<EnvPropertyDefinitionDTO, String> nameCol;

    @FXML
    private TableColumn<EnvPropertyDefinitionDTO, String> typeCol;

    @FXML
    private TableColumn<EnvPropertyDefinitionDTO, String> rangeCol;

    @FXML
    private TableColumn<EnvPropertyDefinitionDTO, Double> fromCol;

    @FXML
    private TableColumn<EnvPropertyDefinitionDTO, Double> toCol;

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
    }
    public void updateTableView(Map<String, EnvPropertyDefinitionDTO> envProps) {
        tableViewComponent.getItems().clear();
        ObservableList<EnvPropertyDefinitionDTO> data = FXCollections.observableArrayList(envProps.values());
        tableViewComponent.setItems(data);
    }
}
