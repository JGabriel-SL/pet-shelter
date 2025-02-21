package br.com.alura.request;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class RequestHttp {
	
	   public HttpResponse<String> executarRequisicaoGet(String uri) throws IOException, InterruptedException {
	    	 HttpClient client = HttpClient.newHttpClient();

	    	 HttpRequest request = HttpRequest.newBuilder()
	                 .uri(URI.create(uri))
	                 .method("GET", HttpRequest.BodyPublishers.noBody())
	                 .build();
	         return client.send(request, HttpResponse.BodyHandlers.ofString());
	    }
	    
	    public HttpResponse<String> executarRequisicaoPost(String uri, Object obj) throws IOException, InterruptedException {
	    	HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(uri))
	                .header("Content-Type", "application/json")
	                .method("POST", HttpRequest.BodyPublishers.ofString(new Gson().toJson(obj)))
	                .build();
	        return client.send(request, HttpResponse.BodyHandlers.ofString());
	   }
}
