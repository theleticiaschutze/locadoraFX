package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.peregrinoti.entity.Caixa;
import com.peregrinoti.entity.Revista;
import com.peregrinoti.entity.TipoColecao;

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
	private ComboBox<Caixa> cbxCaixa;

	@FXML
	private Label lblColecao;

	@FXML
	private ComboBox<TipoColecao> cbxColecao;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Button btnCancela;

	private Stage janelaRevistaEdit;

	private Revista revista;

	private boolean okClick = false;

	private CaixaListaController caixaListaController;
	private TipoColecaoListaController tipoColecaoListaController;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaRevistaEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.revista.setNome(this.txtNome.getText());
			this.revista.setAno(Date.valueOf(this.dtpAno.getValue()));
			this.revista.setCaixa(this.cbxCaixa.getSelectionModel().getSelectedItem());
			this.revista.setTipoColecao(this.cbxColecao.getSelectionModel().getSelectedItem());

			this.okClick = true;
			this.getJanelaRevistaEdit().close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.caixaListaController = new CaixaListaController();
		this.tipoColecaoListaController = new TipoColecaoListaController();

		this.carregarComboBoxCaixas();
		this.carregarComboBoxTipoColecoes();
	}

	public Stage getJanelaRevistaEdit() {
		return janelaRevistaEdit;
	}

	public void setJanelaRevistaEdit(Stage janelaRevistaEdit) {
		this.janelaRevistaEdit = janelaRevistaEdit;
	}

	public void populaTela(Revista revista) {
		this.revista = revista;

		this.txtNome.setText(this.revista.getNome());

		if (this.revista.getAno() != null) {
			this.dtpAno.setValue(this.revista.getAno().toLocalDate());
		}

		if (this.revista.getCaixa() != null) {
			this.cbxCaixa.setValue(this.revista.getCaixa());
		}

		if (this.revista.getTipoColecao() != null) {
			this.cbxColecao.setValue(this.revista.getTipoColecao());
		}
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
			alerta.initOwner(this.janelaRevistaEdit);
			alerta.setTitle("Dados inv�lidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informa��es:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}

	public void carregarComboBoxCaixas() {
		ObservableList<Caixa> observableListaCaixa = FXCollections
				.observableArrayList(this.caixaListaController.retornaListagemCaixa());

		this.cbxCaixa.setItems(observableListaCaixa);
	}

	public void carregarComboBoxTipoColecoes() {
		ObservableList<TipoColecao> observableListaTipoColecao = FXCollections
				.observableArrayList(this.tipoColecaoListaController.retornaListagemTipoColecao());

		this.cbxColecao.setItems(observableListaTipoColecao);
	}
}
