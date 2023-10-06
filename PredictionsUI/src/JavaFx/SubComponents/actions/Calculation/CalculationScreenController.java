/*
package JavaFx.SubComponents.actions.Calculation;

import Defenitions.Actions.Calculation.CalculationDTO;
import Defenitions.Actions.IncreaseDecrease.IncreaseDecreaseDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CalculationScreenController {
    DetailsTabController detailsTabController;

    @FXML
    private TableView<CalculationDTO> tableViewComponent;
    @FXML
    private TableColumn<CalculationDTO, String> typeCol;
    @FXML
    private TableColumn<CalculationDTO, String> primaryEntCol;
    @FXML
    private TableColumn<CalculationDTO, String> secondaryEntCol;
    @FXML
    private TableColumn<CalculationDTO, String> resPropCol;
    @FXML
    private TableColumn<CalculationDTO, String> firstArgCol;
    @FXML
    private TableColumn<CalculationDTO, String> secondArgCol;
    @FXML
    private TableColumn<CalculationDTO, String> actionCol;

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
        resPropCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResult_Prop()));
        firstArgCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstArg()));
        secondArgCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSecondArg()));
        actionCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMathType().toString()));

    }
    public void updateTableView(CalculationDTO calculationDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<CalculationDTO> data = FXCollections.observableArrayList(calculationDTO);
        tableViewComponent.setItems(data);
    }
    public void setDetailsTabController(DetailsTabController detailsTabController) {
        this.detailsTabController = detailsTabController;
    }
}
*/
