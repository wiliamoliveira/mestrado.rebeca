package br.puc.mestrado.rebeca.exception;

public class ConvergenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvergenceException(String var, double varValue, double criteria) {
		super("Erro de convergencia para " + var + " " + varValue + " > " + criteria);
	}

	public ConvergenceException(double pui, double pdi, double pm, double puj, double pdj, double sun) {
		super("Erro de convergencia: somatorios dos pesos > 1. Somatorio pui=" + pui + ", pdi=" + pdi + ", pm=" + pm + ", puj=" + puj + ", pdj=" + pdj + " igual a " + sun);
	}

}
