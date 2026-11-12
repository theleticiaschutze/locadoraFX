package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.peregrinoti.entity.Cliente;
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

public class ClienteEditController {

	@FXML
	private Button btnCancela;

	@FXML
	private Button btnOK;

	@FXML
	private Label lblEmail;

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
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;


	private Stage janelaClienteEdit;

	private Cliente cliente;

	private boolean okClick = false;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaClienteEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.cliente.setNome(this.txtNome.getText());
			this.cliente.setNomeResponsavel(this.txtNomeResponsavel.getText());
			this.cliente.setTelefone(this.txtTelefone.getText());
			this.cliente.setEndereco(this.txtEndereco.getText());

			this.okClick = true;
			this.getJanelaClienteEdit().close();
		}
	}

	public void setJanelaClienteEdit(Stage janelaClienteEdit) {
		this.janelaClienteEdit = janelaClienteEdit;
	}

	public Stage getJanelaClienteEdit() {
		return janelaClienteEdit;
	}

	public void populaTela(Cliente cliente) {
		this.cliente = cliente;

		this.txtNome.setText(cliente.getNome());		
		this.txtTelefone.setText(cliente.getTelefone());
		this.txtEndereco.setText(cliente.getEndereco());
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
			alerta.initOwner(this.janelaClienteEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}

}
