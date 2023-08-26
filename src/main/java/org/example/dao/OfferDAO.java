package org.example.dao;

import org.example.Model.DistributorModel;
import org.example.Model.OfferModel;
import org.example.Model.ProposalModel;
import org.example.config.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
                // Preencher seu preparedStatement aqui...

                preparedStatement.addBatch();
            }

            return preparedStatement.executeBatch().length;
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir oferta no banco de dados.", ex);
        }
    }

    public boolean deleteOffer(int offerId) throws Exception {
        String sql = "DELETE FROM OfferTable WHERE id = ?"; // Ajuste "OfferTable" e "id" conforme sua estrutura de tabela e coluna.

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, offerId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (Exception ex) {
            throw new Exception("Erro ao deletar oferta no banco de dados.", ex);
        }
    }

    public List<OfferModel> listOffers() throws Exception {
        List<OfferModel> offers = new ArrayList<>();
        String sql = "SELECT * FROM Offers";  // Ajuste a tabela e colunas conforme seu banco de dados.

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                OfferModel offer = new OfferModel();
                // Preencha o modelo de oferta usando resultSet. Por exemplo:
                // offer.setId(resultSet.getInt("id"));
                // ... continue para outros  ...

                offers.add(offer);
            }
        } catch (Exception ex) {
            throw new Exception("Erro ao listar ofertas do banco de dados.", ex);
        }

        return offers;
    }

    public OfferModel getOfferById(int offerId) throws Exception {
        OfferModel offer = null;
        String sql = "SELECT * FROM Offers WHERE id = ?";  // Ajuste conforme seu banco de dados.

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, offerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    offer = new OfferModel();
                    // Preencha o modelo de oferta usando resultSet.

                    // Exemplo:
                    // offer.setId(resultSet.getInt("id"));
                    // ... continue para outros campos ...
                }
            }
        } catch (Exception ex) {
            throw new Exception("Erro ao obter oferta pelo ID.", ex);
        }

        return offer;
    }

    public int updateOffer(OfferModel offer) throws Exception {
        String sql = "UPDATE Offers SET ... WHERE id = ?";  // Complete com os detalhes da sua tabela.

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Preencha seu preparedStatement aqui...
            // Por exemplo:
            // preparedStatement.setString(1, offer.getSomeField());
            // preparedStatement.setInt(2, offer.getId());

            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Erro ao atualizar oferta no banco de dados.", ex);
        }
    }
}

