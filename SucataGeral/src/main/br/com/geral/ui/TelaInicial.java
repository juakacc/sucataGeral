package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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
		prepararMenu();
		prepararPainelPrincipal();
		prepararTabela();
		
		prepararBotoes();
		prepararBarraStatus();
		preparaTitulo();
		
		mostrarJanela();
	}

	private void prepararMenu() {
		JMenuBar barraMenu = new JMenuBar();
		JMenu arquivo, ajuda;
		
		arquivo = new JMenu("Arquivo");
		ajuda = new JMenu("Ajuda");
		
		barraMenu.add(arquivo);
		barraMenu.add(ajuda);
		
		//ARQUIVO
		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener(this);
		arquivo.add(sair);
		
		//AJUDA
		JMenuItem about = new JMenuItem("Sobre Sucata.Geral");
		about.addActionListener(this);
		ajuda.add(about);
		
		janela.setJMenuBar(barraMenu);
	}

	private void preparaTitulo() {
		JLabel titulo = new JLabel("SITUAÇÃO DA SUCATA NO MOMENTO", SwingConstants.CENTER);
		titulo.setFont(new Font("Verdana", Font.BOLD, 20));
		painelPrincipal.add(titulo, BorderLayout.NORTH);
	}

	private void mostrarJanela() {
		janela.pack();
		janela.setSize(500, 600);
		janela.setVisible(true);
		new TelaBoasVindas(janela).montarTela();
	}

	private void prepararBarraStatus() {
		barraStatus = new JMenuBar();
		status = new JLabel();
		alteraStatus("Selecione uma peça e escolha uma ação.");
		status.setFont(new Font("consolas", Font.BOLD, 12));
		barraStatus.add(status);
		
		JPanel painelInferior = new JPanel(new BorderLayout(5, 5));
		painelInferior.add(barraStatus, BorderLayout.SOUTH);
		painelInferior.add(painelBotoes, BorderLayout.CENTER);
		
		painelPrincipal.add(painelInferior, BorderLayout.SOUTH);
	}

	private void prepararTabela() {
		tabela = new JTable();
		tabela.setFont(new Font("arial", Font.PLAIN, 12));
		tabela.setBackground(Color.WHITE);
		
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
		String[] nomesDosBotoes = {"Adicionar", "Remover", "Editar", "Limpar", "Pesquisar", "Sair"};
		Character[] atalhos = {'a', 'r', 'e','l','p','s'};
		painelBotoes = new JPanel(new GridLayout(2, 3, 5, 5));
		
		for (int i = 0; i < botao.length; i++) {
			botao[i] = new JButton(nomesDosBotoes[i]);
			botao[i].setFont(new Font("consolas", Font.BOLD, 14));
			botao[i].setMnemonic(atalhos[i]);
			painelBotoes.add(botao[i]);
			
			botao[i].addActionListener(this);
		}
		botao[5].setBackground(Color.ORANGE);
	}

	private void prepararPainelPrincipal() {
		painelPrincipal = new JPanel(new BorderLayout(5, 5));
		janela.add(painelPrincipal);
	}

	private void prepararJanela() {
		janela = new JFrame("Sucata.Geral -> v0.1");
		janela.setIconImage(new ImageIcon("icon.png").getImage());
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
						JOptionPane.showMessageDialog(janela, "Peça removida com sucesso.");
						atualizarTabela();
					}
				}
			} else {
				JOptionPane.showMessageDialog(janela, "Por favor selecione uma peça");
			}
			break;
			
		case "Editar":
			linha = tabela.getSelectedRow();
			if (linha >= 0) {
				peca = recuperarPecaDaLinha(tabela, linha);
				new TelaEditar(janela, peca).montarTela();
				atualizarTabela();
			} else {
				JOptionPane.showMessageDialog(janela, "Por favor selecione uma peça");
			}
			break;
	
		case "Limpar":
			if (JOptionPane.showConfirmDialog(janela, "Deseja realmente excluir todas as peças?") == JOptionPane.OK_OPTION) {
				repositorio.removerAll();
				atualizarTabela();
			}
			break;
			
		case "Pesquisar":
			
			new TelaPesquisar(janela).montarTela();
			atualizarTabela();
			break;
			
		case "Sair":
			fecharAplicacao();
			break;
			
			
			
		case "Sobre Sucata.Geral":
			new TelaSobre(janela).montarTela();
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
		return new Peca(Integer.parseInt(tab.getValueAt(linha, Peca.ID).toString()), 
				tab.getValueAt(linha, Peca.DESCRICAO).toString(), 
				tab.getValueAt(linha, Peca.MARCA).toString(), 
				tab.getValueAt(linha, Peca.MODELO).toString());
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
		alteraStatus("Fazendo o backup ... Aguarde!");
		//JOptionPane.showMessageDialog(janela, "Saindo... Até mais!");
		repositorio.fecharConexao();
		System.exit(0);
		// algum aviso para o usuário
		// fechar conexao com bd e outras coisas ...	
	}
}