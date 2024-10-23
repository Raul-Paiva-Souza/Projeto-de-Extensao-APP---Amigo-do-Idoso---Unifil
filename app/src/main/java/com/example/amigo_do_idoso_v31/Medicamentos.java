package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

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

        // Adicionar medicamento
        btnAddMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exibir o diálogo para adicionar um medicamento
                exibirDialogoAdicionarMedicamento();
            }
        });

        // Remover medicamento
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

    // Método para exibir o diálogo de adicionar medicamento
    private void exibirDialogoAdicionarMedicamento() {
        // Cria o layout do diálogo
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_medicamento, null);

        final EditText editTextNome = dialogView.findViewById(R.id.editTextNomeMedicamento);
        final EditText editTextDosagem = dialogView.findViewById(R.id.editTextDosagemMedicamento);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Adicionar Medicamento");

        // Configura os botões do diálogo
        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            // Recuperar os valores inseridos
            String nomeMedicamento = editTextNome.getText().toString();
            String dosagemMedicamentoStr = editTextDosagem.getText().toString();

            if (!nomeMedicamento.isEmpty() && !dosagemMedicamentoStr.isEmpty()) {
                double dosagemMedicamento = Double.parseDouble(dosagemMedicamentoStr);

                // Criar o novo medicamento e adicionar à lista
                Medicamento novoMedicamento = new Medicamento(0, nomeMedicamento, dosagemMedicamento);
                medicamentosList.add(novoMedicamento);
                adapter.notifyDataSetChanged();

                // Enviar os dados para o banco de dados
                databaseHelper.addMedicamento(novoMedicamento);
            } else {
                // Se algum campo estiver vazio, mostre uma mensagem de erro
                // Podemos mostrar uma mensagem Toast ou um AlertDialog extra se preferir
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
