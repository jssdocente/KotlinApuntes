package com.pmdm.example.projects.kotlinapuntes.examples.Classes.Static.Java;

// Clase que contiene miembros estáticos en Java
public class CalculadoraJava {
    // Miembro estático (constante): 'public static final'
    public static final double PI = 3.14159;

    // Método estático (de utilidad): 'public static'
    public static double calcularCircunferencia(double radio) {
        return 2 * PI * radio;
    }

    // Método de fábrica estático: 'public static'
    // Crea y devuelve una instancia de 'UsuarioJava' con valores predefinidos.
    public static UsuarioJava crearUsuarioInvitado() {
        return new UsuarioJava("Invitado", "guest@example.com");
    }

    // Método de instancia: Requiere un objeto CalculadoraJava para ser llamado.
    public int sumar(int a, int b) {
        return a + b;
    }
}