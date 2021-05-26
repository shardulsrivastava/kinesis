package com.amazonaws.services.kinesis.producer.sample;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		generateUserData();
	}

	public static byte[] generateUserData() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://randomuser.me/api/")).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		String responseBody = response.body();
		byte[] responseBytes = response.body().getBytes("utf-8");
		System.out.println(responseBody);

		return responseBytes;
	}
}