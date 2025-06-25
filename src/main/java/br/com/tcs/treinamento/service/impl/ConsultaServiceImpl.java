package br.com.tcs.treinamento.service.impl;

import br.com.tcs.treinamento.dao.ConsultaDAO;
import br.com.tcs.treinamento.entity.Consulta;
import br.com.tcs.treinamento.service.ConsultaService;

import java.util.List;

public class ConsultaServiceImpl implements ConsultaService {

    private ConsultaDAO consultaDAO = new ConsultaDAO();

    @Override
    public void cadastrar(Consulta consulta) {
        consultaDAO.cadastrar(consulta);
    }

    @Override
    public Consulta buscarPorId(Long id) {
        return consultaDAO.buscarPorId(id);
    }

    @Override
    public List<Consulta> listar() {
        return consultaDAO.listar();
    }
}
