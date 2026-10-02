package com.dawes.ejerciciothymeleafampliacion.modelo;

public class Carrito {
    private LineaCarrito lineaCarrito1;
    private LineaCarrito lineaCarrito2;
    private LineaCarrito lineaCarrito3;

    public Carrito(LineaCarrito lineaCarrito1, LineaCarrito lineaCarrito2, LineaCarrito lineaCarrito3) {
        this.lineaCarrito1 = lineaCarrito1;
        this.lineaCarrito2 = lineaCarrito2;
        this.lineaCarrito3 = lineaCarrito3;
    }

    public double getSubtotal(){
        double suma = this.lineaCarrito1.getProducto().getPrecio() * this.lineaCarrito1.getCantidad()
                + this.lineaCarrito2.getProducto().getPrecio() * this.lineaCarrito2.getCantidad()
                + this.lineaCarrito3.getProducto().getPrecio() * this.lineaCarrito3.getCantidad();
        return suma;
    }

    public double getIVA(){
        return getSubtotal() * 0.21;
    }

    public double getTotal(){
        return getSubtotal() + getIVA();
    }
    public LineaCarrito getLineaCarrito1() {
        return lineaCarrito1;
    }

    public void setLineaCarrito1(LineaCarrito lineaCarrito1) {
        this.lineaCarrito1 = lineaCarrito1;
    }

    public LineaCarrito getLineaCarrito2() {
        return lineaCarrito2;
    }

    public void setLineaCarrito2(LineaCarrito lineaCarrito2) {
        this.lineaCarrito2 = lineaCarrito2;
    }

    public LineaCarrito getLineaCarrito3() {
        return lineaCarrito3;
    }

    public void setLineaCarrito3(LineaCarrito lineaCarrito3) {
        this.lineaCarrito3 = lineaCarrito3;
    }
}
