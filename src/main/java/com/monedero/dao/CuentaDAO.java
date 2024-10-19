package com.monedero.dao;

import com.monedero.model.Cuenta;
import com.monedero.model.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.monedero.util.HibernateUtil;

import java.util.List;

public class CuentaDAO extends BaseDAO<Cuenta> {
    public CuentaDAO() {
        super(Cuenta.class);
    }

    public List<Cuenta> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cuenta> query = session.createQuery("FROM Cuenta WHERE usuario = :usuario", Cuenta.class);
            query.setParameter("usuario", usuario);
            return query.list();
        }
    }

    public Cuenta findByNumeroCuenta(String numeroCuenta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cuenta> query = session.createQuery("FROM Cuenta WHERE numeroCuenta = :numeroCuenta", Cuenta.class);
            query.setParameter("numeroCuenta", numeroCuenta);
            return query.uniqueResult();
        }
    }
}
