package JavaFx.run;

import JavaFx.App.PredictionsAppController;
import engine.api.Engine;
import engine.impl.EngineImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class PredictionsApp extends Application {
    private Engine engine;
    @Override
    public void start(Stage primaryStage) throws Exception {
        engine = new EngineImpl();

        primaryStage.setTitle("Live Example");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("../App/predictionsApp.fxml");
        fxmlLoader.setLocation(url);
        Parent load = fxmlLoader.load(url.openStream());

        PredictionsAppController predictionsAppController = fxmlLoader.getController();
        predictionsAppController.setEngine(engine);

        Scene scene = new Scene(load, 1000, 850);
        primaryStage.setTitle("Predictions Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
