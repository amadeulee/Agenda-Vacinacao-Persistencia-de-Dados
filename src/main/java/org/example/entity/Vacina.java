package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vacina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String descricao;


    private int doses;
    private int periodicidade;
    private int intervalo;

    @OneToMany(mappedBy = "vacina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agenda> agendas = new ArrayList<>();


    public Vacina(String titulo, String descricao, int doses, int periodicidade, int intervalo) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.doses = doses;
        this.periodicidade = periodicidade;
        this.intervalo = intervalo;
    }

    @Override
    public String toString() {
        return "Vacina{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", doses=" + doses +
                ", periodicidade=" + periodicidade +
                ", intervalo=" + intervalo +
                '}';
    }
}
