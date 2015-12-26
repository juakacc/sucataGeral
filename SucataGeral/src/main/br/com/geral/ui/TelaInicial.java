package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.geral.bin.Peca;
import br.com.geral.dao.PecaDAO;

public class TelaInicial implements ActionListener {

	public static void main(String[] args) {
		new TelaInicial().montarTela();
	}
	
	private JFrame janela;
	private JPanel painelPrincipal, painelBotoes;
	private JButton[] botao;
	private JTable tabela;
	private JMenuBar barraStatus;
	
	private PecaDAO repositorio;
	private JLabel status;
	
	public TelaInicial() {
		this.repositorio = PecaDAO.getInstancia();
	}
	
	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();
		prepararTabela();
		
		prepararBotoes();
		prepararBarraStatus();
		
		mostrarJanela();
	}

	private void mostrarJanela() {
		janela.pack();
		janela.setSize(500, 600);
		janela.setVisible(true);
	}

	private void prepararBarraStatus() {
		barraStatus = new JMenuBar();
		status = new JLabel();
		alteraStatus("Selecione uma peça e escolha uma ação.");
		status.setFont(new Font("consolas", Font.BOLD, 14));
		barraStatus.add(status);
		
		painelPrincipal.add(barraStatus, BorderLayout.SOUTH);
	}

	private void prepararTabela() {
		tabela = new JTable();
		tabela.setFont(new Font("arial", Font.PLAIN, 12));
		
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().add(tabela);
		atualizarTabela();
		painelPrincipal.add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Atualiza as informações apresentadas na tabela.
	 * Usada após o usuário realizar alguma alteração no bd.
	 */
	private void atualizarTabela() {
		List<Peca> pecas = repositorio.getPecas();
		PecaTableModel modelo = new PecaTableModel(pecas);
		tabela.setModel(modelo);
	}

	private void prepararBotoes() {
		botao = new JButton[6];
		String[] nomesDosBotoes = {"Adicionar", "Remover", "Editar", "Listar", "Pesquisar", "Sair"};
		Character[] atalhos = {'a', 'r', 'e','l','p','s'};
		painelBotoes = new JPanel(new GridLayout(2, 3, 5, 5));
		
		for (int i = 0; i < botao.length; i++) {
			botao[i] = new JButton(nomesDosBotoes[i]);
			botao[i].setFont(new Font("consolas", Font.BOLD, 14));
			botao[i].setMnemonic(atalhos[i]);
			painelBotoes.add(botao[i]);
			
			botao[i].addActionListener(this);
		}
		painelPrincipal.add(painelBotoes, BorderLayout.NORTH);
	}

	private void prepararPainelPrincipal() {
		painelPrincipal = new JPanel(new BorderLayout(5, 5));
		janela.add(painelPrincipal);
	}

	private void prepararJanela() {
		janela = new JFrame("SUCATA - Geral.Info -> 0.1v");
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int linha;
		Peca peca;
		switch (e.getActionCommand()) {
		
		case "Adicionar":
			new TelaAdicionar(janela).montarTela();
			atualizarTabela();
			break;
			
		case "Remover":
			linha = tabela.getSelectedRow();
			
			if (linha >= 0) {
				peca = recuperarPecaDaLinha(tabela, linha);
				
				int opcao = JOptionPane.showConfirmDialog(janela, "Deseja realmente remover essa peça?");
				if (opcao == JOptionPane.YES_OPTION) {
					if (repositorio.remover(peca)) {
						JOptionPane.showMessageDialog(null, "Peça removida com sucesso.");
						atualizarTabela();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor selecione uma peça");
			}
			break;
			
		case "Editar":
			linha = tabela.getSelectedRow();
			if (linha >= 0) {
				peca = recuperarPecaDaLinha(tabela, linha);
				new TelaEditar(janela, peca).montarTela();
				atualizarTabela();
			} else {
				JOptionPane.showMessageDialog(null, "Por favor selecione uma peça");
			}
			break;
			
		case "Listar":
			// exibir na tabela
			break;
			
		case "Pesquisar":
			
			new TelaPesquisar(janela).montarTela();
			atualizarTabela();
			break;
			
		case "Sair":
			fecharAplicacao();
			break;
			
		default:
			break;
		}
		
	}

	/**
	 * Recupera um peça da lista de acordo com a linha informada.
	 * @param linha - linha selecionada
	 * @return Peça
	 * */
	public static Peca recuperarPecaDaLinha(JTable tab, int linha) {
		
		return new Peca(Integer.parseInt(tab.getValueAt(linha, 0).toString()), 
				tab.getValueAt(linha, 1).toString(), 
				tab.getValueAt(linha, 2).toString(), 
				tab.getValueAt(linha, 3).toString());
	}

	/**
	 * Altera o texto da barra de status.
	 * @param msg - novo status
	 * */
	private void alteraStatus(String msg) {
		status.setText(msg);
	}

	/**
	 * Procedimentos antes do fechamento da aplicação.
	 * */
	private void fecharAplicacao() {
		//JOptionPane.showMessageDialog(null, "Saindo... Até mais!");
		repositorio.fecharConexao();
		System.exit(0);
		// algum aviso para o usuário
		// fechar conexao com bd e outras coisas ...	
	}
}