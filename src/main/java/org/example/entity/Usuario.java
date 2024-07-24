package org.example.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String nome;
    @Column(name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "usuario_alergia",
            joinColumns = { @JoinColumn(name = "usuario_id") },
            inverseJoinColumns = { @JoinColumn(name = "alergia_id") }
    )
    private List<Alergia> alergias = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Agenda> agendas = new ArrayList<>();

    private char sexo;
    private String logradouro;
    private int numero;
    private String setor;
    private String cidade;

    @Enumerated(EnumType.STRING)
    private UF uf;

    public Usuario(String nome, LocalDate dataNascimento, List<Alergia> alergias, char sexo, String logradouro, int numero, String setor, String cidade, UF uf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.alergias = alergias;
        this.sexo = sexo;
        this.logradouro = logradouro;
        this.numero = numero;
        this.setor = setor;
        this.cidade = cidade;
        this.uf = uf;
    }



    //    public void adicionarAlergia(Alergia alergia) {
//        this.alergias.add(alergia);
//        alergia.getUsuarios().add(this);
//    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", alergias=" + alergias.size() +
                ", agendas=" + agendas.size() +
                ", sexo=" + sexo +
                ", logradouro='" + logradouro + '\'' +
                ", numero=" + numero +
                ", setor='" + setor + '\'' +
                ", cidade='" + cidade + '\'' +
                ", uf=" + uf +
                '}';
    }
}
