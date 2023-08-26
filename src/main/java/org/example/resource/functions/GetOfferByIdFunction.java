package org.example.resource.functions;

import org.example.Model.OfferModel;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;

public class GetOfferByIdFunction {

    private final OfferDAO offerDAO;

    public GetOfferByIdFunction(DatabaseManager dbManager) {
        this.offerDAO = new OfferDAO(dbManager);
    }

    public OfferModel handleRequest(int offerId) throws Exception {
        return offerDAO.getOfferById(offerId);
    }
}