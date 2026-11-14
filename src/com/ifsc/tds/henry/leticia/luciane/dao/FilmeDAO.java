package com.ifsc.tds.henry.leticia.luciane.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.henry.leticia.luciane.entity.Filme;

public class FilmeDAO implements DAO<Filme> {

	public FilmeDAO() {
	}

	@Override
	public Object get(Long id) {
		Filme filme = null;
		String sql = "select * from filme where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		// Criar uma classe que guarde o retorno da opera��o
		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, id);
			rset = stm.executeQuery();

			while (rset.next()) {
				filme = new Filme();

				// atribui campo para atributo
				filme.setId(rset.getLong("id"));
				filme.setNome(rset.getString("nome"));

				// buscando as chaves estrangeiras

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
		return filme;
	}

	@Override
	public List<Filme> getAll() {
		List<Filme> filmes = new ArrayList<Filme>();

		String sql = "select * from filme";

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
				Filme filme = new Filme();

				// atribui campo para atributo
				filme.setId(rset.getLong("id"));
				filme.setNome(rset.getString("nome"));

				filmes.add(filme);

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
		return filmes;
	}

	@Override
	public int save(Filme filme) {
		String sql = "insert into filme (nome) values (?)";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, filme.getNome());

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
	public boolean update(Filme filme, String[] params) {
		String sql = "update filme set nome = ? where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, filme.getNome());
			stm.setLong(2, filme.getId());

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
	public boolean delete(Filme filme) {
		String sql = "delete from filme where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, filme.getId());
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
