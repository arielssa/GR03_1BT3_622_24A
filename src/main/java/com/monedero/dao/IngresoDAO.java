package com.monedero.dao;

import com.monedero.model.Ingreso;
import com.monedero.model.Cuenta;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.monedero.util.HibernateUtil;

import java.util.List;

public class IngresoDAO extends BaseDAO<Ingreso> {
    public IngresoDAO() {
        super(Ingreso.class);
    }

    public List<Ingreso> findByCuentaDestino(Cuenta cuentaDestino) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Ingreso> query = session.createQuery("FROM Ingreso WHERE cuentaDestino = :cuentaDestino", Ingreso.class);
            query.setParameter("cuentaDestino", cuentaDestino);
            return query.list();
        }
    }
}
