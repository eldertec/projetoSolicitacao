package br.edu.faculdadedelta.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.faculdadedelta.dao.FuncionarioDaoElder;
import br.edu.faculdadedelta.model.FuncionarioElder;

@FacesConverter(value = "funcionarioConverter")
public class FuncionarioConverter implements Converter {
	FuncionarioDaoElder dao = new FuncionarioDaoElder();

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if (valor != null) {
			try {
				return dao.buscaId(Long.valueOf(valor));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {
		if (valor != null) {
			return String.valueOf(((FuncionarioElder) valor).getId());
		}
		return null;
	}

}
