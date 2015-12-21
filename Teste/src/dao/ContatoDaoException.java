package dao;

public class ContatoDaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContatoDaoException() {
		super("Ocorreu um erro no banco de dados !");
	}
}
