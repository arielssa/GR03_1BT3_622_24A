package com.monedero.model;

import com.monedero.dao.EgresoDAO;
import com.monedero.dao.IngresoDAO;
import com.monedero.dao.TransferenciaDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorTransacciones {

    private Cuenta cuenta;
    private List<Ingreso> ingresos;
    private List<Egreso> egresos;
    private List<Transferencia> transferencias;
    IngresoDAO ingresoDAO;
    EgresoDAO egresoDAO;
    TransferenciaDAO transferenciaDAO;

    public GestorTransacciones(Cuenta cuenta) {
        ingresoDAO = new IngresoDAO();
        egresoDAO = new EgresoDAO();
        transferenciaDAO = new TransferenciaDAO();
        this.cuenta = cuenta;
        cargarTransacciones();
    }

    public void cargarTransacciones() {
        this.ingresos = ingresoDAO.findByCuentaDestino(this.cuenta);
        this.egresos = egresoDAO.findByCuentaOrigen(this.cuenta);
        this.transferencias = transferenciaDAO.findByCuentaAsociada(this.cuenta);
    }

    public void agregarIngreso(Ingreso ingreso) {
        this.ingresos.add(ingreso);
    }

    public void agregarEgreso(Egreso egreso) {
        this.egresos.add(egreso);
    }

    public void agregarTransferencia(Transferencia transferencia) {
        this.transferencias.add(transferencia);
    }

    public List<Ingreso> obtenerIngresosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Ingreso> ingresosPorFecha = new ArrayList<>();

        for (Ingreso ingreso : ingresos) {
            LocalDate fechaIngreso = ingreso.getFecha().toLocalDate();
            boolean dentroDeRango = !fechaIngreso.isBefore(fechaInicio) && !fechaIngreso.isAfter(fechaFin);

            if (dentroDeRango) {
                ingresosPorFecha.add(ingreso);
            }
        }

        return ingresosPorFecha;
    }


    public List<Egreso> obtenerEgresosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Egreso> egresosPorFecha = new ArrayList<>();

        for (Egreso egreso : egresos) {
            LocalDate fechaEgreso = egreso.getFecha().toLocalDate();
            boolean dentroDeRango = !fechaEgreso.isBefore(fechaInicio) && !fechaEgreso.isAfter(fechaFin);

            if (dentroDeRango) {
                egresosPorFecha.add(egreso);
            }
        }

        return egresosPorFecha;
    }

    public List<Transferencia> obtenerTransferenciasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Transferencia> transferenciasPorFecha = new ArrayList<>();

        for (Transferencia transferencia : transferencias) {
            if (!transferencia.getFecha().toLocalDate().isBefore(fechaInicio) && !transferencia.getFecha().toLocalDate().isAfter(fechaFin)) {
                transferenciasPorFecha.add(transferencia);
            }
        }
        return transferenciasPorFecha;
    }

    public double obtenerTotalIngresosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        double total = 0;

        for (Ingreso ingreso : this.obtenerIngresosPorFecha(fechaInicio, fechaFin)) {
            total += ingreso.getValor();
        }

        return total;
    }

    public double obtenerTotalEgresosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        double total = 0;

        for (Egreso egreso : this.obtenerEgresosPorFecha(fechaInicio, fechaFin)) {
            total += egreso.getValor();
        }

        return total;
    }

}
