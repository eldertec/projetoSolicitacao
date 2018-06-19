package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.Usuario;
import br.edu.faculdadedelta.util.Conexao;

public class UsuarioDao {

	public void create(Usuario u) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO usuarios (login, senha,role,cpf) VALUES (?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, u.getLogin().trim());
		stmt.setString(2, u.getSenha().trim());
		stmt.setString(3, u.getRole().trim());
		stmt.setString(4, u.getCpf().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(Usuario u) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE usuarios SET login=?, senha=?, role=?, cpf=? WHERE id_usuario=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, u.getLogin().trim());
		stmt.setString(2, u.getSenha().trim());
		stmt.setString(3, u.getRole().trim());
		stmt.setString(4, u.getCpf().trim());
		stmt.setLong(5, u.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(Usuario u) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM usuarios WHERE id_usuario=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, u.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<Usuario> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_usuario, login, senha, role, cpf FROM usuarios";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<Usuario> usuarios = new ArrayList<>();

		while (rs.next()) {
			Usuario u = new Usuario();
			u.setId(rs.getLong("id_usuario"));
			u.setLogin(rs.getString("login").trim());
			u.setSenha(rs.getString("senha").trim());
			u.setRole(rs.getString("role").trim());
			u.setCpf(rs.getString("cpf").trim());
			usuarios.add(u);
		}

		Conexao.closeConnection(con, stmt, rs);
		return usuarios;
	}

	public boolean existe(String login, String senha) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_usuario, login, senha, role, cpf FROM usuarios WHERE login=? AND senha=? ";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, login);
		stmt.setString(2, senha);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public Usuario retornar(String login, String senha) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_usuario, login, senha, role, cpf FROM usuarios WHERE login=? AND senha=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, login);
		stmt.setString(2, senha);

		ResultSet rs = stmt.executeQuery();
		Usuario busca = new Usuario();

		while (rs.next()) {

			busca.setId(rs.getLong("id_usuario"));
			busca.setLogin(rs.getString("login"));
			busca.setSenha(rs.getString("senha"));
			busca.setRole(rs.getString("role"));
			busca.setCpf(rs.getString("cpf"));

		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}
}
