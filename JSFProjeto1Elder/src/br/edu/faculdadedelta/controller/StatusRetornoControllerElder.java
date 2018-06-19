package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.dao.StatusRetornoDaoElder;
import br.edu.faculdadedelta.model.StatusRetornoElder;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class StatusRetornoControllerElder {

	private StatusRetornoElder status = new StatusRetornoElder();
	private StatusRetornoDaoElder dao = new StatusRetornoDaoElder();

	public StatusRetornoElder getStatus() {
		return status;
	}

	public void setStatus(StatusRetornoElder status) {
		this.status = status;
	}

	public String limparCampos() {
		status = new StatusRetornoElder();
		return "cadStatus.xhtml";
	}

	public String salvar() {
		try {
			if (status.getId() == null) {
				dao.create(status);
				limparCampos();
				FacesUtil.exibirMsg("Status cadastrado");
			} else {
				dao.update(status);
				limparCampos();
				FacesUtil.exibirMsg("Status atualizado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadStatus.xhtml";
	}

	public String alterar() {

		return "cadStatus.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(status);
			limparCampos();
			FacesUtil.exibirMsg("Status removido");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadStatus.xhtml";
	}

	public List<StatusRetornoElder> getListar() {
		List<StatusRetornoElder> status = new ArrayList<>();
		try {
			status = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return status;
	}
}
