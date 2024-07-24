package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Alergia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "alergias")
    private List<Usuario> usuarios = new ArrayList<>();


    public Alergia(String nome) {
        this.nome = nome;
    }

}
