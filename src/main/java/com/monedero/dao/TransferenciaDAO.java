package com.monedero.dao;

import com.monedero.model.Transferencia;
import com.monedero.model.Cuenta;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.monedero.util.HibernateUtil;

import java.util.List;

public class TransferenciaDAO extends BaseDAO<Transferencia> {
    public TransferenciaDAO() {
        super(Transferencia.class);
    }

    public List<Transferencia> findByCuentaOrigen(Cuenta cuentaOrigen) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transferencia> query = session.createQuery("FROM Transferencia WHERE cuentaOrigen = :cuentaOrigen", Transferencia.class);
            query.setParameter("cuentaOrigen", cuentaOrigen);
            return query.list();
        }
    }

    public List<Transferencia> findByCuentaDestino(Cuenta cuentaDestino) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transferencia> query = session.createQuery("FROM Transferencia WHERE cuentaDestino = :cuentaDestino", Transferencia.class);
            query.setParameter("cuentaDestino", cuentaDestino);
            return query.list();
        }
    }
}
