package com.monedero.model;

import javax.persistence.*;

@Entity
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "balance_limite")
    private double balanceLimite;

    private boolean bloqueada;
    // Constructores
    public Cuenta() {
        this.balance = 0;
        this.balanceLimite = 0;
        this.bloqueada = false;
    }

    public Cuenta(String nombre, String numeroCuenta, Usuario usuario, double balance, double balanceLimite) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.usuario = usuario;
        this.balance = balance;
        this.balanceLimite = balanceLimite;
        this.bloqueada = false;
    }
    public Cuenta(String nombre, String numeroCuenta, Usuario usuario, double balance, double balanceLimite, boolean bloqueada) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.usuario = usuario;
        this.balance = balance;
        this.balanceLimite = balanceLimite;
        this.bloqueada = bloqueada;
    }

    // Métodos
    public void retirarDinero(double valor) {
        this.balance -= valor;
    }

    public void depositarDinero(double valor) {
        this.balance += valor;
    }

    public boolean validarRetiro(double valor) {
        return this.balance >= valor;
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

    public double getBalanceLimite() {
        return balanceLimite;
    }

    public void setBalanceLimite(double balanceLimite) {
        this.balanceLimite = balanceLimite;
    }
    public boolean isBloqueada() {
        return bloqueada;
    }
    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }
}