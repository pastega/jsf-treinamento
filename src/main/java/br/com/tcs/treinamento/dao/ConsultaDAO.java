package br.com.tcs.treinamento.dao;

import br.com.tcs.treinamento.entity.Consulta;
import br.com.tcs.treinamento.entity.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ConsultaDAO {

    private EntityManager em;
    private static EntityManagerFactory emf;

    public ConsultaDAO() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("myPU");
        }
        em = emf.createEntityManager();
    }

    public void cadastrar(Consulta consulta) {
        try {
            em.getTransaction().begin();
            em.persist(consulta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public List<Consulta> listar() {
        return em.createQuery("SELECT c FROM Consulta c", Consulta.class).getResultList();
    }

}
