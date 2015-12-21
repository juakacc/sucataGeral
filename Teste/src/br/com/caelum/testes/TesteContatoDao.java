package br.com.caelum.testes;

import java.util.Calendar;
import java.util.List;

import dao.Contato;
import dao.ContatoDao;

public class TesteContatoDao {
	
	public static void main(String[] args) {
		Contato c = new Contato();
		c.setNome("Joaquim");
		c.setEmail("juakacc@gmail.com");
		c.setEndereco("Rua Divone");
		c.setData(Calendar.getInstance());
		c.setId(2);
		
		ContatoDao banco = new ContatoDao();
		
//		banco.adicionaContato(c);
		
		Contato c2 = new Contato();
		c2.setId(2);
		c2.setNome("Julia");
		c2.setEmail("julheba@gmail.com");
		c2.setEndereco("Rua de cima");
		c2.setData(Calendar.getInstance());
		
		banco.alterar(c2);
		System.out.println(c.getId());
		banco.remove(c);
		
		List<Contato> contatos = banco.getContatos();
		for (Contato contato : contatos) {
			System.out.println(contato.toString() + "\n");
		}
		System.out.println(banco.getContatoPorId(2));
	}

}
