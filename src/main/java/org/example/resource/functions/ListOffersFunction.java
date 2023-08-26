package org.example.resource.functions;

import org.example.Model.OfferModel;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;

import java.util.List;

public class ListOffersFunction {

    private final OfferDAO offerDAO;

    public ListOffersFunction(DatabaseManager dbManager) {
        this.offerDAO = new OfferDAO(dbManager);
    }

    public List<OfferModel> handleRequest() throws Exception {
        return offerDAO.listOffers();
    }
}