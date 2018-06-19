package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.LaboratorioElder;
import br.edu.faculdadedelta.util.Conexao;

public class LaboratorioDaoElder {

	public void create(LaboratorioElder l) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO laboratorios (numero, descricao) VALUES (?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, l.getNumero());
		stmt.setString(2, l.getDescricao().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(LaboratorioElder l) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE laboratorios SET numero=?, descricao=? WHERE id_laboratorio=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, l.getNumero());
		stmt.setString(2, l.getDescricao().trim());
		stmt.setLong(3, l.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(LaboratorioElder l) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM laboratorios WHERE id_laboratorio=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, l.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<LaboratorioElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_laboratorio, numero, descricao FROM laboratorios";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<LaboratorioElder> laboratorios = new ArrayList<>();

		while (rs.next()) {
			LaboratorioElder l = new LaboratorioElder();
			l.setId(rs.getLong("id_laboratorio"));
			l.setNumero(rs.getInt("numero"));
			l.setDescricao(rs.getString("descricao").trim());

			laboratorios.add(l);
		}

		Conexao.closeConnection(con, stmt, rs);
		return laboratorios;
	}

	public LaboratorioElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_laboratorio, numero, descricao FROM laboratorios WHERE id_laboratorio=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		LaboratorioElder busca = new LaboratorioElder();

		if (rs.next()) {
			busca.setId(rs.getLong("id_laboratorio"));
			busca.setNumero(rs.getInt("numero"));
			busca.setDescricao(rs.getString("descricao").trim());
		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}
}
