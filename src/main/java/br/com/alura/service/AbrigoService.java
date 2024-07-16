package br.com.alura.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.interfaces.AbrigoInterface;
import br.com.alura.model.Abrigo;
import br.com.alura.request.RequestHttp;

public class AbrigoService implements AbrigoInterface {
	
	RequestHttp requestHttp;
	
	public AbrigoService(RequestHttp request) {
		this.requestHttp = request;
	}
	
	public void listarAbrigosCadastrados() throws IOException, InterruptedException {

        String uri = "http://localhost:8080/abrigos";
        
        HttpResponse<String> response = requestHttp.executarRequisicaoGet(uri);
        
        String responseBody = response.body();
        Abrigo[] abrigos = new ObjectMapper().readValue(responseBody, Abrigo[].class);
        List<Abrigo> abrigoList = Arrays.stream(abrigos).toList();
        System.out.println("Abrigos cadastrados:");
        for (Abrigo abrigo : abrigoList) {
            System.out.println(abrigo.getId() +" - " + abrigo.getNome());
        }
      
    }
    
	public void cadastrarAbrigo() throws IOException, InterruptedException {
    	System.out.println("Digite o nome do abrigo:");
        String nome = new Scanner(System.in).nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String telefone = new Scanner(System.in).nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = new Scanner(System.in).nextLine();

        Abrigo abrigo = new Abrigo(nome, telefone, email);

        String uri = "http://localhost:8080/abrigos";

        HttpResponse<String> response = requestHttp.executarRequisicaoPost(uri, abrigo);
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }
}
