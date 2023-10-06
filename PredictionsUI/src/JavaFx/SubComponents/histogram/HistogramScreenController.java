/*
package JavaFx.SubComponents.histogram;

import Defenitions.EntityDefinitionDTO;
import Defenitions.StatisticsDTO;
import JavaFx.SubComponents.exeResults.ExeResultsController;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HistogramScreenController
{
    private ExeResultsController exeResultsController;
    @FXML
    private TreeView<String> treeViewComponent;
    @FXML
    private BarChart<Object,Integer> propBarChart;
    @FXML
    private ScrollPane consistencySP;
    @FXML
    private TextField consistencyTxt;
    @FXML
    private TextField propValTxt;
    @FXML
    private ComboBox<String> optionsCmbx;
    @FXML
    private CategoryAxis propAxis;

    public void setExeResultsTab(ExeResultsController exeResultsController) {
        this.exeResultsController = exeResultsController;
    }
    public void populateView() {
        TreeItem<String> root = PopulateEntityBranch();
        treeViewComponent.setRoot(root);
        optionsCmbx.setItems(FXCollections.observableArrayList("Histogram", "Consistency"));
        optionsCmbx.getSelectionModel().select("Histogram");
        consistencySP.setVisible(false);
    }
    public TreeItem<String> PopulateEntityBranch() {
        TreeItem<String> entityBranch = new TreeItem<>("Entities");
        Map<String, EntityDefinitionDTO> EntitiesDTO = exeResultsController.getEntityDTO();

        // Iterate through the entities
        EntitiesDTO.forEach((name, entityDefinition) -> {
            TreeItem<String> entityItem = new TreeItem<>(name);

            // Iterate through the property definitions and add them as children
            entityDefinition.getPropertyDefinition().forEach((propName, propDefinition) -> {
                TreeItem<String> propItem = new TreeItem<>(propName);
                entityItem.getChildren().add(propItem);
            });

            entityBranch.getChildren().add(entityItem);
        });

        return entityBranch;
    }
    @FXML
    public void selectedItem()
    {
        TreeItem<String> selectedItem =  treeViewComponent.getSelectionModel().getSelectedItem();
        if(selectedItem != null && selectedItem != treeViewComponent.getRoot() && selectedItem.isLeaf())
        {
            String option = optionsCmbx.getSelectionModel().getSelectedItem();
            if(option != null)
            {
                if(option.equals("Histogram"))
                {
                    consistencySP.setVisible(false);
                    propBarChart.setVisible(true);
                    setBarChart(selectedItem.getParent().getValue(),selectedItem.getValue());

                }
                else if(option.equals("Consistency"))
                {
                    propBarChart.setVisible(false);
                    consistencySP.setVisible(true);
                    setConsistency(selectedItem.getParent().getValue(),selectedItem.getValue());
                }
            }

        }

    }

    private void setConsistency(String parent, String selectedItem) {
        consistencyTxt.clear();
        propValTxt.clear();
        StatisticsDTO StatDTO = exeResultsController.getStaticDTO(parent,selectedItem);
        consistencyTxt.setText(String.valueOf(StatDTO.getAverageTickNumbSinceChangeValue()));
        propValTxt.setText(String.valueOf(StatDTO.getAverageValue()));
    }

    private void setBarChart(String parent, String selectedItem) {
        HistogramByPropertyEntitiesDTO histogram = exeResultsController.getHistogramByProp(parent, selectedItem);
        Map<Object, Integer> data = histogram.getHistogramByProperty();
        XYChart.Series<Object, Integer> series = new XYChart.Series<>();

        // Add data to the series
        for (Map.Entry<Object, Integer> entry : data.entrySet()) {
            // Convert the key to String explicitly
            String keyAsString = entry.getKey().toString();
            series.getData().add(new XYChart.Data<>(keyAsString, entry.getValue()));
        }

        Font xAxisLabelFont = Font.font("Arial", 15);
        propAxis.setTickLabelFont(xAxisLabelFont);

        propBarChart.getData().clear();
        propBarChart.getData().add(series);
    }

}
*/
