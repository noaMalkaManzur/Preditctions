package JavaFx.Tasks;

import Defenitions.ProgressSimulationDTO;
import JavaFx.SubComponents.resultTab.ResultTabController;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class UpdateExeDetailsTask extends Task<ProgressSimulationDTO> {
    private Engine engineCopy;
    private  ResultTabController resultTabController;

    public UpdateExeDetailsTask(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
        this.engineCopy = resultTabController.getEngine();
    }

    @Override
    protected ProgressSimulationDTO call() throws Exception {
        Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Platform.runLater(() -> {
                        String simGuid = resultTabController.getSelectedGuid();
                        if (isCancelled()) {
                            // Do not update the UI if the task is canceled
                            return;
                        }
                        ProgressSimulationDTO progressDTO = engineCopy.getSimulationInfo().get(simGuid).getProgressDTO();
                        updateValue(progressDTO);
                    });
                } catch (Exception e) {
                    // Handle exceptions here (e.g., log or display an error message)
                }
            }
        }, 0, 500);

        while (!isCancelled()) {
            Thread.sleep(100);
        }
        myTimer.cancel();
        return null;
    }


}
