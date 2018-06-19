package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.edu.faculdadedelta.dao.SolicitacaoDaoElder;
import br.edu.faculdadedelta.model.AlunoElder;
import br.edu.faculdadedelta.model.ComputadorElder;
import br.edu.faculdadedelta.model.LaboratorioElder;
import br.edu.faculdadedelta.model.SolicitacaoElder;
import br.edu.faculdadedelta.model.TipoSolicitacaoElder;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class SolicitacaoControllerElder {

	private SolicitacaoElder solicitacao = new SolicitacaoElder();
	private SolicitacaoDaoElder dao = new SolicitacaoDaoElder();
	private ComputadorElder pcSelecionado = new ComputadorElder();
	private LaboratorioElder labSelecionado = new LaboratorioElder();
	private TipoSolicitacaoElder tipoSelecionado = new TipoSolicitacaoElder();
	private AlunoElder alunoSelecionado = new AlunoElder();
	private int laboratorio;
	private int computador;
	private String tipo;
	private Date inicio;
	private Date fim;

	private List<SolicitacaoElder> listaPesquisa = new ArrayList<>();

	public int getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(int laboratorio) {
		this.laboratorio = laboratorio;
	}

	public int getComputador() {
		return computador;
	}

	public void setComputador(int computador) {
		this.computador = computador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public List<SolicitacaoElder> getListaPesquisa() {
		return listaPesquisa;
	}

	public void setListaPesquisa(List<SolicitacaoElder> listaPesquisa) {
		this.listaPesquisa = listaPesquisa;
	}

	public AlunoElder getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(AlunoElder alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public SolicitacaoElder getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoElder solicitacao) {
		this.solicitacao = solicitacao;
	}

	public ComputadorElder getPcSelecionado() {
		return pcSelecionado;
	}

	public void setPcSelecionado(ComputadorElder pcSelecionado) {
		this.pcSelecionado = pcSelecionado;
	}

	public LaboratorioElder getLabSelecionado() {
		return labSelecionado;
	}

	public void setLabSelecionado(LaboratorioElder labSelecionado) {
		this.labSelecionado = labSelecionado;
	}

	public TipoSolicitacaoElder getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(TipoSolicitacaoElder tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public String limparCampos() {
		solicitacao = new SolicitacaoElder();
		alunoSelecionado = new AlunoElder();
		pcSelecionado = new ComputadorElder();
		labSelecionado = new LaboratorioElder();
		tipoSelecionado = new TipoSolicitacaoElder();
		return "cadSolicitacao.xhtml";
	}

	public String salvar() {
		try {
			if (solicitacao.getId() == null) {
				solicitacao.setComputador(pcSelecionado);
				solicitacao.setLaboratorio(labSelecionado);
				solicitacao.setTipo(tipoSelecionado);
				solicitacao.setAluno(alunoSelecionado);
				dao.create(solicitacao);
				limparCampos();
				FacesUtil.exibirMsg("Solicitação cadastrada");
			} else {
				solicitacao.setComputador(pcSelecionado);
				solicitacao.setLaboratorio(labSelecionado);
				solicitacao.setTipo(tipoSelecionado);
				solicitacao.setAluno(alunoSelecionado);
				dao.update(solicitacao);
				limparCampos();
				FacesUtil.exibirMsg("Solicitação atualizada");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadSolicitacao.xhtml";
	}

	public String alterar() {
		pcSelecionado = solicitacao.getComputador();
		labSelecionado = solicitacao.getLaboratorio();
		tipoSelecionado = solicitacao.getTipo();
		alunoSelecionado = solicitacao.getAluno();
		return "cadSolicitacao.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(solicitacao);
			limparCampos();
			FacesUtil.exibirMsg("Solicitação removida");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadSolicitacao.xhtml";
	}

	public List<SolicitacaoElder> getListar() {
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();
		try {
			solicitacoes = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return solicitacoes;
	}
	public List<SolicitacaoElder> getListarAluno() {
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();
		AlunoControllerElder aluno = new AlunoControllerElder();
		List<AlunoElder> logado = aluno.getLogado();
		try {
			for(AlunoElder a : logado) {
				solicitacoes = dao.porAluno(a.getId());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return solicitacoes;
	}
	public List<SolicitacaoElder> getListarNaoAtendidas() {
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();
		try {
			solicitacoes = dao.naoAtendidas();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return solicitacoes;
	}

	public void limpaLab() {
		laboratorio = 0;
	}

	public String buscarLab() {
		try {
			listaPesquisa = dao.porLab(laboratorio);
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		limpaLab();
		return "relatorios.xhtml";
	}
	public String buscarLabAdm() {
		try {
			listaPesquisa = dao.porLab(laboratorio);
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		limpaLab();
		return "relatoriosAdm.xhtml";
	}

	public void limpaPc() {
		computador = 0;
		laboratorio = 0;
	}

	public String buscarPc() {
		try {
			listaPesquisa = dao.porPc(laboratorio, computador);
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		limpaPc();
		return "relatorios.xhtml";
	}
	public String buscarPcAdm() {
		try {
			listaPesquisa = dao.porPc(laboratorio, computador);
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		limpaPc();
		return "relatoriosAdm.xhtml";
	}

	public void limpaData() {
		inicio = null;
		fim = null;
	}

	public String buscarData() {
		if (inicio.after(fim)) {
			FacesUtil.exibirMsg("A data inicial deve ser anterior a data final");
		} else {
			try {
				listaPesquisa = dao.porData(inicio, fim);
			} catch (SQLException e) {
				e.printStackTrace();
				FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
			}
		}
		limpaData();
		return "relatorios.xhtml";
	}
	public String buscarDataAdm() {
		if (inicio.after(fim)) {
			FacesUtil.exibirMsg("A data inicial deve ser anterior a data final");
		} else {
			try {
				listaPesquisa = dao.porData(inicio, fim);
			} catch (SQLException e) {
				e.printStackTrace();
				FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
			}
		}
		limpaData();
		return "relatoriosAdm.xhtml";
	}

	public void limpaTipo() {
		tipo = "";
	}

	public String buscarTipo() {
		try {
			listaPesquisa = dao.porTipo(tipo);
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		limpaTipo();
		return "relatorios.xhtml";
	}
	public String buscarTipoAdm() {
		try {
			listaPesquisa = dao.porTipo(tipo);
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		limpaTipo();
		return "relatoriosAdm.xhtml";
	}

	public List<SolicitacaoElder> getListarGrafLab() {
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();
		try {
			solicitacoes = dao.graficoLab();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return solicitacoes;
	}

	public List<SolicitacaoElder> getListarGrafPc() {
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();
		try {
			solicitacoes = dao.graficoPc();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return solicitacoes;
	}

	public List<SolicitacaoElder> getListarGrafSolData() {
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();
		try {
			solicitacoes = dao.graficoSolData();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return solicitacoes;
	}

	public BarChartModel getGraficoLaboratorio() {
		BarChartModel modelo = new BarChartModel();

		ChartSeries grafico = new ChartSeries();
		grafico.setLabel("Solicitações por Laboratório");

		List<SolicitacaoElder> solicitacoes = getListarGrafLab();

		for (SolicitacaoElder s : solicitacoes) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			grafico.set(sdf.format(s.getDataSolicitacao()), s.getLaboratorio().getNumero());
		}

		modelo.addSeries(grafico);
		return modelo;
	}

	public BarChartModel getGraficoComputador() {
		BarChartModel modelo = new BarChartModel();

		ChartSeries grafico = new ChartSeries();
		grafico.setLabel("Solicitações por Computador");

		List<SolicitacaoElder> solicitacoes = getListarGrafPc();

		for (SolicitacaoElder s : solicitacoes) {
			grafico.set(s.getLaboratorio().getNumero(), s.getComputador().getNumero());
		}

		modelo.addSeries(grafico);
		return modelo;
	}

	public BarChartModel getGraficoSolData() {
		BarChartModel modelo = new BarChartModel();

		ChartSeries grafico = new ChartSeries();
		grafico.setLabel("Média por Laboratório");

		List<SolicitacaoElder> solicitacoes = getListarGrafSolData();

		for (SolicitacaoElder s : solicitacoes) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			grafico.set(sdf.format(s.getDataSolicitacao()), s.getId());
		}

		modelo.addSeries(grafico);
		return modelo;
	}

	public BarChartModel getGraficoTeste() {
		BarChartModel modelo = new BarChartModel();

		ChartSeries graficoA = new ChartSeries();
		ChartSeries graficoB = new ChartSeries();
		graficoA.setLabel("Solicitações por Laboratório");
		graficoB.setLabel("Solicitações por Computador");

		List<SolicitacaoElder> porLab = getListarGrafLab();
		List<SolicitacaoElder> porPc = getListarGrafPc();

		for (SolicitacaoElder sa : porLab) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			graficoA.set(sdf.format(sa.getDataSolicitacao()), sa.getLaboratorio().getNumero());
			for (SolicitacaoElder sb : porPc) {
				graficoB.set(sdf.format(sa.getDataSolicitacao()), sb.getComputador().getNumero());
			}
		}

		modelo.addSeries(graficoA);
		modelo.addSeries(graficoB);
		return modelo;
	}

}
