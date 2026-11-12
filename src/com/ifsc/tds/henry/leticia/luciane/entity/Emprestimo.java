package com.ifsc.tds.henry.leticia.luciane.entity;

import java.sql.Date;

public class Emprestimo {

	private Long id;

	private Date dataEmprestimo;

	private Date dataDevolucao;

	private Amigo amigo;

	private Filme revista;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Amigo getAmigo() {
		return amigo;
	}

	public void setAmigo(Amigo amigo) {
		this.amigo = amigo;
	}

	public Filme getRevista() {
		return revista;
	}

	public void setRevista(Filme revista) {
		this.revista = revista;
	}

}
