package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.dao.TipoSolicitacaoDaoElder;
import br.edu.faculdadedelta.model.TipoSolicitacaoElder;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class TipoSolicitacaoControllerElder {

	private TipoSolicitacaoElder tipo = new TipoSolicitacaoElder();
	private TipoSolicitacaoDaoElder dao = new TipoSolicitacaoDaoElder();

	public TipoSolicitacaoElder getTipo() {
		return tipo;
	}

	public void setTipo(TipoSolicitacaoElder tipo) {
		this.tipo = tipo;
	}

	public String limparCampos() {
		tipo = new TipoSolicitacaoElder();
		return "cadTipo.xhtml";
	}

	public String salvar() {
		try {
			if (tipo.getId() == null) {
				dao.create(tipo);
				limparCampos();
				FacesUtil.exibirMsg("Tipo de Solicitação cadastrada");
			} else {
				dao.update(tipo);
				limparCampos();
				FacesUtil.exibirMsg("Tipo de Solicitação atualizada");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadTipo.xhtml";
	}

	public String alterar() {

		return "cadTipo.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(tipo);
			limparCampos();
			FacesUtil.exibirMsg("Tipo de Solicitação removida");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadTipo.xhtml";
	}

	public List<TipoSolicitacaoElder> getListar() {
		List<TipoSolicitacaoElder> tipos = new ArrayList<>();
		try {
			tipos = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return tipos;
	}
}
