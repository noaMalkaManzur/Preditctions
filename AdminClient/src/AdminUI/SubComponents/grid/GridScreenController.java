package AdminUI.SubComponents.grid;

import Defenitions.GridDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GridScreenController {

    @FXML
    private TableView<GridDTO> tableViewComponent;

    @FXML
    private TableColumn<GridDTO, Integer> rowsCol;

    @FXML
    private TableColumn<GridDTO, Integer> colsCol;

    public void initializeTableView() {

        rowsCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRows()));
        colsCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCols()));

    }
    public void updateTableView(GridDTO gridDTO) {
        tableViewComponent.getItems().clear();
        ObservableList<GridDTO> data = FXCollections.observableArrayList(gridDTO);
        tableViewComponent.setItems(data);
    }


}
