package com.monedero.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@MappedSuperclass
public abstract class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected double valor;
    protected String concepto;
    protected LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "etiqueta_id")
    protected Etiqueta etiqueta;

    // Constructores
    public Transaccion() {
    }

    public Transaccion(double valor, String concepto, Etiqueta etiqueta) {
        this.valor = valor;
        this.concepto = concepto;
        this.etiqueta = etiqueta;
    }

    public Transaccion(double valor, String concepto) {
        this.valor = valor;
        this.concepto = concepto;
    }

    public Transaccion(double valor) {
        this.valor = valor;
    }

    // Métodos

    public abstract void realizarTransaccion();

    public abstract double calcularBalanceAntesDeTransaccion(double saldoDespues, int cuentaId);
    public boolean validarValor() {
        if (this.valor <= 0) {
            throw new IllegalArgumentException("El valor de la transacción no puede ser menor o igual a 0.");
        }
        return true;
    }

    /**
     * Extrae la información de la transacción en un formato de arreglo de cadenas.
     *
     * @return Un arreglo de cadenas que contiene el tipo, fecha, valor, concepto y etiqueta de la transacción.
     */
    public List<String> extraerInformacionTransaccion() {
        String tipo = this.getClass().getSimpleName();
        String fechaTexto = fecha != null ? fecha.toString().substring(0, 10) : "Sin fecha";
        String valorTexto = String.format("%.2f", valor);
        String conceptoTexto = concepto != null ? concepto : "Sin concepto";
        String etiquetaTexto = (etiqueta != null && etiqueta.getNombre() != null) ? etiqueta.getNombre() : "Sin etiqueta";
        // Crear y devolver una lista con los valores de la transacción
        List<String> informacionTransaccion = new ArrayList<>();
        informacionTransaccion.add(tipo);
        informacionTransaccion.add(fechaTexto);
        informacionTransaccion.add(valorTexto);
        informacionTransaccion.add(conceptoTexto);
        informacionTransaccion.add(etiquetaTexto);
        return informacionTransaccion;
    }
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta categoria) {
        this.etiqueta = categoria;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}