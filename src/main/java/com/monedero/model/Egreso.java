package com.monedero.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "egresos")
public class Egreso extends Transaccion {

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    // Constructores
    public Egreso() {
    }

    public Egreso(Cuenta cuentaOrigen, double valor, String concepto, Etiqueta etiqueta) {
        super(valor, concepto, etiqueta);
        this.cuentaOrigen = cuentaOrigen;
    }

    public Egreso(Cuenta cuentaOrigen, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaOrigen = cuentaOrigen;
    }

    public Egreso(double valor, String concepto) {
        super(valor, concepto);
    }

    public Egreso(Cuenta cuentaOrigen, double valor) {
        super(valor);
        this.cuentaOrigen = cuentaOrigen;
    }

    // Métodos
    @Override
    public void realizarTransaccion() {
        validarValor();
        if (!cuentaOrigen.validarRetiro(this.valor)) {
            throw new RuntimeException("Saldo insuficiente para realizar el egreso.");
        } else if (!cuentaOrigen.isBloqueada()) {
            cuentaOrigen.retirarDinero(this.valor);
        }
    }


    @Override
    public double calcularBalanceAntesDeTransaccion(double saldoDespues, int cuentaId) {
        return saldoDespues + this.valor;
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

}