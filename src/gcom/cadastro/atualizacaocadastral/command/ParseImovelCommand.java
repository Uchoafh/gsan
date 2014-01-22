package gcom.cadastro.atualizacaocadastral.command;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import gcom.cadastro.IRepositorioCadastro;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.ImagemAtualizacaoCadastral;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.util.ControladorUtilLocal;
import gcom.util.ParserUtil;

public class ParseImovelCommand extends AbstractAtualizacaoCadastralCommand {

	public ParseImovelCommand(ParserUtil parser, IRepositorioCadastro repositorioCadastro, ControladorUtilLocal controladorUtil, 
			ControladorTransacaoLocal controladorTransacao, IRepositorioImovel repositorioImovel, 
			ControladorImovelLocal controladorImovel, ControladorClienteLocal controladorCliente) {
		super(parser, repositorioCadastro, controladorUtil, controladorTransacao, repositorioImovel, controladorImovel, controladorCliente);
	}

	public void execute(AtualizacaoCadastral atualizacao) throws Exception {
		Map<String, String> linha = atualizacao.getLinhaImovel();
		
		Integer matricula = Integer.parseInt(parser.obterDadoParser(9));
		linha.put("matricula", "" + matricula);

		for (String nomeImagem : atualizacao.getNomesImagens()) {
			if (nomeImagem.contains("" + matricula)) {
				File file = new File(nomeImagem);

				BufferedImage image = ImageIO.read(file);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, nomeImagem.split("[.]")[1], baos);
				byte[] imagem = baos.toByteArray();

				ImagemAtualizacaoCadastral imagemAtualizacaoCadastral = new ImagemAtualizacaoCadastral();
				imagemAtualizacaoCadastral.setIdImovel(matricula);
				imagemAtualizacaoCadastral.setImagem(imagem);
				imagemAtualizacaoCadastral.setUltimaAlteracao(new Date());

				controladorUtil.inserir(imagemAtualizacaoCadastral);

				baos.close();
				file.delete();
			}
		}

		String codigoCliente = parser.obterDadoParser(30).trim();
		linha.put("codigoCliente", codigoCliente);

		String inscricao = parser.obterDadoParser(17).trim();
		linha.put("inscricao", inscricao);

		String rota = parser.obterDadoParser(2).trim();
		linha.put("rota", rota);

		String face = parser.obterDadoParser(2).trim();
		linha.put("face", face);

		String codigoMunicipio = parser.obterDadoParser(8).trim();
		linha.put("codigoMunicipio", codigoMunicipio);

		String numeroIPTU = parser.obterDadoParser(31).trim();
		linha.put("numeroIPTU", numeroIPTU.trim().equals("") ? null : numeroIPTU);

		String numeroCelpa = parser.obterDadoParser(20).trim();
		linha.put("numeroCelpa", numeroCelpa);

		String numeroPontosUteis = parser.obterDadoParser(5).trim();
		linha.put("numeroPontosUteis", numeroPontosUteis);

		String numeroOcupantes = parser.obterDadoParser(5).trim();
		linha.put("numeroOcupantes", numeroOcupantes);

		String tipoLogradouroImovel = parser.obterDadoParser(2).trim();
		linha.put("idTipoLogradouroImovel", tipoLogradouroImovel);

		String logradouroImovel = parser.obterDadoParser(40).trim();
		linha.put("logradouroImovel", logradouroImovel);

		String numeroImovel = parser.obterDadoParser(5).trim();
		linha.put("numeroImovel", numeroImovel);

		String complementoImovel = parser.obterDadoParser(25).trim();
		linha.put("complementoImovel", complementoImovel);

		String bairro = parser.obterDadoParser(20).trim();
		linha.put("bairro", bairro);

		String cep = parser.obterDadoParser(8).trim();
		linha.put("cep", cep);

		String municipio = parser.obterDadoParser(15).trim();
		linha.put("municipio", municipio);

		String codigoLogradouro = parser.obterDadoParser(9).trim();
		linha.put("codigoLogradouro", codigoLogradouro);

		String subcategoriaR1 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaR1", subcategoriaR1);

		String subcategoriaR2 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaR2", subcategoriaR2);

		String subcategoriaR3 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaR3", subcategoriaR3);

		String subcategoriaR4 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaR4", subcategoriaR4);

		String subcategoriaC1 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaC1", subcategoriaC1);

		String subcategoriaC2 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaC2", subcategoriaC2);

		String subcategoriaC3 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaC3", subcategoriaC3);

		String subcategoriaC4 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaC4", subcategoriaC4);

		String subcategoriaP1 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaP1", subcategoriaP1);

		String subcategoriaP2 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaP2", subcategoriaP2);

		String subcategoriaP3 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaP3", subcategoriaP3);

		String subcategoriaP4 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaP4", subcategoriaP4);

		String subcategoriaI1 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaI1", subcategoriaI1);

		String subcategoriaI2 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaI2", subcategoriaI2);

		String subcategoriaI3 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaI3", subcategoriaI3);

		String subcategoriaI4 = parser.obterDadoParser(3).trim();
		linha.put("subcategoriaI4", subcategoriaI4);

		String fonteAbastecimento = parser.obterDadoParser(2).trim();
		linha.put("fonteAbastecimento", fonteAbastecimento);

		String latitude = parser.obterDadoParser(20).trim();
		linha.put("latitude", latitude);

		String longitude = parser.obterDadoParser(20).trim();
		linha.put("longitude", longitude);

		String data = parser.obterDadoParser(26).trim();
		linha.put("data", data);
		
		validaCamposImovel(atualizacao);
	}

	private void validaCamposImovel(AtualizacaoCadastral atualizacao) {
		
	}
}