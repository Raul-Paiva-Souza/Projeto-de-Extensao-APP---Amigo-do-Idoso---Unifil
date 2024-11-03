package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.type.DateTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConsultasExamesActivity extends AppCompatActivity implements ConsultasexamesAdapter.OnConsultaExameClickListener {

    private RecyclerView recyclerViewConsultasExames;
    private ConsultasexamesAdapter adapter;
    private List<ConsultaExame> consultasExamesList;
    private DatabaseHelper databaseHelper;
    private ConsultaExame consultaExameSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultase_exames);

        recyclerViewConsultasExames = findViewById(R.id.recyclerView_consultasexames);
        recyclerViewConsultasExames.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        // Carregar consultas e exames do banco de dados
        consultasExamesList = databaseHelper.getAllConsultasExames();

        adapter = new ConsultasexamesAdapter(consultasExamesList, this);
        recyclerViewConsultasExames.setAdapter(adapter);



        Button btnAddConsultaExame = findViewById(R.id.btn_add_consultasexames);
        Button btnRemoveConsultaExame = findViewById(R.id.btn_rmv_consultasexames);

        // Adicionar consulta/exame
        btnAddConsultaExame.setOnClickListener(v -> exibirDialogoAdicionarConsultaExame());

        // Remover consulta/exame
        btnRemoveConsultaExame.setOnClickListener(v -> {
            if (consultaExameSelecionado != null) {
                exibirDialogoConfirmacaoExclusao();
            } else {
                Toast.makeText(this, "Selecione uma consulta/exame para excluir.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void exibirDialogoAdicionarConsultaExame() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_consulta_exame, null);

        final EditText inputDescricao = dialogView.findViewById(R.id.input_descricao_consultaexame);
        final EditText inputData = dialogView.findViewById(R.id.input_data_consultaexame);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Adicionar Consulta/Exame");

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String descricao = inputDescricao.getText().toString();
            String dataStr = inputData.getText().toString();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date = sdf.parse(dataStr);
                DateTime dataExame = DateTime.newBuilder()
                        .setYear(date.getYear() + 1900)
                        .setMonth(date.getMonth() + 1)
                        .setDay(date.getDate())
                        .build();

                ConsultaExame novaConsultaExame = new ConsultaExame(0, descricao, dataExame);

                // Adicionar ao banco de dados
                databaseHelper.addConsultaExame(novaConsultaExame);

                // Recarregar a lista e atualizar o adaptador
                consultasExamesList.clear();
                consultasExamesList.addAll(databaseHelper.getAllConsultasExames());
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao formatar a data. Use o formato dia-mês-ano.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void exibirDialogoConfirmacaoExclusao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Exclusão");
        builder.setMessage("Excluir consulta/exame " + consultaExameSelecionado.getDescricao() + " em "
                + consultaExameSelecionado.getDataExame().getYear() + "-"
                + consultaExameSelecionado.getDataExame().getMonth() + "-"
                + consultaExameSelecionado.getDataExame().getDay() + "?");

        builder.setPositiveButton("Excluir", (dialog, which) -> {
            consultasExamesList.remove(consultaExameSelecionado);
            adapter.notifyDataSetChanged();
            databaseHelper.removeConsultaExame(consultaExameSelecionado.getIdConsultaExame());
            consultaExameSelecionado = null; // Limpar seleção após exclusão
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onConsultaExameClick(ConsultaExame consultaExame) {
        consultaExameSelecionado = consultaExame;
        adapter.notifyDataSetChanged(); // Atualizar a visualização para destacar o item selecionado
    }

    public ConsultaExame getConsultaExameSelecionado() {
        return consultaExameSelecionado;

    }


}

