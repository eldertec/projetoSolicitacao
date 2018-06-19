package br.edu.faculdadedelta.model;

import java.util.Date;

public class RetornoSolicitacaoElder {

	private Long id;
	private String descricao;
	private Date dataRetorno;
	private FuncionarioElder funcionario;
	private SolicitacaoElder solicitacao;
	private StatusRetornoElder status;

	public RetornoSolicitacaoElder() {
		super();
	}

	public RetornoSolicitacaoElder(Long id, String descricao, Date dataRetorno, FuncionarioElder funcionario,
			SolicitacaoElder solicitacao, StatusRetornoElder status) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataRetorno = dataRetorno;
		this.funcionario = funcionario;
		this.solicitacao = solicitacao;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public FuncionarioElder getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioElder funcionario) {
		this.funcionario = funcionario;
	}

	public SolicitacaoElder getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoElder solicitacao) {
		this.solicitacao = solicitacao;
	}

	public StatusRetornoElder getStatus() {
		return status;
	}

	public void setStatus(StatusRetornoElder status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetornoSolicitacaoElder other = (RetornoSolicitacaoElder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
