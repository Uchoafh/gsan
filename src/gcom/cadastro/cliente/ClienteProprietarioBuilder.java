package gcom.cadastro.cliente;

import gcom.cadastro.atualizacaocadastral.command.AtualizacaoCadastralImovel;
import gcom.cadastro.endereco.EnderecoTipo;

import org.apache.commons.lang.StringUtils;

public class ClienteProprietarioBuilder extends ClienteBuilder {
	public ClienteProprietarioBuilder(AtualizacaoCadastralImovel atualizacaoCadastralImovel){
		super(atualizacaoCadastralImovel);
	}

	public IClienteAtualizacaoCadastral buildCliente(Short clienteRelacaoTipo) {
		buildCliente(PROPRIETARIO, clienteRelacaoTipo);
		
		String campo;
		
		clienteTxt.setDescricaoLogradouro(atualizacaoCadastralImovel.getLinhaCliente("logradouroProprietario"));
		
		campo = atualizacaoCadastralImovel.getLinhaCliente("idTipoLogradouroProprietario");
		if (StringUtils.isNotEmpty(campo) && StringUtils.isNumeric(campo)){
			clienteTxt.setIdLogradouroTipo(Integer.parseInt(campo) == 0 ? null : Integer.parseInt(campo));
		}

		clienteTxt.setNumeroImovel(atualizacaoCadastralImovel.getLinhaCliente("numeroProprietario"));
		clienteTxt.setComplementoEndereco(atualizacaoCadastralImovel.getLinhaCliente("complementoProprietario"));
		clienteTxt.setNomeBairro(atualizacaoCadastralImovel.getLinhaCliente("bairroProprietario"));
		
		campo = atualizacaoCadastralImovel.getLinhaCliente("cepProprietario");
		if (StringUtils.isNotEmpty(campo) && StringUtils.isNumeric(campo)){
			clienteTxt.setCodigoCep(Integer.parseInt(campo));
		}
		
		clienteTxt.setNomeMunicipio(atualizacaoCadastralImovel.getLinhaCliente("municipioProprietario"));
		
		campo = atualizacaoCadastralImovel.getLinhaCliente("tipoEnderecoProprietario");
		clienteTxt.setEnderecoTipo(new EnderecoTipo(Integer.parseInt(campo) == 0 ? null : Integer.parseInt(campo)));
		
		return clienteTxt;
	}
}
