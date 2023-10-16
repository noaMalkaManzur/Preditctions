package AdminUI.SubComponents.actions.Condition;


import Defenitions.Actions.Condition.impl.SingleDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SingleScreenController {


    @FXML
    private TableView<SingleDTO> tableViewComponent;

    @FXML
    private TableColumn<SingleDTO, String> typeCol;

    @FXML
    private TableColumn<SingleDTO, String> primaryEntCol;

    @FXML
    private TableColumn<SingleDTO, String> secondaryEntCol;

    @FXML
    private TableColumn<SingleDTO, String> propCol;

    @FXML
    private TableColumn<SingleDTO, String> operatorCol;

    @FXML
    private TableColumn<SingleDTO, String> valueCol;
    @FXML
    private TableColumn<SingleDTO, Integer> thenCol;
    @FXML
    private TableColumn<SingleDTO, Integer> elseCol;

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
        operatorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOperator()));
        valueCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
        thenCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getThenSize()));
        elseCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getElseSize()));


    }
    public void updateTableView(SingleDTO singleDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<SingleDTO> data = FXCollections.observableArrayList(singleDTO);
        tableViewComponent.setItems(data);
    }


}
