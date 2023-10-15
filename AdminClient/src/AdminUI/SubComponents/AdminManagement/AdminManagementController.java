package AdminUI.SubComponents.AdminManagement;

import exceptions.IllegalThreadNumException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import constants.Constants;


public class AdminManagementController implements Initializable {

    @FXML
    private TextField queueSizeTxt;

    @FXML
    private TextField ThreadNumTxt;

    @FXML
    private AnchorPane detailsScreenComponent;

    @FXML
    private TextField fileNameTxt;

    @FXML
    private TextField finishedSimTxt;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button loadFileBtn;

    @FXML
    private TextField runningSimTxt;

    @FXML
    private Button setThreadsBtn;

    @FXML
    void onLoadFileBtnClicked(ActionEvent event) throws IOException {
        FileChooser fileChooserBtn = new FileChooser();
        fileChooserBtn.setTitle("XML File Chooser");
        fileChooserBtn.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooserBtn.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath().replace("\\","/");
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(Constants.LOAD_FILE_URL+absolutePath)
                .method("POST", body)
                .addHeader("Cookie", "JSESSIONID=0A2C228766172006999B5F2A34A14647")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                showAlert("Loaded file succeeded!", Alert.AlertType.INFORMATION,"Success");
                fileNameTxt.setText(absolutePath.replace("/","\\"));
            } else {
                showAlert("HTTP Request Failed: " + response.code() + " " + response.message(), Alert.AlertType.ERROR,"Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showAlert(String message, Alert.AlertType alertType, String Title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onSetThreadsClicked(ActionEvent event) {
        try {
            int threadCount = Integer.parseInt(ThreadNumTxt.getText());
            if (threadCount < 1) {
                throw new IllegalThreadNumException("Amount of threads cannot be negative!" + Constants.LINE_SEPARATOR);
            } else {
                //Http Post request for thread counters.
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "");
                Request request = new Request.Builder()
                        .url(Constants.THREAD_POOL_URL + threadCount)
                        .method("POST", body)
                        .addHeader("Cookie", "JSESSIONID=0A2C228766172006999B5F2A34A14647")
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        showAlert("Thread pool was set to:" + threadCount + Constants.LINE_SEPARATOR, Alert.AlertType.INFORMATION, "Success");
                    } else {
                        showAlert("HTTP Request Failed: " + response.code() + " " + response.message(), Alert.AlertType.ERROR, "Error");
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch (Exception ex)
        {
            showAlert("Request Failed", Alert.AlertType.ERROR, "Error");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        queueSizeTxt.setText("0");
        runningSimTxt.setText("0");
        finishedSimTxt.setText("0");
    }
}
