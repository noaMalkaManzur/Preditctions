package JavaFx.Tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import simulation.Impl.SimulationManagerImpl;
import simulation.api.SimulationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateListViewTask extends Task<Void> {
    private final ListView<String> listView;
    private final Map<String, SimulationManager> simMap;
    private List<String> myList = new ArrayList<>();
    private String selectedItem;

    private static final long SLEEP_DURATION = 1000;

    public UpdateListViewTask(ListView<String> listView, Map<String, SimulationManager> simMap) {
        this.listView = listView;
        this.simMap = simMap;
    }

    protected Void call() throws Exception {
        while (!isCancelled()) {
            Platform.runLater(() -> {
                //listView.getItems().clear();

                for (String key : simMap.keySet()) {
                    if(!myList.contains(key)) {
                        myList.add(key);
                        listView.getItems().add(key);
                    }
                }
//                listView.setOnMouseClicked(event -> {
//                    selectedItem = listView.getSelectionModel().getSelectedItem();
//                });
//                if(selectedItem !=null)
//                {
//                    listView.getSelectionModel().select(selectedItem);
//                }
            });

            Thread.sleep(SLEEP_DURATION);
        }
        return null;
    }
}