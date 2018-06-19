package br.edu.faculdadedelta.controller;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.faculdadedelta.dao.UsuarioDao;
import br.edu.faculdadedelta.model.Usuario;
import br.edu.faculdadedelta.util.FacesUtil;

@ManagedBean
@SessionScoped
public class Login {

	private Usuario usuario = new Usuario();
	private Usuario temp = new Usuario();
	private UsuarioDao dao = new UsuarioDao();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getTemp() {
		return temp;
	}

	public void setTemp(Usuario temp) {
		this.temp = temp;
	}

	public void limparCampos() {
		usuario = new Usuario();
	}
	
	public String logar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			boolean existe = dao.existe(usuario.getLogin(), usuario.getSenha());
			if (existe) {
				temp = dao.retornar(usuario.getLogin(), usuario.getSenha());
				if (temp.getRole().equalsIgnoreCase("aluno")) {
					
					context.getExternalContext().getSessionMap().put("usuario", this.usuario);
					
					FacesUtil.exibirMsg("Bem Vindo(a)!");
					limparCampos();
					return "homeAluno.xhtml";
				}
				if(temp.getRole().equalsIgnoreCase("administrador")) {
					context.getExternalContext().getSessionMap().put("usuario", this.usuario);
					FacesUtil.exibirMsg("Bem Vindo(a)!");
					limparCampos();
					return "home.xhtml";
				}
				if(temp.getRole().equalsIgnoreCase("funcionario")) {
					context.getExternalContext().getSessionMap().put("usuario", this.usuario);
					FacesUtil.exibirMsg("Bem Vindo(a)!");
					limparCampos();
					return "homeFuncionario.xhtml";
				}
				
			}
			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesUtil.exibirMsg("Usuario não encontrado, cadastre-se.");
			limparCampos();
			return "index.xhtml";
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMsg("Erro ao realizar a operação: " + e.getMessage());
		}
		return "";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuario");
		limparCampos();
		return "index.xhtml";
	}
}
