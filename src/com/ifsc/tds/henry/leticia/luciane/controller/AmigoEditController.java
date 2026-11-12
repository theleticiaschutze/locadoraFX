package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.peregrinoti.entity.Amigo;
import com.peregrinoti.entity.TipoColecao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AmigoEditController {

	@FXML
	private Button btnCancela;

	@FXML
	private Button btnOK;

	@FXML
	private Label lblEndereco;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblTelefone;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private GridPane pnlDados;

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private TextField txtEndereco;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtNomeResponsavel;

	@FXML
	private TextField txtTelefone;

	private Stage janelaAmigoEdit;

	private Amigo amigo;

	private boolean okClick = false;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaAmigoEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.amigo.setNome(this.txtNome.getText());
			this.amigo.setNomeResponsavel(this.txtNomeResponsavel.getText());
			this.amigo.setTelefone(this.txtTelefone.getText());
			this.amigo.setEndereco(this.txtEndereco.getText());

			this.okClick = true;
			this.getJanelaAmigoEdit().close();
		}
	}

	public void setJanelaAmigoEdit(Stage janelaAmigoEdit) {
		this.janelaAmigoEdit = janelaAmigoEdit;
	}

	public Stage getJanelaAmigoEdit() {
		return janelaAmigoEdit;
	}

	public void populaTela(Amigo amigo) {
		this.amigo = amigo;

		this.txtNome.setText(amigo.getNome());
		this.txtNomeResponsavel.setText(amigo.getNomeResponsavel());
		this.txtTelefone.setText(amigo.getTelefone());
		this.txtEndereco.setText(amigo.getEndereco());
	}

	public boolean isOkClick() {
		return okClick;
	}
	
	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.txtNome.getText() == null || this.txtNome.getText().trim().length() == 0) {
			mensagemErros += "Informe o nome!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaAmigoEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
	
}
