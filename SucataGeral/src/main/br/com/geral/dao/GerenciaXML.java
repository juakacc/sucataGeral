package br.com.geral.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import br.com.geral.bin.Peca;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GerenciaXML {

	private String caminho;
	
	public GerenciaXML(String caminho) {
		this.caminho = caminho;
	}
	
	/**
	 * 
	 * */
	public void gravarBackup(List<Peca> pecas) {
		try {
			File file = new File(caminho);
			
			XStream stream = new XStream(new DomDriver());
			stream.alias("peca", Peca.class);
			stream.setMode(XStream.NO_REFERENCES);
			
			PrintStream print = new PrintStream(file);
			print.println(stream.toXML(pecas));
			print.close();
		} catch (FileNotFoundException e) {
			System.err.println("Ocorreu um erro ao gravar o backup.");
		}
	}
	
	/**
	 * 
	 * */
	public List<Peca> recuperarBackup() {
		
		try {
			FileReader reader = new FileReader(new File(caminho));
			
			XStream stream = new XStream(new DomDriver());
			stream.alias("peca", Peca.class);
			@SuppressWarnings("unchecked")
			List<Peca> pecas = (List<Peca>) stream.fromXML(reader);
			reader.close();
			return pecas;
		} catch (FileNotFoundException e) {
			System.err.println("Ocorreu um erro ao recuperar o backup.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
