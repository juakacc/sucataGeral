package dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Contato {

	private long id;
	private String nome;
	private String email;
	private String endereco;
	private Calendar data;
	
	public Contato() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the data
	 */
	public Calendar getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Calendar data) {
		this.data = data;
	}

	@Override
	public String toString() {
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return "Contato [ID:" + id + ", nome = " + nome + ", email = " + email
				+ ", endereco = " + endereco + ", data = " + formato.format(data.getTime()) + "]";
	}
	
	

}
