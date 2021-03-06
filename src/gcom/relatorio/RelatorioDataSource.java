package gcom.relatorio;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class RelatorioDataSource implements JRDataSource {
	private List data;

	private int index = -1;

	public RelatorioDataSource(List data) {
		this.data = data;
	}

	public Object getFieldValue(JRField field) throws JRException {
		RelatorioBean lineItem = (RelatorioBean) data.get(index);

		Object value = null;

		try {
			Object[] args = {};
			Class[] paramTypes = {};
			Class lineItemClass = lineItem.getClass();
			Method getMethod = lineItemClass.getDeclaredMethod(resolverNomeCampo(field.getName()),
					paramTypes);

			value = getMethod.invoke(lineItem, args);
		} catch (Exception e) {
			throw new JRException(e.getMessage());
		}
		return value;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 * @exception JRException
	 *                Descri��o da exce��o
	 */

	public boolean next() throws JRException {
		// Incrementa o indice para buscar o item da cole��o
		index++;

		return (index < data.size());
	}

	private String resolverNomeCampo(String nomeCampo) {

		if (!nomeCampo.startsWith("get")) {

			nomeCampo = "get" + nomeCampo.substring(0, 1).toUpperCase()
					+ nomeCampo.substring(1, nomeCampo.length());

		}
		return nomeCampo;

	}

}
