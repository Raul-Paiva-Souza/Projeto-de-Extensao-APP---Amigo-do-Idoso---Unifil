package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Medicamentos extends AppCompatActivity implements MedicamentoAdapter.OnMedicamentoClickListener {

    private RecyclerView recyclerView;
    private MedicamentoAdapter adapter;
    private List<Medicamento> medicamentosList;
    private DatabaseHelper databaseHelper;
    private Medicamento medicamentoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        recyclerView = findViewById(R.id.recyclerView_medicamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        // Carregar medicamentos do banco de dados
        medicamentosList = databaseHelper.getAllMedicamentos();

        adapter = new MedicamentoAdapter(medicamentosList, this);
        recyclerView.setAdapter(adapter);

        Button btnAddMedicamentos = findViewById(R.id.btn_add_medicamentos);
        Button btnRmvMedicamentos = findViewById(R.id.btn_rmv_medicamentos);

        // Adicionar medicamento
        btnAddMedicamentos.setOnClickListener(v -> exibirDialogoAdicionarMedicamento());

        // Remover medicamento
        btnRmvMedicamentos.setOnClickListener(v -> {
            if (medicamentoSelecionado != null) {
                exibirDialogoConfirmacaoExclusao();
            } else {
                Toast.makeText(this, "Selecione um medicamento para excluir.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exibirDialogoAdicionarMedicamento() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_medicamento, null);

        final EditText editTextNome = dialogView.findViewById(R.id.editTextNomeMedicamento);
        final EditText editTextDosagem = dialogView.findViewById(R.id.editTextDosagemMedicamento);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Adicionar Medicamento");

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String nomeMedicamento = editTextNome.getText().toString();
            String dosagemMedicamentoStr = editTextDosagem.getText().toString();

            if (!nomeMedicamento.isEmpty() && !dosagemMedicamentoStr.isEmpty()) {
                double dosagemMedicamento = Double.parseDouble(dosagemMedicamentoStr);

                Medicamento novoMedicamento = new Medicamento(0, nomeMedicamento, dosagemMedicamento);
                medicamentosList.add(novoMedicamento);
                adapter.notifyDataSetChanged();

                databaseHelper.addMedicamento(novoMedicamento);
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void exibirDialogoConfirmacaoExclusao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Exclusão");
        builder.setMessage("Excluir medicamento " + medicamentoSelecionado.getNome() + " " + medicamentoSelecionado.getDosagem() + " mg?");
        builder.setPositiveButton("Excluir", (dialog, which) -> {
            medicamentosList.remove(medicamentoSelecionado);
            adapter.notifyDataSetChanged();
            databaseHelper.removeMedicamento(medicamentoSelecionado.getId());
            medicamentoSelecionado = null; // Limpar seleção após exclusão
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onMedicamentoClick(Medicamento medicamento) {
        setMedicamentoSelecionado(medicamento);
        adapter.notifyDataSetChanged(); // Atualizar a visualização para destacar o item selecionado
    }

    public Medicamento getMedicamentoSelecionado() {
        return medicamentoSelecionado;
    }

    public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
        this.medicamentoSelecionado = medicamentoSelecionado;
    }
}
