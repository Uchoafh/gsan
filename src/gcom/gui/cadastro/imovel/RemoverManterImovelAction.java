package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelInscricaoAlterada;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelInscricaoAlterada;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.micromedicao.ControladorMicromedicao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class RemoverManterImovelAction extends GcomAction {

    /**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param actionForm
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     * @param httpServletResponse
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     * @throws ControladorException 
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws ControladorException {

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        Fachada fachada = Fachada.getInstancia();

        
        
        //mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum
        //registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException(
                    "atencao.registros.nao_selecionados");
        }
        
        
        String[] idsFormatado = new String[ids.length];
        
        for (int i = 0; i < ids.length; i++) {
			String dadosImovel = ids[i];
			String[] idUltimaAlteracao = dadosImovel.split("-");
			String idImovel = idUltimaAlteracao[0].trim();
			
			idsFormatado[i] = idImovel;
			Calendar dataInicio = new GregorianCalendar();
			dataInicio.set(Calendar.YEAR,1900);
			dataInicio.set(Calendar.MONTH,0);
			dataInicio.set(Calendar.DATE,01);

			Calendar dataFim = new GregorianCalendar();
			dataFim.set(Calendar.YEAR,9999);
			dataFim.set(Calendar.MONTH,11);
			dataFim.set(Calendar.DATE,31);		
			
        	fachada.validarImovelEmCampo(Integer.parseInt(idImovel));
			
			ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper =  fachada.obterDebitoImovelOuCliente(1,idImovel,null,null,"190001","999912",dataInicio.getTime(),dataFim.getTime(),1,1,1,1,1,1,1,null);
        	boolean existeDebito = false;
        	if(obterDebitoImovelOuClienteHelper != null){
        		//contas
        		if(obterDebitoImovelOuClienteHelper.getColecaoContasValores() != null && !obterDebitoImovelOuClienteHelper.getColecaoContasValores().isEmpty()){
        			existeDebito = true;
        		}else 
        		//credito a realizar
        		if(obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar() != null && !obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar().isEmpty()){
        			existeDebito = true;	
        		}else 
        		//debito a cobrar
        		if(obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar() != null && !obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar().isEmpty()){
        			existeDebito = true;	
        		}else 
        		//guias pagamento
        		if(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores() != null && !obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores().isEmpty()){
        			existeDebito = true;	
        		}
        		
        	}
        	
        	//Se existir debito para o imovel
        	if(existeDebito){
        		throw new ActionServletException("atencao.imovel.possui.debito.nao.excluir");
        	}
        	
        	FiltroImovel filtroImovel = new FiltroImovel();
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID,idImovel));
        	
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel,Imovel.class.getName());
        	
        	//[FS0004] - Im�vel possui v�nculos no sistema
        	if(colecaoImovel != null && !colecaoImovel.isEmpty()){

        		Imovel imovel = (Imovel)colecaoImovel.iterator().next();

       		if((imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.LIGADO.intValue() 
        				
        			| imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.CORTADO.intValue())
        				
        			| (imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue())){
    				
        			throw new ActionServletException(
    					"atencao.imovel.possui.ligacao_agua.ligacao_esgoto");
        		}

	        		
	        	if((imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null) | 
	        		 imovel.getHidrometroInstalacaoHistorico() != null){
	        		//N�o � poss�vel excluir o(s) im�vel(is) selecionado(s) devido a exist�ncia de hidr�metro na liga��o de e/ou no po�o
	        		throw new ActionServletException(
					"atencao.imovel.possui.hidrometro.ligacao.poco");
	        	}
	        	
	        	FiltroImovel filtroImovelVinculados = new FiltroImovel();
	        	filtroImovelVinculados.adicionarParametro(new ParametroSimples("imovelCondominio.id", imovel.getId()));
	        	
	        	Collection<Imovel> imovelPesquisadoVinculados = fachada.pesquisar(filtroImovelVinculados, Imovel.class.getName());
	        	
	        	if((imovel.getIndicadorImovelCondominio() != null &&  imovel.getIndicadorImovelCondominio().shortValue() == Imovel.IMOVEL_CONDOMINIO.shortValue())
	        			& (imovelPesquisadoVinculados != null && !imovelPesquisadoVinculados.isEmpty())
	        		){
	        		//N�o � poss�vel excluir o(s) im�vel(is) selecionado(s) devido a ser(em) im�vel(is) condom�nio, com outros im�veis associados
	        		throw new ActionServletException(
					"atencao.imovel.ser.condominio.outros.imoveis");
	        	}
        		
        	}
        	
        	
		}
        
        
        
        /** alterado por pedro alexandre dia 17/11/2006 
         * Recupera o usu�rio logado para passar no met�do de atualizar im�vel 
         * para verificar se o usu�rio tem abrang�ncia para atualizar o im�vel
         * informado.
         */
        HttpSession sessao = httpServletRequest.getSession(false);
        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        
        fachada.removerImovel(ids, usuarioLogado);
	    
        // [FS0038 Verificar Exist�ncia de Altera��o de Inscri��o Pendente para o Im�vel]
        Date dataAtual = new Date();
        
        for (int i = 0; i < ids.length; i++) {
        	
        	String dadosImovel = ids[i];
			String[] idUltimaAlteracao = dadosImovel.split("-");
			String idImovel = idUltimaAlteracao[0].trim();
					
			FiltroImovelInscricaoAlterada filtroImovelInscricaoAlterada
				= new FiltroImovelInscricaoAlterada();
			
			filtroImovelInscricaoAlterada.adicionarParametro(
				new ParametroSimples(
					FiltroImovelInscricaoAlterada.IMOVEL_ID,
					idImovel));
			filtroImovelInscricaoAlterada.adicionarParametro(
				new ParametroSimples(
					FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
					ConstantesSistema.NAO));
			filtroImovelInscricaoAlterada.adicionarParametro(
				new ParametroSimples(
					FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA,
					ConstantesSistema.NAO));
			filtroImovelInscricaoAlterada.adicionarParametro(
				new ParametroNulo(
					FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));
			
			Collection<ImovelInscricaoAlterada> colecaoImovelInscricaoAlterada 
				= fachada.pesquisar(filtroImovelInscricaoAlterada,
									ImovelInscricaoAlterada.class.getName());
			
			if(!Util.isVazioOrNulo(colecaoImovelInscricaoAlterada)){		
				for (ImovelInscricaoAlterada imovelInscricaoAlterada : colecaoImovelInscricaoAlterada) {
					
					imovelInscricaoAlterada.setIndicadorAtualizacaoExcluida(ConstantesSistema.SIM);
					imovelInscricaoAlterada.setIndicadorImovelExcluido(ConstantesSistema.SIM);
					imovelInscricaoAlterada.setUltimaAlteracao(dataAtual);
					
					fachada.atualizar(imovelInscricaoAlterada);
				}
			}	
        }

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " Im�vel(is) removido(s) com sucesso.",
                    "Realizar outra Manuten��o de Im�vel",
                    "exibirFiltrarImovelAction.do");
        }

        return retorno;
    }

}
