package org.example.resource.functions;

import org.example.Model.DistributorModel;
import org.example.Model.ProposalModel;
import org.example.Model.OfferModel;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;
import org.example.util.ValidationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateOfferFunction {

    private final OfferDAO offerDAO;

    public UpdateOfferFunction(DatabaseManager dbManager) {
        this.offerDAO = new OfferDAO(dbManager);
    }

    public Map<String, Object> handleRequest(OfferModel offerModel) {
        Map<String, Object> result = new HashMap<>();
        ProposalModel proposal = offerModel.getProposal();
        List<DistributorModel> distributors = offerModel.getDistributors();

        try {
            Map<String, String> validationErrors = validateInputValues(distributors, proposal);

            if (validationErrors.isEmpty()) {
                int affectedRows = offerDAO.updateOffer(offerModel);

                if (affectedRows > 0) {
                    result.put("success", true);
                    result.put("message", "Record updated successfully.");
                } else {
                    result.put("success", false);
                    result.put("error_message", "Failed to update record.");
                }
            } else {
                result.put("success", false);
                result.put("validation_errors", validationErrors);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("error_message", e.getMessage());
        }

        return result;
    }

    private Map<String, String> validateInputValues(List<DistributorModel> distributors, ProposalModel proposal) {
        return ValidationUtils.getUpdateValidationErrors(distributors, proposal);
    }
}
