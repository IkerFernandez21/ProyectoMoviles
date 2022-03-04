package com.example.proyectomoviles;

public class Clientes {
    private String nombre;
    private double TotalGastado;

    public Clientes(String nombre, double totalGastado) {
        this.nombre = nombre;
        this.TotalGastado = totalGastado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotalGastado() {
        return TotalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        TotalGastado = totalGastado;
    }
}
