package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.dao.LaboratorioDaoElder;
import br.edu.faculdadedelta.model.LaboratorioElder;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class LaboratorioControllerElder {

	private LaboratorioElder laboratorio = new LaboratorioElder();
	private LaboratorioDaoElder dao = new LaboratorioDaoElder();

	public LaboratorioElder getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(LaboratorioElder laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String limparCampos() {
		laboratorio = new LaboratorioElder();
		return "cadLab.xhtml";
	}

	public String salvar() {
		try {
			if (laboratorio.getId() == null) {
				dao.create(laboratorio);
				limparCampos();
				FacesUtil.exibirMsg("Laboratorio cadastrado");
			} else {
				dao.update(laboratorio);
				limparCampos();
				FacesUtil.exibirMsg("Laboratorio atualizado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadLab.xhtml";
	}

	public String alterar() {

		return "cadLab.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(laboratorio);
			limparCampos();
			FacesUtil.exibirMsg("Laboratorio removido");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadLab.xhtml";
	}

	public List<LaboratorioElder> getListar() {
		List<LaboratorioElder> laboratorios = new ArrayList<>();
		try {
			laboratorios = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return laboratorios;
	}
}
