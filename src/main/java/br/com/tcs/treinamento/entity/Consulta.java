package br.com.tcs.treinamento.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Pessoa paciente;

    private LocalDateTime dataHora;
}
