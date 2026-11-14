package com.ifsc.tds.henry.leticia.luciane.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.henry.leticia.luciane.entity.Emprestimo;
import com.ifsc.tds.henry.leticia.luciane.entity.Cliente;
import com.ifsc.tds.henry.leticia.luciane.entity.Filme;



public class EmprestimoDAO implements DAO<Emprestimo> {

	private ClienteDAO clienteDAO;

	private FilmeDAO filmeDAO;

	public EmprestimoDAO() {
		this.clienteDAO = new ClienteDAO();
		this.filmeDAO = new FilmeDAO();
	}

	@Override
	public Object get(Long id) {
		Emprestimo emprestimo = null;
		String sql = "select * from emprestimo where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		// Criar uma classe que guarde o retorno da opera��o
		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				emprestimo = new Emprestimo();

				// atribui campo para atributo
				emprestimo.setId(rset.getLong("id"));
				emprestimo.setObservacao(rset.getString("observacao"));
				emprestimo.setDataEmprestimo(rset.getDate("data_emprestimo"));
				emprestimo.setDataDevolucao(rset.getDate("data_devolucao"));

				// buscando as chaves estrangeiras
				emprestimo.setCliente((Cliente) this.clienteDAO.get(rset.getLong("cliente_id")));
				emprestimo.setFilme((Filme) this.filmeDAO.get(rset.getLong("filme_id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprestimo;
	}

	@Override
	public List<Emprestimo> getAll() {
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		String sql = "select * from emprestimo";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		// Criar uma classe que guarde o retorno da opera��o
		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Emprestimo emprestimo = new Emprestimo();

				// atribui campo para atributo
				emprestimo.setId(rset.getLong("id"));
				emprestimo.setObservacao(rset.getString("observacao"));
				emprestimo.setDataEmprestimo(rset.getDate("data_emprestimo"));
				emprestimo.setDataDevolucao(rset.getDate("data_devolucao"));

				// buscando as chaves estrangeiras
				emprestimo.setCliente((Cliente) this.clienteDAO.get(rset.getLong("cliente_id")));
				emprestimo.setFilme((Filme) this.filmeDAO.get(rset.getLong("filme_id")));

				emprestimos.add(emprestimo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprestimos;
	}

	@Override
	public int save(Emprestimo emprestimo) {
		String sql = "insert into emprestimo (observacao, data_emprestimo, data_devolucao, cliente_id, filme_id)" + " values (?, ?, ?, ?, ?)";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, emprestimo.getObservacao());
			stm.setDate(2, emprestimo.getDataEmprestimo());
			stm.setDate(3, emprestimo.getDataDevolucao());
			stm.setLong(4, emprestimo.getCliente().getId());
			stm.setLong(5, emprestimo.getFilme().getId());

			stm.execute();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean update(Emprestimo emprestimo, String[] params) {
		String sql = "update emprestimo set observacao = ?, data_emprestimo = ?, data_devolucao = ?, cliente_id = ?, filme_id = ? where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, emprestimo.getObservacao());
			stm.setDate(2, emprestimo.getDataEmprestimo());
			stm.setDate(3, emprestimo.getDataDevolucao());
			stm.setLong(4, emprestimo.getCliente().getId());
			stm.setLong(5, emprestimo.getFilme().getId());
			stm.setLong(6, emprestimo.getId());

			stm.execute();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean delete(Emprestimo emprestimo) {
		String sql = "delete from emprestimo where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, emprestimo.getId());
			stm.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
