package br.edu.femass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.dao.ClienteDao;
import br.edu.femass.dao.ContaCorrenteDao;
import br.edu.femass.dao.Dao;
import br.edu.femass.diversos.DiversosJavaFx;
import br.edu.femass.model.Cliente;
import br.edu.femass.model.ContaCorrente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ContaCorrenteController implements Initializable {

  @FXML
  private TextField TxtNumero;

  @FXML
  private ComboBox<Cliente> CboClientes;

  @FXML
  private ListView<ContaCorrente> listaConta;

  private Dao<Cliente> clienteDao = new ClienteDao();
  private Dao<ContaCorrente> contaDao = new ContaCorrenteDao();

  @FXML
  private void listaConta_keyPressed(KeyEvent event) {
    exibirDados();
  }

  @FXML
  private void listaConta_mouseClicked(MouseEvent event) {
    exibirDados();
  }

  private void exibirDados() {
    ContaCorrente conta = listaConta.getSelectionModel().getSelectedItem();
    if (conta == null)
      return;

    TxtNumero.setText(conta.getNumero());
    CboClientes.getSelectionModel().select(conta.getCliente());
  }

  @FXML
  private void BtnNovo_Click(ActionEvent event) {
    System.out.println("Novo");
  }

  @FXML
  private void BtnExcluir_Click(ActionEvent event) {
    ContaCorrente conta = listaConta.getSelectionModel().getSelectedItem();
    if (conta == null)
      return;

    try {
      if (contaDao.excluir(conta) == false) {
        DiversosJavaFx.exibirMensagem("Não foi possível excluir a conta selecionado");
      }
      exibirContas();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void BtnGravar_Click(ActionEvent event) {
    try {
      ContaCorrente conta = new ContaCorrente(
          TxtNumero.getText(),
          CboClientes.getSelectionModel().getSelectedItem());

      if (contaDao.gravar(conta) == false) {
        DiversosJavaFx.exibirMensagem("Não foi possível gravar a conta");
        return;
      }

      TxtNumero.setText("null");
      CboClientes.getSelectionModel().select(null);

      exibirContas();
    } catch (Exception e) {
      DiversosJavaFx.exibirMensagem(e.getMessage());
    }
  }

  public void exibirContas() {
    try {
      ObservableList<ContaCorrente> data = FXCollections.observableArrayList(
          contaDao.buscarAtivos());
      listaConta.setItems(data);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void exibirClientes() {
    try {
      ObservableList<Cliente> data = FXCollections.observableArrayList(
          clienteDao.buscarAtivos());
      CboClientes.setItems(data); // preenche a combo box
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exibirContas();
    exibirClientes();
  }

}
