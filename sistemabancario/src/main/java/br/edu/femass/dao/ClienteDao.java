package br.edu.femass.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;

import br.edu.femass.model.Cliente;

public class ClienteDao extends Persist implements Dao<Cliente> { // trocou alguns clientes por objeto, eu
  // deixei cliente mesmo

  public ClienteDao() {
    super("Clientes.json");
  }

  public boolean gravar(Cliente objeto) throws StreamWriteException, IOException {
    Set<Cliente> clientes = buscar(); // o Set não permite que tenha dois atributos iguais, como o cpf
    boolean gravou = clientes.add(objeto); // mesma coisa que acontece no excluir

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, clientes); // pega a lista de clientes
    // e grava no arquivo e deixa bonito
    return gravou;
  }

  public boolean excluir(Cliente cliente) throws StreamWriteException, IOException {
    Set<Cliente> clientes = buscar();
    // boolean gravou = clientes.remove(cliente); // pra saber se quando não cria um
    // cpf igual, se gravou ou não. Então vai testar aqui pq o remove me retorna um
    // boolean, pra se gravou ou não

    for (Cliente clienteSelecionado : clientes) {
      if (clienteSelecionado.equals(cliente)) {
        clienteSelecionado.setAtivo(false);
      }
    }

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, clientes);
    return true;
  }

  // vai no arquivo, pega todo mundo e adiciona
  public Set<Cliente> buscar() throws DatabindException {
    try {
      Set<Cliente> clientes = objectMapper.readValue(arquivo, new TypeReference<Set<Cliente>>() {
      }); // foi no arquivo e chamou todos os clientes
      Cliente.atualizarUltimoId(clientes); // Atuliza
      return clientes;
    } catch (IOException ex) {
      return new HashSet<Cliente>();
    }

  }

  public List<Cliente> buscarAtivos() throws DatabindException {
    Set<Cliente> clientes = buscar();

    // forma de filtrar uma collection sem precisar interar por ela
    List<Cliente> clienteAtivos = clientes // sem precisar fazer um for e ir pegando os objetos e ir jogando em
        // outra lista
        .stream()
        .filter(cliente -> cliente.getAtivo().equals(true))// esse primeiro elemento cliente é cada
        .collect(Collectors.toList()); // converte em lista

    return clienteAtivos;
  }
}
