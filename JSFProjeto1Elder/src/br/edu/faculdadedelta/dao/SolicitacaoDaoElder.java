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
import br.edu.faculdadedelta.model.LaboratorioElder;
import br.edu.faculdadedelta.model.SolicitacaoElder;
import br.edu.faculdadedelta.model.TipoSolicitacaoElder;
import br.edu.faculdadedelta.util.Conexao;

public class SolicitacaoDaoElder {

	public void create(SolicitacaoElder s) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "INSERT INTO solicitacoes (descricao,data_solicitacao,id_aluno,id_laboratorio,id_computador,id_tipo) VALUES (?,?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, s.getDescricao().trim());
		stmt.setDate(2, new Date(s.getDataSolicitacao().getTime()));
		stmt.setLong(3, s.getAluno().getId());
		stmt.setLong(4, s.getLaboratorio().getId());
		stmt.setLong(5, s.getComputador().getId());
		stmt.setLong(6, s.getTipo().getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void update(SolicitacaoElder s) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "UPDATE solicitacoes SET descricao=?,data_solicitacao=?,id_aluno=?,id_laboratorio=?,id_computador=?,id_tipo=? WHERE id_solicitacao = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, s.getDescricao().trim());
		stmt.setDate(2, new Date(s.getDataSolicitacao().getTime()));
		stmt.setLong(3, s.getAluno().getId());
		stmt.setLong(4, s.getLaboratorio().getId());
		stmt.setLong(5, s.getComputador().getId());
		stmt.setLong(6, s.getTipo().getId());
		stmt.setLong(7, s.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public void delete(SolicitacaoElder s) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "DELETE FROM solicitacoes WHERE id_solicitacao = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, s.getId());

		stmt.executeUpdate();
		Conexao.closeConnection(con, stmt, null);
	}

	public List<SolicitacaoElder> read() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, "
				+ "a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, c.numero numComp, "
				+ "c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo FROM solicitacoes s "
				+ "INNER JOIN alunos a ON s.id_aluno = a.id_aluno INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo";

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
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

			solicitacoes.add(s);
		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;
	}

	public List<SolicitacaoElder> naoAtendidas() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, "
				+ "a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, c.numero numComp, "
				+ "c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo FROM solicitacoes s "
				+ "INNER JOIN alunos a ON s.id_aluno = a.id_aluno INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo "
				+ "WHERE s.id_solicitacao NOT IN (SELECT id_solicitacao FROM retorno_solicitacao)";

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
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

			solicitacoes.add(s);
		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;
	}

	public SolicitacaoElder buscaId(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, "
				+ "a.id_aluno idAluno, a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, "
				+ "a.data_nascimento dataAluno, a.email emailAluno, l.id_laboratorio idLab, "
				+ "l.numero numLab, l.descricao descLab, c.id_computador idComp, c.numero numComp, "
				+ "c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo "
				+ "FROM solicitacoes s INNER JOIN alunos a ON s.id_aluno = a.id_aluno "
				+ "INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador "
				+ "INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo WHERE id_solicitacao=?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		SolicitacaoElder busca = new SolicitacaoElder();

		if (rs.next()) {

			busca.setId(rs.getLong("idSol"));
			busca.setDescricao(rs.getString("descSol").trim());
			busca.setDataSolicitacao(rs.getDate("dataSol"));

			AlunoElder a = new AlunoElder();
			a.setId(rs.getLong("idAluno"));
			a.setNome(rs.getString("nomeAluno").trim());
			a.setMatricula(rs.getString("matAluno").trim());
			a.setCpf(rs.getString("cpfAluno").trim());
			a.setDataNascimento(rs.getDate("dataAluno"));
			a.setEmail(rs.getString("emailAluno").trim());

			busca.setAluno(a);

			LaboratorioElder l = new LaboratorioElder();
			l.setId(rs.getLong("idLab"));
			l.setNumero(rs.getInt("numLab"));
			l.setDescricao(rs.getString("descLab").trim());

			busca.setLaboratorio(l);

			ComputadorElder c = new ComputadorElder();
			c.setId(rs.getLong("idComp"));
			c.setNumero(rs.getInt("numComp"));
			c.setDescricao(rs.getString("descComp").trim());

			busca.setComputador(c);

			TipoSolicitacaoElder t = new TipoSolicitacaoElder();
			t.setId(rs.getLong("idTipo"));
			t.setNome(rs.getString("nomeTipo").trim());
			t.setDescricao(rs.getString("descTipo").trim());

			busca.setTipo(t);

		}

		Conexao.closeConnection(con, stmt, rs);
		return busca;
	}

	public List<SolicitacaoElder> porLab(int numero) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, "
				+ "a.id_aluno idAluno, a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, "
				+ "a.data_nascimento dataAluno, a.email emailAluno, l.id_laboratorio idLab, l.numero numLab, "
				+ "l.descricao descLab, c.id_computador idComp, c.numero numComp, c.descricao descComp, "
				+ "t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo FROM solicitacoes s "
				+ "INNER JOIN alunos a ON s.id_aluno = a.id_aluno INNER JOIN laboratorios l "
				+ "ON s.id_laboratorio = l.id_laboratorio INNER JOIN computadores c "
				+ "ON s.id_computador = c.id_computador INNER JOIN tipos_solicitacao t "
				+ "ON s.id_tipo = t.id_tipo WHERE l.numero = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, numero);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
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

			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> porPc(int lab, int comp) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, "
				+ "a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, c.numero numComp, "
				+ "c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo FROM solicitacoes s "
				+ "INNER JOIN alunos a ON s.id_aluno = a.id_aluno INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador INNER JOIN tipos_solicitacao t "
				+ "ON s.id_tipo = t.id_tipo WHERE l.numero = ? AND c.numero = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, lab);
		stmt.setInt(2, comp);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
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

			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> porTipo(String tipo) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, "
				+ "a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, c.numero numComp, "
				+ "c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo "
				+ "FROM solicitacoes s INNER JOIN alunos a ON s.id_aluno = a.id_aluno "
				+ "INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador "
				+ "INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo WHERE t.nome LIKE ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, tipo);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
			SolicitacaoElder s = new SolicitacaoElder();
			s.setId(rs.getLong("idSol"));
			s.setDescricao(rs.getString("descSol"));
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

			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> porAluno(Long id) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, a.id_aluno idAluno, "
				+ "a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, a.data_nascimento dataAluno, a.email emailAluno, "
				+ "l.id_laboratorio idLab, l.numero numLab, l.descricao descLab, c.id_computador idComp, c.numero numComp, "
				+ "c.descricao descComp, t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo "
				+ "FROM solicitacoes s INNER JOIN alunos a ON s.id_aluno = a.id_aluno "
				+ "INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador "
				+ "INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo WHERE a.id_aluno= ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
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

			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> porData(java.util.Date inicio, java.util.Date fim) throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "SELECT s.id_solicitacao idSol, s.descricao descSol ,s.data_solicitacao dataSol, "
				+ "a.id_aluno idAluno, a.nome nomeAluno, a.matricula matAluno, a.cpf cpfAluno, "
				+ "a.data_nascimento dataAluno, a.email emailAluno, l.id_laboratorio idLab, l.numero numLab, "
				+ "l.descricao descLab, c.id_computador idComp, c.numero numComp, c.descricao descComp, "
				+ "t.id_tipo idTipo, t.nome nomeTipo, t.descricao descTipo "
				+ "FROM solicitacoes s INNER JOIN alunos a ON s.id_aluno = a.id_aluno "
				+ "INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador "
				+ "INNER JOIN tipos_solicitacao t ON s.id_tipo = t.id_tipo WHERE s.data_solicitacao BETWEEN ? AND ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setDate(1, new Date(inicio.getTime()));
		stmt.setDate(2, new Date(fim.getTime()));

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
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

			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> graficoLab() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "select s.data_solicitacao, count(l.numero) From solicitacoes s "
				+ "INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio " + "Group by s.data_solicitacao";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
			SolicitacaoElder s = new SolicitacaoElder();
			s.setDataSolicitacao(rs.getDate("data_solicitacao"));

			LaboratorioElder l = new LaboratorioElder();
			l.setNumero(rs.getInt("count"));
			s.setLaboratorio(l);
			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> graficoPc() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "select l.numero numLab, count(c.numero) From solicitacoes s "
				+ "INNER JOIN laboratorios l ON s.id_laboratorio = l.id_laboratorio "
				+ "INNER JOIN computadores c ON s.id_computador = c.id_computador " + "Group by l.numero";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
			SolicitacaoElder s = new SolicitacaoElder();

			LaboratorioElder l = new LaboratorioElder();
			l.setNumero(rs.getInt("numLab"));
			s.setLaboratorio(l);

			ComputadorElder c = new ComputadorElder();
			c.setNumero(rs.getInt("count"));

			s.setComputador(c);
			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}

	public List<SolicitacaoElder> graficoSolData() throws SQLException {
		Connection con = Conexao.getConnection();
		String sql = "select data_solicitacao, count(id_solicitacao) From solicitacoes " + "Group by data_solicitacao;";
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		List<SolicitacaoElder> solicitacoes = new ArrayList<>();

		while (rs.next()) {
			SolicitacaoElder s = new SolicitacaoElder();
			s.setId(rs.getLong("count"));
			s.setDataSolicitacao(rs.getDate("data_solicitacao"));
			solicitacoes.add(s);

		}

		Conexao.closeConnection(con, stmt, rs);
		return solicitacoes;

	}
}
