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

public class Main {

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
                String sampleInput = "{"
                        + "\"nomeSimplificadoDistribuidor\": \"MG\","
                        + "\"codTipoInstrumentoFinanceiro\": 1,"
                        + "\"numIndexadorEmissao\": 18,"
                        + "\"percTaxaFlutuante\": 1.1,"
                        + "\"percTaxaJuroSpread\": 1.1,"
                        + "\"percSpreadMinimo\": 1.1,"
                        + "\"percSpreadMaximo\": 1.1,"
                        + "\"valPrecoUnitarioEmissao\": 22.2,"
                        + "\"valVolumeMinimoEmissao\": 22.2,"
                        + "\"valVolumeMaximoEmissao\": 22.2,"
                        + "\"dataValidadeProposta\": \"2020-07-21\","
                        + "\"dataVencimentoAtivo\": \"2020-07-21\","
                        + "\"indCondicaoResgateAntecipado\": \"N\","
                        + "\"dataCarenciaMinima\": \"2020-07-21\","
                        + "\"numTipoAtivoFinanceiro\": 1,"
                        + "\"numSequencialTipoStatusOferta\": 2,"
                        + "\"numSequencialCriterioCalculoJuro\": 3,"
                        + "\"numSequencialContaDistribuidor\": 1,"
                        + "\"numSequencialContaEmissor\": 2,"
                        + "\"numSequencialCanalComunicacao\": 1,"
                        + "\"numSequencialTipoAtivoFinanceiro\": 1,"
                        + "\"codUuid\": \"123\","
                        + "\"codUsuarioInclusaoRegistro\": \"5\","
                        + "\"dataInclusaoRegistro\": \"2020-07-21\","
                        + "\"codUsuarioAlteracaoRegistro\": \"5\","
                        + "\"dataAtualizacaoRegistro\": \"2020-07-21\","
                        + "\"codUsuarioExclusaoRegistro\": \"5\","
                        + "\"dataExclusaoRegistro\": \"2020-07-21\""
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
                    CreateOfferFunction createOffer = new CreateOfferFunction(configLoader, dbManager);
                    String result = createOffer.handleRequest(offer);

                    // Imprimindo o resultado
                    System.out.println(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
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
