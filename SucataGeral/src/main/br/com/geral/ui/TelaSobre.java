package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class TelaSobre extends Tela implements ActionListener {

	public static void main(String[] args) {
		new TelaSobre(null).montarTela();
	}
	
	private JButton ok;
	private JLabel icone;
	private JTextPane area;
	
	
	public TelaSobre(JFrame janelaChamadora) {
		super("Sobre o Sucata.Geral");
		this.janelaChamadora = janelaChamadora;
	}

	public void montarTela() {
		prepararJanela();
		prepararPainel();
		prepararBotao();
		prepararIcone();
		prepararTexto();
		
		mostrarJanela();
	}

	private void prepararTexto() {
		area = new JTextPane();
		area.setEditable(false);
		area.setBackground(Color.YELLOW);
		area.setFont(new Font("consolas", Font.BOLD, 14));
		
		String texto = "Um cliente de gerenciamento básico de " +
						"sucata de eletrônicos feito em Java." +
						"\nLicensa GPL.\nBase de dados Mysql." +
						"\n\nAutor: Joaquim Aníbal Rocha Neto";
		
		area.setText(texto);
		painelPrincipal.add(area, BorderLayout.CENTER);
	}

	private void prepararIcone() {
		icone = new JLabel(new ImageIcon("geral.png"));
		painelPrincipal.add(icone, BorderLayout.NORTH);
	}

	private void prepararBotao() {
		ok = new JButton("OK");
		ok.setFont(new Font("consolas", Font.BOLD, 14));
		ok.addActionListener(this);
		
		painelPrincipal.add(ok, BorderLayout.SOUTH);
	}

	protected void mostrarJanela() {
		janela.setSize(250, 300);
		janela.setBackground(Color.YELLOW);
		painelPrincipal.setBackground(Color.YELLOW);
		super.mostrarJanela();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fecharJanela();
	}
}
