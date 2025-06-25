package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Consulta;
import br.com.tcs.treinamento.model.ConsultaVO;
import br.com.tcs.treinamento.service.ConsultaService;
import br.com.tcs.treinamento.service.impl.ConsultaServiceImpl;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "calendarioBean")
@ViewScoped
public class CalendarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ConsultaVO selectedConsulta;
    private ScheduleModel model;

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


    }

    public void onEventSelect() {
        // TODO: implement this
    }

    public ConsultaVO getSelectedConsulta() {
        return selectedConsulta;
    }

    public void setSelectedConsulta(ConsultaVO selectedConsulta) {
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
}
