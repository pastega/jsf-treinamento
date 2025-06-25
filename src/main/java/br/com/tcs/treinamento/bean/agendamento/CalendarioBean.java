package br.com.tcs.treinamento.bean.agendamento;

import br.com.tcs.treinamento.entity.Consulta;
import br.com.tcs.treinamento.model.ConsultaVO;
import br.com.tcs.treinamento.service.ConsultaService;
import br.com.tcs.treinamento.service.impl.ConsultaServiceImpl;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "calendarioBean")
@ViewScoped
public class CalendarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Consulta selectedConsulta;
    private ScheduleModel model;

    private String errorMessage;

    private transient ConsultaService consultaService = new ConsultaServiceImpl();

    @PostConstruct
    public void init() {
        model = new DefaultScheduleModel();
        List<Consulta> consultas = consultaService.listar();

        if (consultas.isEmpty()) return;

        consultas.forEach(consulta -> {
            DefaultScheduleEvent<Object> event = DefaultScheduleEvent.builder()
                    .title("Consulta: " + consulta.getPaciente().getNome())
                    .startDate(consulta.getDataHora())
                    .endDate(consulta.getDataHora().plusMinutes(45))
                    .data(consulta.getId())
                    .description("Consulta: " + consulta.getPaciente().getNome())
                    .build();
            model.addEvent(event);
        });

        // Buscar por parâmetros de URL
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        String consultaIdParam = params.get("consultaId");

        if (consultaIdParam != null && !consultaIdParam.trim().isEmpty()) {
            try {
                selectedConsulta = consultaService.buscarPorId(Long.parseLong(consultaIdParam));
            } catch (NumberFormatException e) {
                errorMessage = "ID inválido";
            }
        }

        if (selectedConsulta == null) {
            errorMessage = "Consulta inválida";
        }
    }

    public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
        ScheduleEvent event = selectEvent.getObject();
        Long consultaId = (Long) event.getData();

        // Buscar no banco de dados a consulta inteira e cirar um VO a partir dela
        selectedConsulta = consultaService.buscarPorId(consultaId);
    }

    public String prepararEdicao() {
        System.out.println("Redirecionando para edição");
        return "alterar?faces-redirect=true&consultaId=" + selectedConsulta.getId() ;
    }

    // <editor-fold desc="Getters and Setters">
    public Consulta getSelectedConsulta() {
        return selectedConsulta;
    }

    public void setSelectedConsulta(Consulta selectedConsulta) {
        this.selectedConsulta = selectedConsulta;
    }

    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public ConsultaService getConsultaService() {
        return consultaService;
    }

    public void setConsultaService(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // </editor-fold>
    public LocalDate getHoje() {
        return LocalDate.now();
    }

    public String atualizar() {
        consultaService.atualizar(selectedConsulta);
        PrimeFaces.current().executeScript("PF('successDialog').show();");

        return "calendario?faces-redirect=true";
    }
}
