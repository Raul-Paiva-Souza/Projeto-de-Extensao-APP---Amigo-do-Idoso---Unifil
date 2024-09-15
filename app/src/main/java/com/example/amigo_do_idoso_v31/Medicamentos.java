package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.amigo_do_idoso_v31.Medicamento;


public class Medicamentos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicamentoAdapter adapter;
    private List<Medicamento> medicamentosList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        recyclerView = findViewById(R.id.recyclerView_medicamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicamentosList = new ArrayList<>();
        adapter = new MedicamentoAdapter(medicamentosList);
        recyclerView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);

        Button btnAddMedicamentos = findViewById(R.id.btn_add_medicamentos);
        Button btnRmvMedicamentos = findViewById(R.id.btn_rmv_medicamentos);

        btnAddMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adicionar medicamento à lista e atualizar o RecyclerView
                Medicamento novoMedicamento = new Medicamento(0, "Novo Medicamento", 10.0);
                medicamentosList.add(novoMedicamento);
                adapter.notifyDataSetChanged();

                // Enviar dados para o banco de dados
                databaseHelper.addMedicamento(novoMedicamento);
            }
        });

        btnRmvMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!medicamentosList.isEmpty()) {
                    Medicamento medicamentoARemover = medicamentosList.get(medicamentosList.size() - 1);
                    medicamentosList.remove(medicamentoARemover);
                    adapter.notifyDataSetChanged();

                    // Remover do banco de dados
                    databaseHelper.removeMedicamento(medicamentoARemover.getId());
                }
            }
        });
    }
}


