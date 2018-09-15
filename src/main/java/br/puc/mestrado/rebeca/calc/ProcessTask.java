package br.puc.mestrado.rebeca.calc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.TextBuilder;

public class ProcessTask extends javafx.concurrent.Task<Void> {
	
	private static Logger logger = Logger.getLogger(ProcessTask.class);

	private List<Task> list;
	private int maxProgress;
	private int curProgress;

	public ProcessTask() {
		this.list = new ArrayList<Task>();
	}

	public void add(Task t) {
		list.add(t);
		maxProgress += t.getMaxProgress();
	}

	public void processAll() throws ConvergenceException {
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			logger.debug("Staring task: " + task.getClass());

			updateMessage(task.taskName());

			task.doInit(this);
			task.doJob();
		}

		updateMessage("Finished!");
	}

	@Override
	protected Void call() throws Exception {
		try {
			processAll();
			return null;
		} catch (Exception e) {
			logger.debug(TextBuilder.getFistFrameOfThrowable(e));
			updateMessage("Error!");
			e.printStackTrace();
			throw e;
		}
		
	}

	public void updateProgress() {
		curProgress++;
		updateProgress(curProgress, maxProgress);
	}
}
