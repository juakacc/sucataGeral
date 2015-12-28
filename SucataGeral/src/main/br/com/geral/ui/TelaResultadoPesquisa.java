package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.geral.bin.Peca;
import br.com.geral.dao.PecaDAO;

public class TelaResultadoPesquisa extends Tela implements ActionListener {

	private JButton remover, editar, cancelar;
	
	private List<Peca> pecas;
	
	private JTable tabela;
	
	public TelaResultadoPesquisa(JFrame janelaChamadora, List<Peca> pecas) {
		super("Resultados da Pesquisa");
		this.janelaChamadora = janelaChamadora;
		this.pecas = pecas;
	}
	
	protected void montarTela() {
		prepararJanela();
		prepararPainel();
		prepararTabela();
		
		prepararBotoes();
		mostrarJanela();
	}


	private void prepararTabela() {
		tabela = new JTable(new PecaTableModel(pecas));
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().add(tabela);
		
		painelPrincipal.add(scroll, BorderLayout.CENTER);
	}


	protected void mostrarJanela() {
		janela.setSize(400, 200);
		super.mostrarJanela();
	}

	private void prepararBotoes() {
		remover = new JButton("Remover");
		remover.setFont(new Font("consolas", Font.BOLD, 14));
		remover.setMnemonic('r');
		remover.addActionListener(this);
		
		editar = new JButton("Editar");
		editar.setFont(new Font("consolas", Font.BOLD, 14));
		editar.setMnemonic('e');
		editar.addActionListener(this);
		
		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("consolas", Font.BOLD, 14));
		cancelar.setMnemonic('c');
		cancelar.addActionListener(this);
		
		JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 5, 5));
		painelBotoes.add(remover);
		painelBotoes.add(editar);
		painelBotoes.add(cancelar);
		
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int linha;
		Peca peca;
		switch (e.getActionCommand()) {
		
		case "Remover":
			linha = tabela.getSelectedRow();
			
			if (linha >= 0) {
				peca = TelaInicial.recuperarPecaDaLinha(tabela, linha);
				
				int opcao = JOptionPane.showConfirmDialog(janela, "Deseja realmente remover essa peça?");
				if (opcao == JOptionPane.YES_OPTION) {
					if (PecaDAO.getInstancia().remover(peca)) {
						JOptionPane.showMessageDialog(janela, "Peça removida com sucesso.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(janela, "Por favor selecione uma peça");
			}
			break;

		case "Editar":
			linha = tabela.getSelectedRow();
			if (linha >= 0) {
				peca = TelaInicial.recuperarPecaDaLinha(tabela, linha);
				new TelaEditar(janelaChamadora, peca).montarTela();
				// ver ainda o que o vou fazer
			} else {
				JOptionPane.showMessageDialog(janela, "Por favor selecione uma peça");
			}
			break;
			
		case "Cancelar":	
			break;
		}
		fecharJanela();
	}
}