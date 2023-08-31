package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferModel {
    private String nomeSimplificadoDistribuidor;
    private Integer codTipoInstrumentoFinanceiro;
    private Integer numIndexadorEmissao;
    private Double percTaxaFlutuante;
    private Double percTaxaJuroSpread;
    private Double percSpreadMinimo;
    private Double percSpreadMaximo;
    private Double valPrecoUnitarioEmissao;
    private Double valVolumeMinimoEmissao;
    private Double valVolumeMaximoEmissao;
    private String dataValidadeProposta;
    private String dataVencimentoAtivo;
    private String indCondicaoResgateAntecipado;
    private String dataCarenciaMinima;
    private Integer numTipoAtivoFinanceiro;
    private Integer numSequencialTipoStatusOferta;
    private Integer numSequencialCriterioCalculoJuro;
    private Integer numSequencialContaDistribuidor;
    private Integer numSequencialContaEmissor;
    private Integer numSequencialCanalComunicacao;
    private Integer numSequencialTipoAtivoFinanceiro;
    private String codUuid;
    private String codUsuarioInclusaoRegistro;
    private String dataInclusaoRegistro;
    private String codUsuarioAlteracaoRegistro;
    private String dataAtualizacaoRegistro;
    private String codUsuarioExclusaoRegistro;
    private String dataExclusaoRegistro;
}