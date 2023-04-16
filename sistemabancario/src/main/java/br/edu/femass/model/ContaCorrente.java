package br.edu.femass.model;

public class ContaCorrente {
  private String numero;
  private Cliente cliente;
  private Double saldo;
  private Boolean ativo;

  public ContaCorrente() {
  }

  public ContaCorrente(String numero, Cliente cliente) {
    this.numero = numero;
    this.cliente = cliente;
    this.saldo = 0.0;
    this.ativo = true;
  }

  @Override
  public String toString() {
    return "numero=" + numero + ", cliente=" + cliente.getNome();// faz os nomes e numeros aparecerem na ListView
  }

  public String getNumero() {
    return numero;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public Double getSaldo() {
    return saldo;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

}
