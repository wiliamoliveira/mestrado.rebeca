package br.puc.mestrado.rebeca.gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class RunEventHandler implements EventHandler<WorkerStateEvent> {

	private MainController mainController;

	public RunEventHandler(MainController mainController) {
		this.mainController = mainController;
	}

	public void handle(WorkerStateEvent event) {
		if (event.getEventType() == WorkerStateEvent.WORKER_STATE_SCHEDULED) {
			mainController.setDisableButtons(true);
		} else if (event.getEventType() == WorkerStateEvent.WORKER_STATE_SUCCEEDED) {
			mainController.loadGenerateFFields();
			mainController.setDisableButtons(false);
		} else {
			mainController.setDisableButtons(false);
		}
	}

}
