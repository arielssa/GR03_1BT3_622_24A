package com.monedero.model;

import com.monedero.dao.BaseDAO;
import com.monedero.dao.CuentaDAO;

import javax.persistence.*;

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

    public Transferencia(double valor, String concepto) {
        super(valor, concepto);
    }

    // Métodos
    @Override
    public void realizarTransaccion() {
        // Verificar si la cuenta origen está bloqueada
        if (cuentaOrigen.isBloqueada()) {
            throw new RuntimeException("La cuenta de origen está bloqueada. No se puede realizar la transferencia.");
        }

        // Validar saldo suficiente en la cuenta de origen
        if (!cuentaOrigen.validarRetiro(this.valor)) {
            throw new RuntimeException("Saldo insuficiente para realizar la transferencia.");
        }

        // Verificar que la cuenta destino exista (aquí se pasa el DAO como parámetro)
        CuentaDAO cuentaDAO = new CuentaDAO();  // Preferiblemente inyectar el DAO
        if (cuentaDAO.findById(cuentaDestino.getId()) == null) {
            throw new RuntimeException("La cuenta destino no existe.");
        }

        // Realizar la transferencia si todas las validaciones pasaron
        cuentaOrigen.retirarDinero(this.valor);
        cuentaDestino.depositarDinero(this.valor);
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    @Override
    public double calcularBalanceAntesDeTransaccion(double saldoDespues, int cuentaId) {
        if(this.getCuentaOrigen().getId() == cuentaId){
            return saldoDespues + this.getValor();
        } else if (this.getCuentaDestino().getId() == cuentaId) {
            return saldoDespues - this.getValor();
        }
        return 0;
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