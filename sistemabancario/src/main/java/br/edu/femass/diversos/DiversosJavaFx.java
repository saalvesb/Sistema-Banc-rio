package br.edu.femass.diversos;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DiversosJavaFx {

  public static void exibirMensagem(String mensagem) {
    Alert alerta = new Alert(AlertType.ERROR);
    alerta.setTitle(mensagem);
    alerta.show();
  }

}
