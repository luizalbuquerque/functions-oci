package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.Model.DistributorModel;
import org.example.Model.ProposalModel;
import org.example.Model.OfferModel;
import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;
import org.example.util.ValidationUtils;

public class CreateOfferFunction {

    private final ConfigLoader configLoader;

    public CreateOfferFunction(ConfigLoader configLoader, DatabaseManager dbManager) {
        this.configLoader = configLoader;
    }

    public String handleRequest(OfferModel offerModel) {

        Map<String, Object> result = new HashMap<>();
        ProposalModel proposal = offerModel.getProposal();
        List<DistributorModel> distributors = offerModel.getDistributors();

        try {
            // Carregando as configurações do banco de dados
            loadDatabaseConfig();

            Map<String, String> validationErrors = validateInputValues(distributors, proposal);

            if (validationErrors.isEmpty()) {
                // Suponha que a inserção foi bem-sucedida (isso é apenas uma simulação)
                int affectedRows = 1; // suponha que um registro foi inserido com sucesso

                if (affectedRows > 0) {
                    result.put("success", true);
                    result.put("message", "Record inserted successfully (simulated).");
                } else {
                    result.put("success", false);
                    result.put("error_message", "Failed to insert record.");
                }
            } else {
                result.put("success", false);
                result.put("validation_errors", validationErrors);
            }
            /*
            }
            */
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

    private void loadDatabaseConfig() throws Exception {
        configLoader.load();
    }
}
