package br.puc.mestrado.rebeca.calc;

import br.puc.mestrado.rebeca.exception.ConvergenceException;

public interface Task {

	public void doJob() throws ConvergenceException;
	public void doInit(ProcessTask pt);
	public int getMaxProgress();
	public String taskName();
}
