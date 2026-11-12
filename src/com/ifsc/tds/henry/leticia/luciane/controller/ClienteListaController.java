package com.ifsc.tds.henry.leticia.luciane.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.peregrinoti.dao.ClienteDAO;
import com.peregrinoti.dao.ClienteDAO;
import com.peregrinoti.entity.Cliente;
import com.peregrinoti.entity.Cliente;

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
    private Label lblDataCadastro;

    @FXML
    private Label lblDataCadastroValor;

    @FXML
    private Label lblDetalhes;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblEmailValor;

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

	private ClienteDAO clienteDAO;
	private List<Cliente> listaClientes;
	private ObservableList<Cliente> observableListaClientes = FXCollections.observableArrayList();

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Cliente cliente = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (cliente != null) {
			boolean btnConfirmarClic = this.onShowTelaClienteEditar(cliente,
					ClienteListaController.AMIGO_EDITAR);

			if (btnConfirmarClic) {
				this.getClienteDAO().update(cliente, null);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um cliente na tabela!");
			alerta.show();
		}

	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Cliente cliente = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (cliente != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do cliente?\n" + cliente.getNome());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getClienteDAO().delete(cliente);
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
		Cliente cliente = new Cliente();

		boolean btnConfirmarClic = this.onShowTelaClienteEditar(cliente, ClienteListaController.CLIENTE_INCLUIR);

		if (btnConfirmarClic) {
			this.getClienteDAO().save(cliente);
			this.carregarTableViewClientes();
		}
	}

	public boolean onShowTelaClienteEditar(Cliente cliente, String operacao) {
		try {
			// carregando o loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ifsc/tds/henry/leticia/luciane/view/ClienteEdit.fxml"));
			Parent clienteEditXML = loader.load();

			// criando uma janela nova
			Stage janelaClienteEditar = new Stage();
			janelaClienteEditar.setTitle("Cadastro de cliente " + operacao);
			janelaClienteEditar.initModality(Modality.APPLICATION_MODAL);
			janelaClienteEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene clienteEditLayout = new Scene(clienteEditXML);
			janelaClienteEditar.setScene(clienteEditLayout);

			// carregando o controller e a scene
			ClienteEditController clienteEditController = loader.getController();
			clienteEditController.setJanelaClienteEdit(janelaClienteEditar);
			clienteEditController.populaTela(cliente);

			// mostrando a tela
			janelaClienteEditar.showAndWait();

			return clienteEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de cliente?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setClienteDAO(new ClienteDAO());
		this.carregarTableViewClientes();
		this.selecionarItemTableViewClientes(null);

		this.tbvClientes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));

	}

	public void carregarTableViewClientes() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaClientes(this.getClienteDAO().getAll());
		this.setObservableListaClientes(FXCollections.observableArrayList(this.getListaClientes()));
		this.tbvClientes.setItems(this.getObservableListaClientes());
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public ObservableList<Cliente> getObservableListaClientes() {
		return observableListaClientes;
	}

	public void setObservableListaClientes(ObservableList<Cliente> observableListaClientes) {
		this.observableListaClientes = observableListaClientes;
	}

	public void selecionarItemTableViewClientes(Cliente cliente) {
		if (cliente != null) {
			this.lblNomeValor.setText(cliente.getNome());
			this.lblTelefoneValor.setText(cliente.getTelefone());
		} else {
			this.lblNomeValor.setText("");
			this.lblTelefoneValor.setText("");
		}
	}


}
