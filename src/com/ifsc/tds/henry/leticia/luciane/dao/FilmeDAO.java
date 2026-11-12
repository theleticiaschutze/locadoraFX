package com.ifsc.tds.henry.leticia.luciane.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peregrinoti.entity.Revista;

public class FilmeDAO implements DAO<Revista> {

	private CaixaDAO caixaDAO;

	private TipoColecaoDAO tipoColecaoDAO;

	public FilmeDAO() {
		this.caixaDAO = new CaixaDAO();
		this.tipoColecaoDAO = new TipoColecaoDAO();
	}

	@Override
	public Object get(Long id) {
		Revista revista = null;
		String sql = "select * from revista where id = ?";

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
				revista = new Revista();

				// atribui campo para atributo
				revista.setId(rset.getLong("id"));
				revista.setNome(rset.getString("nome"));
				revista.setAno(rset.getDate("ano"));

				// buscando as chaves estrangeiras
				revista.setCaixa(this.caixaDAO.get(rset.getLong("caixa_id")));
				revista.setTipoColecao(this.tipoColecaoDAO.get(rset.getLong("tipo_colecao_id")));
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
		return revista;
	}

	@Override
	public List<Revista> getAll() {
		List<Revista> revistas = new ArrayList<Revista>();

		String sql = "select * from revista";

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
				Revista revista = new Revista();

				// atribui campo para atributo
				revista.setId(rset.getLong("id"));
				revista.setNome(rset.getString("nome"));
				revista.setAno(rset.getDate("ano"));

				// buscando as chaves estrangeiras
				revista.setCaixa(this.caixaDAO.get(rset.getLong("caixa_id")));
				revista.setTipoColecao(this.tipoColecaoDAO.get(rset.getLong("tipo_colecao_id")));

				revistas.add(revista);
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
		return revistas;
	}

	@Override
	public int save(Revista revista) {
		String sql = "insert into revista (nome, ano, caixa_id, tipo_colecao_id)" + " values (?, ?, ?, ?)";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, revista.getNome());
			stm.setDate(2, revista.getAno());
			stm.setLong(3, revista.getCaixa().getId());
			stm.setLong(4, revista.getTipoColecao().getId());

			stm.execute();

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
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean update(Revista revista, String[] params) {
		String sql = "update revista set nome = ?, ano = ?, caixa_id = ?, tipo_colecao_id = ? where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, revista.getNome());
			stm.setDate(2, revista.getAno());
			stm.setLong(3, revista.getCaixa().getId());
			stm.setLong(4, revista.getTipoColecao().getId());
			stm.setLong(5, revista.getTipoColecao().getId());

			stm.execute();

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
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Revista revista) {
		String sql = "delete from revista where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, revista.getId());
			stm.execute();
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
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
