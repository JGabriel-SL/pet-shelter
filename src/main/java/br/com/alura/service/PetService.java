package br.com.alura.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.alura.interfaces.PetInterface;
import br.com.alura.model.Pet;
import br.com.alura.request.RequestHttp;

public class PetService implements PetInterface {
	
	RequestHttp requestHttp;
	
	public PetService(RequestHttp request) {
		this.requestHttp = request;
	}

	public void listarPetsAbrigo() throws IOException, InterruptedException {
    	System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        HttpClient client = HttpClient.newHttpClient();
        String uri = "http://localhost:8080/abrigos/" +idOuNome +"/pets";
        
        HttpResponse<String> response = requestHttp.executarRequisicaoGet(uri);

        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
        }
        
        String responseBody = response.body();
        Pet[] pets = new ObjectMapper().readValue(responseBody, Pet[].class);
        List<Pet> petsList = Arrays.stream(pets).toList();
        System.out.println("Pets cadastrados:");
        for (Pet pet : petsList) {
            System.out.println(pet.getId() +" - " + pet.getTipo() + " - " + pet.getNome() +" - " + pet.getRaca() +" - " + pet.getIdade() +" ano(s)");
        }
	}
    
	public void importarArquivoPets() throws IOException, InterruptedException {
    	System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = new Scanner(System.in).nextLine();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " +nomeArquivo);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campos = line.split(",");
            String tipo = campos[0];
            String nome = campos[1];
            String raca = campos[2];
            int idade = Integer.parseInt(campos[3]);
            String cor = campos[4];
            Float peso = Float.parseFloat(campos[5]);

            String tipoAux = tipo.toUpperCase();        
            Pet pet = new Pet(tipoAux, nome, raca, idade, cor, peso);

            HttpClient client = HttpClient.newHttpClient();
            String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";

            HttpResponse<String> response = requestHttp.executarRequisicaoPost(uri, pet);
            
            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + nome);
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + nome);
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }
}
