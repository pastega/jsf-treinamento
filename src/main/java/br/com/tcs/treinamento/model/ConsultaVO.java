package br.com.tcs.treinamento.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ConsultaVO implements Serializable {

    private static final long serialVersionUID = -8356450769757744792L;

    private String cpfPaciente;
    private String nomePaciente;
    private LocalDateTime dataHoraConsulta;

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public LocalDateTime getDataHoraConsulta() {
        return dataHoraConsulta;
    }

    public void setDataHoraConsulta(LocalDateTime dataHoraConsulta) {
        this.dataHoraConsulta = dataHoraConsulta;
    }
}
