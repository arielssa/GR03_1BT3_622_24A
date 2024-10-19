package com.monedero.model;

import javax.persistence.*;

@Entity
@Table(name = "egresos")
public class Egreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    @Column(name = "valor")
    private double valor;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "categoria")
    private String categoria;

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
}
