package br.com.geral.bin;

/** As peças terão as seguintes caracteristicas solicitadas:
 * 
 * - ID para fácil localização
 * - nome a que a peça se refere
 * - marca do produto de que foi retirado
 * - modelo do produto de que foi retirado
 * 
 * */
public class Peca {

	private int id;
	private String descricao;
	private String marca;
	private String modelo;
	
	public final static int ID = 0, DESCRICAO = 1, MARCA = 2, MODELO = 3;
	
	
	public Peca(int id, String descricao, String marca, String modelo) {
		this(descricao, marca, modelo);
		this.id = id;
	}
	
	/**
	 * @param id
	 * @param descricao
	 * @param marca
	 * @param modelo
	 */
	public Peca(String descricao, String marca, String modelo) {
		this.descricao = descricao;
		this.marca = marca;
		this.modelo = modelo;
	}
	
	public Peca() { 
	}

	@Override
	public String toString() {
		return "ID: " + getId() + " -> " + getDescricao() + ", " + getMarca() + ", "
				+ getModelo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Peca)) {
			return false;
		}
		Peca temp = (Peca) obj;
		
		return this.getId() == temp.getId();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the nome
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setDescricao(String nome) {
		this.descricao = nome;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
