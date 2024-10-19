package com.monedero.dao;

import com.monedero.model.Usuario;
import org.hibernate.query.Query;
import org.hibernate.Session;
import com.monedero.util.HibernateUtil;

public class UsuarioDAO extends BaseDAO<Usuario> {
    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario findByNombreUsuario(String nombreUsuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE nombreUsuario = :nombreUsuario", Usuario.class);
            query.setParameter("nombreUsuario", nombreUsuario);
            return query.uniqueResult();
        }
    }
}
