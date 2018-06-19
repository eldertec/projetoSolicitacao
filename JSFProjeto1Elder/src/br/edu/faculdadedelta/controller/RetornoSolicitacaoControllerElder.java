package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.dao.RetornoSolicitacaoDaoElder;
import br.edu.faculdadedelta.model.AlunoElder;
import br.edu.faculdadedelta.model.FuncionarioElder;
import br.edu.faculdadedelta.model.RetornoSolicitacaoElder;
import br.edu.faculdadedelta.model.SolicitacaoElder;
import br.edu.faculdadedelta.model.StatusRetornoElder;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class RetornoSolicitacaoControllerElder {

	private RetornoSolicitacaoElder retorno = new RetornoSolicitacaoElder();
	private RetornoSolicitacaoDaoElder dao = new RetornoSolicitacaoDaoElder();
	private FuncionarioElder funcSelecionado = new FuncionarioElder();
	private StatusRetornoElder statusSelecionado = new StatusRetornoElder();
	private SolicitacaoElder solSelecionada = new SolicitacaoElder();

	public RetornoSolicitacaoElder getRetorno() {
		return retorno;
	}

	public void setRetorno(RetornoSolicitacaoElder retorno) {
		this.retorno = retorno;
	}

	public FuncionarioElder getFuncSelecionado() {
		return funcSelecionado;
	}

	public void setFuncSelecionado(FuncionarioElder funcSelecionado) {
		this.funcSelecionado = funcSelecionado;
	}

	public StatusRetornoElder getStatusSelecionado() {
		return statusSelecionado;
	}

	public void setStatusSelecionado(StatusRetornoElder statusSelecionado) {
		this.statusSelecionado = statusSelecionado;
	}

	public SolicitacaoElder getSolSelecionada() {
		return solSelecionada;
	}

	public void setSolSelecionada(SolicitacaoElder solSelecionada) {
		this.solSelecionada = solSelecionada;
	}

	public String limparCampos() {
		retorno = new RetornoSolicitacaoElder();
		statusSelecionado = new StatusRetornoElder();
		solSelecionada = new SolicitacaoElder();
		funcSelecionado = new FuncionarioElder();
		return "cadRetorno.xhtml";
	}

	public String salvar() {
		try {
			if(retorno.getDataRetorno().before(solSelecionada.getDataSolicitacao())) {
				FacesUtil.exibirMsg("A data de retorno deve ser posterior a data da solicitação");
			}else {
			if (retorno.getId() == null) {
				retorno.setFuncionario(funcSelecionado);
				retorno.setSolicitacao(solSelecionada);
				retorno.setStatus(statusSelecionado);
				dao.create(retorno);
				limparCampos();
				FacesUtil.exibirMsg("Retorno cadastrado");
			} else {
				retorno.setFuncionario(funcSelecionado);
				retorno.setSolicitacao(solSelecionada);
				retorno.setStatus(statusSelecionado);
				dao.update(retorno);
				limparCampos();
				FacesUtil.exibirMsg("Retorno atualizado");
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadRetorno.xhtml";
	}

	public String alterar() {
		funcSelecionado = retorno.getFuncionario();
		statusSelecionado = retorno.getStatus();
		solSelecionada = retorno.getSolicitacao();
		return "cadRetorno.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(retorno);
			limparCampos();
			FacesUtil.exibirMsg("Retorno removido");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadRetorno.xhtml";
	}

	public List<RetornoSolicitacaoElder> getListar() {
		List<RetornoSolicitacaoElder> retornos = new ArrayList<>();
		try {
			retornos = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return retornos;
	}
	public List<RetornoSolicitacaoElder> getRetornado() {
		List<RetornoSolicitacaoElder> retornos = new ArrayList<>();
		AlunoControllerElder aluno = new AlunoControllerElder();
		List<AlunoElder> logado = aluno.getLogado();
		try {
			for(AlunoElder a : logado) {
				retornos = dao.getRetornado(a.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		
		return retornos;
	}
}
