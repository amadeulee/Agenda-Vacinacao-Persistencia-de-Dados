package org.example.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.example.entity.Alergia;

public class AlergiaDAO {
    private final EntityManager em;

    public AlergiaDAO(EntityManager em) {
        this.em = em;
    }

    public Alergia findById(int id) {
        return em.find(Alergia.class, id);
    }

    public void save(Alergia alergia) {
        em.getTransaction().begin();
        em.persist(alergia);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        var userToRemove = findById(id);
        em.getTransaction().begin();
        em.remove(userToRemove);
        em.getTransaction().commit();
    }

    public List<Alergia> findAll() {
        String query = "SELECT alergia FROM Alergia alergia";
        return em.createQuery(query, Alergia.class).getResultList();
    }

    public void update(Alergia alergia) {
        em.merge(alergia);
    }
}
