package br.com.geral.excecoes;

public class PecaNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PecaNotFoundException() {
		super("Peça não encontrada!");
	}

	public PecaNotFoundException(String msg) {
		super(msg);
	}
}
