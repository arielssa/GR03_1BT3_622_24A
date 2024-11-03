package com.monedero.model;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "etiquetas")
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nombre", unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructores

    public Etiqueta () {
    }

    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }

    public Etiqueta(String nombre, Usuario usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public static Optional<Etiqueta> buscarEtiquetaPorNombre(List<Etiqueta> etiquetas, String nombreBuscado) {
        return etiquetas.stream()
                .filter(etiqueta -> etiqueta.getNombre().equals(nombreBuscado))
                .findFirst();
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
