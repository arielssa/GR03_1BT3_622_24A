package com.monedero.dao;

import com.monedero.model.Cuenta;
import com.monedero.model.Egreso;
import com.monedero.model.Etiqueta;
import com.monedero.model.Usuario;
import com.monedero.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class EtiquetaDAO extends BaseDAO<Etiqueta> {
    public EtiquetaDAO() {
        super(Etiqueta.class);
    }

    public List<Etiqueta> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Etiqueta> query = session.createQuery("FROM Etiqueta WHERE usuario = :usuario", Etiqueta.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        }
    }

    public Etiqueta findByNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Etiqueta> query = session.createQuery("FROM Etiqueta WHERE nombre = :nombre", Etiqueta.class);
            query.setParameter("nombre", nombre);
            return query.uniqueResult();
        }
    }
}