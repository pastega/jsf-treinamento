package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.model.ConsultaVO;
import br.com.tcs.treinamento.service.ConsultaService;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "calendarioBean")
@ViewScoped
public class CalendarioBean {

    private ConsultaVO consulta;
    private ScheduleModel model;

    private ConsultaService agendamentoService;

    @PostConstruct
    public void init() {
        model = new DefaultScheduleModel();

        List<ConsultaVO> consultas = agendamentoService.listAll();
        if (consultas.isEmpty()) return;

    }

    public void onEventSelect() {

    }
}
