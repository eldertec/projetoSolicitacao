package br.edu.faculdadedelta.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.faculdadedelta.dao.StatusRetornoDaoElder;
import br.edu.faculdadedelta.model.StatusRetornoElder;

@FacesConverter(value = "statusRetornoConverter")
public class StatusRetornoConverter implements Converter {

	StatusRetornoDaoElder dao = new StatusRetornoDaoElder();

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
			return String.valueOf(((StatusRetornoElder) valor).getId());
		}
		return null;
	}

}
