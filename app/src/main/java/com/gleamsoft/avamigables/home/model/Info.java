package com.gleamsoft.avamigables.home.model;

/**
 * Created by MacBook on 10/8/17.
 */

public class Info {
    private int id;
    private String titulo;
    private int gramos;

    public Info(int id, String titulo, int gramos) {
        this.id = id;
        this.titulo = titulo;
        this.gramos = gramos;
    }


    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getGramos() {
        return gramos;
    }
}
