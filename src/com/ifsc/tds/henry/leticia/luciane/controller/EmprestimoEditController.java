package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.ifsc.tds.henry.leticia.luciane.entity.Cliente;
import com.ifsc.tds.henry.leticia.luciane.entity.Emprestimo;
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

public class EmprestimoEditController implements Initializable {

	@FXML
	private Button btnCancela;

	@FXML
	private Button btnOK;

	@FXML
	private ComboBox<Cliente> cbxCliente;
	@FXML
	private ComboBox<Filme> cbxFilme;

	@FXML
	private DatePicker dtpDataDevolucao;

	@FXML
	private DatePicker dtpDataEmprestimo;

	@FXML
	private Label lblCliente;

	@FXML
	private Label lblDataDevolucao;

	@FXML
	private Label lblDataEmprestimo;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblObservacao;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private GridPane pnlDados;

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private TextField txtObservacao;

	private Stage janelaEmprestimoEdit;

	private Emprestimo emprestimo;

	private boolean okClick = false;

	private ClienteListaController clienteListaController;
	private FilmeListaController filmeListaController;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaEmprestimoEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.emprestimo.setObservacao(this.txtObservacao.getText());
			if (this.dtpDataEmprestimo.getValue() != null) {
				this.emprestimo.setDataEmprestimo(Date.valueOf(this.dtpDataEmprestimo.getValue()));
			}
			this.emprestimo.setDataDevolucao(
					this.dtpDataDevolucao.getValue() != null ? Date.valueOf(this.dtpDataDevolucao.getValue()) : null);
		}
		this.emprestimo.setCliente(this.cbxCliente.getValue());
		this.emprestimo.setFilme(this.cbxFilme.getValue());

		this.okClick = true;
		this.getJanelaEmprestimoEdit().close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.clienteListaController = new ClienteListaController();
		this.filmeListaController = new FilmeListaController();

		this.carregarComboBoxClientes();
		this.carregarComboBoxFilmes();
	}

	public Stage getJanelaEmprestimoEdit() {
		return janelaEmprestimoEdit;
	}

	public void setJanelaEmprestimoEdit(Stage janelaEmprestimoEdit) {
		this.janelaEmprestimoEdit = janelaEmprestimoEdit;
	}

	public void populaTela(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;

		this.txtObservacao.setText(this.emprestimo.getObservacao() != null ? this.emprestimo.getObservacao() : "");

		if (this.emprestimo.getDataEmprestimo() != null) {
			this.dtpDataEmprestimo.setValue(this.emprestimo.getDataEmprestimo().toLocalDate());
		}
		if (this.emprestimo.getDataDevolucao() != null) {
			this.dtpDataDevolucao.setValue(this.emprestimo.getDataDevolucao().toLocalDate());
		}

		if (this.emprestimo.getCliente() != null) {
			this.cbxCliente.setValue(this.emprestimo.getCliente());
		}
		if (this.emprestimo.getFilme() != null) {
			this.cbxFilme.setValue(this.emprestimo.getFilme());
		}
	}

	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.cbxCliente.getValue() == null) {
			mensagemErros += "Selecione um cliente!\n";
		}
		if (this.cbxFilme.getValue() == null) {
			mensagemErros += "Selecione um filme!\n";
		}
		if (this.dtpDataEmprestimo.getValue() == null) {
			mensagemErros += "Informe a data de empréstimo!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaEmprestimoEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}

	public void carregarComboBoxClientes() {
		ObservableList<Cliente> observableListaCliente = FXCollections
				.observableArrayList(this.clienteListaController.retornaListagemCliente());

		this.cbxCliente.setItems(observableListaCliente);
	}

	public void carregarComboBoxFilmes() {
		ObservableList<Filme> observableListaFilme = FXCollections
				.observableArrayList(this.filmeListaController.retornaListagemFilme());

		this.cbxFilme.setItems(observableListaFilme);
		;
	}
}
