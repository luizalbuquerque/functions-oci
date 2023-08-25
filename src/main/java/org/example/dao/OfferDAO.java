package org.example.dao;

import org.example.Model.DistributorModel;
import org.example.Model.ProposalModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OfferDAO {

    private Connection connection;

    public OfferDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertOffer(List<DistributorModel> distributors, ProposalModel proposal) throws Exception {
        String sql = "INSERT INTO your_table (distributor_name, asset_type_code, index_code, rate_floating_percentage, interest_rate_spread_percentage, interest_rate_criteria_type_code, minimum_spread_percentage, maximum_spread_percentage, issue_unit_price_value, minimum_issue_volume_value, maximum_issue_volume_value, proposal_validity_date, asset_expiration_date, early_redemption_condition_code, early_redemption_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (DistributorModel distributor : distributors) {
                preparedStatement.setString(1, distributor.getDistributorSimplifiedName());
                preparedStatement.setString(2, proposal.getAssetTypeCode());
                preparedStatement.setString(3, proposal.getIndexCode());
                preparedStatement.setInt(4, proposal.getRateFloatingPercentage());
                preparedStatement.setInt(5, proposal.getInterestRateSpreadPercentage());
                preparedStatement.setString(6, proposal.getInterestRateCriteriaTypeCode());
                preparedStatement.setInt(7, proposal.getMinimumSpreadPercentage());
                preparedStatement.setInt(8, proposal.getMaximumSpreadPercentage());
                preparedStatement.setDouble(9, proposal.getIssueUnitPriceValue());
                preparedStatement.setDouble(10, proposal.getMinimumIssueVolumeValue());
                preparedStatement.setDouble(11, proposal.getMaximumIssueVolumeValue());
                preparedStatement.setDate(12, new java.sql.Date(proposal.getProposalValidityDate().getTime()));
                preparedStatement.setDate(13, new java.sql.Date(proposal.getAssetExpirationDate().getTime()));
                preparedStatement.setString(14, proposal.getEarlyRedemptionConditionCode());
                preparedStatement.setDate(15, new java.sql.Date(proposal.getEarlyRedemptionDate().getTime()));

                preparedStatement.addBatch();
            }

            return preparedStatement.executeBatch().length;
        }
    }
}
