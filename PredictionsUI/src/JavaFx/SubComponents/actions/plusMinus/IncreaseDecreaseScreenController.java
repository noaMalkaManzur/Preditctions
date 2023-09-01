package JavaFx.SubComponents.actions.plusMinus;

import Defenitions.Actions.api.ActionDTO;
import Defenitions.EntityPropDefinitionDTO;
import JavaFx.SubComponents.detailsTab.DetailsTabController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IncreaseDecreaseScreenController {
    DetailsTabController detailsTabController;

    @FXML
    private TableView<ActionDTO> tableViewComponent;

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

}
