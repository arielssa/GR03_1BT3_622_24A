package com.monedero.dao;

import com.monedero.model.Egreso;
import com.monedero.model.Cuenta;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.monedero.util.HibernateUtil;

import java.util.List;

public class EgresoDAO extends BaseDAO<Egreso> {
    public EgresoDAO() {
        super(Egreso.class);
    }

    public List<Egreso> findByCuentaOrigen(Cuenta cuentaOrigen) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Egreso> query = session.createQuery("FROM Egreso WHERE cuentaOrigen = :cuentaOrigen", Egreso.class);
            query.setParameter("cuentaOrigen", cuentaOrigen);
            return query.list();
        }
    }
}
