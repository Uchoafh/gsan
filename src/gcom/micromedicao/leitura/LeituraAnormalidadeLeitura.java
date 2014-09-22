package gcom.micromedicao.leitura;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class LeituraAnormalidadeLeitura implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer id;
    private String descricaoFaturamento;
    private Short indicadorSemLeitura;
    private Short indicadorComLeitura;
    private Short indicadorUso;
    private Date ultimaAlteracao;

    public final static Integer ANTERIOR_MAIS_MEDIA = new Integer(0);
    public final static Integer ANTERIOR = new Integer(1);
    public final static Integer ANTERIOR_MAIS_CONSUMO = new Integer(2);
    public final static Integer INFORMADA = new Integer(3);
    public final static Integer NORMAL = new Integer(4);

    public LeituraAnormalidadeLeitura(String descricaoFaturamento,
            Short indicadorSemLeitura, Short indicadorComLeitura,
            Short indicadorUso, Date ultimaAlteracao) {
        this.descricaoFaturamento = descricaoFaturamento;
        this.indicadorSemLeitura = indicadorSemLeitura;
        this.indicadorComLeitura = indicadorComLeitura;
        this.indicadorUso = indicadorUso;
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public LeituraAnormalidadeLeitura() {
    }
    
    public LeituraAnormalidadeLeitura(Integer id) {
    	this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoFaturamento() {
        return this.descricaoFaturamento;
    }

    public void setDescricaoFaturamento(String descricaoFaturamento) {
        this.descricaoFaturamento = descricaoFaturamento;
    }

    public Short getIndicadorSemLeitura() {
        return this.indicadorSemLeitura;
    }

    public void setIndicadorSemLeitura(Short indicadorSemLeitura) {
        this.indicadorSemLeitura = indicadorSemLeitura;
    }

    public Short getIndicadorComLeitura() {
        return this.indicadorComLeitura;
    }

    public void setIndicadorComLeitura(Short indicadorComLeitura) {
        this.indicadorComLeitura = indicadorComLeitura;
    }

    public Short getIndicadorUso() {
        return this.indicadorUso;
    }

    public void setIndicadorUso(Short indicadorUso) {
        this.indicadorUso = indicadorUso;
    }

    public Date getUltimaAlteracao() {
        return this.ultimaAlteracao;
    }

    public void setUltimaAlteracao(Date ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}
