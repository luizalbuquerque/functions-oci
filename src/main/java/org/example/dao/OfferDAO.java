package org.example.dao;

import org.example.Model.DistributorModel;
import org.example.Model.ProposalModel;
import org.example.config.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OfferDAO {

    private final DatabaseManager dbManager;

    public OfferDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public int insertOffer(List<DistributorModel> distributors, ProposalModel proposal) throws Exception {
        String sql = "..."; // Mantenha sua query aqui

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (DistributorModel distributor : distributors) {
                // Preencha seu preparedStatement aqui...

                preparedStatement.addBatch();
            }

            return preparedStatement.executeBatch().length;
        } catch (Exception ex) {
            // Aqui você pode adicionar algum log ou tratamento mais específico
            throw new Exception("Erro ao inserir oferta no banco de dados.", ex);
        }
    }
}
