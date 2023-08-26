package org.example.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.example.Model.DistributorModel;
import org.example.Model.ProposalModel;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ValidationUtils {

    public static boolean isValidDistributor(String distributor) {
        return distributor.length() <= 8;
    }

    public static boolean isValidDateFormat(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Desativar ajustes automáticos de datas inválidas
            dateFormat.parse(date);
            return true; // A data está em formato válido
        } catch (ParseException e) {
            return false; // A data não está em formato válido
        }
    }

    public static boolean isValidIndexer(String indexer) {
        return indexer.equals("0003") || indexer.equals("0018") || indexer.equals("0099");
    }

    public static boolean isValidAssetType(String assetType) {
        return assetType.equals("CDB") || assetType.equals("LCA") || assetType.equals("LCI");
    }

    public static boolean isValidFloatingRate(String assetType, String indexer, double floatingRate) {
        if (assetType.equals("CDB")) {
            if (indexer.equals("0003")) {
                return floatingRate > 0;
            }
            if (indexer.equals("0099") || indexer.equals("0018")) {
                return floatingRate == 0;
            }
        } else if (assetType.equals("LCA") || assetType.equals("LCI")) {
            if ((indexer.equals("0003") || indexer.equals("0018")) && floatingRate > 0) {
                return true;
            }
            if (indexer.equals("0099")) {
                return floatingRate == 0;
            }
        }
        return true;
    }

    public static boolean isValidInterestRateCriteria(String spreadOrCupom, String interestRateCriteria) {
        if (!spreadOrCupom.isEmpty()) {
            return !interestRateCriteria.isEmpty();
        } else {
            return interestRateCriteria.isEmpty();
        }
    }

    public static boolean isValidSpreadMinimum(double spreadMinimum) {
        return spreadMinimum >= 0;
    }

    public static boolean isValidSpreadMaximum(double spreadMaximum, double spreadMinimum) {
        return spreadMaximum >= 0 && spreadMaximum >= spreadMinimum;
    }

    public static boolean isValidUnitIssuancePrice(double unitIssuancePrice) {
        return unitIssuancePrice > 0;
    }

    public static boolean isValidMinIssuanceVolume(double minIssuanceVolume) {
        return minIssuanceVolume > 0;
    }

    public static boolean isValidProposalValidityDate(Date proposalValidityDate) {
        Date currentDate = new Date();
        return proposalValidityDate.compareTo(currentDate) >= 0;
    }

    public static Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isValidAssetExpirationDate(Date assetExpirationDate, String remunerator, String assetType) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        if (assetExpirationDate.compareTo(currentDate.getTime()) <= 0) {
            return false; // A data de vencimento do ativo deve ser maior que a data atual
        }

        if (assetType.equals("CDB")) {
            if (remunerator.equals("IPCA")) {
                // Verifica se a data de vencimento é exatamente um ano após a data atual
                Calendar oneYearLater = Calendar.getInstance();
                oneYearLater.setTime(currentDate.getTime());
                oneYearLater.add(Calendar.YEAR, 1);
                return assetExpirationDate.compareTo(oneYearLater.getTime()) == 0;
            }
        } else if (assetType.equals("LCA") || assetType.equals("LCI")) {
            if (remunerator.equals("DI")) {
                // Verifica se a data de vencimento é exatamente 90 dias após a data atual
                Calendar ninetyDaysLater = Calendar.getInstance();
                ninetyDaysLater.setTime(currentDate.getTime());
                ninetyDaysLater.add(Calendar.DAY_OF_YEAR, 90);
                return assetExpirationDate.compareTo(ninetyDaysLater.getTime()) == 0;
            } else if (remunerator.equals("IPCA")) {
                // Verifica se a data de vencimento é exatamente um ano após a data atual
                Calendar oneYearLater = Calendar.getInstance();
                oneYearLater.setTime(currentDate.getTime());
                oneYearLater.add(Calendar.YEAR, 1);
                return assetExpirationDate.compareTo(oneYearLater.getTime()) == 0;
            }
        }

        return true; // Se não cair em nenhuma das condições acima, considera válido
    }

    public static boolean isValidMinRedemptionDelay(Date minRedemptionDelay, String redemptionCondition, Date assetExpirationDate) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        if (minRedemptionDelay.compareTo(currentDate.getTime()) <= 0) {
            return false; // O atraso mínimo de resgate deve ser maior que a data atual
        }

        if (redemptionCondition.equals("N")) {
            return true; // O atraso mínimo de resgate não deve ser preenchido
        } else if (redemptionCondition.equals("M")) {
            if (assetExpirationDate != null && minRedemptionDelay.compareTo(assetExpirationDate) < 0) {
                return false; // O atraso mínimo de resgate deve ser maior que a data de vencimento do ativo
            }
        }
        return true; // Se não cair em nenhuma das condições acima, considera válido
    }

    // Método de decrypta o password
    public static String decryptDbPassword(String dbPwdCypher) {
        // Implementar a lógica de decodificação da senha do banco de dados
        // Exemplo de pseudocódigo usando Java Cipher API:
        // return yourDecryptionMethod(dbPwdCypher);
        return "decoded_password";
    }

    public static String basicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedCredentials;
    }

    // Itens obrigatórios
    public static Map<String, Boolean> getMandatoryFields() {
        Map<String, Boolean> mandatoryFields = new HashMap<>();

        mandatoryFields.put("earlyRedemptionCondition", true);
        mandatoryFields.put("assetMaturityDate", true);
        mandatoryFields.put("indexer", true);
        mandatoryFields.put("assetType", true);
        mandatoryFields.put("distributor", true);

        return mandatoryFields;
    }

    public static Map<String, String> getBusinessValidationErrors(List<DistributorModel> distributorsArray, ProposalModel proposalObject) {
        Map<String, String> businessErrors = new HashMap<>();
        Map<String, Boolean> mandatoryFields = getMandatoryFields();

        String proposalJson = new Gson().toJson(proposalObject);
        JSONObject jsonObject = new JSONObject(proposalJson);

        mandatoryFields.forEach((fieldName, isMandatory) -> {
            if (isMandatory && !jsonObject.has(fieldName)) {
                businessErrors.put(fieldName, "Mandatory field not provided: " + fieldName);
            } else if (fieldName.equals("assetMaturityDate")) {
                String assetMaturityDate = jsonObject.optString(fieldName);
                if (!isValidDateFormat(assetMaturityDate)) {
                    businessErrors.put(fieldName, "Invalid date format for field: " + fieldName);
                }
            } else if (fieldName.equals("distributor")) {
                String distributor = jsonObject.optString(fieldName);
                if (!isValidDistributor(distributor)) {
                    businessErrors.put(fieldName, "Invalid distributor: " + distributor +
                            ". The distributor should have up to 8 characters.");
                }
            } else if (fieldName.equals("assetType")) {
                String assetType = jsonObject.optString(fieldName);
                if (!isValidAssetType(assetType)) {
                    businessErrors.put(fieldName, "Invalid asset type: " + assetType +
                            ". The asset type should be 'CDB', 'LCA' or 'LCI'.");
                }
            } else if (fieldName.equals("indexer")) {
                String indexer = jsonObject.optString(fieldName);
                if (!isValidIndexer(indexer)) {
                    businessErrors.put(fieldName, "Invalid indexer: " + indexer +
                            ". The indexer should be one of '0003', '0018' or '0099'.");
                }

            } else if (fieldName.equals("floatingRate")) {
                String assetType = jsonObject.optString("assetType");
                String indexer = jsonObject.optString("indexer");
                double floatingRate = jsonObject.optDouble(fieldName);

                if (!isValidFloatingRate(assetType, indexer, floatingRate)) {
                    businessErrors.put(fieldName, "Invalid floating rate for the given asset and indexer.");
                }
            } else if (fieldName.equals("interestRateCriteria")) {
                String spreadOrCupom = jsonObject.optString("spreadOrCupom");
                String interestRateCriteria = jsonObject.optString(fieldName);

                if (!isValidInterestRateCriteria(spreadOrCupom, interestRateCriteria)) {
                    businessErrors.put(fieldName, "Invalid interest rate criteria based on spread/cupom field.");
                }
            } else if (fieldName.equals("spreadMinimum")) {
                double spreadMinimum = jsonObject.optDouble(fieldName);
                if (!isValidSpreadMinimum(spreadMinimum)) {
                    businessErrors.put(fieldName, "Invalid spread minimum value. It should be >= 0.");
                }
            } else if (fieldName.equals("spreadMaximum")) {
                double spreadMaximum = jsonObject.optDouble(fieldName);
                double spreadMinimum = jsonObject.optDouble("spreadMinimum");
                if (!isValidSpreadMaximum(spreadMaximum, spreadMinimum)) {
                    businessErrors.put(fieldName, "Invalid spread maximum value. It should be >= 0 and >= spread minimum.");
                }
            } else if (fieldName.equals("unitIssuancePrice")) {
                double unitIssuancePrice = jsonObject.optDouble(fieldName);
                if (!isValidUnitIssuancePrice(unitIssuancePrice)) {
                    businessErrors.put(fieldName, "Invalid unit issuance price value. It should be > 0.");
                }
            } else if (fieldName.equals("minIssuanceVolume")) {
                double minIssuanceVolume = jsonObject.optDouble(fieldName);
                if (!isValidMinIssuanceVolume(minIssuanceVolume)) {
                    businessErrors.put(fieldName, "Invalid minimum issuance volume value. It should be > 0.");
                }
            } else if (fieldName.equals("proposalValidityDate")) {
                String proposalValidityDateStr = jsonObject.optString(fieldName);
                Date proposalValidityDate = parseDate(proposalValidityDateStr);

                if (proposalValidityDate == null) {
                    // If not provided, consider current date (D0)
                    proposalValidityDate = new Date();
                }

                if (!isValidProposalValidityDate(proposalValidityDate)) {
                    businessErrors.put(fieldName, "Invalid proposal validity date. It should be >= current date.");
                }
            } else if (fieldName.equals("assetExpirationDate")) {
                Date assetExpirationDate = parseDate(jsonObject.optString(fieldName));
                String remunerator = jsonObject.optString("remunerator");
                String assetType = jsonObject.optString("assetType");

                if (assetExpirationDate == null) {
                    businessErrors.put(fieldName, "Invalid asset expiration date.");
                } else {
                    if (!isValidAssetExpirationDate(assetExpirationDate, remunerator, assetType)) {
                        businessErrors.put(fieldName, "Invalid asset expiration date based on asset type and remunerator.");
                    }
                }
            } else if (fieldName.equals("minRedemptionDelay")) {
                Date minRedemptionDelay = parseDate(jsonObject.optString(fieldName));
                String redemptionCondition = jsonObject.optString("redemptionCondition");
                Date assetExpirationDate = parseDate(jsonObject.optString("assetExpirationDate"));

                if (minRedemptionDelay == null) {
                    businessErrors.put(fieldName, "Invalid minimum redemption delay.");
                } else {
                    if (!isValidMinRedemptionDelay(minRedemptionDelay, redemptionCondition, assetExpirationDate)) {
                        businessErrors.put(fieldName, "Invalid minimum redemption delay based on redemption condition and asset expiration date.");
                    }
                }
            }
        });

        return businessErrors;
    }

    static JsonObject parseJsonResponse(String responseBody) {
        JsonObject jsonObject = new JsonObject(); // biblioteca Gson para análise JSON
        try {
            JsonParser parser = new JsonParser();
            jsonObject = parser.parse(responseBody).getAsJsonObject();
        } catch (JsonParseException e) {
            // Lidar com erros de análise de JSON, se necessário
            e.printStackTrace();
        }
        return jsonObject;
    }

}