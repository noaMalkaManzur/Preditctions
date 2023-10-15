package AdminUI.SubComponents.actions.Condition;


import Defenitions.Actions.Condition.impl.MultipleDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MultipleScreenController {
    @FXML
    private TableView<MultipleDTO> tableViewComponent;
    @FXML
    private TableColumn<MultipleDTO, String> typeCol;
    @FXML
    private TableColumn<MultipleDTO, String> primaryEntCol;
    @FXML
    private TableColumn<MultipleDTO, String> secondaryEntCol;
    @FXML
    private TableColumn<MultipleDTO, String> logicCol;
    @FXML
    private TableColumn<MultipleDTO, Integer> conditionsCol;
    @FXML
    private TableColumn<MultipleDTO, Integer> thenCol;
    @FXML
    private TableColumn<MultipleDTO, Integer> elseCol;
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
        logicCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLogic()));
        conditionsCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCondNum()));
        thenCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getThenSize()));
        elseCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getElseSize()));


    }
    public void updateTableView(MultipleDTO multipleDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<MultipleDTO> data = FXCollections.observableArrayList(multipleDTO);
        tableViewComponent.setItems(data);
    }



}
