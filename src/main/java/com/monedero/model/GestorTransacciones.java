package com.monedero.model;

import com.monedero.dao.EgresoDAO;
import com.monedero.dao.IngresoDAO;
import com.monedero.dao.TransferenciaDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorTransacciones {

    private Cuenta cuenta;
    private List<Transaccion> transacciones;
    private IngresoDAO ingresoDAO;
    private EgresoDAO egresoDAO;
    private TransferenciaDAO transferenciaDAO;

    // Constructor principal con DAOs inyectables
    public GestorTransacciones(Cuenta cuenta) {
        this.cuenta = cuenta;
        this.ingresoDAO = new IngresoDAO();
        this.egresoDAO = new EgresoDAO();
        this.transferenciaDAO = new TransferenciaDAO();
        cargarTransacciones();
    }

    // Constructor alternativo para pruebas sin cargar datos desde los DAOs
    public GestorTransacciones(Cuenta cuenta, List<Transaccion> transacciones) {
        this.cuenta = cuenta;
        this.transacciones = transacciones.stream()
                .filter(t -> {
                    try {
                        t.validarValor();
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // Método que carga las transacciones desde los DAOs
    public void cargarTransacciones() {
        this.transacciones = new ArrayList<>();
        transacciones.addAll(ingresoDAO.findByCuentaDestino(cuenta));
        transacciones.addAll(egresoDAO.findByCuentaOrigen(cuenta));
        transacciones.addAll(transferenciaDAO.findByCuentaAsociada(cuenta));
    }

    // Método para agregar una transacción a la lista
    public void addTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    // Filtrar transacciones por fecha
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

    // Filtrar transacciones por tipo
    public <T extends Transaccion> List<T> filtrarTransaccionesPorTipo(List<Transaccion> transacciones, Class<T> tipoClase) {
        List<T> resultado = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (tipoClase.isInstance(transaccion)) {
                resultado.add(tipoClase.cast(transaccion));
            }
        }
        return resultado;
    }

    // Calcular el total de las transacciones
    public double calcularTotalTransacciones(List<Transaccion> transacciones) {
        double total = 0;
        for (Transaccion transaccion : transacciones) {
            total += transaccion.getValor();
        }
        return total;
    }

    // Filtrar transacciones por etiqueta
    public List<Transaccion> filtrarTransaccionesPorEtiqueta(Etiqueta etiqueta) {
        List<Transaccion> transaccionesPorEtiqueta = new ArrayList<>();

        for (Transaccion transaccion : transacciones) {
            if (transaccion.getEtiqueta().getNombre().equals(etiqueta.getNombre())) {
                transaccionesPorEtiqueta.add(transaccion);
            }
        }

        return transaccionesPorEtiqueta;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public String getMensajeRangoInvalido() {
        return "El rango de fechas es inválido. Por favor, cambie el rango.";
    }
}
