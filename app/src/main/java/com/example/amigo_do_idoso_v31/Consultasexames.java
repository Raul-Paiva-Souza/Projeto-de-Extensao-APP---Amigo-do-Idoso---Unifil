package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.type.DateTime;
import java.util.ArrayList;
import java.util.List;

public class Consultasexames extends AppCompatActivity {

    private int idConsultaexame;
    private String descricao_consultasexames;
    private DateTime data_consultasexames;

    private RecyclerView recyclerView;
    private ConsultasexamesAdapter adapter;
    private List<Consultasexames> consultasexamesList;
    private DatabaseHelper databaseHelper;

    public Consultasexames(int idConsultaexame, String descricao_consultaexame, DateTime dataexame) {
        this.idConsultaexame = idConsultaexame;
        this.descricao_consultasexames = descricao_consultaexame;
        this.data_consultasexames = dataexame;
    }

    // Métodos Getter
    public int getIdConsultaexame() {
        return idConsultaexame;
    }

    public String getDescricao_consultaexame() {
        return descricao_consultasexames;
    }

    public DateTime getDataexame() {
        return data_consultasexames;
    }

    public Time getHorarioexame() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultase_exames);

        recyclerView = findViewById(R.id.recyclerView_consultasexames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        consultasexamesList = new ArrayList<>();
        adapter = new ConsultasexamesAdapter(consultasexamesList);
        recyclerView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this); // Inicialização do databaseHelper

        Button btnAddConsultaexames = findViewById(R.id.btn_add_consultasexames);
        btnAddConsultaexames.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Adicionar consulta/exame à lista e atualizar o RecyclerView
                Consultasexames novaConsultaExame = new Consultasexames(0, "Nova Consulta/Exame", DateTime.getDefaultInstance());
                consultasexamesList.add(novaConsultaExame);
                adapter.notifyDataSetChanged();
                // Enviar dados para o banco de dados
                databaseHelper.addConsultaExame(novaConsultaExame);
            }
        });

        Button btnRemoveConsultaexames = findViewById(R.id.btn_rmv_consultasexames);
        btnRemoveConsultaexames.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!consultasexamesList.isEmpty()) {
                    // Remover o último item da lista e atualizar o RecyclerView
                    Consultasexames consultaExameToRemove = consultasexamesList.get(consultasexamesList.size() - 1);
                    consultasexamesList.remove(consultaExameToRemove);
                    adapter.notifyDataSetChanged();
                    // Remover do banco de dados
                    databaseHelper.removeConsultaExame(consultaExameToRemove.getIdConsultaexame());
                }
            }
        });

    }


}
