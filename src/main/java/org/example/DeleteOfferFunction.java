package org.example;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteOfferFunction {

    private String ordsBaseUrl = "https://z3gvypai.adb.sa-saopaulo-1.oraclecloud.com";
    private String dbSchema = "YOUR_DB_SCHEMA";
    private String dbPwdCypher = "YOUR_DB_PWD_CYPHER";

    public String handleRequest(InputStream inputStream) {
        Map<String, Object> result = new HashMap<>();

        try {
            String dbPwd = decryptDbPassword(dbPwdCypher);

            //instancia que envia Requisicao http ao servidor
            OkHttpClient client = new OkHttpClient();

            String offerId = "YOUR_OFFER_ID"; // Substitua pelo ID da oferta a ser desativada
            String updateUrl = ordsBaseUrl + dbSchema + "/_/offers/" + offerId;

            // Simula a requisição de desativação (pode variar de acordo com sua API)
            String updateData = "{ \"active\": false }"; // Configura "active" como falso para desativar
            Request request = new Request.Builder()
                    .url(updateUrl)
                    .addHeader("Authorization", basicAuthHeader(dbSchema, dbPwd))
                    .put(RequestBody.create(null, updateData))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    result.put("success", true); // Indica que a desativação foi bem-sucedida
                } else {
                    result.put("success", false);
                    result.put("error_message", "Deactivation request was not processed successfully");
                    result.put("http_status_code", response.code());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("error_message", e.getMessage());
        }

        return result.toString();
    }

    private String decryptDbPassword(String dbPwdCypher) {
        // Implementar a lógica de decodificação da senha do banco de dados
        // Exemplo de pseudocódigo usando Java Cipher API:
        // return yourDecryptionMethod(dbPwdCypher);
        return "decoded_password";
    }

    private String basicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedCredentials;
    }
}