package AdminUI.AdminFullApp;

import javafx.scene.control.Alert;

public class AdminFullAppController {


    public void showAlert(String message, Alert.AlertType alertType, String Title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
