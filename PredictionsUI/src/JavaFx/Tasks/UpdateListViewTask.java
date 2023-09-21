package JavaFx.Tasks;

import Defenitions.simulationViewDTO;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class UpdateListViewTask extends Task<Void> {
    private final ObservableList<simulationViewDTO> observableList;
    private Engine engineCopy;

    private Consumer<simulationViewDTO> simulationViewDTOConsumer;

    private static final long SLEEP_DURATION = 1000;

    public UpdateListViewTask(ObservableList<simulationViewDTO> observableList, Engine engineCopy, Consumer<simulationViewDTO> simulationViewDTOConsumer) {
        this.observableList = observableList;
        this.engineCopy = engineCopy;
        this.simulationViewDTOConsumer = simulationViewDTOConsumer;
    }


    protected Void call() throws Exception {
        while (!isCancelled()) {
            Platform.runLater(() -> {
                for (simulationViewDTO newDTO : engineCopy.getSimulationsView()) {
                    Optional<simulationViewDTO> existingDTO = observableList.stream()
                            .filter(dto -> dto.getGuid().equals(newDTO.getGuid()))
                            .findFirst();

                    if (existingDTO.isPresent()) {
                        simulationViewDTO currentDTO = existingDTO.get();

                        if (currentDTO.getState() != newDTO.getState()) {
                            currentDTO.setState(newDTO.getState());
                            simulationViewDTOConsumer.accept(currentDTO);
                        }
                    } else {
                        observableList.add(newDTO);
                    }
                }
            });

            Thread.sleep(SLEEP_DURATION);
        }
        return null;
    }

}