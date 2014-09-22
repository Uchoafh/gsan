package gcom.faturamento.debito;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DebitoACobrarGeral extends ObjetoTransacao {
	private static final long serialVersionUID = 1L;
	public final static Short INDICADOR_POSSUI_HISTORICO = new Short("1");
	public final static Short INDICADOR_NAO_POSSUI_HISTORICO = new Short("2");	
	
    private Integer id;
    private short indicadorHistorico;
    private Date ultimaAlteracao;
    private DebitoACobrarHistorico debitoACobrarHistorico;
    private DebitoACobrar debitoACobrar;

    public DebitoACobrarGeral() {
    }
    
    public DebitoACobrarGeral(Integer id) {
    	this.id = id;
    }
    
    public String[] retornaCamposChavePrimaria(){
		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}
	
    public DebitoACobrarGeral(short indicadorHistorico, Date ultimaAlteracao,DebitoACobrarHistorico debitoACobrarHistorico,DebitoACobrar debitoACobrar) {
        this.indicadorHistorico = indicadorHistorico;
        this.ultimaAlteracao = ultimaAlteracao;
        this.debitoACobrarHistorico = debitoACobrarHistorico;
        this.debitoACobrar = debitoACobrar;
    }
    
    public DebitoACobrarGeral(short indicadorHistorico) {
    	this.indicadorHistorico = indicadorHistorico;
    }

    public Filtro retornaFiltro(){
    	FiltroDebitoACobrarGeral filtroDebitoACobrarGeral = new FiltroDebitoACobrarGeral();
    	
    	filtroDebitoACobrarGeral.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarGeral.ID, this.getId()));		
    	
    	filtroDebitoACobrarGeral.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarHistorico");
    	filtroDebitoACobrarGeral.adicionarCaminhoParaCarregamentoEntidade("debitoACobrar");
    	
    	return filtroDebitoACobrarGeral; 
    }
    
    public DebitoACobrar getDebitoACobrar() {
		return debitoACobrar;
	}

	public void setDebitoACobrar(DebitoACobrar debitoACobrar) {
		this.debitoACobrar = debitoACobrar;
	}

	public DebitoACobrarHistorico getDebitoACobrarHistorico() {
		return debitoACobrarHistorico;
	}

	public void setDebitoACobrarHistorico(
			DebitoACobrarHistorico debitoACobrarHistorico) {
		this.debitoACobrarHistorico = debitoACobrarHistorico;
	}

	public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUltimaAlteracao() {
        return this.ultimaAlteracao;
    }

    public void setUltimaAlteracao(Date ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

	public short getIndicadorHistorico() {
		return indicadorHistorico;
	}

	public void setIndicadorHistorico(short indicadorHistorico) {
		this.indicadorHistorico = indicadorHistorico;
	}
}