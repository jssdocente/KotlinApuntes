package com.pmdm.example.projects.kotlinapuntes.examples.Classes.Static.Java;

// Clase simple para representar un usuario en Java
class UsuarioJava {
    private String nombre;
    private String email;

    public UsuarioJava(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "UsuarioJava [nombre=" + nombre + ", email=" + email + "]";
    }
}