package br.com.geral.ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Tela {

	protected JFrame janelaChamadora;
	protected JDialog janela;
	protected JPanel painelPrincipal;
	
	private String titulo;
	
	public Tela(String titulo) {
		this.titulo = titulo;
	}
	
	protected abstract void montarTela();
	
	protected void prepararPainel() {
		painelPrincipal = new JPanel(new BorderLayout(5, 5));	
		janela.add(painelPrincipal);
	}

	protected void prepararJanela() {
		janela = new JDialog(janelaChamadora, titulo, true);
		janela.setResizable(false);
		janela.setIconImage(new ImageIcon("icon.png").getImage());
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	protected void mostrarJanela() {
		//janela.pack();
		janela.setLocationRelativeTo(janelaChamadora);
		janela.setVisible(true);
	}
	
	/**
	 * Fecha a janela corretamente
	 */
	protected void fecharJanela() {
		janela.dispose();
	}
}
