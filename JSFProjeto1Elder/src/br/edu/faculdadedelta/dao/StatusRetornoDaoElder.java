package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.StatusRetornoElder;
import br.edu.faculdadedelta.util.Conexao;

public class StatusRetornoDaoElder {

	public void create(StatusRetornoElder s) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO status_retorno (descricao) VALUES (?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, s.getDescricao().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(StatusRetornoElder s) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE status_retorno SET descricao=? WHERE id_status=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, s.getDescricao().trim());
		stmt.setLong(2, s.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(StatusRetornoElder s) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM status_retorno WHERE id_status=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, s.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<StatusRetornoElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_status, descricao FROM status_retorno";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<StatusRetornoElder> statusRetorno = new ArrayList<>();

		while (rs.next()) {
			StatusRetornoElder s = new StatusRetornoElder();
			s.setId(rs.getLong("id_status"));
			s.setDescricao(rs.getString("descricao").trim());

			statusRetorno.add(s);
		}

		Conexao.closeConnection(con, stmt, rs);
		return statusRetorno;
	}

	public StatusRetornoElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_status, descricao FROM status_retorno WHERE id_status=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		StatusRetornoElder busca = new StatusRetornoElder();

		if (rs.next()) {
			busca.setId(rs.getLong("id_status"));
			busca.setDescricao(rs.getString("descricao").trim());
		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}
}
