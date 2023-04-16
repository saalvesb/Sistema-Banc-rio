package br.edu.femass.dao;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

public interface Dao<T> {

  public boolean gravar(T objeto) throws StreamWriteException, IOException;

  public boolean excluir(T objeto) throws StreamWriteException, IOException;

  public Set<T> buscar() throws DatabindException;

  public List<T> buscarAtivos() throws DatabindException;

}
