package gcom.gui.seguranca.acesso;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import org.apache.struts.validator.ValidatorForm;


/**
 * Esse action form manipula os dados da p�gina de alterar a senha do usu�rio 
 *
 * @author Pedro Alexandre
 * @date 07/07/2006
 */
public class EfetuarAlteracaoSenhaActionForm extends ValidatorForm {
    private String login;
    private String dataNascimento;
    private String cpf;
    private String senha;
    private String lembreteSenha;
    private String novaSenha;
    private String confirmacaoNovaSenha;
    private static final long serialVersionUID = 1L;
    /**
	 * @return Retorna o campo login.
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login O login a ser setado.
	 */
	public void setLogin(String login) {
		this.login = login;
	}


	/**
	 * @return Retorna o campo senha.
	 */
	public String getSenha() {
		return senha;
	}


	/**
	 * @param senha O senha a ser setado.
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}


	/**
     * <<Descri��o do m�todo>>
     *
     * @param actionMapping       Descri��o do par�metro
     * @param httpServletRequest  Descri��o do par�metro
     */
    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        login = null;
        senha = null;
    }


	/**
	 * @return Retorna o campo cpf.
	 */
	public String getCpf() {
		return cpf;
	}


	/**
	 * @param cpf O cpf a ser setado.
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	/**
	 * @return Retorna o campo dataNascimento.
	 */
	public String getDataNascimento() {
		return dataNascimento;
	}


	/**
	 * @param dataNascimento O dataNascimento a ser setado.
	 */
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	/**
	 * @return Retorna o campo confirmacaoNovaSenha.
	 */
	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}


	/**
	 * @param confirmacaoNovaSenha O confirmacaoNovaSenha a ser setado.
	 */
	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}


	/**
	 * @return Retorna o campo lembreteSenha.
	 */
	public String getLembreteSenha() {
		return lembreteSenha;
	}


	/**
	 * @param lembreteSenha O lembreteSenha a ser setado.
	 */
	public void setLembreteSenha(String lembreteSenha) {
		this.lembreteSenha = lembreteSenha;
	}


	/**
	 * @return Retorna o campo novaSenha.
	 */
	public String getNovaSenha() {
		return novaSenha;
	}


	/**
	 * @param novaSenha O novaSenha a ser setado.
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
}
