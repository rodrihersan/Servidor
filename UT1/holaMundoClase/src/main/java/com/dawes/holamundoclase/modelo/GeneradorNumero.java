package com.dawes.holamundoclase.modelo;

import java.util.Random;

public class GeneradorNumero {
    public int generar() {
        Random random = new Random();
// Genera y devuelve un número entre 1 y 100
        return random.nextInt(100) + 1;
    }
}
