package br.edu.femass.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;

import br.edu.femass.model.ContaCorrente;

public class ContaCorrenteDao extends Persist implements Dao<ContaCorrente> {

  public ContaCorrenteDao() {
    super("Contas.json");
  }

  @Override
  public boolean gravar(ContaCorrente objeto) throws StreamWriteException, IOException {
    Set<ContaCorrente> contas = buscar();
    boolean gravou = contas.add(objeto);

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, contas);
    return gravou;
  }

  @Override
  public boolean excluir(ContaCorrente objeto) throws StreamWriteException, IOException {
    Set<ContaCorrente> contas = buscar();
    for (ContaCorrente contaSelecionado : contas) {
      if (contaSelecionado.equals(objeto)) {
        contaSelecionado.setAtivo(false);
      }
    }

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, contas);
    return true;
  }

  @Override
  public Set<ContaCorrente> buscar() throws DatabindException {
    try {
      Set<ContaCorrente> contas = objectMapper.readValue(arquivo, new TypeReference<Set<ContaCorrente>>() {
      }); // foi no arquivo e chamou todos os clientes
      return contas;
    } catch (IOException ex) {
      return new HashSet<ContaCorrente>();
    }
  }

  @Override
  public List<ContaCorrente> buscarAtivos() throws DatabindException {
    Set<ContaCorrente> contas = buscar();

    List<ContaCorrente> contasAtivas = contas
        .stream()
        .filter(conta -> conta.getAtivo().equals(true))
        .collect(Collectors.toList());

    return contasAtivas;
  }

}
