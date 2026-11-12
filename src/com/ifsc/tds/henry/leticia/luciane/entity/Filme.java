package com.ifsc.tds.henry.leticia.luciane.entity;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Filme {

	private Long id;

	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
