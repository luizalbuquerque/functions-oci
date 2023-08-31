package org.example.resource.functions;

import com.google.gson.Gson;
import org.example.Model.OfferModel;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;
import org.example.util.ValidationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateOfferFunction {

    private final DatabaseManager dbManager;
    private final OfferDAO offerDAO;

    public UpdateOfferFunction(DatabaseManager dbManager, OfferDAO offerDAO) {
        this.dbManager = dbManager;
        this.offerDAO = offerDAO;
    }


    public String handleRequest(OfferModel offerModel) {
        Map<String, Object> result = new HashMap<>();

        try {
            int affectedRows = offerDAO.updateOffer(offerModel);

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

    //Avaliar necessidade de validação
    private Map<String, String> validateUpdateValues(OfferModel offerModel) {
        return ValidationUtils.getBusinessValidationErrors(offerModel);
    }


    private void handleException(Map<String, Object> result, Exception e) {
        e.printStackTrace();
        result.put("success", false);
        result.put("error_message", e.getMessage());
    }
}