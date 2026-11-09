package com.dawes.practicaexamen.modelo;

public class Jugador {
    private String nickname;
    private String equipo;
    private int kills;
    private int muertes;
    private int asistencias;

    public Jugador(String nickname, String equipo, int kills, int muertes, int asistencias) {
        this.nickname = nickname;
        this.equipo = equipo;
        this.kills = kills;
        this.muertes = muertes;
        this.asistencias = asistencias;
    }

    // KDA sin redondear. Si no hay muertes, no se divide
    private double calcularKda() {
        if (this.muertes == 0) {
            return this.kills + this.asistencias;
        } else {
            return 1.0 * (this.kills + this.asistencias) / this.muertes;
        }
    }

    public double getKda() {
        return Math.round(calcularKda() * 100) / 100.0;
    }

    public String getValoracion() {
        double kda = calcularKda();
        if (kda < 2) {
            return "Flojo";
        } else if (kda < 4) {
            return "Sólido";
        } else {
            return "Leyenda";
        }
    }

    public String getNickname() { return nickname; }
    public String getEquipo() { return equipo; }
    public int getKills() { return kills; }
    public int getMuertes() { return muertes; }
    public int getAsistencias() { return asistencias; }
}
