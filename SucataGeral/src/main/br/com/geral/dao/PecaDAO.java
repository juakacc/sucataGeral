package br.com.geral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.geral.bin.Peca;
import br.com.geral.excecoes.PecaDaoException;
import br.com.geral.excecoes.PecaNotFoundException;

/**
 * Classe para representar o CRUD de peças do usuário.
 * Essa classe trabalha com o padrão de projeto Singleton,
 * ou seja, existe apenas uma instancia do repositório, 
 * as classes recuperam essa instancia apartir do método disponível.
 * */
public class PecaDAO {
	
	/**
	 * Instancia do repositório.
	 * */
	private static PecaDAO instancia;
	
	/**
	 * @return Instancia de PecaDAO
	 * */
	public static PecaDAO getInstancia() {
		if(instancia == null) {
			instancia = new PecaDAO();
		}
		return instancia;
	}
	
	/**
	 * Conexão com o banco de dados.
	 * */
	private Connection conexao;

	/**
	 * Inicializa a conexão com o banco de dados.
	 * */
	private PecaDAO() {
		conexao = new FactoryConnection().getConnection();
	}
	
	
	/**
	 * Adiciona uma nova peça ao conjunto de peças do usuário
	 * @param peca - Peça a ser adicionada
	 * */
	public boolean adicionar(Peca peca) {
		String sql = "insert into pecas (descricao, marca, modelo)" +
				"values (?, ?, ?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			stmt.setString(1, peca.getDescricao());
			stmt.setString(2, peca.getMarca());
			stmt.setString(3, peca.getModelo());
			
			stmt.execute();
			stmt.close();
			
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Remove uma peça do conjunto de peças do usuário
	 * @param peca - Peça a ser removida
	 * */
	public boolean remover(Peca peca) {
		String sql = "delete from pecas where id = ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, peca.getId());
			
			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Atualiza uma determinada peça de acordo com o ID informado
	 * @param peca - peça que vai ser atualizada
	 * @return True se atualizar, False caso contrário.
	 * */
	public boolean atualizar(Peca peca) {
		String sql = "update pecas set descricao = ?, marca = ?, " +
				"modelo = ? where id = ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, peca.getDescricao());
			stmt.setString(2, peca.getMarca());
			stmt.setString(3, peca.getModelo());
			stmt.setInt(4, peca.getId());
			
			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e) {
			return false;
		}	
	}

	/**
	 * Recupera todas as peças do usuário
	 * @return Peças do usuário
	 * */
	public List<Peca> getPecas() {
		String sql = "select * from pecas";
		List<Peca> pecas = new ArrayList<>();
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Peca peca = new Peca();
				peca.setId(rs.getInt("id"));
				peca.setDescricao(rs.getString("descricao"));
				peca.setModelo(rs.getString("modelo"));
				peca.setMarca(rs.getString("marca"));
			
				pecas.add(peca);
			}
			rs.close();
			stmt.close();
			return pecas;
		} catch (SQLException e) {
			// se explodir excecão eu tento recuperar do backup
			GerenciaXML gerenciador = new GerenciaXML("pecas.xml");
			pecas = gerenciador.recuperarBackup();
			return pecas;
		}
	}
	
	/**
	 * Recupera uma determinada peça de acordo com o ID informado.
	 * @param id - ID da peça a ser pesquisada
	 * @return A Peça caso seja encontrada.
	 * @throws PecaNotFoundException 
	 * */
	public Peca getPeca(int id) throws PecaNotFoundException {
		String sql = "select * from pecas where id = ?";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Peca peca = new Peca();
				
				peca.setId(rs.getInt("id"));
				peca.setMarca(rs.getString("marca"));
				peca.setModelo(rs.getString("modelo"));
				peca.setDescricao(rs.getString("descricao"));
				
				return peca;
			} else {
				throw new PecaNotFoundException();
			}
		} catch (SQLException e) {
			throw new PecaNotFoundException();
		}
	}
	
	/**
	 * Recupera uma lista com as peças com o mesmo nome
	 * @throws PecaNotFoundException
	 * @throws PecaDaoException 
	 * */
	public List<Peca> recuperarPorDescricao(String descricao) throws PecaDaoException {
		List<Peca> pecas = new ArrayList<>();
		
		String sql = "select * from pecas where descricao = ?";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, descricao);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Peca peca = new Peca();
				
				peca.setId(rs.getInt("id"));
				peca.setDescricao(rs.getString("descricao"));
				peca.setMarca(rs.getString("marca"));
				peca.setModelo(rs.getString("modelo"));
				
				pecas.add(peca);
			}
			
			rs.close();
			stmt.close();
			return pecas;
		} catch (SQLException e) {
			throw new PecaDaoException();
		}
	}
	
	/**
	 * Recupera uma lista com as peças com a mesma marca
	 * @throws PecaDaoException 
	 * @throws PecaNotFoundException
	 * */
	public List<Peca> recuperarPorMarca(String marca) throws PecaDaoException {
		List<Peca> pecas = new ArrayList<>();
		String sql = "select * from pecas where marca = ?";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, marca);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Peca peca = new Peca();
				
				peca.setId(rs.getInt("id"));
				peca.setDescricao(rs.getString("descricao"));
				peca.setMarca(rs.getString("marca"));
				peca.setModelo(rs.getString("modelo"));
				
				pecas.add(peca);
			}
			rs.close();
			stmt.close();
			
			return pecas;
		} catch (SQLException e) {
			throw new PecaDaoException();
		}
	}
	
	/**
	 * Recupera uma lista com as peças com o mesmo modelo
	 * @throws PecaDaoException 
	 * @throws 
	 * */
	public List<Peca> recuperarPorModelo(String modelo) throws PecaDaoException {
		List<Peca> pecas = new ArrayList<>();
		String sql = "select * from pecas where modelo = ?";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, modelo);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Peca peca = new Peca();
				
				peca.setId(rs.getInt("id"));
				peca.setDescricao(rs.getString("descricao"));
				peca.setMarca(rs.getString("marca"));
				peca.setModelo(rs.getString("modelo"));
				
				pecas.add(peca);
			}
			rs.close();
			stmt.close();
			
			return pecas;
		} catch (SQLException e) {
			throw new PecaDaoException();
		}
	}

	public void fecharConexao() {
		try {
			GerenciaXML gerenciador = new GerenciaXML("pecas.xml");
			gerenciador.gravarBackup(getPecas());
			
			conexao.close();
		} catch (SQLException e) {
			System.err.println("Ocorreu um erro ao fechar a " +
					"conexão com o banco de dados");
		}		
	}


	public void removerAll() {
		String sql = "delete from pecas where id > 0";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}