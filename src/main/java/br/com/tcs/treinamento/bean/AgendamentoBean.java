package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Consulta;
import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.model.ConsultaVO;
import br.com.tcs.treinamento.service.ConsultaService;
import br.com.tcs.treinamento.service.PessoaService;
import br.com.tcs.treinamento.service.impl.ConsultaServiceImpl;
import br.com.tcs.treinamento.service.impl.PessoaServiceImpl;
import org.primefaces.PrimeFaces;

import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;

@ManagedBean(name = "agendamentoBean")
@ViewScoped
public class AgendamentoBean implements Serializable {

    private static final long serialVersionUID = -1820703288600208162L;
    private transient PessoaService pessoaService = new PessoaServiceImpl();
    private transient ConsultaService consultaService = new ConsultaServiceImpl();

    private ConsultaVO consultaVO;
    private Pessoa pessoa;
    private String errorMessage;

    @PostConstruct
    public void init() {
        consultaVO = new ConsultaVO();
    }

    public void buscarPaciente() {
        pessoa = pessoaService.buscarPorCpf(consultaVO.getCpfPaciente());
        if (pessoa == null) {
            consultaVO.setNomePaciente("");
            return;
        }
        consultaVO.setNomePaciente(pessoa.getNome());
    }

    public void salvar() {
        // Fazer validações
        if (pessoa == null) return;
        if (consultaVO.getCpfPaciente().isEmpty()) return;
        if (consultaVO.getNomePaciente().isEmpty()) return;
        if (consultaVO.getDataHoraConsulta() == null) return;

        System.out.println("Validações concluídas");

        Consulta c = mapVOEntity();
        try {
            consultaService.cadastrar(c);

            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }

        // Reiniciar o formulário
        init();
    }


    public LocalDate getHoje() {
        return LocalDate.now();
    }

    // <editor-fold desc="Getters and Setters">
    public ConsultaVO getConsultaVO() {
        return consultaVO;
    }

    public void setConsultaVO(ConsultaVO consultaVO) {
        this.consultaVO = consultaVO;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // </editor-fold>

    private Consulta mapVOEntity() {
        Consulta c = new Consulta();

        return c;
    }
}
