package gcom.faturamento.conta;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public interface IContaCategoria {

//    public IContaCategoriaPK getComp_id();
//    public void setComp_id(IContaCategoriaPK comp_id);

    public short getQuantidadeEconomia();
    public void setQuantidadeEconomia(short quantidadeEconomia);

    public Date getUltimaAlteracao();
    public void setUltimaAlteracao(Date ultimaAlteracao);

    public BigDecimal getValorAgua();
    public void setValorAgua(BigDecimal valorAgua);

    public Integer getConsumoAgua();
    public void setConsumoAgua(Integer consumoAgua);

    public BigDecimal getValorEsgoto();
    public void setValorEsgoto(BigDecimal valorEsgoto);

    public Integer getConsumoEsgoto();
    public void setConsumoEsgoto(Integer consumoEsgoto);

    public BigDecimal getValorTarifaMinimaAgua();
    public void setValorTarifaMinimaAgua(BigDecimal valorTarifaMinimaAgua);

    public Integer getConsumoMinimoAgua();
    public void setConsumoMinimoAgua(Integer consumoMinimoAgua);

    public BigDecimal getValorTarifaMinimaEsgoto();
    public void setValorTarifaMinimaEsgoto(BigDecimal valorTarifaMinimaEsgoto);

    public Integer getConsumoMinimoEsgoto();
    public void setConsumoMinimoEsgoto(Integer consumoMinimoEsgoto);

    @SuppressWarnings("rawtypes")
	public Set getContaCategoriaConsumoFaixas();

    @SuppressWarnings("rawtypes")
	public void setContaCategoriaConsumoFaixas(Set contaCategoriaConsumoFaixas);

}
