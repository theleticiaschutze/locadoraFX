package com.ifsc.tds.henry.leticia.luciane.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peregrinoti.entity.Amigo;
import com.peregrinoti.entity.TipoColecao;

public class ClienteDAO implements DAO<Cliente> {

	@Override
	public Object get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getAll() {
		List<Cliente> amigos = new ArrayList<Cliente>();

		String sql = "select * from amigo";

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
				Cliente amigo = new Cliente();

				// atribui campo para atributo
				amigo.setId(rset.getLong("id"));
				amigo.setNome(rset.getString("nome"));
				amigo.setEndereco(rset.getString("email"));
				amigo.setTelefone(rset.getString("telefone"));

				amigos.add(amigo);
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
		return amigos;
	}

	@Override
	public int save(Cliente amigo) {
		String sql = "insert into amigo(nome, nome_responsavel, telefone, endereco) ";
		sql += "values (?, ?, ?, ?)";

		// Recupera a conexão com o banco
		Connection conexao = null;

		// Criar uma preparação da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, amigo.getNome());
			stm.setString(2, amigo.getNomeResponsavel());
			stm.setString(3, amigo.getTelefone());
			stm.setString(4, amigo.getEndereco());

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
	public boolean update(Cliente amigo, String[] params) {
		String sql = "update amigo set nome = ?, nome_responsavel = ?, telefone = ?, endereco = ? where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, amigo.getNome());
			stm.setString(2, amigo.getNomeResponsavel());
			stm.setString(3, amigo.getTelefone());
			stm.setString(4, amigo.getEndereco());
			stm.setLong(5, amigo.getId());

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
	public boolean delete(Cliente amigo) {
		String sql = "delete from amigo where id = ?";

		// Recupera a conex�o com o banco
		Connection conexao = null;

		// Criar uma prepara��o da consulta
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, amigo.getId());
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
