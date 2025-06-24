package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.model.ConsultaVO;
import br.com.tcs.treinamento.service.PessoaService;
import br.com.tcs.treinamento.service.impl.PessoaServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;

@ManagedBean(name = "agendamentoBean")
@ViewScoped
public class AgendamentoBean implements Serializable {

    private transient PessoaService pessoaService = new PessoaServiceImpl();

    private static final long serialVersionUID = -1820703288600208162L;
    private ConsultaVO cadastroConsulta;

    @PostConstruct
    public void init() {
        cadastroConsulta = new ConsultaVO();
    }

    public void buscarPaciente() {
        Pessoa p = pessoaService.buscarPorCpf(cadastroConsulta.getCpfPaciente());
        cadastroConsulta.setNomePaciente(p.getNome());
    }

    public void salvar() {

        // Reset the form
        init();
    }

    public LocalDate getHoje() {
        return LocalDate.now();
    }

    // <editor-fold desc="Getters and Setters">
    public ConsultaVO getCadastroConsulta() {
        return cadastroConsulta;
    }

    public void setCadastroConsulta(ConsultaVO cadastroConsulta) {
        this.cadastroConsulta = cadastroConsulta;
    }
    // </editor-fold>
}
