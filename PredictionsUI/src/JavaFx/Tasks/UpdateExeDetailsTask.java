package JavaFx.Tasks;

import Defenitions.ProgressSimulationDTO;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class UpdateExeDetailsTask extends Task<ProgressSimulationDTO> {
    private Engine engineCopy;
    private String simGuid;
    private static final long SLEEP_DURATION = 1000;

    public UpdateExeDetailsTask(Engine engineCopy, String simGuid) {
        this.engineCopy = engineCopy;
        this.simGuid = simGuid;
    }

    @Override
    protected ProgressSimulationDTO call() throws Exception {
        Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ProgressSimulationDTO progressDTO = engineCopy.getSimulationInfo().get(simGuid).getProgressDTO();
                    updateValue(progressDTO);
                });
            }
        }, 0, 500);

        while (!isCancelled()) {
            Thread.sleep(100);
        }
        myTimer.cancel();
        return null;
    }
}
