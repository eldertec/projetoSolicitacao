package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.faculdadedelta.dao.FuncionarioDaoElder;
import br.edu.faculdadedelta.dao.UsuarioDao;
import br.edu.faculdadedelta.model.FuncionarioElder;
import br.edu.faculdadedelta.model.Usuario;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class FuncionarioControllerElder {

	private FuncionarioElder funcionario = new FuncionarioElder();
	private FuncionarioDaoElder dao = new FuncionarioDaoElder();
	private Usuario usuario = new Usuario();
	private UsuarioDao userDao = new UsuarioDao();
	private String email;
	private String cpf;
	private static final String ROLE = "funcionario";

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public FuncionarioElder getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioElder funcionario) {
		this.funcionario = funcionario;
	}

	public String limparCampos() {
		email = "";
		cpf = "";
		funcionario = new FuncionarioElder();
		return "dadosFuncionario.xhtml";
	}
	public String limparCadastro() {
		email = "";
		cpf = "";
		funcionario = new FuncionarioElder();
		return "cadFuncionario.xhtml";
	}

	public String salvar() {
		try {
			if (funcionario.getId() == null) {
				usuario.setLogin(email);
				usuario.setRole(ROLE);
				usuario.setCpf(cpf);
				userDao.create(usuario);
				funcionario.setEmail(email);
				funcionario.setCpf(cpf);
				dao.create(funcionario);
				limparCampos();
				FacesUtil.exibirMsg("Funcionario cadastrado");
				return "cadFuncionario.xhtml";
			} else {
				dao.update(funcionario);
				limparCampos();
				FacesUtil.exibirMsg("Cadastro Atualizado");
				return "dadosFuncionario.xhtml";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "";
	}

	
	
	public String alterar() {
		
		return "dadosFuncionario.xhtml";
	}
	
	public String atualizar() {
		try {
			if(funcionario.getId() != null) {
				dao.update(funcionario);
				limparCampos();
				FacesUtil.exibirMsg("Funcionario atualizado");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "dadosFuncionario.xhtml";
	}
	
	
	public String excluir() {
		try {
			dao.delete(funcionario);
			limparCampos();
			FacesUtil.exibirMsg("Funcionario removido");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "cadFuncionario.xhtml";
	}

	public List<FuncionarioElder> getListar() {
		List<FuncionarioElder> funcionarios = new ArrayList<>();
		try {
			funcionarios = dao.read();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}

		return funcionarios;
	}

	public List<FuncionarioElder> getLogado() {
		List<FuncionarioElder> retorno = new ArrayList<>();
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
