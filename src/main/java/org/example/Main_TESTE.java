package org.example;

import com.google.gson.Gson;
import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;
import org.example.resource.functions.CreateOfferFunction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main_TESTE {

        public static void main(String[] args) {

            // Instanciando o DatabaseManager mockado
            DatabaseManager dbManager = createMockDatabaseManager();

            // Exemplo de JSON para teste. Ajuste conforme necessário.
            String sampleInput = "{"
                    + "\"distributors\": ["
                    + "    {\"distributorSimplifiedName\": \"BTG\"},"
                    + "    {\"distributorSimplifiedName\": \"itau\"}"
                    + "],"
                    + "\"proposal\": {"
                    + "    \"assetTypeCode\": \"CDB\","
                    + "    \"indexCode\": \"0003\","
                    + "    \"rateFloatingPercentage\": 0.1,"
                    + "    \"interestRateSpreadPercentage\": 0,"
                    + "    \"interestRateCriteriaTypeCode\": \"01\","
                    + "    \"minimumSpreadPercentage\": 0,"
                    + "    \"maximumSpreadPercentage\": 1,"
                    + "    \"issueUnitPriceValue\": 1.5,"
                    + "    \"minimumIssueVolumeValue\": 2.5,"
                    + "    \"maximumIssueVolumeValue\": 3.5,"  // Valor adicional, apenas para completar o JSON
                    + "    \"proposalValidityDate\": \"2023-08-27\","
                    + "    \"assetExpirationDate\": \"2024-08-26\","
                    + "    \"earlyRedemptionConditionCode\": \"N\","
                    + "    \"earlyRedemptionDate\": \"2023-08-27\""
                    + "}"
                    + "}";

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
                CreateOfferFunction createOffer = new CreateOfferFunction(null, dbManager); // Nulo, já que não estamos usando o ConfigLoader
                String result = createOffer.handleRequest(offer);

                // Imprimindo o resultado
                System.out.println(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Mockando o DatabaseManager
        private static DatabaseManager createMockDatabaseManager() {
            return new DatabaseManager(null) {
                @Override
                public void connect() {
                    // Não faça nada
                }

                @Override
                public void close() {
                    // Não faça nada
                }

                // Você pode mockar outros métodos conforme necessário
            };
        }
    }
