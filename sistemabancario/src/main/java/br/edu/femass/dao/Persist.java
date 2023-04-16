package br.edu.femass.dao;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Persist {
  protected File arquivo;
  protected ObjectMapper objectMapper = new ObjectMapper();

  public Persist(String nomeArquivo) {
    arquivo = new File(nomeArquivo);
  }
}
