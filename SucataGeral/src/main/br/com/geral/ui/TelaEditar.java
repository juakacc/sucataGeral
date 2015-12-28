package br.com.geral.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.geral.bin.Peca;
import br.com.geral.dao.PecaDAO;

public class TelaEditar extends Tela implements ActionListener {
	
	private JPanel painelLabelText;
	private JTextField descricao, marca, modelo;	
	private JLabel descricaoL, marcaL, modeloL;
	private JButton pronto, cancelar;
	
	private PecaDAO repositorioPecas;
	private Peca peca;
	
	public TelaEditar(JFrame janelaChamadora, Peca peca) {
		super("Editar Peça");
		this.janelaChamadora = janelaChamadora;
		this.repositorioPecas = PecaDAO.getInstancia();
		this.peca = peca;
	}

	protected void montarTela() {
		prepararJanela();
		prepararPainel();
		
		prepararCampos();
		prepararBotoes();
		prepararLabels();
		prepararPainelCamposELabels();
		mostrarJanela();
	}

	protected void mostrarJanela() {
		janela.setSize(300, 200);
		super.mostrarJanela();
	}

	private void prepararPainelCamposELabels() {
		painelLabelText = new JPanel(new GridLayout(3, 2));
		painelLabelText.add(descricaoL);
		painelLabelText.add(descricao);
		painelLabelText.add(marcaL);
		painelLabelText.add(marca);
		painelLabelText.add(modeloL);
		painelLabelText.add(modelo);
		
		painelPrincipal.add(painelLabelText, BorderLayout.CENTER);
	}

	private void prepararLabels() {
		descricaoL = new JLabel("DESCRIÇÃO:");
		descricaoL.setFont(new Font("consolas", Font.BOLD, 14));
		
		marcaL = new JLabel("MARCA:");
		marcaL.setFont(new Font("consolas", Font.BOLD, 14));
		
		modeloL = new JLabel("MODELO:");
		modeloL.setFont(new Font("consolas", Font.BOLD, 14));
	}

	private void prepararCampos() {
		descricao = new JTextField(20);
		descricao.setFont(new Font("consolas", Font.PLAIN, 14));
		descricao.setText(peca.getDescricao());
		
		modelo = new JTextField(10);
		modelo.setFont(new Font("consolas", Font.PLAIN, 14));
		modelo.setText(peca.getModelo());
		
		marca = new JTextField(10);
		marca.setFont(new Font("consolas", Font.PLAIN, 14));
		marca.setText(peca.getMarca());
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

	private Peca recuperarPeca() {
		String d = descricao.getText(); 
		String m = marca.getText();
		String mo = modelo.getText();
		
		return new Peca(peca.getId(), d, m, mo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		
		case "Pronto":
			if (descricao.getText().trim().isEmpty() || 
				marca.getText().trim().isEmpty() || 
				modelo.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(janela, "Informações incompletas");
			} else {
				Peca peca = recuperarPeca();
				repositorioPecas.atualizar(peca);
				fecharJanela();
			}
			break;

		case "Cancelar":
			fecharJanela();
			break;
		}
	}
}