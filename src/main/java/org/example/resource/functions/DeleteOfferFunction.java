package org.example.resource.functions;

import org.example.config.ConfigLoader;
import org.example.config.DatabaseManager;
import org.example.dao.OfferDAO;
import java.util.HashMap;
import java.util.Map;

public class DeleteOfferFunction {

    private final DatabaseManager dbManager;
    private final OfferDAO offerDAO;

    public DeleteOfferFunction(ConfigLoader configLoader, DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.offerDAO = new OfferDAO(dbManager);
    }

    public String handleRequest(int offerId, String deletedBy) {
        Map<String, Object> resultado = new HashMap<>();

        try {
            boolean foiAtualizado = offerDAO.offerLogicDeletion(offerId, deletedBy);

            if (foiAtualizado) {
                resultado.put("sucesso", true);
                resultado.put("mensagem", "Registro deletado logicamente com sucesso.");
            } else {
                resultado.put("sucesso", false);
                resultado.put("mensagem_de_erro", "Falha ao realizar a deleção lógica ou registro não existe.");
            }

        } catch (Exception e) {
            lidarComExcecao(resultado, e);
        }

        return resultado.toString();
    }

    private void lidarComExcecao(Map<String, Object> resultado, Exception e) {
        e.printStackTrace();
        resultado.put("sucesso", false);
        resultado.put("mensagem_de_erro", e.getMessage());
    }
}
