package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.FuncionarioElder;
import br.edu.faculdadedelta.util.Conexao;

public class FuncionarioDaoElder {

	public void create(FuncionarioElder f) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO funcionarios (nome,cpf,data_nascimento,email) VALUES (?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, f.getNome().trim());
		stmt.setString(2, f.getCpf().trim());
		stmt.setDate(3, new Date(f.getDataNascimento().getTime()));
		stmt.setString(4, f.getEmail().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(FuncionarioElder f) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE funcionarios SET nome=?,cpf=?,data_nascimento=?,email=? WHERE id_funcionario = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, f.getNome().trim());
		stmt.setString(2, f.getCpf().trim());
		stmt.setDate(3, new Date(f.getDataNascimento().getTime()));
		stmt.setString(4, f.getEmail().trim());
		stmt.setLong(5, f.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(FuncionarioElder f) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM funcionarios WHERE id_funcionario = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, f.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<FuncionarioElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_funcionario,nome,cpf,data_nascimento,email FROM funcionarios";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<FuncionarioElder> funcionarios = new ArrayList<>();

		while (rs.next()) {
			FuncionarioElder f = new FuncionarioElder();

			f.setId(rs.getLong("id_funcionario"));
			f.setNome(rs.getString("nome").trim());
			f.setCpf(rs.getString("cpf").trim());
			f.setDataNascimento(rs.getDate("data_nascimento"));
			f.setEmail(rs.getString("email").trim());

			funcionarios.add(f);
		}

		Conexao.closeConnection(con, stmt, rs);
		return funcionarios;
	}

	public FuncionarioElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_funcionario,nome,cpf,data_nascimento,email FROM funcionarios WHERE id_funcionario=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		FuncionarioElder busca = new FuncionarioElder();

		if (rs.next()) {

			busca.setId(rs.getLong("id_funcionario"));
			busca.setNome(rs.getString("nome").trim());
			busca.setCpf(rs.getString("cpf").trim());
			busca.setDataNascimento(rs.getDate("data_nascimento"));
			busca.setEmail(rs.getString("email").trim());

		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}

	public List<FuncionarioElder> buscaLogin(String login) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_funcionario,nome,f.cpf,data_nascimento,email "
				+ "FROM funcionarios f, usuarios u WHERE f.cpf = u.cpf AND u.login =?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, login);

		ResultSet rs = stmt.executeQuery();
		List<FuncionarioElder> logado = new ArrayList<>();

		if (rs.next()) {
			FuncionarioElder busca = new FuncionarioElder();
			busca.setId(rs.getLong("id_funcionario"));
			busca.setNome(rs.getString("nome").trim());
			busca.setCpf(rs.getString("cpf").trim());
			busca.setDataNascimento(rs.getDate("data_nascimento"));
			busca.setEmail(rs.getString("email").trim());
			logado.add(busca);

		}

		Conexao.closeConnection(con, stmt, rs);
		return logado;
	}
}
