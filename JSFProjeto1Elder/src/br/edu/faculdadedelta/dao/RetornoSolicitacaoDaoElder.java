package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.model.AlunoElder;
import br.edu.faculdadedelta.model.ComputadorElder;
import br.edu.faculdadedelta.model.FuncionarioElder;
import br.edu.faculdadedelta.model.LaboratorioElder;
import br.edu.faculdadedelta.model.RetornoSolicitacaoElder;
import br.edu.faculdadedelta.model.SolicitacaoElder;
import br.edu.faculdadedelta.model.StatusRetornoElder;
import br.edu.faculdadedelta.model.TipoSolicitacaoElder;
import br.edu.faculdadedelta.util.Conexao;

public class RetornoSolicitacaoDaoElder {

	public void create(RetornoSolicitacaoElder r) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO retorno_solicitacao (descricao ,data_retorno ,id_solicitacao ,id_status ,id_funcionario) VALUES (?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, r.getDescricao().trim());
		stmt.setDate(2, new Date(r.getDataRetorno().getTime()));
		stmt.setLong(3, r.getSolicitacao().getId());
		stmt.setLong(4, r.getStatus().getId());
		stmt.setLong(5, r.getFuncionario().getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(RetornoSolicitacaoElder r) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE retorno_solicitacao SET descricao=? ,data_retorno=? ,id_solicitacao=? ,id_status=? ,id_funcionario=? WHERE id_retorno=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, r.getDescricao().trim());
		stmt.setDate(2, new Date(r.getDataRetorno().getTime()));
		stmt.setLong(3, r.getSolicitacao().getId());
		stmt.setLong(4, r.getStatus().getId());
		stmt.setLong(5, r.getFuncionario().getId());
		stmt.setLong(6, r.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(RetornoSolicitacaoElder r) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM retorno_solicitacao WHERE id_retorno = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, r.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<RetornoSolicitacaoElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT r.id_retorno idRetorno, r.descricao descRetorno, r.data_retorno dataRetorno, "
				+ "sr.id_status idStatus, sr.descricao descStatus, f.id_funcionario idFunc, f.nome nomeFunc, "
				+ "f.cpf cpfFunc, f.data_nascimento dataFunc, f.email emailFunc, s.id_solicitacao idSol, "
				+ "s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, a.nome nomeAluno, "
				+ "a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, "
				+ "c.numero numComp, c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo "
				+ "FROM retorno_solicitacao r INNER JOIN status_retorno sr ON r.id_status = sr.id_status "
				+ "INNER JOIN funcionarios f ON r.id_funcionario = f.id_funcionario INNER JOIN solicitacoes s ON r.id_solicitacao = s.id_solicitacao "
				+ "INNER JOIN alunos a ON s.id_aluno = a.id_aluno INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<RetornoSolicitacaoElder> retorno = new ArrayList<>();

		while (rs.next()) {
			RetornoSolicitacaoElder r = new RetornoSolicitacaoElder();
			r.setId(rs.getLong("idRetorno"));
			r.setDescricao(rs.getString("descRetorno").trim());
			r.setDataRetorno(rs.getDate("dataRetorno"));

			StatusRetornoElder sr = new StatusRetornoElder();
			sr.setId(rs.getLong("idStatus"));
			sr.setDescricao(rs.getString("descStatus").trim());

			r.setStatus(sr);

			FuncionarioElder f = new FuncionarioElder();
			f.setId(rs.getLong("idFunc"));
			f.setNome(rs.getString("nomeFunc").trim());
			f.setCpf(rs.getString("cpfFunc").trim());
			f.setDataNascimento(rs.getDate("dataFunc"));
			f.setEmail(rs.getString("emailFunc").trim());

			r.setFuncionario(f);

			SolicitacaoElder s = new SolicitacaoElder();
			s.setId(rs.getLong("idSol"));
			s.setDescricao(rs.getString("descSol").trim());
			s.setDataSolicitacao(rs.getDate("dataSol"));

			AlunoElder a = new AlunoElder();
			a.setId(rs.getLong("idAluno"));
			a.setNome(rs.getString("nomeAluno").trim());
			a.setMatricula(rs.getString("matAluno").trim());
			a.setCpf(rs.getString("cpfAluno").trim());
			a.setDataNascimento(rs.getDate("dataAluno"));
			a.setEmail(rs.getString("emailAluno").trim());

			s.setAluno(a);

			LaboratorioElder l = new LaboratorioElder();
			l.setId(rs.getLong("idLab"));
			l.setNumero(rs.getInt("numLab"));
			l.setDescricao(rs.getString("descLab").trim());

			s.setLaboratorio(l);

			ComputadorElder c = new ComputadorElder();
			c.setId(rs.getLong("idComp"));
			c.setNumero(rs.getInt("numComp"));
			c.setDescricao(rs.getString("descComp").trim());

			s.setComputador(c);

			TipoSolicitacaoElder t = new TipoSolicitacaoElder();
			t.setId(rs.getLong("idTipo"));
			t.setNome(rs.getString("nomeTipo").trim());
			t.setDescricao(rs.getString("descTipo").trim());

			s.setTipo(t);

			r.setSolicitacao(s);

			retorno.add(r);

		}

		Conexao.closeConnection(con, stmt, rs);
		return retorno;
	}
	public List<RetornoSolicitacaoElder> getRetornado(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT r.id_retorno idRetorno, r.descricao descRetorno, r.data_retorno dataRetorno, "
				+ "sr.id_status idStatus, sr.descricao descStatus, f.id_funcionario idFunc, f.nome nomeFunc, "
				+ "f.cpf cpfFunc, f.data_nascimento dataFunc, f.email emailFunc, s.id_solicitacao idSol, "
				+ "s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, a.nome nomeAluno, "
				+ "a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, "
				+ "c.numero numComp, c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo "
				+ "FROM retorno_solicitacao r INNER JOIN status_retorno sr ON r.id_status = sr.id_status "
				+ "INNER JOIN funcionarios f ON r.id_funcionario = f.id_funcionario INNER JOIN solicitacoes s ON r.id_solicitacao = s.id_solicitacao "
				+ "INNER JOIN alunos a ON s.id_aluno = a.id_aluno INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo "
				+ "WHERE a.id_aluno =?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		List<RetornoSolicitacaoElder> retorno = new ArrayList<>();
		
		while (rs.next()) {
			RetornoSolicitacaoElder r = new RetornoSolicitacaoElder();
			r.setId(rs.getLong("idRetorno"));
			r.setDescricao(rs.getString("descRetorno").trim());
			r.setDataRetorno(rs.getDate("dataRetorno"));
			
			StatusRetornoElder sr = new StatusRetornoElder();
			sr.setId(rs.getLong("idStatus"));
			sr.setDescricao(rs.getString("descStatus").trim());
			
			r.setStatus(sr);
			
			FuncionarioElder f = new FuncionarioElder();
			f.setId(rs.getLong("idFunc"));
			f.setNome(rs.getString("nomeFunc").trim());
			f.setCpf(rs.getString("cpfFunc").trim());
			f.setDataNascimento(rs.getDate("dataFunc"));
			f.setEmail(rs.getString("emailFunc").trim());
			
			r.setFuncionario(f);
			
			SolicitacaoElder s = new SolicitacaoElder();
			s.setId(rs.getLong("idSol"));
			s.setDescricao(rs.getString("descSol").trim());
			s.setDataSolicitacao(rs.getDate("dataSol"));
			
			AlunoElder a = new AlunoElder();
			a.setId(rs.getLong("idAluno"));
			a.setNome(rs.getString("nomeAluno").trim());
			a.setMatricula(rs.getString("matAluno").trim());
			a.setCpf(rs.getString("cpfAluno").trim());
			a.setDataNascimento(rs.getDate("dataAluno"));
			a.setEmail(rs.getString("emailAluno").trim());
			
			s.setAluno(a);
			
			LaboratorioElder l = new LaboratorioElder();
			l.setId(rs.getLong("idLab"));
			l.setNumero(rs.getInt("numLab"));
			l.setDescricao(rs.getString("descLab").trim());
			
			s.setLaboratorio(l);
			
			ComputadorElder c = new ComputadorElder();
			c.setId(rs.getLong("idComp"));
			c.setNumero(rs.getInt("numComp"));
			c.setDescricao(rs.getString("descComp").trim());
			
			s.setComputador(c);
			
			TipoSolicitacaoElder t = new TipoSolicitacaoElder();
			t.setId(rs.getLong("idTipo"));
			t.setNome(rs.getString("nomeTipo").trim());
			t.setDescricao(rs.getString("descTipo").trim());
			
			s.setTipo(t);
			
			r.setSolicitacao(s);
			
			retorno.add(r);
			
		}
		
		Conexao.closeConnection(con, stmt, rs);
		return retorno;
	}
}
