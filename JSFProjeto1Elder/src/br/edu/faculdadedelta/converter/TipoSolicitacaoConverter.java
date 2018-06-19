package br.edu.faculdadedelta.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.faculdadedelta.dao.TipoSolicitacaoDaoElder;
import br.edu.faculdadedelta.model.TipoSolicitacaoElder;

@FacesConverter(value = "tipoSolicitacaoConverter")
public class TipoSolicitacaoConverter implements Converter {

	private TipoSolicitacaoDaoElder dao = new TipoSolicitacaoDaoElder();

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
			return String.valueOf(((TipoSolicitacaoElder) valor).getId());
		}
		return null;
	}

}
