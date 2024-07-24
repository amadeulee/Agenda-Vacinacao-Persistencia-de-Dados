package org.example.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.example.entity.Vacina;

public class VacinaDAO {
    private final EntityManager em;

    public VacinaDAO(EntityManager em) {
        this.em = em;
    }

    public Vacina findById(int id) {
        return em.find(Vacina.class, id);
    }

    public void save(Vacina vacina) {
        em.getTransaction().begin();
        em.persist(vacina);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        var userToRemove = findById(id);
        em.getTransaction().begin();
        em.remove(userToRemove);
        em.getTransaction().commit();
    }

    public List<Vacina> findAll() {
        String query = "SELECT vacina FROM Vacina vacina";
        return em.createQuery(query, Vacina.class).getResultList();
    }

    public void update(Vacina vacina) {
        em.merge(vacina);
    }
}
