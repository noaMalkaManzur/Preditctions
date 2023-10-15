package AdminUI.RunAdmin;

import AdminUI.AdminFullApp.AdminFullAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AdminApp extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Live Example");
        FXMLLoader fxmlLoader = new FXMLLoader();

        URL url = getClass().getResource("/AdminUI/AdminFullApp/AdminFullApp.fxml");
        fxmlLoader.setLocation(url);
        Parent load = fxmlLoader.load(url.openStream());

        AdminFullAppController predictionsAppController = fxmlLoader.getController();

        Scene scene = new Scene(load);
        primaryStage.setTitle("Predictions Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        Application.launch(AdminApp.class);
    }
}
