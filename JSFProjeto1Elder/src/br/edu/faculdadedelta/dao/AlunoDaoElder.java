package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.AlunoElder;
import br.edu.faculdadedelta.util.Conexao;

public class AlunoDaoElder {

	public void create(AlunoElder a) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO alunos (nome,matricula,cpf,data_nascimento,email) VALUES (?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, a.getNome().trim());
		stmt.setString(2, a.getMatricula().trim());
		stmt.setString(3, a.getCpf().trim());
		stmt.setDate(4, new Date(a.getDataNascimento().getTime()));
		stmt.setString(5, a.getEmail().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(AlunoElder a) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE alunos SET nome=?,matricula=?,cpf=?,data_nascimento=?,email=? WHERE id_aluno = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, a.getNome().trim());
		stmt.setString(2, a.getMatricula().trim());
		stmt.setString(3, a.getCpf().trim());
		stmt.setDate(4, new Date(a.getDataNascimento().getTime()));
		stmt.setString(5, a.getEmail().trim());
		stmt.setLong(6, a.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(AlunoElder a) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM alunos WHERE id_aluno = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, a.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<AlunoElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_aluno,nome,matricula,cpf,data_nascimento,email FROM alunos";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<AlunoElder> alunos = new ArrayList<>();

		while (rs.next()) {
			AlunoElder a = new AlunoElder();

			a.setId(rs.getLong("id_aluno"));
			a.setNome(rs.getString("nome").trim());
			a.setMatricula(rs.getString("matricula").trim());
			a.setCpf(rs.getString("cpf").trim());
			a.setDataNascimento(rs.getDate("data_nascimento"));
			a.setEmail(rs.getString("email").trim());

			alunos.add(a);
		}

		Conexao.closeConnection(con, stmt, rs);
		return alunos;
	}

	public AlunoElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_aluno,nome,matricula,cpf,data_nascimento,email FROM alunos WHERE id_aluno=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		AlunoElder busca = new AlunoElder();

		if (rs.next()) {

			busca.setId(rs.getLong("id_aluno"));
			busca.setNome(rs.getString("nome").trim());
			busca.setMatricula(rs.getString("matricula").trim());
			busca.setCpf(rs.getString("cpf").trim());
			busca.setDataNascimento(rs.getDate("data_nascimento"));
			busca.setEmail(rs.getString("email").trim());

		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}

	public List<AlunoElder> buscaLogin(String login) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_aluno, nome, matricula, a.cpf, data_nascimento, email "
				+ "FROM alunos a, usuarios u WHERE a.cpf = u.cpf AND u.login =?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, login);

		ResultSet rs = stmt.executeQuery();
		List<AlunoElder> logado = new ArrayList<>();

		if (rs.next()) {
			AlunoElder busca = new AlunoElder();

			busca.setId(rs.getLong("id_aluno"));
			busca.setNome(rs.getString("nome").trim());
			busca.setMatricula(rs.getString("matricula").trim());
			busca.setCpf(rs.getString("cpf").trim());
			busca.setDataNascimento(rs.getDate("data_nascimento"));
			busca.setEmail(rs.getString("email").trim());
			logado.add(busca);
		}

		Conexao.closeConnection(con, stmt, rs);
		return logado;
	}
}
