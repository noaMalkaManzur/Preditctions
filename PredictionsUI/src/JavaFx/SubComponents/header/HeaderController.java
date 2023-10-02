package JavaFx.SubComponents.header;

import JavaFx.App.PredictionsAppController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.omg.CosNaming.BindingIterator;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

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
    private ComboBox<String> skinsComboBox;


    @FXML
    public void initialize() {
    Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {
                ExecutorService myService = mainController.getThreadManager().getThreadExecutor();
                QueueSizeLbl.setText(String.valueOf(((ThreadPoolExecutor)myService).getQueue().size()));
                RunningSimLbl.setText(String.valueOf(((ThreadPoolExecutor)myService).getActiveCount()));
                FinishedSimLbl.setText(String.valueOf((int)((ThreadPoolExecutor)myService).getCompletedTaskCount()));
            });
        }
    }, 0, 1000);

    }

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
        skinsComboBox.setItems(FXCollections.observableArrayList("Dark Mode", "Pistachio Mode","Default"));
        skinsComboBox.getSelectionModel().select(2);
        skinsComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                mainController.changeSkin(skinsComboBox.getValue());
            }
        });
    }
}

