package com.example.proyectomoviles;

public class Articulos {
    private int imagen;
    private String nombre;
    private int precio;

    public Articulos(int imagen,String nombre,int precio) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }



    public String getNombre() {
        return nombre;
    }



    public int getPrecio() {
        return precio;
    }


}
