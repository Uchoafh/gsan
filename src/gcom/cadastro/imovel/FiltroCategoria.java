package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroCategoria extends Filtro {
	
	private static final long serialVersionUID = 1L;

    /**
     * Description of the Field
     */
    public final static String CODIGO = "id";

    /**
     * Description of the Field
     */
    public final static String DESCRICAO = "descricao";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";

    /**
     * Description of the Field
     */
    public final static String TIPO_CATEGORIA = "categoriaTipo.id";

    /**
     * Construtor da classe FiltroCategoria
     */
    public FiltroCategoria() {
    }

    /**
     * Construtor da classe FiltroCategoria
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroCategoria(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
