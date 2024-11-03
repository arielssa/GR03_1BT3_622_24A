package com.monedero.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencias")
public class Transferencia extends Transaccion {

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Cuenta cuentaDestino;

    // Constructores
    public Transferencia() {
    }

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double valor, String concepto, Etiqueta etiqueta) {
        super(valor, concepto, etiqueta);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double valor) {
        super(valor);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    // Métodos
    @Override
    public void realizarTransaccion() {
        if (validarValor() && cuentaOrigen.validarRetiro(this.valor)) {
            cuentaOrigen.retirarDinero(this.valor);
            cuentaDestino.depositarDinero(this.valor);
        } else {
            throw new RuntimeException("Saldo insuficiente para realizar la transferencia.");
        }
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

}
