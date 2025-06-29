package br.com.tcs.treinamento.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Pessoa paciente;

    @Column(nullable = false, unique = true)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", dataHora=" + dataHora +
                '}';
    }
}
