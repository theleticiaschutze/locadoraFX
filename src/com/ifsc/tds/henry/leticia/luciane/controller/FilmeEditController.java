package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.ifsc.tds.henry.leticia.luciane.entity.Filme;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FilmeEditController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private GridPane pnlDados;

	@FXML
	private Label lblNome;

	@FXML
	private TextField txtNome;

	@FXML
	private Label lblAno;

	@FXML
	private DatePicker dtpAno;

	@FXML
	private Label lblCaixa;

	@FXML
	private Label lblColecao;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Button btnCancela;

	private Stage janelaFilmeEdit;

	private Filme filme;

	private boolean okClick = false;


	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaFilmeEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.filme.setNome(this.txtNome.getText());

			this.okClick = true;
			this.getJanelaFilmeEdit().close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public Stage getJanelaFilmeEdit() {
		return janelaFilmeEdit;
	}

	public void setJanelaFilmeEdit(Stage janelaFilmeEdit) {
		this.janelaFilmeEdit = janelaFilmeEdit;
	}

	public void populaTela(Filme filme) {
		this.filme = filme;

		this.txtNome.setText(this.filme.getNome());

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
			alerta.initOwner(this.janelaFilmeEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}

}
