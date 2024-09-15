package com.example.amigo_do_idoso_v31;

public class Medicamento {
    private int id;
    private String nome;
    private double dosagem;

    public Medicamento(int id, String nome, double dosagem) {
        this.id = id;
        this.nome = nome;
        this.dosagem = dosagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDosagem() {
        return dosagem;
    }

    public void setDosagem(double dosagem) {
        this.dosagem = dosagem;
    }
}
