package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ifsc.tds.henry.leticia.luciane.dao.EmprestimoDAO;
import com.ifsc.tds.henry.leticia.luciane.entity.Emprestimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmprestimoListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Emprestimo> tbvEmprestimos;

	@FXML
	private TableColumn<Emprestimo, Long> tbcCodigo;

	@FXML
	private TableColumn<Emprestimo, String> tbcCliente;

	@FXML
	private TableColumn<Emprestimo, String> tbcFilme;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblCliente;

	@FXML
	private Label lblClienteValor;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblFilmeValor;

	@FXML
	private Label lblDataEmprestimo;

	@FXML
	private Label lblDataEmprestimoValor;

	@FXML
	private Label lblDataDevolucao;

	@FXML
	private Label lblDataDevolucaoValor;

	@FXML
	private Label lblObservacao;

	@FXML
	private Label lblObservacaoValor;

	@FXML
	private ButtonBar barBotoes;

	@FXML
	private Button btnIncluir;

	@FXML
	private Tooltip tlpIncluir;

	@FXML
	private Button btnEditar;

	@FXML
	private Tooltip tlpEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Tooltip tlpExcluir;

	private List<Emprestimo> listaEmprestimos;
	private ObservableList<Emprestimo> observableListaEmprestimos = FXCollections.observableArrayList();
	private EmprestimoDAO emprestimoDAO;

	public static final String EMPRESTIMO_EDITAR = " - Editar";
	public static final String EMPRESTIMO_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Emprestimo emprestimo = this.tbvEmprestimos.getSelectionModel().getSelectedItem();

		if (emprestimo != null) {
			boolean btnConfirmarClic = this.onShowTelaEmprestimoEditar(emprestimo,
					EmprestimoListaController.EMPRESTIMO_EDITAR);

			if (btnConfirmarClic) {
				this.getEmprestimoDAO().update(emprestimo, null);
				this.carregarTableViewEmprestimos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um empréstimo na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Emprestimo emprestimo = this.tbvEmprestimos.getSelectionModel().getSelectedItem();

		if (emprestimo != null) {

			String nomeCliente = (emprestimo.getCliente() != null ? emprestimo.getCliente().getNome() : "") + " - "
					+ (emprestimo.getFilme() != null ? emprestimo.getFilme().getNome() : "");

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do emprestimo?\n" + nomeCliente);

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.isPresent() && resultado.get() == botaoSim) {
				this.getEmprestimoDAO().delete(emprestimo);
				this.carregarTableViewEmprestimos();
				this.selecionarItemTableViewEmprestimos(null);
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha uma emprestimo na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Emprestimo emprestimo = new Emprestimo();

		boolean btnConfirmarClic = this.onShowTelaEmprestimoEditar(emprestimo,
				EmprestimoListaController.EMPRESTIMO_INCLUIR);

		if (btnConfirmarClic) {
			this.getEmprestimoDAO().save(emprestimo);
			this.carregarTableViewEmprestimos();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setEmprestimoDAO(new EmprestimoDAO());
		this.carregarTableViewEmprestimos();
		this.selecionarItemTableViewEmprestimos(null);

		this.tbvEmprestimos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewEmprestimos(newValue));
	}

	public EmprestimoDAO getEmprestimoDAO() {
		return emprestimoDAO;
	}

	public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
		this.emprestimoDAO = emprestimoDAO;
	}

	public List<Emprestimo> getListaEmprestimos() {
		return listaEmprestimos;
	}

	public void setListaEmprestimos(List<Emprestimo> listaEmprestimos) {
		this.listaEmprestimos = listaEmprestimos;
	}

	public ObservableList<Emprestimo> getObservableListaEmprestimos() {
		return observableListaEmprestimos;
	}

	public void setObservableListaEmprestimos(ObservableList<Emprestimo> observableListaEmprestimos) {
		this.observableListaEmprestimos = observableListaEmprestimos;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de emprestimo?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.isPresent() && result.get() == buttonTypeYES;
	}

	public void carregarTableViewEmprestimos() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		this.tbcCliente.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getCliente() != null ? cellData.getValue().getCliente().getNome() : ""));

		this.tbcFilme.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getFilme() != null ? cellData.getValue().getFilme().getNome() : ""));

		this.setListaEmprestimos(this.getEmprestimoDAO().getAll());
		this.setObservableListaEmprestimos(FXCollections.observableArrayList(this.getListaEmprestimos()));
		this.tbvEmprestimos.setItems(this.getObservableListaEmprestimos());
	}

	public void selecionarItemTableViewEmprestimos(Emprestimo emprestimo) {
		if (emprestimo != null) {
			this.lblClienteValor.setText(emprestimo.getCliente() != null ? emprestimo.getCliente().getNome() : "");
			this.lblFilmeValor.setText(emprestimo.getFilme() != null ? emprestimo.getFilme().getNome() : "");
			this.lblDataEmprestimoValor
					.setText(emprestimo.getDataEmprestimo() != null ? emprestimo.getDataEmprestimo().toString() : "");
			this.lblDataDevolucaoValor
					.setText(emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao().toString() : "");
			this.lblObservacaoValor.setText(emprestimo.getObservacao() != null ? emprestimo.getObservacao() : "");
		} else {
			this.lblClienteValor.setText("");
			this.lblFilmeValor.setText("");
			this.lblDataEmprestimoValor.setText("");
			this.lblDataDevolucaoValor.setText("");
			this.lblObservacaoValor.setText("");
		}
	}

	public boolean onShowTelaEmprestimoEditar(Emprestimo emprestimo, String operacao) {
		try {
			// carregando o loader
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/ifsc/tds/henry/leticia/luciane/view/EmprestimoEdit.fxml"));
			Parent emprestimoEditXML = loader.load();

			// criando uma janela nova
			Stage janelaEmprestimoEditar = new Stage();
			janelaEmprestimoEditar.setTitle("Cadastro de emprestimo" + operacao);
			janelaEmprestimoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaEmprestimoEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene emprestimoEditLayout = new Scene(emprestimoEditXML);
			janelaEmprestimoEditar.setScene(emprestimoEditLayout);

			// carregando o controller e a scene
			EmprestimoEditController emprestimoEditController = loader.getController();
			emprestimoEditController.setJanelaEmprestimoEdit(janelaEmprestimoEditar);
			emprestimoEditController.populaTela(emprestimo);

			// mostrando a tela
			janelaEmprestimoEditar.showAndWait();

			return emprestimoEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
