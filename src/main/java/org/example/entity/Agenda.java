package org.example.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "DATE")
    private LocalDate data;
    private String hora;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @Column(columnDefinition = "DATE")
    private LocalDate dataSituacao;

    @ManyToOne
    @JoinColumn(name = "vacina_id")
    private Vacina vacina;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String observacoes;


    public Agenda(LocalDate data, String hora, Situacao situacao, LocalDate dataSituacao, Vacina vacina, Usuario usuario, String observacoes) {
        this.data = data;
        this.hora = hora;
        this.situacao = situacao;
        this.dataSituacao = dataSituacao;
        this.vacina = vacina;
        this.usuario = usuario;
        this.observacoes = observacoes;
    }

    public Agenda(LocalDate data, String hora, Situacao situacao, Vacina vacina, Usuario usuario, String observacoes) {
        this.data = data;
        this.hora = hora;
        this.situacao = situacao;
        this.vacina = vacina;
        this.usuario = usuario;
        this.observacoes = observacoes;
    }
}
