package br.com.tcs.treinamento.bean.agendamento;

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
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "agendamentoBean")
@ViewScoped
public class AgendamentoBean implements Serializable {

    private static final long serialVersionUID = -1820703288600208162L;
    private transient PessoaService pessoaService = new PessoaServiceImpl();
    private transient ConsultaService consultaService = new ConsultaServiceImpl();

    private ConsultaVO consultaVO;
    private String errorMessage;

    @PostConstruct
    public void init() {
        consultaVO = new ConsultaVO();
    }

    public void buscarPaciente() {
        Pessoa pessoa = pessoaService.buscarPorCpf(consultaVO.getCpfPaciente());
        if (pessoa == null) {
            consultaVO.setNomePaciente("");
            return;
        }
        System.out.println("Selecionando pessoa: " + pessoa);
        consultaVO.setNomePaciente(pessoa.getNome());
    }

    public void salvar() {
        List<String> erros = new ArrayList<>();
        Pessoa pessoa = null;

        // 1. First, attempt to find the patient and handle NoResultException
        try {
            pessoa = pessoaService.buscarPorCpf(consultaVO.getCpfPaciente());
        } catch (Exception e) {
            // Patient not found in DB
            pessoa = null; // Explicitly set to null if NoResultException occurs
            System.err.println("Paciente não encontrado pelo CPF: " + consultaVO.getCpfPaciente());
            erros.add("Paciente não encontrado no banco de dados.");
        }

        // 2. Perform other validations
        if (pessoa == null) { // This check now also catches the NoResultException case
            // If errors were not added in the try-catch, add them here
            if (!erros.contains("Paciente não encontrado no banco de dados.")) {
                erros.add("Paciente não encontrado no banco de dados.");
            }
        }
        if (consultaVO.getDataHoraConsulta() == null) {
            erros.add("Data e hora da consulta são inválidas.");
        }

        // 3. If any validation errors exist, show the error dialog and stop.
        if (!erros.isEmpty()) {
            errorMessage = String.join("<br/>", erros);
            System.out.println("Validation Errors: " + errorMessage); // For debugging
            PrimeFaces.current().ajax().update("errorDialog");
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
            return; // STOP here if there are validation errors
        }

        // 4. If validations pass, proceed with saving the consultation.
        Consulta c = mapVOEntity(pessoa);
        try {
            consultaService.cadastrar(c);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
            // Reiniciar o formulário only on success
            init();
        } catch(Exception e) {
            // If an exception occurs during cadastrar (e.g., database error)
            System.err.println("Erro ao agendar consulta: " + e.getMessage());
            errorMessage = "Erro ao agendar consulta: " + e.getMessage(); // Provide specific error if desired
            PrimeFaces.current().ajax().update("errorDialog");
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }

        // Remove this line unless 'confirmDialog' has a very specific, different purpose
        // PrimeFaces.current().executeScript("PF('confirmDialog').show();");
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

    private Consulta mapVOEntity(Pessoa p) {
        Consulta c = new Consulta();
        c.setPaciente(p);
        c.setDataHora(consultaVO.getDataHoraConsulta());
        return c;
    }
}
