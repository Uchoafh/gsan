package gcom.gui.faturamento;

import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir D�bito a Cobrar ao Imovel [UC0183] Inserir D�bito a Cobrar
 * 
 * @author Rafael Santos
 * @since 21/12/2005
 * 
 */
public class ExibirInserirDebitoACobrarAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
		.findForward("exibirInserirDebitoACobrar");
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Fachada fachada = Fachada.getInstancia();
		
		InserirDebitoACobrarActionForm inserirDebitoACobrarActionForm = (InserirDebitoACobrarActionForm) actionForm;
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		String limparForm = (String) httpServletRequest
		.getParameter("limparForm");
		String idImovel = null;
		String idRegistroAtendimento = inserirDebitoACobrarActionForm.getRegistroAtendimento();
		String idOrdemSerico = inserirDebitoACobrarActionForm.getOrdemServico();
		String idImovelInformado = inserirDebitoACobrarActionForm.getIdImovel();
		String numeroPrestacoes = inserirDebitoACobrarActionForm.getNumeroPrestacoes();
		String menu = httpServletRequest.getParameter("menu");
		
		
		if(menu!= null && menu.equals("sim")){
			
			inserirDebitoACobrarActionForm.reset(actionMapping,
					httpServletRequest);
			
			idImovel = null;
			
			idRegistroAtendimento = null;
			
			idOrdemSerico = null;
			
			idImovelInformado = null;
			
			numeroPrestacoes = null;
			
			menu = null;
		}
		
		if(numeroPrestacoes == null || numeroPrestacoes.equals("")){	
			numeroPrestacoes = "1";
			inserirDebitoACobrarActionForm.setNumeroPrestacoes(numeroPrestacoes);
		}
		
		
		String idDebitoTipo = inserirDebitoACobrarActionForm.getIdTipoDebito();
		
		BigDecimal percentualTaxaJurosFinanciamento = fachada
		.pesquisarParametrosDoSistema()
		.getPercentualTaxaJurosFinanciamento();
		
		percentualTaxaJurosFinanciamento = percentualTaxaJurosFinanciamento.setScale(2);
		
		inserirDebitoACobrarActionForm
		.setTaxaJurosFinanciamento(percentualTaxaJurosFinanciamento
				.toString().replace('.', ','));
		
		if (httpServletRequest.getParameter("objetoConsulta") != null
				&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("1")) {
			
			//pesquisar debito Tipo
			if (idDebitoTipo != null && !idDebitoTipo.trim().equals("")) {
				//[FS0008] - Verificar Exist�ncia de d�bito acobrar para o registro de atendimento
				DebitoTipo debitoTipo = fachada.pesquisarDebitoTipo(idDebitoTipo);
				if(debitoTipo != null){

					//[FS0018] - Verificar bloqueio do tipo do d�bito para inclus�o on-line da d�bito a cobrar.
					if (debitoTipo.getIndicadorJurosParCliente() != null
							&& debitoTipo.getIndicadorJurosParCliente().compareTo(ConstantesSistema.SIM) == 0) {
						throw new ActionServletException(
							"atencao.debito_a_cobrar.contrato_parcelamento", debitoTipo.getDescricao());
					}
					
					inserirDebitoACobrarActionForm.setIdTipoDebito(debitoTipo.getId().toString());
					inserirDebitoACobrarActionForm.setDescricaoTipoDebito(debitoTipo.getDescricao());
					inserirDebitoACobrarActionForm.setValorTotalServico(
							Util.formatarMoedaReal(debitoTipo.getValorSugerido()));
					httpServletRequest.setAttribute("corDebitoTipo",
					"valor");
					httpServletRequest.setAttribute("nomeCampo",
					"valorTotalServico");
				}else{
					inserirDebitoACobrarActionForm.setIdTipoDebito("");
					inserirDebitoACobrarActionForm.setDescricaoTipoDebito("Tipo de D�bito Inexistente");
					httpServletRequest.setAttribute("corDebitoTipo",
							null);
					httpServletRequest.setAttribute("nomeCampo",
					"idTipoDebito");
				}
			}
			
		}else if (  (idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals(""))  
				|| (idOrdemSerico != null && !idOrdemSerico.trim().equals(""))  
				|| (idImovelInformado != null && !idImovelInformado.equals(""))
		) {
			
			//pesquisou pela RA
			if (httpServletRequest.getParameter("objetoConsulta") != null
					&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("2")) {
				
				//pesquisa o imovel pelo registro de atendimento 
				if(idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals("")){
					FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
					filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID,idRegistroAtendimento));
					//filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.IMOVEL_ID);
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
					
					Collection colecaoRegistroAtendimento = fachada.pesquisar(filtroRegistroAtendimento,RegistroAtendimento.class.getName());
					if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){
						
						RegistroAtendimento registroAtendimento = (RegistroAtendimento) colecaoRegistroAtendimento.iterator().next();
						
						//Registro de Atendimento n�o est� associado a um im�vel
						if(registroAtendimento.getImovel() == null){
							//FS0005 - Validar Registro de Atendimento
							throw new ActionServletException(
							"atencao.registro_atendimento.nao.associado.imovel");
						}
						
						//Registro de Atendimento est� encerradp
						if(registroAtendimento.getAtendimentoMotivoEncerramento() != null){
							//FS0005 - Validar Registro de Atendimento
							throw new ActionServletException("atencao.registro_atendimento.esta.encerrado");
						}
						
						//Especifica��o do Tipo de Solicita��o do Registro de Atendimento n�o permite cobran�a de d�bito
						if(registroAtendimento.getSolicitacaoTipoEspecificacao().getIndicadorGeracaoDebito() == 2){
							//FS0005 - Validar Registro de Atendimento
							throw new ActionServletException(
							"atencao.registro_atendimento.nao.permite.cobranca.debito");
						}
						
						//caso tenha o imovel
						idImovel = registroAtendimento.getImovel().getId().toString();
						
						inserirDebitoACobrarActionForm.setRegistroAtendimento(registroAtendimento.getId().toString());
						inserirDebitoACobrarActionForm.setNomeRegistroAtendimento(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());
						
						httpServletRequest.setAttribute("corRegistroAtendimento",
						"valor");
						httpServletRequest.setAttribute("nomeCampo",
						"ordemServico");
						
						sessao.setAttribute("travarRegistroAtendimento",
						"nao");
						sessao.setAttribute("travarOrdemServico",
								null);
						sessao.setAttribute("travarImovel",
								null);
						inserirDebitoACobrarActionForm.setOrdemServico("");
						inserirDebitoACobrarActionForm.setNomeOrdemServico("");
						
						sessao.setAttribute("travarDebitoTipo",
						"nao");
						
						//n�o encontrou a RA
					}else{
						//FS0004-Validar Registro de Atendimento
						inserirDebitoACobrarActionForm.setCodigoImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovel("");
						inserirDebitoACobrarActionForm.setNomeCliente("");
						inserirDebitoACobrarActionForm.setSituacaoAgua("");
						inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
						inserirDebitoACobrarActionForm.setNomeRegistroAtendimento("RA inexistente");
						httpServletRequest.setAttribute("corRegistroAtendimento",
						"exception");
						httpServletRequest.setAttribute("nomeCampo",
						"registroAtendimento");
						sessao.setAttribute("travarRegistroAtendimento",
						"nao");
					}
				}
			}
			
			//pesquisou pela ordem de seri�o
			if (httpServletRequest.getParameter("objetoConsulta") != null
					&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("3")) {
				
				//pesquisa o imovel pela ordem de servi�o 
				if(idOrdemSerico != null && !idOrdemSerico.trim().equals("")){
					FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
					filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID,idOrdemSerico));
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("imovel");
					//filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.ID_IMOVEL);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.REGISTRO_ATENDIMENTO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DEBITO_TIPO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SOLICITACAO_TIPO_ESPECIFICACAO);
					//filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("cobrancaDocumento.imovel.id");
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("cobrancaDocumento.imovel");
					
					Collection colecaoOrdemServico = fachada.pesquisar(filtroOrdemServico,OrdemServico.class.getName());
					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
						
						OrdemServico ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();
						
//						//Ordem de Servi�o n�o est� associada a um Registro de Atendimento
//						if(ordemServico.getRegistroAtendimento() == null){
//						//FS0005 - Validar Registro de Atendimento
//						throw new ActionServletException(
//						"atencao.ordem_servico.nao.esta.associado.registro_atendimento");
//						}
						
						//caso tenha o imovel do RA
						if (ordemServico.getRegistroAtendimento() != null) {
							//Registro de Atendimento da Ordem de Servi�o n�o est� associado a um im�vel
							if(ordemServico.getRegistroAtendimento().getImovel() == null){
								//FS0007 - Validar Ordem de Servi�o
								throw new ActionServletException(
								"atencao.ordem_servico.imovel.registro_atendimento.nao.associado");
							} else {
								idImovel = ordemServico.getRegistroAtendimento().getImovel().getId().toString();
							}
						} else {
							if (ordemServico.getCobrancaDocumento() != null) {
								idImovel = ordemServico.getCobrancaDocumento().getImovel().getId().toString();
							}
						}			
						
						inserirDebitoACobrarActionForm.setOrdemServico(ordemServico.getId().toString());
						inserirDebitoACobrarActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());
						
						if (ordemServico.getRegistroAtendimento() != null) {
							inserirDebitoACobrarActionForm.setRegistroAtendimento(ordemServico.getRegistroAtendimento().getId().toString());
							inserirDebitoACobrarActionForm.setNomeRegistroAtendimento(ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getDescricao());
						} else {
							inserirDebitoACobrarActionForm.setRegistroAtendimento("");
							inserirDebitoACobrarActionForm.setNomeRegistroAtendimento("");
						}
						
						
						//seta a RA
						sessao.setAttribute("travarRegistroAtendimento",null);
						sessao.setAttribute("travarImovel",null);	
						
						httpServletRequest.setAttribute("corRegistroAtendimento",
						"valor");
						
						httpServletRequest.setAttribute("corNomeOrdemServico",
						"valor");
						httpServletRequest.setAttribute("nomeCampo",
						"idTipoDebito");
						
						sessao.setAttribute("travarOrdemServico",
						"nao");
						sessao.setAttribute("travarDebitoTipo",
						"nao");
						
						//validar debito tipo
						if(ordemServico.getServicoTipo().getDebitoTipo() != null){
							idDebitoTipo = ordemServico.getServicoTipo().getDebitoTipo().getId().toString();
							inserirDebitoACobrarActionForm.setIdTipoDebito(idDebitoTipo);
							inserirDebitoACobrarActionForm.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getDescricao());
							sessao.setAttribute("travarDebitoTipo",
									null);
						}else{
							sessao.setAttribute("travarDebitoTipo",
							"valor");
						}
						
						
						//n�o encontrou a RA
					}else{
						//FS0004-Validar Ordem Servico
						inserirDebitoACobrarActionForm.setCodigoImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovel("");
						inserirDebitoACobrarActionForm.setNomeCliente("");
						inserirDebitoACobrarActionForm.setSituacaoAgua("");
						inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
						inserirDebitoACobrarActionForm.setNomeOrdemServico("OS inexistente");
						httpServletRequest.setAttribute("corNomeOrdemServico",
						"exception");
						httpServletRequest.setAttribute("nomeCampo",
						"ordemServico");
						sessao.setAttribute("travarOrdemServico",
						"nao");
						sessao.setAttribute("travarDebitoTipo",
						"nao");
						
					}
				}
			}
			
			//pesquisou pelo Im�vel
			if (httpServletRequest.getParameter("objetoConsulta") != null
					&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("4")) {
				
				//pesquisa o imovel pelo registro de atendimento 
				if(idImovelInformado != null && !idImovelInformado.trim().equals("")){
					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelInformado));					
					
					Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
					if(colecaoImovel != null && !colecaoImovel.isEmpty()){
						
						Imovel imovel = (Imovel) colecaoImovel.iterator().next();
						
						//caso tenha o imovel
						idImovel = imovel.getId().toString();
						
						inserirDebitoACobrarActionForm.setIdImovel(imovel.getId().toString());
						
						httpServletRequest.setAttribute("corRegistroAtendimento",
						"valor");
						httpServletRequest.setAttribute("nomeCampo",
						"idImovel");
						
						sessao.setAttribute("travarRegistroAtendimento",
								null);
						sessao.setAttribute("travarOrdemServico",
								null);
						inserirDebitoACobrarActionForm.setOrdemServico("");
						inserirDebitoACobrarActionForm.setNomeOrdemServico("");
						
						sessao.setAttribute("travarDebitoTipo",
						"nao");
						
						sessao.setAttribute("travarImovel","n�o");
						//n�o encontrou o im�vel
					}else{
						inserirDebitoACobrarActionForm.setIdImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovelInformada("Im�vel Inexistente");
						inserirDebitoACobrarActionForm.setCodigoImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovel("");
						inserirDebitoACobrarActionForm.setNomeCliente("");
						inserirDebitoACobrarActionForm.setSituacaoAgua("");
						inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
						//inserirDebitoACobrarActionForm.setNomeRegistroAtendimento("RA inexistente");
						httpServletRequest.setAttribute("corInscricao","exception");
						//httpServletRequest.setAttribute("nomeCampo","registroAtendimento");
						//sessao.setAttribute("travarRegistroAtendimento","nao");
					}
				}
			}
			
			
			//verifica o imovel para ser carregado
			if(idImovel != null && !idImovel.equals("")){
				// Pesquisa o imovel na base
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(
						FiltroImovel.ID, idImovel));
				
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
				
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroImovel
				.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroImovel
				.adicionarCaminhoParaCarregamentoEntidade("quadra");
				
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
				
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
				
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
				filtroImovel
				.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
				filtroImovel
				.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
				/*filtroImovel
				 .adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao.id");*/
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
				
				filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(
						FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO,
						Imovel.IMOVEL_EXCLUIDO, FiltroParametro.CONECTOR_OR,2));
				
				filtroImovel.adicionarParametro(new ParametroNulo(
						FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO));
				
				Collection<Imovel> imovelPesquisado = fachada.pesquisar(
						filtroImovel, Imovel.class.getName());
				
				// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
				// Inxistente
				if (imovelPesquisado == null || imovelPesquisado.isEmpty()) {
					inserirDebitoACobrarActionForm.setCodigoImovel("");
					inserirDebitoACobrarActionForm.setInscricaoImovel("Matr�cula Inexistente");
					inserirDebitoACobrarActionForm.setNomeCliente("");
					inserirDebitoACobrarActionForm.setSituacaoAgua("");
					inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
					httpServletRequest.setAttribute("corMatriculaImovel",
							null);
					httpServletRequest.setAttribute("nomeCampo",
					"codigoImovel");
				}
				
				// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
				// Escluido
				if (imovelPesquisado != null && !imovelPesquisado.isEmpty()) {
					Imovel imovel = imovelPesquisado.iterator().next();
					if (imovel.getIndicadorExclusao() == Imovel.IMOVEL_EXCLUIDO) {
						throw new ActionServletException(
						"atencao.pesquisa.imovel.excluido");
					}
				}
				
				// [FS0002 - Verificar usu�rio com d�bito em cobran�a
				// administrativa]
				if (imovelPesquisado != null && !imovelPesquisado.isEmpty()) {
					Imovel imovel = imovelPesquisado.iterator().next();
					FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
					
					filtroImovelCobrancaSituacao
					.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
					filtroImovelCobrancaSituacao
					.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,CobrancaSituacao.COBRANCA_ADMINISTRATIVA));
					
					filtroImovelCobrancaSituacao
					.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));
					
					filtroImovelCobrancaSituacao
					.adicionarParametro(new ParametroSimples(
							FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel
							.getId()));
					
					Collection imovelCobrancaSituacaoEncontrada = fachada
					.pesquisar(filtroImovelCobrancaSituacao,
							ImovelCobrancaSituacao.class.getName());
					
					// Verifica se o im�vel tem d�bito em cobran�a administrativa
					if (imovelCobrancaSituacaoEncontrada != null
							&& !imovelCobrancaSituacaoEncontrada.isEmpty()) {
						throw new ActionServletException(
						"atencao.pesquisa.imovel.cobranca_administrativa");
					}
				}
				
				// [FS0003 - Verificar situa��o liga��o de �giua e esgoto]
				if (imovelPesquisado != null && !imovelPesquisado.isEmpty()) {
					Imovel imovel = imovelPesquisado.iterator().next();
					
					/**
					 * Bruno Barros
					 * 
					 * 16/01/2009
					 * 
					 * Caso de uso alterado FS0003
					 */
					if ( !imovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao().equals( ConstantesSistema.SIM ) &&
							!imovel.getLigacaoEsgotoSituacao().getIndicadorFaturamentoSituacao().equals( ConstantesSistema.SIM ) ){
						
						/*
						 * Colocado por Raphael Rossiter em 03/10/2007
						 * OBJ: Inserir debito a cobrar independente da situacao da ligacao de agua e esgoto
						 * do imovel
						 */
						boolean temPermissaoInserirDebitoACobrarImovelSituacao = 
							fachada.verificarPermissaoInserirDebitoACobrarImovelSituacao(usuario);
						
						if (!temPermissaoInserirDebitoACobrarImovelSituacao){
							throw new ActionServletException("atencao.pesquisa.imovel.inativo");
						}
						
					}
				}
				
				/*
				 * Fluxo Principal - PASSO 4 FiltroDebitoTipo filtroDebitoTipo = new
				 * FiltroDebitoTipo();
				 * 
				 * filtroDebitoTipo.adicionarParametro(new ParametroSimples(
				 * FiltroDebitoTipo.INDICADOR_USO,
				 * ConstantesSistema.INDICADOR_USO_ATIVO));
				 * 
				 * filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
				 * 
				 * filtroDebitoTipo.adicionarParametro(new ParametroSimples(
				 * FiltroDebitoTipo.FINANCIAMENTO_TIPO, FinanciamentoTipo.NORMAL));
				 * 
				 * filtroDebitoTipo.adicionarParametro( new ParametroSimples(
				 * filtroDebitoTipo.INDICADOR_GERACAO_AUTOMATICA, DebitoTipo.
				 * //COMPARAR COM 2 ))
				 */
				
				// Obtem o cliente imovel do imovel pesquisado
				if (imovelPesquisado != null && !imovelPesquisado.isEmpty()) {
					
					Imovel imovel = imovelPesquisado.iterator().next();
					
					sessao.setAttribute("imovelPesquisado", imovel);
					
					FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
					filtroClienteImovel
					.adicionarCaminhoParaCarregamentoEntidade("cliente");
					filtroClienteImovel.adicionarParametro(new ParametroSimples(
							FiltroClienteImovel.IMOVEL_ID, idImovel));
					filtroClienteImovel.adicionarParametro(new ParametroSimples(
							FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
							ClienteRelacaoTipo.USUARIO));
					
					Collection<ClienteImovel> clienteImovelPesquisado = fachada
					.pesquisar(filtroClienteImovel, ClienteImovel.class
							.getName());
					
					if (clienteImovelPesquisado != null
							&& !clienteImovelPesquisado.isEmpty()) {
						ClienteImovel clienteImovel = clienteImovelPesquisado
						.iterator().next();
						if (clienteImovel.getCliente() != null) {
							inserirDebitoACobrarActionForm
							.setNomeCliente(clienteImovel.getCliente()
									.getNome());
						}
					}
					if (imovel.getLigacaoAguaSituacao() != null) {
						inserirDebitoACobrarActionForm.setSituacaoAgua(imovel
								.getLigacaoAguaSituacao().getDescricao());
					}
					
					if (imovel.getLigacaoEsgotoSituacao() != null) {
						inserirDebitoACobrarActionForm.setSituacaoEsgoto(imovel
								.getLigacaoEsgotoSituacao().getDescricao());
					}
					inserirDebitoACobrarActionForm.setCodigoImovel(idImovel);
					
					inserirDebitoACobrarActionForm.setInscricaoImovel(imovel
							.getInscricaoFormatada());
					
					if(idImovelInformado != null && !idImovelInformado.equals("")){
						inserirDebitoACobrarActionForm.setInscricaoImovelInformada(imovel
								.getInscricaoFormatada());
					}
					
					/*inserirDebitoACobrarActionForm.setRegistroAtendimento("");
					 inserirDebitoACobrarActionForm.setOrdemServico("");
					 inserirDebitoACobrarActionForm.setIdTipoDebito("");
					 inserirDebitoACobrarActionForm.setDescricaoTipoDebito("");
					 inserirDebitoACobrarActionForm.setValorPrestacao("");
					 inserirDebitoACobrarActionForm.setValorEntrada("");
					 inserirDebitoACobrarActionForm.setValorTotalServico("");
					 inserirDebitoACobrarActionForm.setValorTotalServicoAParcelar("");
					 inserirDebitoACobrarActionForm.setvalorTotalServicoAParcelar("");
					 inserirDebitoACobrarActionForm.setValorJuros("");
					 inserirDebitoACobrarActionForm.setPercentualAbatimento("");
					 inserirDebitoACobrarActionForm.setNumeroPrestacoes("");*/
					
					httpServletRequest.setAttribute("corMatriculaImovel",
					"valor");
					httpServletRequest.setAttribute("nomeCampo",
					"idTipoDebito");
				}
			}//fim do imovel
		}else{
			sessao.setAttribute("travarRegistroAtendimento",
			"nao");
			sessao.setAttribute("travarDebitoTipo",
			"nao");
			sessao.setAttribute("travarOrdemServico",
			"nao");
			sessao.setAttribute("travarImovel",
			"nao");
			
		}
		
		if (limparForm != null && !limparForm.trim().equalsIgnoreCase("")) {
			
			inserirDebitoACobrarActionForm.reset(actionMapping,
					httpServletRequest);
			
			if (sessao.getAttribute("imovelPesquisado") != null) {
				sessao.removeAttribute("imovelPesquisado");
			}
		}
		
		sessao.removeAttribute("informarImovel");
		
		// -----------------------------------------------------------
		// Verificar permiss�o especial
		boolean temPermissaoInserirDebitoACobrarSemRa = fachada.verificarPermissaoInserirDebitoACobrarSemRa(usuario);
		// -----------------------------------------------------------
		if(temPermissaoInserirDebitoACobrarSemRa){         
			sessao.setAttribute("informarImovel", "sim");  
		}
		boolean alterarValorSugeridoEmTipoDebito = Fachada
		.getInstancia()
		.verificarPermissaoEspecial(
				PermissaoEspecial.ALTERAR_VALOR_SUGERIDO_EM_TIPO_DEBITO,
				usuario);
		
		httpServletRequest.setAttribute("alterarValorSugeridoEmTipoDebito",
				alterarValorSugeridoEmTipoDebito);
		
		
		if(inserirDebitoACobrarActionForm.getValorTotalServico() ==null 
				|| inserirDebitoACobrarActionForm.getValorTotalServico().equals("")){
			
			httpServletRequest.setAttribute("alterarValorSugeridoEmTipoDebito",true);
			
		}
		
		return retorno;
	}
}
