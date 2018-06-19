package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.TipoSolicitacaoElder;
import br.edu.faculdadedelta.util.Conexao;

public class TipoSolicitacaoDaoElder {

	public void create(TipoSolicitacaoElder t) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO tipos_solicitacao (nome, descricao) VALUES (?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, t.getNome().trim());
		stmt.setString(2, t.getDescricao().trim());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(TipoSolicitacaoElder t) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE tipos_solicitacao SET nome=?, descricao=? WHERE id_tipo=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, t.getNome().trim());
		stmt.setString(2, t.getDescricao().trim());
		stmt.setLong(3, t.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(TipoSolicitacaoElder t) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM tipos_solicitacao WHERE id_tipo=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, t.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<TipoSolicitacaoElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_tipo, nome, descricao FROM tipos_solicitacao";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<TipoSolicitacaoElder> tipo_solicitacao = new ArrayList<>();

		while (rs.next()) {
			TipoSolicitacaoElder t = new TipoSolicitacaoElder();
			t.setId(rs.getLong("id_tipo"));
			t.setNome(rs.getString("nome").trim());
			t.setDescricao(rs.getString("descricao").trim());

			tipo_solicitacao.add(t);
		}

		Conexao.closeConnection(con, stmt, rs);
		return tipo_solicitacao;
	}

	public TipoSolicitacaoElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT id_tipo, nome, descricao FROM tipos_solicitacao WHERE id_tipo=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		TipoSolicitacaoElder busca = new TipoSolicitacaoElder();

		if (rs.next()) {
			busca.setId(rs.getLong("id_tipo"));
			busca.setNome(rs.getString("nome").trim());
			busca.setDescricao(rs.getString("descricao").trim());
		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}
}
