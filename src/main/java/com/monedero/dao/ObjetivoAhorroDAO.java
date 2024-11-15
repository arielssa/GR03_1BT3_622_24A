package com.monedero.dao;

import com.monedero.model.Cuenta;
import com.monedero.model.ObjetivoAhorro;
import com.monedero.model.Usuario;
import com.monedero.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ObjetivoAhorroDAO extends BaseDAO<ObjetivoAhorro> {
    public ObjetivoAhorroDAO() {
        super(ObjetivoAhorro.class);
    }

    public List<ObjetivoAhorro> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ObjetivoAhorro> query = session.createQuery("FROM ObjetivoAhorro WHERE usuario = :usuario", ObjetivoAhorro.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        }
    }
}
