package com.monedero.model;

import javax.persistence.*;

@Entity
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero_cuenta", unique = true)
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "balance")
    private double balance;

    // Constructores
    public Cuenta() {
    }

    public Cuenta(String nombre, String numeroCuenta, Usuario usuario, double balance) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.usuario = usuario;
        this.balance = balance;
    }

    // Métodos
    public void retirarDinero(double valor) {
        this.balance -= valor;
    }

    public void depositarDinero(double valor) {
        this.balance += valor;
    }

    public boolean validarRetiro(double valor) {
        if (this.balance >= valor) {
            return true;
        }
        return false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
