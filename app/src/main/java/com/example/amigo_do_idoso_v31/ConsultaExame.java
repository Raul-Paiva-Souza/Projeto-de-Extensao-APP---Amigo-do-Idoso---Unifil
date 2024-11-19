package com.example.amigo_do_idoso_v31;

//import com.google.type.DateTime;

import java.util.Date;

public class ConsultaExame {

    private int idConsultaExame;
    private String descricao;
    private Date dataExame;

    public ConsultaExame(int idConsultaExame, String descricao, Date dataExame) {
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

    public Date getDataExame() {
        return dataExame;
    }
}

