package com.example.amigo_do_idoso_v31;

import com.google.type.DateTime;

public class ConsultaExame {

    private int idConsultaExame;
    private String descricao;
    private DateTime dataExame;

    public ConsultaExame(int idConsultaExame, String descricao, DateTime dataExame) {
        this.idConsultaExame = idConsultaExame;
        this.descricao = descricao;
        this.dataExame = dataExame;
    }

    public int getIdConsultaExame() {
        return idConsultaExame;
    }

    public String getDescricao() {
        return descricao;
    }

    public DateTime getDataExame() {
        return dataExame;
    }
}

