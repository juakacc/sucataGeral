package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContatoDao {

	private Connection conexao;
	
	public ContatoDao() {
		conexao = new FactoryConnection().getConnection();
	}
	
	/* adiciona um novo contato a tabela de contatos */
	public void adicionaContato(Contato contato) {
		
		String sql = "insert into contatos " +
				"(nome, email, endereco, dataNascimento) " +
				"values (?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			//seta os dados
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			Calendar data = contato.getData();
			stmt.setDate(4, new Date(
					data.getTimeInMillis()));
			
			//executa e fecha a conexão
			stmt.execute();
			System.out.println("Executou ...");
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new ContatoDaoException();
		}
	}
	
	/* Exibi todos os contatos da tabela */
	public void exibirContatos() {
		
		String sql = "select * from contatos";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String nome = rs.getString(2);
				String email = rs.getString(3);
				
				System.out.println("nome: " + nome + " - email: " + email);
			}
			rs.close();
			stmt.close();	
		} catch (SQLException e) {
			throw new ContatoDaoException();
		}
	}
	
	/* Recupera todos os contatos da tabela */
	public List<Contato> getContatos() {
		List<Contato> contatos = new ArrayList<>();
		
		String sql = "select * from contatos";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Contato c = new Contato();
				c.setId(rs.getLong("id"));
				c.setNome(rs.getString("nome"));
				c.setEmail(rs.getString("email"));
				c.setEndereco(rs.getString("endereco"));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				c.setData(data);
				
				contatos.add(c);
			}
			
			rs.close();
			stmt.close();
			return contatos;			
		} catch (SQLException e) {
			throw new ContatoDaoException();
		}
	}
	
	/* Recupera um contato de acordo com o ID informado */
	public Contato getContatoPorId(int id) {
		
		String sql = "select * from contatos where id = " + id;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Contato contato = new Contato();
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(rs.getDate("dataNascimento").getTime());
				contato.setData(c);
				
				return contato;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new ContatoDaoException();
		}
	}
	
	/* Atualiza as informações de um determinado contato
	 * de acordo com o seu ID */
	public void alterar(Contato contato) {
		
		String sql = "update contatos set nome = ?, email = ?, endereco = ?, dataNascimento = ?" +
				"where id = ?";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, 
					new Date(contato.getData().getTimeInMillis()));
			stmt.setLong(5, contato.getId());
			
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new ContatoDaoException();
		}
	}
	
	public void remove(Contato contato) {
		
		String sql = "delete from contatos where id = ?";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, contato.getId());
			
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new ContatoDaoException();
		}
		
	}

}
