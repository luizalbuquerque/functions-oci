package org.example;

import com.google.gson.Gson;
import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        ConfigLoader configLoader = new ConfigLoader();

        try {
            configLoader.load();

            String dbName = configLoader.getProperty("database.name");
            String dbUser = configLoader.getProperty("database.user");
            String dbPassword = configLoader.getProperty("database.password");
            String dbUrl = configLoader.getProperty("database.url");

            System.out.println("Database Name: " + dbName);
            System.out.println("Database User: " + dbUser);
            System.out.println("Database Pass: " + dbPassword);
            System.out.println("Database url: " + dbUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Exemplo de JSON para teste. Ajuste conforme necessário.
        String sampleInput = "{\"distributors\": [], \"proposal\": {}}";

        try (
                // Convertendo a string de entrada em um InputStream
                InputStream inputStream = new ByteArrayInputStream(sampleInput.getBytes());
                // Criando um InputStreamReader para ler o InputStream
                InputStreamReader reader = new InputStreamReader(inputStream)
        ) {
            // Usando o Gson para converter o InputStreamReader em um objeto OfferModel
            Gson gson = new Gson();
            OfferModel offer = gson.fromJson(reader, OfferModel.class);

            // Criando uma instância da função e chamando-a
            CreateOfferFunction createOffer = new CreateOfferFunction();
            String result = createOffer.handleRequest(offer);

            // Imprimindo o resultado
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
