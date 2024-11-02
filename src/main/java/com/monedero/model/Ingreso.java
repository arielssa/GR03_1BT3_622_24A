package com.monedero.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingresos")
public class Ingreso extends Transaccion {

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Cuenta cuentaDestino;


    // Constructores

    public Ingreso() {
    }

    public Ingreso(Cuenta cuentaDestino, double valor, String concepto, String categoria) {
        super(valor, concepto, categoria);
        this.cuentaDestino = cuentaDestino;
    }

    public Ingreso(Cuenta cuentaDestino, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaDestino = cuentaDestino;
    }

    public Ingreso(Cuenta cuentaDestino, double valor) {
        super(valor);
        this.cuentaDestino = cuentaDestino;
    }

    // Métodos
    @Override
    public void realizarTransaccion() {
        cuentaDestino.depositarDinero(getValor());
    }

    // Getters y Setters
    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
}
