package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class TelaBoasVindas extends Tela implements ActionListener {
	
	private JTextPane campo;
	private JButton sair, prosseguir;

	public static void main(String[] args) {
		new TelaBoasVindas(null).montarTela();
	}

	public TelaBoasVindas(JFrame janelaChamadora) {
		super("Sucata.Geral");
		this.janelaChamadora = janelaChamadora;
		this.janelaChamadora.setEnabled(false);
	}

	@Override
	protected void montarTela() {
		prepararJanela();
		prepararPainel();
		prepararImagem();
		prepararCampo();
		prepararTexto();
		prepararBotoes();
		
		mostrarJanela();
	}
	
	private void prepararBotoes() {
		sair = new JButton("Sair");
		sair.setFont(new Font("consolas", Font.BOLD, 14));
		sair.setBackground(Color.WHITE);
		sair.addActionListener(this);
		
		prosseguir = new JButton("Prosseguir");
		prosseguir.setFont(new Font("consolas", Font.BOLD, 14));
		prosseguir.addActionListener(this);
		
		JPanel botoes = new JPanel(new GridLayout(1, 2, 5, 5));
		botoes.setBackground(Color.ORANGE);
		botoes.add(prosseguir);
		botoes.add(sair);
		
		painelPrincipal.add(botoes, BorderLayout.SOUTH);
	}

	private void prepararCampo() {
		campo = new JTextPane();
		campo.setFont(new Font("consolas", Font.BOLD, 16));
		campo.setBackground(Color.ORANGE);
		campo.setEditable(false);
	}

	private void prepararTexto() {
		String tratamento = definirTratamento();
		
		String texto = tratamento + ";\n" +
				"Bem vindo ao Gerenciador Sucata.Geral";
		
		campo.setText(texto);
		painelPrincipal.add(campo, BorderLayout.CENTER);	
	}

	private String definirTratamento() {
		
		Calendar agora = Calendar.getInstance();
		int hora = agora.get(Calendar.HOUR_OF_DAY);
		
		if (hora >= 6 && hora < 12) {
			return "Bom dia";
		} else if (hora >= 12 && hora < 18) {
			return "Boa tarde";
		} else if (hora >= 18 && hora < 24) {
			return "Boa noite";
		} else {
			return "Boa madrugada";
		}
	}

	private void prepararImagem() {
		JLabel imagem = new JLabel(new ImageIcon("telaInicial.png"));
		painelPrincipal.add(imagem, BorderLayout.NORTH);
	}

	@Override
	protected void mostrarJanela() {
		janela.setSize(400, 300);
		janela.setBackground(Color.ORANGE);
		painelPrincipal.setBackground(Color.ORANGE);
		super.mostrarJanela();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		
		case "Prosseguir":
			fecharJanela();
			janelaChamadora.setEnabled(true);
			break;
		case "Sair":
			System.exit(0);
			break;
		}
	}

}
