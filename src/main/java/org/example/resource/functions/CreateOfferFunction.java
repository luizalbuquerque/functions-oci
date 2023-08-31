package org.example.resource.functions;

import com.google.gson.Gson;
import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;
import org.example.util.ValidationUtils;
import java.util.HashMap;
import java.util.Map;

public class CreateOfferFunction {

    private final DatabaseManager dbManager;
    private final OfferDAO offerDAO;

    public CreateOfferFunction(ConfigLoader configLoader, DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.offerDAO = new OfferDAO(dbManager);
    }

    public String handleRequest(OfferModel offerModel) {
        Map<String, Object> result = new HashMap<>();

        try {
            int affectedRows = offerDAO.insertOffer(offerModel);

            if (affectedRows > 0) {
                result.put("success", true);
                result.put("message", "Registro inserido com sucesso.");
            } else {
                result.put("success", false);
                result.put("error_message", "Falha ao inserir o registro.");
            }
        } catch (Exception e) {
            handleException(result, e);
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    private Map<String, String> validateInputValues(OfferModel offerModel) {
        return ValidationUtils.getBusinessValidationErrors(offerModel);
    }


    private void handleException(Map<String, Object> result, Exception e) {
        e.printStackTrace();
        result.put("success", false);
        result.put("error_message", e.getMessage());
    }
}
