package br.com.alura.interfaces;

import java.io.IOException;

public interface AbrigoInterface {
	
	void listarAbrigosCadastrados() throws IOException, InterruptedException;
	
	void cadastrarAbrigo() throws IOException, InterruptedException;
}
