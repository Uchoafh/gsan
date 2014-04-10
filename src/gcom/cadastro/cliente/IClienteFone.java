package gcom.cadastro.cliente;

import java.util.Date;

public interface IClienteFone {

	public abstract Integer getId();

	public abstract void setId(Integer id);

	public abstract String getDdd();

	public abstract void setDdd(String ddd);

	public abstract String getTelefone();

	public abstract void setTelefone(String telefone);

	public abstract FoneTipo getFoneTipo();

	public abstract void setFoneTipo(FoneTipo foneTipo);

	public abstract Cliente getCliente();

	public abstract void setCliente(Cliente cliente);
	
	public abstract Date getUltimaAlteracao();
	
	public abstract void setUltimaAlteracao(Date ultimaAlteracao);
}