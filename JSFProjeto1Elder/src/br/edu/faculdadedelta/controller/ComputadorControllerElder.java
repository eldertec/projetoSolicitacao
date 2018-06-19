package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.dao.ComputadorDaoElder;
import br.edu.faculdadedelta.model.ComputadorElder;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class ComputadorControllerElder {

	private ComputadorElder computador = new ComputadorElder();
	private ComputadorDaoElder dao = new ComputadorDaoElder();

	public ComputadorElder getComputador() {
		return computador;
	}

	public void setComputador(ComputadorElder computador) {
		this.computador = computador;
	}

	public String limparCampos() {
		computador = new ComputadorElder();
		return "cadPc.xhtml";
	}

	public String salvar() {
		try {
			if (computador.getId() == null) {
				dao.create(computador);
				limparCampos();
				FacesUtil.exibirMsg("Computador cadastrado");
			} else {
				dao.update(computador);
				limparCampos();
				FacesUtil.exibirMsg("Computador atualizado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadPc.xhtml";
	}

	public String alterar() {

		return "cadPc.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(computador);
			limparCampos();
			FacesUtil.exibirMsg("Computador removido");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadPc.xhtml";
	}

	public List<ComputadorElder> getListar() {
		List<ComputadorElder> computadores = new ArrayList<>();
		try {
			computadores = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return computadores;
	}
}
