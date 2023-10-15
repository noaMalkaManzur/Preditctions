package AdminUI.SubComponents.actions.Replace;

import Defenitions.Actions.Replace.ReplaceDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReplaceScreenController {

    @FXML
    private TableView<ReplaceDTO> tableViewComponent;

    @FXML
    private TableColumn<ReplaceDTO, String> typeCol;

    @FXML
    private TableColumn<ReplaceDTO, String> primaryEntCol;

    @FXML
    private TableColumn<ReplaceDTO, String> secondaryEntCol;

    @FXML
    private TableColumn<ReplaceDTO, String> killCol;

    @FXML
    private TableColumn<ReplaceDTO, String> createCol;

    @FXML
    private TableColumn<ReplaceDTO, String> modeCol;
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
        killCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getToKill()));
        createCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getToCreate()));
        modeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMode().toString()));
    }
    public void updateTableView(ReplaceDTO replaceDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<ReplaceDTO> data = FXCollections.observableArrayList(replaceDTO);
        tableViewComponent.setItems(data);
    }


}
