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
    private int rateFloatingPercentage;
    private int interestRateSpreadPercentage;
    private String interestRateCriteriaTypeCode;
    private int minimumSpreadPercentage;
    private int maximumSpreadPercentage;
    private double issueUnitPriceValue;
    private double minimumIssueVolumeValue;
    private double maximumIssueVolumeValue;
    private Date proposalValidityDate;
    private Date assetExpirationDate;
    private String earlyRedemptionConditionCode;
    private Date earlyRedemptionDate;
}