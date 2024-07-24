package org.example.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.example.entity.Usuario;

public class UsuarioDAO {
    private final EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public Usuario findById(int id) {
        return em.find(Usuario.class, id);
    }

    public void save(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        var userToRemove = findById(id);
        em.getTransaction().begin();
        em.remove(userToRemove);
        em.getTransaction().commit();
    }

    public List<Usuario> findAll() {
        String query = "SELECT user FROM Usuario user";
        return em.createQuery(query, Usuario.class).getResultList();
    }

    public void update(Usuario usuario) {
        em.merge(usuario);
    }
}
