package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.faculdadedelta.dao.AlunoDaoElder;
import br.edu.faculdadedelta.dao.UsuarioDao;
import br.edu.faculdadedelta.model.AlunoElder;
import br.edu.faculdadedelta.model.Usuario;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class AlunoControllerElder {

	private AlunoElder aluno = new AlunoElder();
	private AlunoDaoElder dao = new AlunoDaoElder();
	private Usuario usuario = new Usuario();
	private UsuarioDao userDao = new UsuarioDao();
	private String email;
	private String cpf;
	private static final String ROLE = "aluno";

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AlunoElder getAluno() {
		return aluno;
	}

	public void setAluno(AlunoElder aluno) {
		this.aluno = aluno;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String limparCampos() {
		email = "";
		cpf = "";
		aluno = new AlunoElder();
		return "cadAluno.xhtml";
	}

	public String salvar() {
		try {
			if (aluno.getId() == null) {
				usuario.setLogin(email);
				usuario.setRole(ROLE);
				usuario.setCpf(cpf);
				userDao.create(usuario);
				aluno.setEmail(email);
				aluno.setCpf(cpf);
				dao.create(aluno);
				limparCampos();
				FacesUtil.exibirMsg("Aluno cadastrado");
				return "index.xhtml";
			} else {
				dao.update(aluno);
				limparCampos();
				FacesUtil.exibirMsg("Aluno atualizado");
				return "cadAluno.xhtml";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "";
	}

	public String alterar() {

		return "cadAluno.xhtml";
	}

	public String excluir() {
		try {
			dao.delete(aluno);
			limparCampos();
			FacesUtil.exibirMsg("Aluno removido");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "index.xhtml";
	}

	public List<AlunoElder> getListar() {
		List<AlunoElder> alunos = new ArrayList<>();
		try {
			alunos = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return alunos;
	}

	public List<AlunoElder> getLogado() {
		List<AlunoElder> retorno = new ArrayList<>();
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
		try {
			retorno = dao.buscaLogin(usuarioLogado.getLogin());
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return retorno;
	}

}
