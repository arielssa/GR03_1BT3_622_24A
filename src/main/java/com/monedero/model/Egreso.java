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

    public Egreso(Cuenta cuentaOrigen, double valor, String concepto, String categoria) {
        super(valor, concepto, categoria);
        this.cuentaOrigen = cuentaOrigen;
    }

    public Egreso(Cuenta cuentaOrigen, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaOrigen = cuentaOrigen;
    }

    public Egreso(Cuenta cuentaOrigen, double valor) {
        super(valor);
        this.cuentaOrigen = cuentaOrigen;
    }

    // Métodos
    @Override
    public void realizarTransaccion() {
        if (validarValor() && cuentaOrigen.validarRetiro(this.valor)) {
            cuentaOrigen.retirarDinero(this.valor);
        } else {
            throw new RuntimeException("Saldo insuficiente para realizar el egreso.");
        }
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

}
