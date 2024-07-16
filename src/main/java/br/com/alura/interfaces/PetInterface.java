package br.com.alura.interfaces;

import java.io.IOException;

public interface PetInterface {
	 void importarArquivoPets() throws IOException, InterruptedException;
	 
	 void listarPetsAbrigo() throws IOException, InterruptedException;
}
