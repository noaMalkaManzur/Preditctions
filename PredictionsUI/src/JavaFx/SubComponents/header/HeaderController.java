package JavaFx.SubComponents.header;

import JavaFx.App.PredictionsAppController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class HeaderController {
    private PredictionsAppController mainController;
    @FXML
    private Button loadXmlButton;
    @FXML
    private TextField xmlPathTextField;
    @FXML
    private Label QueueSizeLbl;
    @FXML
    private Label RunningSimLbl;
    @FXML
    private Label FinishedSimLbl;



    @FXML
    void loadXmlFileButtonPressedHandler(ActionEvent event) {
        FileChooser fileChooserBtn = new FileChooser();
        fileChooserBtn.setTitle("XML File Chooser");
        fileChooserBtn.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooserBtn.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        mainController.readWorldData(absolutePath);
    }

    public void setMainController(PredictionsAppController mainController) {
        this.mainController = mainController;
    }
    public void bindHeaderToFullApp(){
        xmlPathTextField.textProperty().bind(mainController.selectedFileProperty());
        //QueueSizeLbl.textProperty().bind(Bindings.format("%d",mainController.getQueueSizeLabel()));
    }
}

