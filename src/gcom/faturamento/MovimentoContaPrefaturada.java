package gcom.faturamento;

import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.CobrancaDocumento;
import gcom.faturamento.conta.Conta;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoTipo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MovimentoContaPrefaturada implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer anoMesReferenciaPreFaturamento;
    private Integer leituraHidrometro;
    private Date dataHoraLeitura;
    private Short indicadorSituacaoLeitura;
    private Integer leituraFaturamento;
    private Integer consumoMedido;
    private Integer consumoCobrado;
    private Date dataHoraGeracaoMovimento;
    private Short indicadorAtualizacaoFaturamento;
    private Date utlimaAlteracao;
    private Short indicadorEmissaoConta;
    private Short indicadorGeracaoConta;
    private Integer consumoRateioAgua;
    private BigDecimal valorRateioAgua;
    private Integer consumoRateioEsgoto;
    private BigDecimal valorRateioEsgoto;
    private Imovel imovel;
    private Integer consumoImoveisVinculados;
    private Integer leituraHidrometroAnterior;
    private Short indicadorRetransmissao;
    private String latitude;
    private String longitude;
    private Short indicadorAlteracao;
    private MedicaoTipo medicaoTipo;
    private FaturamentoGrupo faturamentoGrupo;
    private Rota rota;
    private LeituraAnormalidade leituraAnormalidadeFaturamento;
    private ConsumoTipo consumoTipo;
    private ConsumoAnormalidade consumoAnormalidade;
    private Conta conta;
    private MovimentoContaPrefaturada movimentoContaPreFaturadaEsgoto;
    private LeituraAnormalidade leituraAnormalidadeLeitura;
    private CobrancaDocumento cobrancaDocumento;
    
    @SuppressWarnings("rawtypes")
	private Set movimentoContaPrefaturadaCategorias;
    
    @SuppressWarnings("rawtypes")
    private Set movimentoContaImpostoDeduzidos;
    

    public MovimentoContaPrefaturada getMovimentoContaPreFaturadaEsgoto() {
		return movimentoContaPreFaturadaEsgoto;
	}

	public void setMovimentoContaPreFaturadaEsgoto(
			MovimentoContaPrefaturada movimentoContaPreFaturadaEsgoto) {
		this.movimentoContaPreFaturadaEsgoto = movimentoContaPreFaturadaEsgoto;
	}

	@SuppressWarnings("rawtypes")
	public MovimentoContaPrefaturada(Integer id, Integer anoMesReferenciaPreFaturamento, Integer leituraHidrometro, Date dataHoraLeitura, Short indicadorSituacaoLeitura, Integer leituraFaturamento, Integer consumoMedido, Integer consumoCobrado, Date dataHoraGeracaoMovimento, Short indicadorAtualizacaoFaturamento, Date utlimaAlteracao, Short indicadorEmissaoConta, Integer consumoRateioAgua, Integer consumoRateioEsgoto, Imovel imovel, MedicaoTipo medicaoTipo, gcom.faturamento.FaturamentoGrupo faturamentoGrupo, Rota rota, LeituraAnormalidade leituraAnormalidadeFaturamento, ConsumoTipo consumoTipo, ConsumoAnormalidade consumoAnormalidade, Conta conta, Set movimentoContaPrefaturadaCategorias, Set movimentoContaImpostoDeduzidos) {
        this.id = id;
        this.anoMesReferenciaPreFaturamento = anoMesReferenciaPreFaturamento;
        this.leituraHidrometro = leituraHidrometro;
        this.dataHoraLeitura = dataHoraLeitura;
        this.indicadorSituacaoLeitura = indicadorSituacaoLeitura;
        this.leituraFaturamento = leituraFaturamento;
        this.consumoMedido = consumoMedido;
        this.consumoCobrado = consumoCobrado;
        this.dataHoraGeracaoMovimento = dataHoraGeracaoMovimento;
        this.indicadorAtualizacaoFaturamento = indicadorAtualizacaoFaturamento;
        this.utlimaAlteracao = utlimaAlteracao;
        this.indicadorEmissaoConta = indicadorEmissaoConta;
        this.consumoRateioAgua = consumoRateioAgua;
        this.consumoRateioEsgoto = consumoRateioEsgoto;
        this.imovel = imovel;
        this.medicaoTipo = medicaoTipo;
        this.faturamentoGrupo = faturamentoGrupo;
        this.rota = rota;
        this.leituraAnormalidadeFaturamento = leituraAnormalidadeFaturamento;
        this.consumoTipo = consumoTipo;
        this.consumoAnormalidade = consumoAnormalidade;
        this.conta = conta;
        this.movimentoContaPrefaturadaCategorias = movimentoContaPrefaturadaCategorias;
        this.movimentoContaImpostoDeduzidos = movimentoContaImpostoDeduzidos;
    }

    public MovimentoContaPrefaturada() {
    }

    @SuppressWarnings("rawtypes")
    public MovimentoContaPrefaturada(Integer id, Integer anoMesReferenciaPreFaturamento, Date dataHoraLeitura, Short indicadorSituacaoLeitura, Date dataHoraGeracaoMovimento, Short indicadorAtualizacaoFaturamento, Date utlimaAlteracao, Short indicadorEmissaoConta, Imovel imovel, MedicaoTipo medicaoTipo, gcom.faturamento.FaturamentoGrupo faturamentoGrupo, Rota rota, LeituraAnormalidade leituraAnormalidadeFaturamento, ConsumoTipo consumoTipo, ConsumoAnormalidade consumoAnormalidade, Conta conta, Set movimentoContaPrefaturadaCategorias, Set movimentoContaImpostoDeduzidos) {
        this.id = id;
        this.anoMesReferenciaPreFaturamento = anoMesReferenciaPreFaturamento;
        this.dataHoraLeitura = dataHoraLeitura;
        this.indicadorSituacaoLeitura = indicadorSituacaoLeitura;
        this.dataHoraGeracaoMovimento = dataHoraGeracaoMovimento;
        this.indicadorAtualizacaoFaturamento = indicadorAtualizacaoFaturamento;
        this.utlimaAlteracao = utlimaAlteracao;
        this.indicadorEmissaoConta = indicadorEmissaoConta;
        this.imovel = imovel;
        this.medicaoTipo = medicaoTipo;
        this.faturamentoGrupo = faturamentoGrupo;
        this.rota = rota;
        this.leituraAnormalidadeFaturamento = leituraAnormalidadeFaturamento;
        this.consumoTipo = consumoTipo;
        this.consumoAnormalidade = consumoAnormalidade;
        this.conta = conta;
        this.movimentoContaPrefaturadaCategorias = movimentoContaPrefaturadaCategorias;
        this.movimentoContaImpostoDeduzidos = movimentoContaImpostoDeduzidos;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnoMesReferenciaPreFaturamento() {
        return this.anoMesReferenciaPreFaturamento;
    }

    public void setAnoMesReferenciaPreFaturamento(Integer anoMesReferenciaPreFaturamento) {
        this.anoMesReferenciaPreFaturamento = anoMesReferenciaPreFaturamento;
    }

    public Integer getLeituraHidrometro() {
        return this.leituraHidrometro;
    }

    public void setLeituraHidrometro(Integer leituraHidrometro) {
        this.leituraHidrometro = leituraHidrometro;
    }

    public Date getDataHoraLeitura() {
        return this.dataHoraLeitura;
    }

    public void setDataHoraLeitura(Date dataHoraLeitura) {
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public Short getIndicadorSituacaoLeitura() {
        return this.indicadorSituacaoLeitura;
    }

    public void setIndicadorSituacaoLeitura(Short indicadorSituacaoLeitura) {
        this.indicadorSituacaoLeitura = indicadorSituacaoLeitura;
    }

    public Integer getLeituraFaturamento() {
        return this.leituraFaturamento;
    }

    public void setLeituraFaturamento(Integer leituraFaturamento) {
        this.leituraFaturamento = leituraFaturamento;
    }

    public Integer getConsumoMedido() {
        return this.consumoMedido;
    }

    public void setConsumoMedido(Integer consumoMedido) {
        this.consumoMedido = consumoMedido;
    }

    public Integer getConsumoCobrado() {
        return this.consumoCobrado;
    }

    public void setConsumoCobrado(Integer consumoCobrado) {
        this.consumoCobrado = consumoCobrado;
    }

    public Date getDataHoraGeracaoMovimento() {
        return this.dataHoraGeracaoMovimento;
    }

    public void setDataHoraGeracaoMovimento(Date dataHoraGeracaoMovimento) {
        this.dataHoraGeracaoMovimento = dataHoraGeracaoMovimento;
    }

    public Short getIndicadorAtualizacaoFaturamento() {
        return this.indicadorAtualizacaoFaturamento;
    }

    public void setIndicadorAtualizacaoFaturamento(Short indicadorAtualizacaoFaturamento) {
        this.indicadorAtualizacaoFaturamento = indicadorAtualizacaoFaturamento;
    }

    public Date getUtlimaAlteracao() {
        return this.utlimaAlteracao;
    }

    public void setUtlimaAlteracao(Date utlimaAlteracao) {
        this.utlimaAlteracao = utlimaAlteracao;
    }

    public Short getIndicadorEmissaoConta() {
        return this.indicadorEmissaoConta;
    }

    public void setIndicadorEmissaoConta(Short indicadorEmissaoConta) {
        this.indicadorEmissaoConta = indicadorEmissaoConta;
    }

    public Integer getConsumoRateioAgua() {
        return this.consumoRateioAgua;
    }

    public void setConsumoRateioAgua(Integer consumoRateioAgua) {
        this.consumoRateioAgua = consumoRateioAgua;
    }

    public Integer getConsumoRateioEsgoto() {
        return this.consumoRateioEsgoto;
    }

    public void setConsumoRateioEsgoto(Integer consumoRateioEsgoto) {
        this.consumoRateioEsgoto = consumoRateioEsgoto;
    }

    public Imovel getImovel() {
        return this.imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public MedicaoTipo getMedicaoTipo() {
        return this.medicaoTipo;
    }

    public void setMedicaoTipo(MedicaoTipo medicaoTipo) {
        this.medicaoTipo = medicaoTipo;
    }

    public gcom.faturamento.FaturamentoGrupo getFaturamentoGrupo() {
        return this.faturamentoGrupo;
    }

    public void setFaturamentoGrupo(gcom.faturamento.FaturamentoGrupo faturamentoGrupo) {
        this.faturamentoGrupo = faturamentoGrupo;
    }

    public Rota getRota() {
        return this.rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public LeituraAnormalidade getLeituraAnormalidadeFaturamento() {
        return this.leituraAnormalidadeFaturamento;
    }

    public void setLeituraAnormalidadeFaturamento(LeituraAnormalidade leituraAnormalidadeFaturamento) {
        this.leituraAnormalidadeFaturamento = leituraAnormalidadeFaturamento;
    }

    public ConsumoTipo getConsumoTipo() {
        return this.consumoTipo;
    }

    public void setConsumoTipo(ConsumoTipo consumoTipo) {
        this.consumoTipo = consumoTipo;
    }

    public ConsumoAnormalidade getConsumoAnormalidade() {
        return this.consumoAnormalidade;
    }

    public void setConsumoAnormalidade(ConsumoAnormalidade consumoAnormalidade) {
        this.consumoAnormalidade = consumoAnormalidade;
    }

    public Conta getConta() {
        return this.conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @SuppressWarnings("rawtypes")
    public Set getMovimentoContaPrefaturadaCategorias() {
        return this.movimentoContaPrefaturadaCategorias;
    }

    @SuppressWarnings("rawtypes")
    public void setMovimentoContaPrefaturadaCategorias(Set movimentoContaPrefaturadaCategorias) {
        this.movimentoContaPrefaturadaCategorias = movimentoContaPrefaturadaCategorias;
    }

    @SuppressWarnings("rawtypes")
    public Set getMovimentoContaImpostoDeduzidos() {
        return this.movimentoContaImpostoDeduzidos;
    }

    @SuppressWarnings("rawtypes")
    public void setMovimentoContaImpostoDeduzidos(Set movimentoContaImpostoDeduzidos) {
        this.movimentoContaImpostoDeduzidos = movimentoContaImpostoDeduzidos;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

	public Short getIndicadorGeracaoConta() {
		return indicadorGeracaoConta;
	}

	public void setIndicadorGeracaoConta(Short indicadorGeracaoConta) {
		this.indicadorGeracaoConta = indicadorGeracaoConta;
	}

	public Integer getConsumoImoveisVinculados() {
		return consumoImoveisVinculados;
	}

	public void setConsumoImoveisVinculados(Integer consumoImoveisVinculados) {
		this.consumoImoveisVinculados = consumoImoveisVinculados;
	}

	public LeituraAnormalidade getLeituraAnormalidadeLeitura() {
		return leituraAnormalidadeLeitura;
	}

	public void setLeituraAnormalidadeLeitura(
			LeituraAnormalidade leituraAnormalidadeLeitura) {
		this.leituraAnormalidadeLeitura = leituraAnormalidadeLeitura;
	}

	public CobrancaDocumento getCobrancaDocumento() {
		return cobrancaDocumento;
	}

	public void setCobrancaDocumento(CobrancaDocumento cobrancaDocumento) {
		this.cobrancaDocumento = cobrancaDocumento;
	}

	public Integer getLeituraHidrometroAnterior() {
		return leituraHidrometroAnterior;
	}

	public void setLeituraHidrometroAnterior(Integer leituraHidrometroAnterior) {
		this.leituraHidrometroAnterior = leituraHidrometroAnterior;
	}  
	
	public Short getIndicadorRetransmissao() {
		return indicadorRetransmissao;
	}

	public void setIndicadorRetransmissao(Short indicadorRetransmissao) {
		this.indicadorRetransmissao = indicadorRetransmissao;
	}

	public BigDecimal getValorRateioAgua() {
		return valorRateioAgua;
	}

	public void setValorRateioAgua(BigDecimal valorRateioAgua) {
		this.valorRateioAgua = valorRateioAgua;
	}

	public BigDecimal getValorRateioEsgoto() {
		return valorRateioEsgoto;
	}

	public void setValorRateioEsgoto(BigDecimal valorRateioEsgoto) {
		this.valorRateioEsgoto = valorRateioEsgoto;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	} 
	public Short getIndicadorAlteracao() {
		return indicadorAlteracao;
	}

	public void setIndicadorAlteracao(Short indicadorAlteracao) {
		this.indicadorAlteracao = indicadorAlteracao;
	}  

}
