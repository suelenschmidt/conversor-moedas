package com.conversormoedas;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorDeMoedas {

    private static final String API_KEY = "215953d709dbfc2873fa0fb5";  
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Conversor de Moedas");
            System.out.println("Selecione a conversão desejada:");
            System.out.println("1. USD para EUR");
            System.out.println("2. EUR para USD");
            System.out.println("3. USD para BRL");
            System.out.println("4. BRL para USD");
            System.out.println("5. EUR para BRL");
            System.out.println("6. BRL para EUR");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();
            if (opcao == 0) {
                break;
            }

            System.out.print("Digite o valor a ser convertido: ");
            double valor = scanner.nextDouble();

            switch (opcao) {
                case 1 -> converterMoeda("USD", "EUR", valor);
                case 2 -> converterMoeda("EUR", "USD", valor);
                case 3 -> converterMoeda("USD", "BRL", valor);
                case 4 -> converterMoeda("BRL", "USD", valor);
                case 5 -> converterMoeda("EUR", "BRL", valor);
                case 6 -> converterMoeda("BRL", "EUR", valor);
                default -> System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private static void converterMoeda(String from, String to, double amount) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + from))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            double rate = jsonObject.getAsJsonObject("conversion_rates").get(to).getAsDouble();

            double convertedAmount = amount * rate;
            System.out.printf("Taxa de conversão de %s para %s: %.4f%n", from, to, rate);
            System.out.printf("%.2f %s é igual a %.2f %s%n", amount, from, convertedAmount, to);
        } catch (Exception e) {
            System.out.println("Erro ao obter a taxa de conversão: " + e.getMessage());
        }
    }
}
