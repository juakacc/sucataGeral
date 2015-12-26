package br.com.geral.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.geral.bin.Peca;

public class PecaTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 7362317573579308853L;
	
	private List<Peca> pecas;
	
	public PecaTableModel(List<Peca> pecas) {
		this.pecas = pecas;
	}
	
	@Override
	public int getRowCount() {
		return pecas.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		
		Peca peca = pecas.get(linha);
		
		switch (coluna) {
		case 0:
			return peca.getId();

		case 1:
			return peca.getDescricao();
			
		case 2:
			return peca.getMarca();
			
		case 3:
			return peca.getModelo();
		}
		return "";
	}
	
	/**
	 * Método para nomear as colunas da tabela,
	 * para melhor visualização.
	 * */
	@Override
	public String getColumnName(int coluna) {
		
		switch(coluna) {
		case 0:
			return "ID";
		case 1:
			return "Descrição";
		case 2:
			return "Marca";
		case 3:
			return "Modelo";
		}
		return "";
	}
}