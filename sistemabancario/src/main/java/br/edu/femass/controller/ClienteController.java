package br.edu.femass.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.exc.StreamWriteException;

import br.edu.femass.dao.ClienteDao;
import br.edu.femass.diversos.DiversosJavaFx;
import br.edu.femass.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ClienteController implements Initializable {

  @FXML
  private TextField TxtId;

  @FXML
  private TextField TxtNome;

  @FXML
  private TextField TxtCpf;

  @FXML
  private TextField TxtEndereco;

  @FXML
  private TextField TxtEmail;

  @FXML
  private TextField TxtTelefone;

  @FXML
  private ListView<Cliente> listaCliente;

  private ClienteDao clienteDao = new ClienteDao();

  @FXML
  private void listaCliente_mouseClicked(MouseEvent event) {
    exibirDados();
  }

  @FXML
  private void listaCliente_keyPressed(KeyEvent event) {
    exibirDados();
  }

  private void exibirDados() {
    Cliente cliente = listaCliente.getSelectionModel().getSelectedItem();
    if (cliente == null)
      return;

    TxtCpf.setText(cliente.getCpf());
    TxtEmail.setText(cliente.getEmail());
    TxtEndereco.setText(cliente.getEndereco());
    TxtId.setText(cliente.getId().toString());
    TxtNome.setText(cliente.getNome());
    TxtTelefone.setText(cliente.getTelefones().get(0));
  }

  @FXML
  private void BtnNovo_Click(ActionEvent event) {
    System.out.println("Novo");
  }

  @FXML
  private void BtnExcluir_Click(ActionEvent event) throws StreamWriteException, IOException {
    Cliente cliente = listaCliente.getSelectionModel().getSelectedItem();
    if (cliente == null)
      return;

    try {
      if (clienteDao.excluir(cliente) == false) {
        DiversosJavaFx.exibirMensagem("Não foi possível excluir o cliente selecionado");
      }
      exibirClientes();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void BtnGravar_Click(ActionEvent event) {
    try {
      Cliente cliente = new Cliente(
          TxtCpf.getText(),
          TxtNome.getText(),
          TxtTelefone.getText());

      cliente.setEmail(TxtEmail.getText());
      cliente.setEndereco(TxtEndereco.getText());
      TxtId.setText(cliente.getId().toString());

      if (clienteDao.gravar(cliente) == false) {
        DiversosJavaFx.exibirMensagem("Não foi possível gravar cliente");
        return;
      }

      TxtCpf.setText("");
      TxtEmail.setText("");
      TxtEndereco.setText("");
      TxtId.setText("");
      TxtNome.setText("");
      TxtTelefone.setText("");

      exibirClientes();

    } catch (Exception e) {
      DiversosJavaFx.exibirMensagem(e.getMessage());
    }
  }

  public void exibirClientes() {
    try {
      ObservableList<Cliente> data = FXCollections.observableArrayList(clienteDao.buscarAtivos());
      listaCliente.setItems(data);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exibirClientes();
  }

}
