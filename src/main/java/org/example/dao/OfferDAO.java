package org.example.dao;

import org.example.Model.OfferModel;
import org.example.config.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    private final DatabaseManager dbManager;

    public OfferDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public int insertOffer(OfferModel offerModel) throws Exception {
        String sql = "INSERT INTO DV01DISTEMISSO.TSDEENVIO_OFERTA " +
                "(NOME_SIMPLIFICADO_DISTRIBUIDOR, COD_TIPO_INSTRUMENTO_FINANCEIRO, " +
                "NUM_INDEXADOR_EMISSAO, PERC_TAXA_FLUTUANTE, PERC_TAXA_JURO_SPREAD, " +
                "PERC_SPREAD_MINIMO, PERC_SPREAD_MAXIMO, VAL_PRECO_UNITARIO_EMISSAO, " +
                "VAL_VOLUME_MINIMO_EMISSAO, VAL_VOLUME_MAXIMO_EMISSAO, DATA_VALIDADE_PROPOSTA, " +
                "DATA_VENCIMENTO_ATIVO, IND_CONDICAO_RESGATE_ANTECIPADO, DATA_CARENCIA_MINIMA, " +
                "NUM_TIPO_ATIVO_FINANCEIRO, NUM_SEQUENCIAL_TIPO_STATUS_OFERTA, " +
                "NUM_SEQUENCIAL_CRITERIO_CALCULO_JURO, NUM_SEQUENCIAL_CONTA_DISTRIBUIDOR, " +
                "NUM_SEQUENCIAL_CONTA_EMISSOR, NUM_SEQUENCIAL_CANAL_COMUNICACAO, " +
                "NUM_SEQUENCIAL_TIPO_ATIVO_FINANCEIRO, COD_UUID, COD_USUARIO_INCLUSAO_REGISTRO, " +
                "DATA_INCLUSAO_REGISTRO, COD_USUARIO_ALTERACAO_REGISTRO, DATA_ATUALIZACAO_REGISTRO, " +
                "COD_USUARIO_EXCLUSAO_REGISTRO, DATA_EXCLUSAO_REGISTRO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?)";


        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, offerModel.getNomeSimplificadoDistribuidor());
            preparedStatement.setInt(2, offerModel.getCodTipoInstrumentoFinanceiro());
            preparedStatement.setInt(3, offerModel.getNumIndexadorEmissao());
            preparedStatement.setDouble(4, offerModel.getPercTaxaFlutuante());
            preparedStatement.setDouble(5, offerModel.getPercTaxaJuroSpread());
            preparedStatement.setDouble(6, offerModel.getPercSpreadMinimo());
            preparedStatement.setDouble(7, offerModel.getPercSpreadMaximo());
            preparedStatement.setDouble(8, offerModel.getValPrecoUnitarioEmissao());
            preparedStatement.setDouble(9, offerModel.getValVolumeMinimoEmissao());
            preparedStatement.setDouble(10, offerModel.getValVolumeMaximoEmissao());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date parsedDataValidadeProposta = dateFormat.parse(offerModel.getDataValidadeProposta());
            java.sql.Date sqlDataValidadeProposta = new java.sql.Date(parsedDataValidadeProposta.getTime());
            preparedStatement.setDate(11, sqlDataValidadeProposta);

            java.util.Date parsedDataVencimentoAtivo = dateFormat.parse(offerModel.getDataVencimentoAtivo());
            java.sql.Date sqlDataVencimentoAtivo = new java.sql.Date(parsedDataVencimentoAtivo.getTime());
            preparedStatement.setDate(12, sqlDataVencimentoAtivo);

            preparedStatement.setString(13, offerModel.getIndCondicaoResgateAntecipado());

            java.util.Date parsedDataCarenciaMinima = dateFormat.parse(offerModel.getDataCarenciaMinima());
            java.sql.Date sqlDataCarenciaMinima = new java.sql.Date(parsedDataCarenciaMinima.getTime());
            preparedStatement.setDate(14, sqlDataCarenciaMinima);

            preparedStatement.setInt(15, offerModel.getNumTipoAtivoFinanceiro());
            preparedStatement.setInt(16, offerModel.getNumSequencialTipoStatusOferta());
            preparedStatement.setInt(17, offerModel.getNumSequencialCriterioCalculoJuro());
            preparedStatement.setInt(18, offerModel.getNumSequencialContaDistribuidor());
            preparedStatement.setInt(19, offerModel.getNumSequencialContaEmissor());
            preparedStatement.setInt(20, offerModel.getNumSequencialCanalComunicacao());
            preparedStatement.setInt(21, offerModel.getNumSequencialTipoAtivoFinanceiro());

            preparedStatement.setString(22, offerModel.getCodUuid());
            preparedStatement.setString(23, offerModel.getCodUsuarioInclusaoRegistro());

            java.util.Date parsedDataInclusaoRegistro = dateFormat.parse(offerModel.getDataInclusaoRegistro());
            java.sql.Date sqlDataInclusaoRegistro = new java.sql.Date(parsedDataInclusaoRegistro.getTime());
            preparedStatement.setDate(24, sqlDataInclusaoRegistro);

            preparedStatement.setString(25, offerModel.getCodUsuarioAlteracaoRegistro());

            java.util.Date parsedDataAtualizacaoRegistro = dateFormat.parse(offerModel.getDataAtualizacaoRegistro());
            java.sql.Date sqlDataAtualizacaoRegistro = new java.sql.Date(parsedDataAtualizacaoRegistro.getTime());
            preparedStatement.setDate(26, sqlDataAtualizacaoRegistro);

            preparedStatement.setString(27, offerModel.getCodUsuarioExclusaoRegistro());

            java.util.Date parsedDataExclusaoRegistro = dateFormat.parse(offerModel.getDataExclusaoRegistro());
            java.sql.Date sqlDataExclusaoRegistro = new java.sql.Date(parsedDataExclusaoRegistro.getTime());
            preparedStatement.setDate(28, sqlDataExclusaoRegistro);

            preparedStatement.addBatch();

            int[] batchResults = preparedStatement.executeBatch();
            for (int i = 0; i < batchResults.length; i++) {
                if (batchResults[i] == PreparedStatement.EXECUTE_FAILED) {
                    System.err.println("Batch execution failed for index: " + i);
                }
            }

            return batchResults.length;
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir oferta no banco de dados.", ex);
        }}


    public boolean offerLogicDeletion(int offerId, String deletedBy) throws Exception {
        String sql = "UPDATE OfferTable SET status = ?, deleted_by = ?, deletion_time = ? WHERE id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Define os valores para as colunas na query
            preparedStatement.setString(1, "deleted"); // Status definido como "deleted"
            preparedStatement.setString(2, deletedBy); // Quem deletou (nome do usuário, por exemplo)
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Hora atual da deleção
            preparedStatement.setInt(4, offerId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (Exception ex) {
            throw new Exception("Erro ao realizar a deleção lógica da oferta no banco de dados.", ex);
        }
    }


    public List<OfferModel> listOffers() throws Exception {
        List<OfferModel> offers = new ArrayList<>();
        String sql = "SELECT * FROM DV01DISTEMISSO.TSDEENVIO_OFERTA ";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                OfferModel offer = new OfferModel();

                offer.setNomeSimplificadoDistribuidor(resultSet.getString("nomeSimplificadoDistribuidor"));
                offer.setCodTipoInstrumentoFinanceiro(resultSet.getInt("codTipoInstrumentoFinanceiro"));
                offer.setNumIndexadorEmissao(resultSet.getInt("numIndexadorEmissao"));
                offer.setPercTaxaFlutuante(resultSet.getDouble("percTaxaFlutuante"));
                offer.setPercTaxaJuroSpread(resultSet.getDouble("percTaxaJuroSpread"));
                offer.setPercSpreadMinimo(resultSet.getDouble("percSpreadMinimo"));
                offer.setPercSpreadMaximo(resultSet.getDouble("percSpreadMaximo"));
                offer.setValPrecoUnitarioEmissao(resultSet.getDouble("valPrecoUnitarioEmissao"));
                offer.setValVolumeMinimoEmissao(resultSet.getDouble("valVolumeMinimoEmissao"));
                offer.setValVolumeMaximoEmissao(resultSet.getDouble("valVolumeMaximoEmissao"));
                offer.setDataValidadeProposta(resultSet.getString("dataValidadeProposta"));
                offer.setDataVencimentoAtivo(resultSet.getString("dataVencimentoAtivo"));
                offer.setIndCondicaoResgateAntecipado(resultSet.getString("indCondicaoResgateAntecipado"));
                offer.setDataCarenciaMinima(resultSet.getString("dataCarenciaMinima"));
                offer.setNumTipoAtivoFinanceiro(resultSet.getInt("numTipoAtivoFinanceiro"));
                offer.setNumSequencialTipoStatusOferta(resultSet.getInt("numSequencialTipoStatusOferta"));
                offer.setNumSequencialCriterioCalculoJuro(resultSet.getInt("numSequencialCriterioCalculoJuro"));
                offer.setNumSequencialContaDistribuidor(resultSet.getInt("numSequencialContaDistribuidor"));
                offer.setNumSequencialContaEmissor(resultSet.getInt("numSequencialContaEmissor"));
                offer.setNumSequencialCanalComunicacao(resultSet.getInt("numSequencialCanalComunicacao"));
                offer.setNumSequencialTipoAtivoFinanceiro(resultSet.getInt("numSequencialTipoAtivoFinanceiro"));
                offer.setCodUuid(resultSet.getString("codUuid"));
                offer.setCodUsuarioInclusaoRegistro(resultSet.getString("codUsuarioInclusaoRegistro"));
                offer.setDataInclusaoRegistro(resultSet.getString("dataInclusaoRegistro"));
                offer.setCodUsuarioAlteracaoRegistro(resultSet.getString("codUsuarioAlteracaoRegistro"));
                offer.setDataAtualizacaoRegistro(resultSet.getString("dataAtualizacaoRegistro"));
                offer.setCodUsuarioExclusaoRegistro(resultSet.getString("codUsuarioExclusaoRegistro"));
                offer.setDataExclusaoRegistro(resultSet.getString("dataExclusaoRegistro"));

                offers.add(offer);
            }

        } catch (Exception ex) {
            throw new Exception("Erro ao listar ofertas do banco de dados.", ex);
        }

        return offers;
    }

    public OfferModel getOfferById(int offerId) throws Exception {
        OfferModel offer = null;
        String sql = "SELECT * FROM DV01DISTEMISSO.TSDEENVIO_OFERTA WHERE id = ?";  // Ajuste conforme seu banco de dados.

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, offerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    offer = new OfferModel();
                    if (resultSet.next()) {
                        offer = new OfferModel();
                        offer.setNomeSimplificadoDistribuidor(resultSet.getString("nomeSimplificadoDistribuidor"));
                        offer.setCodTipoInstrumentoFinanceiro(resultSet.getInt("codTipoInstrumentoFinanceiro"));
                        offer.setNumIndexadorEmissao(resultSet.getInt("numIndexadorEmissao"));
                        offer.setPercTaxaFlutuante(resultSet.getDouble("percTaxaFlutuante"));
                        offer.setPercTaxaJuroSpread(resultSet.getDouble("percTaxaJuroSpread"));
                        offer.setPercSpreadMinimo(resultSet.getDouble("percSpreadMinimo"));
                        offer.setPercSpreadMaximo(resultSet.getDouble("percSpreadMaximo"));
                        offer.setValPrecoUnitarioEmissao(resultSet.getDouble("valPrecoUnitarioEmissao"));
                        offer.setValVolumeMinimoEmissao(resultSet.getDouble("valVolumeMinimoEmissao"));
                        offer.setValVolumeMaximoEmissao(resultSet.getDouble("valVolumeMaximoEmissao"));
                        offer.setDataValidadeProposta(resultSet.getString("dataValidadeProposta"));
                        offer.setDataVencimentoAtivo(resultSet.getString("dataVencimentoAtivo"));
                        offer.setIndCondicaoResgateAntecipado(resultSet.getString("indCondicaoResgateAntecipado"));
                        offer.setDataCarenciaMinima(resultSet.getString("dataCarenciaMinima"));
                        offer.setNumTipoAtivoFinanceiro(resultSet.getInt("numTipoAtivoFinanceiro"));
                        offer.setNumSequencialTipoStatusOferta(resultSet.getInt("numSequencialTipoStatusOferta"));
                        offer.setNumSequencialCriterioCalculoJuro(resultSet.getInt("numSequencialCriterioCalculoJuro"));
                        offer.setNumSequencialContaDistribuidor(resultSet.getInt("numSequencialContaDistribuidor"));
                        offer.setNumSequencialContaEmissor(resultSet.getInt("numSequencialContaEmissor"));
                        offer.setNumSequencialCanalComunicacao(resultSet.getInt("numSequencialCanalComunicacao"));
                        offer.setNumSequencialTipoAtivoFinanceiro(resultSet.getInt("numSequencialTipoAtivoFinanceiro"));
                        offer.setCodUuid(resultSet.getString("codUuid"));
                        offer.setCodUsuarioInclusaoRegistro(resultSet.getString("codUsuarioInclusaoRegistro"));
                        offer.setDataInclusaoRegistro(resultSet.getString("dataInclusaoRegistro"));
                        offer.setCodUsuarioAlteracaoRegistro(resultSet.getString("codUsuarioAlteracaoRegistro"));
                        offer.setDataAtualizacaoRegistro(resultSet.getString("dataAtualizacaoRegistro"));
                        offer.setCodUsuarioExclusaoRegistro(resultSet.getString("codUsuarioExclusaoRegistro"));
                        offer.setDataExclusaoRegistro(resultSet.getString("dataExclusaoRegistro"));
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Erro ao obter oferta pelo ID.", ex);
        }

        return offer;
    }

    public int updateOffer(OfferModel offer) throws Exception {
        String sql = "UPDATE DV01DISTEMISSO.TSDEENVIO_OFERTA SET " +
                "nomeSimplificadoDistribuidor = ?, " +
                "codTipoInstrumentoFinanceiro = ?, " +
                "numIndexadorEmissao = ?, " +
                "percTaxaFlutuante = ?, " +
                "percTaxaJuroSpread = ?, " +
                "percSpreadMinimo = ?, " +
                "percSpreadMaximo = ?, " +
                "valPrecoUnitarioEmissao = ?, " +
                "valVolumeMinimoEmissao = ?, " +
                "valVolumeMaximoEmissao = ?, " +
                "dataValidadeProposta = ?, " +
                "dataVencimentoAtivo = ?, " +
                "indCondicaoResgateAntecipado = ?, " +
                "dataCarenciaMinima = ?, " +
                "numTipoAtivoFinanceiro = ?, " +
                "numSequencialTipoStatusOferta = ?, " +
                "numSequencialCriterioCalculoJuro = ?, " +
                "numSequencialContaDistribuidor = ?, " +
                "numSequencialContaEmissor = ?, " +
                "numSequencialCanalComunicacao = ?, " +
                "numSequencialTipoAtivoFinanceiro = ?, " +
                "codUuid = ?, " +
                "codUsuarioInclusaoRegistro = ?, " +
                "dataInclusaoRegistro = ?, " +
                "codUsuarioAlteracaoRegistro = ?, " +
                "dataAtualizacaoRegistro = ?, " +
                "codUsuarioExclusaoRegistro = ?, " +
                "dataExclusaoRegistro = ? " +
                "WHERE id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, offer.getNomeSimplificadoDistribuidor());
            preparedStatement.setInt(2, offer.getCodTipoInstrumentoFinanceiro());
            preparedStatement.setInt(3, offer.getNumIndexadorEmissao());
            preparedStatement.setDouble(4, offer.getPercTaxaFlutuante());
            preparedStatement.setDouble(5, offer.getPercTaxaJuroSpread());
            preparedStatement.setDouble(6, offer.getPercSpreadMinimo());
            preparedStatement.setDouble(7, offer.getPercSpreadMaximo());
            preparedStatement.setDouble(8, offer.getValPrecoUnitarioEmissao());
            preparedStatement.setDouble(9, offer.getValVolumeMinimoEmissao());
            preparedStatement.setDouble(10, offer.getValVolumeMaximoEmissao());
            preparedStatement.setString(11, offer.getDataValidadeProposta());
            preparedStatement.setString(12, offer.getDataVencimentoAtivo());
            preparedStatement.setString(13, offer.getIndCondicaoResgateAntecipado());
            preparedStatement.setString(14, offer.getDataCarenciaMinima());
            preparedStatement.setInt(15, offer.getNumTipoAtivoFinanceiro());
            preparedStatement.setInt(16, offer.getNumSequencialTipoStatusOferta());
            preparedStatement.setInt(17, offer.getNumSequencialCriterioCalculoJuro());
            preparedStatement.setInt(18, offer.getNumSequencialContaDistribuidor());
            preparedStatement.setInt(19, offer.getNumSequencialContaEmissor());
            preparedStatement.setInt(20, offer.getNumSequencialCanalComunicacao());
            preparedStatement.setInt(21, offer.getNumSequencialTipoAtivoFinanceiro());
            preparedStatement.setString(22, offer.getCodUuid());
            preparedStatement.setString(23, offer.getCodUsuarioInclusaoRegistro());
            preparedStatement.setString(24, offer.getDataInclusaoRegistro());
            preparedStatement.setString(25, offer.getCodUsuarioAlteracaoRegistro());
            preparedStatement.setString(26, offer.getDataAtualizacaoRegistro());
            preparedStatement.setString(27, offer.getCodUsuarioExclusaoRegistro());
            preparedStatement.setString(28, offer.getDataExclusaoRegistro());

            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Erro ao atualizar oferta no banco de dados.", ex);
        }
    }

}

