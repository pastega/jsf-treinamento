package br.com.tcs.treinamento.service;

import br.com.tcs.treinamento.entity.Consulta;

import java.util.List;

public interface ConsultaService {
    void cadastrar(Consulta consulta);
    void atualizar(Consulta consulta);
    Consulta buscarPorId(Long id);
    List<Consulta> listar();
}
