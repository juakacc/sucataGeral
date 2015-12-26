package br.com.geral.excecoes;

public class PecaDaoException extends Exception {
	private static final long serialVersionUID = 1L;

	public PecaDaoException() {
		super("Ocorreu um erro no banco de dados ... Lamentamos!");
	}
}