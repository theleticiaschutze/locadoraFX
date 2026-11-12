package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.peregrinoti.dao.RevistaDAO;
import com.peregrinoti.entity.Revista;

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

public class FilmeListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Revista> tbvRevistas;

	@FXML
	private TableColumn<Revista, Long> tbcCodigo;

	@FXML
	private TableColumn<Revista, String> tbcNome;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblAno;

	@FXML
	private Label lblNomeValor;

	@FXML
	private Label lblAnoValor;

	@FXML
	private Label lblCaixa;

	@FXML
	private Label lblCaixaValor;

	@FXML
	private Label lblColecao;

	@FXML
	private Label lblColecaoValor;

	@FXML
	private ButtonBar barBotoes;

	@FXML
	private Button btnInclur;

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

	private List<Revista> listaRevistas;
	private ObservableList<Revista> observableListaRevistas = FXCollections.observableArrayList();
	private RevistaDAO revistaDAO;

	public static final String REVISTA_EDITAR = " - Editar";
	public static final String REVISTA_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Revista revista = this.tbvRevistas.getSelectionModel().getSelectedItem();

		if (revista != null) {
			boolean btnConfirmarClic = this.onShowTelaRevistaEditar(revista, FilmeListaController.REVISTA_EDITAR);

			if (btnConfirmarClic) {
				this.getRevistaDAO().update(revista, null);
				this.carregarTableViewRevistas();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha uma revista na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Revista revista = this.tbvRevistas.getSelectionModel().getSelectedItem();

		if (revista != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclus�o da revista?\n" + revista.getNome());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getRevistaDAO().delete(revista);
				this.carregarTableViewRevistas();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha uma revista na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Revista revista = new Revista();

		boolean btnConfirmarClic = this.onShowTelaRevistaEditar(revista, FilmeListaController.REVISTA_INCLUIR);

		if (btnConfirmarClic) {
			this.getRevistaDAO().save(revista);
			this.carregarTableViewRevistas();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setRevistaDAO(new RevistaDAO());
		this.carregarTableViewRevistas();
		this.selecionarItemTableViewRevistas(null);

		this.tbvRevistas.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewRevistas(newValue));
	}

	public RevistaDAO getRevistaDAO() {
		return revistaDAO;
	}

	public void setRevistaDAO(RevistaDAO revistaDAO) {
		this.revistaDAO = revistaDAO;
	}

	public List<Revista> getListaRevistas() {
		return listaRevistas;
	}

	public void setListaRevistas(List<Revista> listaRevistas) {
		this.listaRevistas = listaRevistas;
	}

	public ObservableList<Revista> getObservableListaRevistas() {
		return observableListaRevistas;
	}

	public void setObservableListaRevistas(ObservableList<Revista> observableListaRevistas) {
		this.observableListaRevistas = observableListaRevistas;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de revista?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public void carregarTableViewRevistas() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaRevistas(this.getRevistaDAO().getAll());
		this.setObservableListaRevistas(FXCollections.observableArrayList(this.getListaRevistas()));
		this.tbvRevistas.setItems(this.getObservableListaRevistas());
	}

	public void selecionarItemTableViewRevistas(Revista revista) {
		if (revista != null) {
			this.lblNomeValor.setText(revista.getNome());
			this.lblAnoValor.setText(revista.getAnoFormatado());
			this.lblCaixaValor.setText(revista.getCaixa().getCor());
			this.lblColecaoValor.setText(revista.getTipoColecao().getNome());
		} else {
			this.lblNomeValor.setText("");
			this.lblAnoValor.setText("");
			this.lblCaixaValor.setText("");
			this.lblColecaoValor.setText("");
		}
	}

	public boolean onShowTelaRevistaEditar(Revista revista, String operacao) {
		try {
			// carregando o loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/peregrinoti/view/RevistaEdit.fxml"));
			Parent revistaEditXML = loader.load();

			// criando uma janela nova
			Stage janelaRevistaEditar = new Stage();
			janelaRevistaEditar.setTitle("Cadastro de revista" + operacao);
			janelaRevistaEditar.initModality(Modality.APPLICATION_MODAL);
			janelaRevistaEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene revistaEditLayout = new Scene(revistaEditXML);
			janelaRevistaEditar.setScene(revistaEditLayout);

			// carregando o controller e a scene
			FilmeEditController revistaEditController = loader.getController();
			revistaEditController.setJanelaRevistaEdit(janelaRevistaEditar);
			revistaEditController.populaTela(revista);

			// mostrando a tela
			janelaRevistaEditar.showAndWait();

			return revistaEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
