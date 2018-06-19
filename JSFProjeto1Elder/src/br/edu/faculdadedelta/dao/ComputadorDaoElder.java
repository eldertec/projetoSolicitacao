package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.ComputadorElder;
import br.edu.faculdadedelta.util.Conexao;

public class ComputadorDaoElder {

	public void create(ComputadorElder c) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO computadores (numero, descricao) VALUES (?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, c.getNumero());
		stmt.setString(2, c.getDescricao().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(ComputadorElder c) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE computadores SET numero=?, descricao=? WHERE id_computador=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, c.getNumero());
		stmt.setString(2, c.getDescricao().trim());
		stmt.setLong(3, c.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(ComputadorElder c) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM computadores WHERE id_computador=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, c.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<ComputadorElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_computador, numero, descricao FROM computadores";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<ComputadorElder> computadores = new ArrayList<>();

		while (rs.next()) {
			ComputadorElder c = new ComputadorElder();
			c.setId(rs.getLong("id_computador"));
			c.setNumero(rs.getInt("numero"));
			c.setDescricao(rs.getString("descricao").trim());

			computadores.add(c);
		}

		Conexao.closeConnection(con, stmt, rs);
		return computadores;
	}

	public ComputadorElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_computador, numero, descricao FROM computadores WHERE id_computador=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		ComputadorElder busca = new ComputadorElder();

		if (rs.next()) {
			busca.setId(rs.getLong("id_computador"));
			busca.setNumero(rs.getInt("numero"));
			busca.setDescricao(rs.getString("descricao").trim());
		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}
}
