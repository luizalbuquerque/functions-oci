package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalModel {
    private String assetTypeCode;
    private String indexCode;
    private double rateFloatingPercentage; // Alterado para double
    private double interestRateSpreadPercentage; // Alterado para double
    private String interestRateCriteriaTypeCode;
    private double minimumSpreadPercentage; // Alterado para double
    private double maximumSpreadPercentage; // Alterado para double
    private double issueUnitPriceValue;
    private double minimumIssueVolumeValue;
    private double maximumIssueVolumeValue;
    private Date proposalValidityDate;
    private Date assetExpirationDate;
    private String earlyRedemptionConditionCode;
    private Date earlyRedemptionDate;
}
