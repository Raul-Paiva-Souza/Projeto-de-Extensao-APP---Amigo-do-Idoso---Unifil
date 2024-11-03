package com.example.amigo_do_idoso_v31;

public class AgendaItem {
    private int id;
    private String data;
    private String horario;
    private String tipo;
    private String recado;
    private int medicamentoId;

    public AgendaItem(int id, String data, String horario, String tipo, String recado, int medicamentoId) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.recado = recado;
        this.medicamentoId = medicamentoId;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRecado() {
        return recado;
    }

    public int getMedicamentoId() {
        return medicamentoId;
    }
}

