package org.example;

import com.google.gson.Gson;
import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main_COM_BANCO_OK {

    public static void main(String[] args) {

        ConfigLoader configLoader = new ConfigLoader();
        DatabaseManager dbManager = new DatabaseManager(configLoader);

        try {
            // Carregar configurações do banco de dados
            configLoader.load();

            // Exibe informações
            configLoader.displayDatabaseInfo();

            // Estabelecer conexão com o banco de dados
            dbManager.connect();

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
                CreateOfferFunction createOffer = new CreateOfferFunction(configLoader, dbManager);
                String result = createOffer.handleRequest(offer);

                // Imprimindo o resultado
                System.out.println(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fechar conexão ao banco de dados
                dbManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
