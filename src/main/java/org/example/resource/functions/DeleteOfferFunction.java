package org.example.resource.functions;

import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;
import org.example.util.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class DeleteOfferFunction {

    private final DatabaseManager dbManager;
    private final OfferDAO offerDAO;

    public DeleteOfferFunction(ConfigLoader configLoader, DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.offerDAO = new OfferDAO(dbManager);
    }

    public String handleRequest(int offerId) {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, String> validationErrors = validateOfferId(offerId);

            if (validationErrors.isEmpty()) {
                boolean isDeleted = offerDAO.deleteOffer(offerId);

                if (isDeleted) {
                    result.put("success", true);
                    result.put("message", "Record deleted successfully.");
                } else {
                    result.put("success", false);
                    result.put("error_message", "Failed to delete record or record does not exist.");
                }
            } else {
                result.put("success", false);
                result.put("validation_errors", validationErrors);
            }

        } catch (Exception e) {
            handleException(result, e);
        }

        return result.toString();
    }

    private Map<String, String> validateOfferId(int offerId) {
        return ValidationUtils.getDeleteValidation(offerId);
    }


    private void handleException(Map<String, Object> result, Exception e) {
        e.printStackTrace();
        result.put("success", false);
        result.put("error_message", e.getMessage());
    }
}
