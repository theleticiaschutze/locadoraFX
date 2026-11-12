package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.peregrinoti.dao.AmigoDAO;
import com.peregrinoti.dao.TipoColecaoDAO;
import com.peregrinoti.entity.Amigo;
import com.peregrinoti.entity.TipoColecao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClienteListaController implements Initializable {

	@FXML
	private ButtonBar barBotoes;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnInclur;

	@FXML
	private Label lblDetalhes;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblNomeValor;

	@FXML
	private Label lblTelefone;

	@FXML
	private Label lblTelefoneValor;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private TableColumn<Cliente, Long> tbcCodigo;

	@FXML
	private TableColumn<Cliente, String> tbcNome;

	@FXML
	private TableView<Cliente> tbvClientes;

	@FXML
	private Tooltip tlpEditar;

	@FXML
	private Tooltip tlpExcluir;

	@FXML
	private Tooltip tlpIncluir;

	public static final String CLIENTE_EDITAR = " - Editar";
	public static final String CLIENTE_INCLUIR = " - Incluir";

	private ClienteDAO amigoDAO;
	private List<Cliente> listaClientes;
	private ObservableList<Cliente> observableListaClientes = FXCollections.observableArrayList();

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Cliente amigo = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (amigo != null) {
			boolean btnConfirmarClic = this.onShowTelaClienteEditar(amigo,
					ClienteListaController.AMIGO_EDITAR);

			if (btnConfirmarClic) {
				this.getClienteDAO().update(amigo, null);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um amigo na tabela!");
			alerta.show();
		}

	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Cliente amigo = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (amigo != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do amigo?\n" + amigo.getNome());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getClienteDAO().delete(amigo);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um cliente na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Cliente amigo = new Cliente();

		boolean btnConfirmarClic = this.onShowTelaClienteEditar(amigo, ClienteListaController.AMIGO_INCLUIR);

		if (btnConfirmarClic) {
			this.getClienteDAO().save(amigo);
			this.carregarTableViewClientes();
		}
	}

	public boolean onShowTelaClienteEditar(Cliente amigo, String operacao) {
		try {
			// carregando o loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ifsc/tds/henry/leticia/luciane/view/ClienteEdit.fxml"));
			Parent amigoEditXML = loader.load();

			// criando uma janela nova
			Stage janelaAmigoEditar = new Stage();
			janelaClienteEditar.setTitle("Cadastro de amigo " + operacao);
			janelaClienteEditar.initModality(Modality.APPLICATION_MODAL);
			janelaClienteEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene amigoEditLayout = new Scene(amigoEditXML);
			janelaClienteEditar.setScene(amigoEditLayout);

			// carregando o controller e a scene
			CleinteEditController amigoEditController = loader.getController();
			amigoEditController.setJanelaAmigoEdit(janelaClienteEditar);
			amigoEditController.populaTela(amigo);

			// mostrando a tela
			janelaClienteEditar.showAndWait();

			return amigoEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de amigo?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public ClienteDAO getClienteDAO() {
		return amigoDAO;
	}

	public void setClienteDAO(ClienteDAO amigoDAO) {
		this.amigoDAO = amigoDAO;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setClienteDAO(new ClienteDAO());
		this.carregarTableViewClientes();
		this.selecionarItemTableViewTipoColecoes(null);

		this.tbvClientes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewTipoColecoes(newValue));

	}

	public void carregarTableViewAmigos() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaAmigos(this.getClienteDAO().getAll());
		this.setObservableListaTipoColecoes(FXCollections.observableArrayList(this.getListaAmigos()));
		this.tbvAmigos.setItems(this.getObservableListaAmigos());
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public ObservableList<Amigo> getObservableListaAmigos() {
		return observableListaClientes;
	}

	public void setObservableListaTipoColecoes(ObservableList<Cliente> observableListaClientes) {
		this.observableListaClientes = observableListaClientes;
	}

	public void selecionarItemTableViewTipoColecoes(Cliente amigo) {
		if (amigo != null) {
			this.lblNomeValor.setText(amigo.getNome());
			this.lblTelefoneValor.setText(amigo.getTelefone());
		} else {
			this.lblNomeValor.setText("");
			this.lblTelefoneValor.setText("");
		}
	}


}
