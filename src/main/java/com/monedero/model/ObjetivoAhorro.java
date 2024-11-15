package com.monedero.model;

import javax.persistence.*;

@Entity
@Table(name = "objetivos_ahorro")
public class ObjetivoAhorro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "monto_objetivo")
    private double montoObjetivo;

    @Column(name = "monto_actual")
    private double montoActual;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public ObjetivoAhorro() {}

    public ObjetivoAhorro(double montoObjetivo, double montoActual, String descripcion, Usuario usuario) {
        this.montoObjetivo = montoObjetivo;
        this.montoActual = montoActual;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Double obtenerPorcentajeProgreso() {
        Double porcentaje = (montoActual/montoObjetivo) * 100;
        return porcentaje;
    }

    public Double obtenerMontoRestante() {
        Double montoRestante = montoObjetivo - montoActual;
        return montoRestante;
    }

    public void ingresarDinero(double monto) {
        this.montoActual += monto;
    }

    public boolean retirarDinero(double monto) {
        if (verificarSaldoSuficiente(monto)) {
            this.montoActual -= monto;
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarSaldoSuficiente(double monto) {
        return this.montoActual >= monto;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public double getMontoObjetivo() {
        return montoObjetivo;
    }

    public void setMontoObjetivo(double montoObjetivo) {
        this.montoObjetivo = montoObjetivo;
    }

    public double getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(double montoActual) {
        this.montoActual = montoActual;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
