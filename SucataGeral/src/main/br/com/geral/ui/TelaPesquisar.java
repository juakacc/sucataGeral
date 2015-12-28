package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.geral.bin.Peca;
import br.com.geral.dao.PecaDAO;
import br.com.geral.excecoes.PecaDaoException;
import br.com.geral.excecoes.PecaNotFoundException;

public class TelaPesquisar extends Tela implements ActionListener {
	
	private JComboBox<String> box;
	private JButton pronto, cancelar;
	private JTextField campo;
	
	private PecaDAO repositorio;
	
	public TelaPesquisar(JFrame janelaChamadora) {
		super("Pesquisar Peça");
		this.janelaChamadora = janelaChamadora;
		this.repositorio = PecaDAO.getInstancia();		
	}
	
	@Override
	protected void montarTela() {
		prepararJanela();
		prepararPainel();
		prepararBotoes();
		prepararCampo();
		prepararBox();
		
		mostrarJanela();
	}
	
	@Override
	protected void mostrarJanela() {
		janela.setSize(300, 150);
		super.mostrarJanela();
	}
	
	private void prepararBox() {
		String[] opcoes = {"ID", "Descrição", "Marca", "Modelo"};
		box = new JComboBox<>(opcoes);
		
		painelPrincipal.add(box, BorderLayout.NORTH);
	}

	private void prepararCampo() {
		campo = new JTextField(20);
		campo.setFont(new Font("consolas", Font.PLAIN, 14));
		campo.setBorder(BorderFactory.createTitledBorder("Digite:"));
		
		painelPrincipal.add(campo, BorderLayout.CENTER);
	}

	private void prepararBotoes() {
		pronto = new JButton("Pronto");
		pronto.setFont(new Font("consolas", Font.BOLD, 14));
		pronto.setMnemonic('p');
		pronto.addActionListener(this);
		
		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("consolas", Font.BOLD, 14));
		cancelar.setMnemonic('c');
		cancelar.addActionListener(this);
		
		JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
		painelBotoes.add(pronto);
		painelBotoes.add(cancelar);
		
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String valorCampo = campo.getText();
		
		switch (e.getActionCommand()) {
		
			case "Pronto":
				if (valorCampo.trim().isEmpty()) {
					JOptionPane.showMessageDialog(janela, "Campo vazio.");
				} else {
					int opcaoEscolhida = box.getSelectedIndex();
					administrarEscolha(valorCampo, opcaoEscolhida);
				}
				break;
	
			case "Cancelar":
				fecharJanela();
				break;
		}
	}

	/**
	 * @param valorCampo
	 * @param opcaoEscolhida
	 */
	private void administrarEscolha(String valorCampo, int opcaoEscolhida) {
		List<Peca> pecas;
		switch (opcaoEscolhida) {
		
		case Peca.ID:
			try {
				int id = Integer.parseInt(valorCampo);
				Peca peca = repositorio.getPeca(id);
				pecas = new ArrayList<>();
				pecas.add(peca);
				new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
				// exibir a peça para o usuário ...
			} catch (PecaNotFoundException e1) {
				JOptionPane.showMessageDialog(janela, "Nenhuma peça encontrada!");
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(janela, "ID inválido!");
			}
			
			break;

		case Peca.DESCRICAO:
			try {
				pecas = repositorio.recuperarPorDescricao(valorCampo);
				if (!pecas.isEmpty()) {
					new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
				} else {
					JOptionPane.showMessageDialog(janela, "Nenhuma peça encontrada!");
				}
			} catch (PecaDaoException e1) {
				System.err.println(e1.getMessage());
			}
			break;
			
		case Peca.MARCA:
			try {
				pecas = repositorio.recuperarPorMarca(valorCampo);
				if (!pecas.isEmpty()) {
					new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
				} else {
					JOptionPane.showMessageDialog(janela, "Nenhuma peça encontrada!");
				}
			} catch (PecaDaoException e1) {
				System.err.println(e1.getMessage());
			}
			break;
			
		case Peca.MODELO:
			try {
				pecas = repositorio.recuperarPorModelo(valorCampo);
				if (!pecas.isEmpty()) {
					new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
				} else {
					JOptionPane.showMessageDialog(janela, "Nenhuma peça encontrada!");
				}
			} catch (PecaDaoException e1) {
				System.err.println(e1.getMessage());
			}
			break;
		}
	}
}
