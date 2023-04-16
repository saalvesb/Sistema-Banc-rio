package br.edu.femass.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

  private Cliente cliente;

  @BeforeEach
  void setUp() {
    cliente = new Cliente(
        "53105575049",
        "Teste",
        "22 99999-9999");
  }

  @Test
  void construtorCpfIncorreto() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Cliente(
            "123456789",
            "Teste",
            "22 99999-9999"));

  }

  @Test
  void clienteCriadoComUmTelefone() {
    assertEquals(1, cliente.getTelefones().size());
  }

  @Test
  void clienteComDoisTelefones() {
    cliente.adicionarTelefone("1234458388");
    assertEquals(2, cliente.getTelefones().size());
  }

  @Test
  void clienteRemoverUmTelefone() throws Exception {
    assertThrows(Exception.class,
        () -> cliente.removerTelefone("22 99999-9999"));
  }

  @Test
  void clienteRemoverUmTelefoneTendoDois() throws Exception {
    cliente.adicionarTelefone("122344668");
    cliente.removerTelefone("22 99999-9999");

    assertEquals(1, cliente.getTelefones().size());
  }
}
