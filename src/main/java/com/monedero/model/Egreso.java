package com.monedero.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "egresos")
public class Egreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    private double valor;
    private String concepto;
    private String categoria;
    private LocalDateTime fecha;


    // Constructores
    public Egreso() {
    }

    public Egreso(Cuenta cuentaOrigen, double valor, String concepto, String categoria) {
        this.cuentaOrigen = cuentaOrigen;
        this.valor = valor;
        this.concepto = concepto;
        this.categoria = categoria;
    }

    // Métodos
    public void registrarEgreso() {
        if(cuentaOrigen.validarRetiro(this.valor))
            cuentaOrigen.retirarDinero(this.valor);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
