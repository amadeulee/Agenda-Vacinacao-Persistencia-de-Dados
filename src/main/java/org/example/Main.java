package org.example;

import org.example.dao.AgendaDAO;
import org.example.dao.AlergiaDAO;
import org.example.dao.UsuarioDAO;
import org.example.dao.VacinaDAO;
import org.example.entity.Agenda;
import org.example.entity.Alergia;
import org.example.entity.Situacao;
import org.example.entity.UF;
import org.example.entity.Usuario;
import org.example.entity.Vacina;
import org.example.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        var em = EntityManagerFactory.getEntityManager();
        em.getTransaction().begin();
        VacinaDAO vacinaDAO = new VacinaDAO(em);
        AgendaDAO agendaDAO = new AgendaDAO(em);
        AlergiaDAO alergiaDAO = new AlergiaDAO(em);
        UsuarioDAO usuarioDAO = new UsuarioDAO(em);


//        var vacinaExistente = vacinaDAO.findById(1);
//        var agendaExistente = agendaDAO.findById(1);
//        var alergiaExistente = alergiaDAO.findById(1);
//        var usuarioExistente = usuarioDAO.findById(2);

//        System.out.println(vacinaExistente);
//        System.out.println(agendaExistente);
//        System.out.println(alergiaExistente);
//        System.out.println(usuarioExistente);


//        List<Alergia> alergias = new ArrayList<>();
//        Alergia alergia = new Alergia("melancia");
//        usuarioExistente.adicionarAlergia(alergia);
//
//        usuarioExistente.getAlergias().add(alergia);
//        alergia.getUsuarios().add(usuarioExistente);
//        Vacina vacina = new Vacina(1, 1, 1);
//        alergias.add(alergia);
//
//
//
//        List<Alergia> alergias = new ArrayList<>();
//        alergias.add(alergiaDAO.findById(3));
//        Usuario usuario = new Usuario("Carlos", LocalDate.now(), alergias, 'M', "Logradouro", 10, "Bela Vista", "Goiania", UF.GO);
//        Agenda agenda = new Agenda(LocalDate.now(), "10:10:15",  Situacao.AGENDADO, LocalDate.now(), vacinaExistente, usuario, "PS:nada");
//
//
//        alergiaDAO.save(alergia);
//        vacinaDAO.save(vacina);
//        usuarioDAO.save(usuario);
//        agendaDAO.save(agenda);


//        var usuarioAchado = usuarioDAO.findById(1);
//        usuarioAchado.setUf(UF.RS);
//        usuarioDAO.update(usuarioAchado);


        em.getTransaction().commit();


//        var vacinaExistente = vacinaDAO.findById(1);
//        var agendaExistente = agendaDAO.findById(1);
//        var usuarioSalvo = usuarioDAO.findById(2);

        System.out.println(vacinaDAO.findAll());
        System.out.println(agendaDAO.findAll());
        System.out.println(alergiaDAO.findAll());
        System.out.println(usuarioDAO.findAll());
        em.close();
    }
}