package com.monedero.model;

import com.monedero.dao.EgresoDAO;
import com.monedero.dao.IngresoDAO;
import com.monedero.dao.TransferenciaDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorTransacciones {

    private Cuenta cuenta;
    private List<Transaccion> transacciones;
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
        this.transacciones = new ArrayList<>();
        transacciones.addAll(ingresoDAO.findByCuentaDestino(cuenta));
        transacciones.addAll(egresoDAO.findByCuentaOrigen(cuenta));
        transacciones.addAll(transferenciaDAO.findByCuentaAsociada(cuenta));
    }

    public void addTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public List<Transaccion> filtrarTransaccionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Transaccion> transaccionesPorFecha = new ArrayList<>();

        for (Transaccion transaccion : transacciones) {
            LocalDate fechaTransaccion = transaccion.getFecha().toLocalDate();
            boolean dentroDeRango = (!fechaTransaccion.isBefore(fechaInicio) && !fechaTransaccion.isAfter(fechaFin));

            if (dentroDeRango) {
                transaccionesPorFecha.add(transaccion);
            }
        }

        return transaccionesPorFecha;
    }


    public <T extends Transaccion> List<T> filtrarTransaccionesPorTipo(List<Transaccion> transacciones, Class<T> tipoClase) {
        List<T> resultado = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (tipoClase.isInstance(transaccion)) {
                resultado.add(tipoClase.cast(transaccion));
            }
        }
        return resultado;
    }

    public double calcularTotalTransacciones(List<Transaccion> transacciones) {
        double total = 0;

        for (Transaccion transaccion : transacciones) {
            total += transaccion.getValor();
        }

        return total;
    }
}
