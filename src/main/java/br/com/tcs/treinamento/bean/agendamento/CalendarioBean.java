package br.com.tcs.treinamento.bean.agendamento;

import br.com.tcs.treinamento.entity.Consulta;
import br.com.tcs.treinamento.model.ConsultaVO;
import br.com.tcs.treinamento.service.ConsultaService;
import br.com.tcs.treinamento.service.impl.ConsultaServiceImpl;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
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

    public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
        ScheduleEvent event = selectEvent.getObject();
        Long consultaId = (Long) event.getData();

        // Buscar no banco de dados a consulta inteira e cirar um VO a partir dela
        Consulta consulta = consultaService.buscarPorId(consultaId);

        System.out.println(consulta);

        ConsultaVO consultaVO = new ConsultaVO();
        consultaVO.setNomePaciente(consulta.getPaciente().getNome());
        consultaVO.setCpfPaciente(consulta.getPaciente().getNumeroCPF());
        consultaVO.setDataHoraConsulta(consulta.getDataHora());

        selectedConsulta = consultaVO;
    }

    public String prepararEdicao() {
        return "alterar";
    }

    // <editor-fold desc="Getters and Setters">
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

    // </editor-fold>
}
