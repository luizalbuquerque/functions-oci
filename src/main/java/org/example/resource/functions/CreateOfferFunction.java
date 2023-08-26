package org.example.resource.functions;

import org.example.Model.DistributorModel;
import org.example.Model.ProposalModel;
import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;
import org.example.util.ValidationUtils;

import java.util.HashMap;
import java.util.List;
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
        ProposalModel proposal = offerModel.getProposal();
        List<DistributorModel> distributors = offerModel.getDistributors();

        try {
            Map<String, String> validationErrors = validateInputValues(distributors, proposal);

            if (validationErrors.isEmpty()) {
                int affectedRows = offerDAO.insertOffer(distributors, proposal);

                if (affectedRows > 0) {
                    result.put("success", true);
                    result.put("message", "Record inserted successfully.");
                } else {
                    result.put("success", false);
                    result.put("error_message", "Failed to insert record.");
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

    private Map<String, String> validateInputValues(List<DistributorModel> distributors, ProposalModel proposal) {
        return ValidationUtils.getBusinessValidationErrors(distributors, proposal);
    }

    private void handleException(Map<String, Object> result, Exception e) {
        e.printStackTrace();
        result.put("success", false);
        result.put("error_message", e.getMessage());
    }
}
