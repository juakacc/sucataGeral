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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.geral.bin.Peca;
import br.com.geral.dao.PecaDAO;
import br.com.geral.excecoes.PecaDaoException;
import br.com.geral.excecoes.PecaNotFoundException;

public class TelaPesquisar implements ActionListener {
	
	public static void main(String[] args) {
		new TelaPesquisar(null);
	}

	private JFrame janelaChamadora;
	private JDialog janela;
	private JPanel painelPrincipal;
	private JComboBox<String> box;
	private JButton pronto, cancelar;
	private JTextField campo;
	
	private PecaDAO repositorio;
	
	
	
	public TelaPesquisar(JFrame janelaChamadora) {
		this.janelaChamadora = janelaChamadora;
		this.repositorio = PecaDAO.getInstancia();		
	}
	
	protected void montarTela() {
		prepararJanela();
		prepararPainel();
		prepararBotoes();
		prepararCampo();
		prepararBox();
		
		mostrarJanela();
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

	private void prepararPainel() {
		painelPrincipal = new JPanel(new BorderLayout(10, 10));		
		janela.add(painelPrincipal);
	}

	private void prepararJanela() {
		janela = new JDialog(janelaChamadora, "Pesquisar Peça", true);
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
	private void mostrarJanela() {
		janela.pack();
		janela.setSize(300, 150);
		janela.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String valor_campo = campo.getText();
		List<Peca> pecas;
		
		switch (e.getActionCommand()) {
		
		case "Pronto":
			if (valor_campo.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Campo vazio.");
			} else {
				//faz o procedimento
				int opcaoEscolhida = box.getSelectedIndex();
				
				switch (opcaoEscolhida) {
				
				case Peca.ID:
					try {
						int id = Integer.parseInt(valor_campo);
						Peca peca = repositorio.getPeca(id);
						pecas = new ArrayList<>();
						pecas.add(peca);
						new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
						// exibir a peça para o usuário ...
					} catch (PecaNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Nenhuma peça encontrada!");
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "ID inválido!");
					}
					
					break;

				case Peca.DESCRICAO:
					try {
						pecas = repositorio.recuperarPorDescricao(valor_campo);
						if (!pecas.isEmpty()) {
							new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
						} else {
							JOptionPane.showMessageDialog(null, "Nenhuma peça encontrada!");
						}
					} catch (PecaDaoException e1) {
						System.err.println(e1.getMessage());
					}
					break;
					
				case Peca.MARCA:
					try {
						pecas = repositorio.recuperarPorMarca(valor_campo);
						if (!pecas.isEmpty()) {
							new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
						} else {
							JOptionPane.showMessageDialog(null, "Nenhuma peça encontrada!");
						}
					} catch (PecaDaoException e1) {
						System.err.println(e1.getMessage());
					}
					break;
					
				case Peca.MODELO:
					try {
						pecas = repositorio.recuperarPorModelo(valor_campo);
						if (!pecas.isEmpty()) {
							new TelaResultadoPesquisa(janelaChamadora, pecas).montarTela();
						} else {
							JOptionPane.showMessageDialog(null, "Nenhuma peça encontrada!");
						}
					} catch (PecaDaoException e1) {
						System.err.println(e1.getMessage());
					}
					break;
				}
			}
			break;

		case "Cancelar":
			fecharJanela();
			break;
		}
	}
	
	/**
	 * Fecha a janela corretamente
	 */
	private void fecharJanela() {
		janela.dispose();
	}

}
