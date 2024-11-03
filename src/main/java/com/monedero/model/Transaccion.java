package com.monedero.model;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    protected boolean validarValor() {
        if (this.valor <= 0) {
            throw new IllegalArgumentException("El valor de la transacción no puede ser menor o igual a 0.");
        }
        return true;
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
