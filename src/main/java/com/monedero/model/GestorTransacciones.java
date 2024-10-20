package com.monedero.model;

import com.monedero.dao.EgresoDAO;
import com.monedero.dao.IngresoDAO;
import com.monedero.dao.TransferenciaDAO;

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

    public List<Ingreso> obtenerIngresosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Ingreso> ingresosPorFecha = new ArrayList<>();

        for (Ingreso ingreso : ingresos) {
            if (!ingreso.getFecha().isBefore(fechaInicio) && !ingreso.getFecha().isAfter(fechaFin)) {
                ingresosPorFecha.add(ingreso);
            }
        }
        return ingresosPorFecha;
    }

    public List<Egreso> obtenerEgresosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Egreso> egresosPorFecha = new ArrayList<>();

        for (Egreso egreso : egresos) {
            if (!egreso.getFecha().isBefore(fechaInicio) && !egreso.getFecha().isAfter(fechaFin)) {
                egresosPorFecha.add(egreso);
            }
        }
        return egresosPorFecha;
    }

    public List<Transferencia> obtenerTransferenciaPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Transferencia> transferenciasPorFecha = new ArrayList<>();

        for (Transferencia transferencia : transferencias) {
            if (!transferencia.getFecha().isBefore(fechaInicio) && !transferencia.getFecha().isAfter(fechaFin)) {
                transferenciasPorFecha.add(transferencia);
            }
        }
        return transferenciasPorFecha;
    }

    public double obtenerTotalIngresosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        double total = 0;
        List<Ingreso> ingresosPorFecha = this.obtenerIngresosPorFecha(fechaInicio, fechaFin);
        for (Ingreso ingreso : ingresosPorFecha) {
            total += ingreso.getValor();
        }
        return total;
    }

    public double obtenerTotalEgresosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        double total = 0;
        List<Egreso> egresosPorFecha = this.obtenerEgresosPorFecha(fechaInicio, fechaFin);
        for (Egreso egreso : egresosPorFecha) {
            total += egreso.getValor();
        }
        return total;
    }

}
