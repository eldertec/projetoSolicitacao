package br.edu.faculdadedelta.model;

import java.util.Date;

public class SolicitacaoElder {

	private Long id;
	private String descricao;
	private Date dataSolicitacao;
	private AlunoElder aluno;
	private LaboratorioElder laboratorio;
	private ComputadorElder computador;
	private TipoSolicitacaoElder tipo;

	public SolicitacaoElder() {
		super();
	}

	public SolicitacaoElder(Long id, String descricao, Date dataSolicitacao, AlunoElder aluno,
			LaboratorioElder laboratorio, ComputadorElder computador, TipoSolicitacaoElder tipo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataSolicitacao = dataSolicitacao;
		this.aluno = aluno;
		this.laboratorio = laboratorio;
		this.computador = computador;
		this.tipo = tipo;
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

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public AlunoElder getAluno() {
		return aluno;
	}

	public void setAluno(AlunoElder aluno) {
		this.aluno = aluno;
	}

	public LaboratorioElder getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(LaboratorioElder laboratorio) {
		this.laboratorio = laboratorio;
	}

	public ComputadorElder getComputador() {
		return computador;
	}

	public void setComputador(ComputadorElder computador) {
		this.computador = computador;
	}

	public TipoSolicitacaoElder getTipo() {
		return tipo;
	}

	public void setTipo(TipoSolicitacaoElder tipo) {
		this.tipo = tipo;
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
		SolicitacaoElder other = (SolicitacaoElder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
