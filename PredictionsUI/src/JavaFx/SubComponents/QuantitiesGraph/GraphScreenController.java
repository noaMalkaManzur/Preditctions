package JavaFx.SubComponents.QuantitiesGraph;

import Instance.EntityPopGraphDTO;
import Instance.InstancesPerTickDTO;
import JavaFx.SubComponents.exeResults.ExeResultsController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GraphScreenController
{
    private ExeResultsController exeResultsController;

    @FXML
    private ListView<String> entLstView;
    @FXML
    private LineChart<Integer,Integer> entPopGraph;
    public void setExeResultsTab(ExeResultsController exeResultsController) {
        this.exeResultsController = exeResultsController;
    }
    @FXML
    private void selectedItem() {
        String selectedItem = entLstView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            EntityPopGraphDTO graphDTO = exeResultsController.getGraphDTO();

            List<InstancesPerTickDTO> filteredList = graphDTO.getGraphData().stream()
                    .filter(map -> map.containsKey(selectedItem))
                    .map(map -> map.get(selectedItem))
                    .collect(Collectors.toList());

            XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
            series.setName(selectedItem);

            for (InstancesPerTickDTO dto : filteredList) {
                series.getData().add(new XYChart.Data<>(dto.getTick(), dto.getAmount()));
            }

            entPopGraph.getData().clear();
            entPopGraph.getData().add(series);
        }
    }
    public void initEntList() {
        if(entLstView.getItems().size() > 0)
            entLstView.getItems().clear();
        Collection<String> EntitiesDTO = exeResultsController.getEntityDTO();
        entLstView.getItems().addAll(EntitiesDTO);
    }
}
