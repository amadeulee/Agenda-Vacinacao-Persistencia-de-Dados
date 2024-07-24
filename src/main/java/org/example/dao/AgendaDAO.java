package org.example.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.example.entity.Agenda;

public class AgendaDAO {
    private final EntityManager em;

    public AgendaDAO(EntityManager em) {
        this.em = em;
    }

    public Agenda findById(int id) {
        return em.find(Agenda.class, id);
    }

    public void save(Agenda agenda) {
        em.getTransaction().begin();
        em.persist(agenda);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        var userToRemove = findById(id);
        em.getTransaction().begin();
        em.remove(userToRemove);
        em.getTransaction().commit();
    }

    public List<Agenda> findAll() {
        String query = "SELECT agenda FROM Agenda agenda";
        return em.createQuery(query, Agenda.class).getResultList();
    }

    public void update(Agenda agenda) {
        em.getTransaction().begin();
        em.merge(agenda);
        em.getTransaction().commit();
    }
}
