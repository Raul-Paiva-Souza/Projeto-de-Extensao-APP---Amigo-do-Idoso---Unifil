package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.type.DateTime;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Consultasexames extends AppCompatActivity {

    private int idConsultaexame;
    private String descricao_consultasexames;
    private DateTime data_consultasexames;

    private RecyclerView recyclerViewConsultasexames;
    private ConsultasexamesAdapter adapter;
    private List<Consultasexames> consultasexamesList;
    private DatabaseHelper databaseHelper;

    public Consultasexames() {
        // Construtor padrão vazio
    }
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

        recyclerViewConsultasexames = findViewById(R.id.recyclerView_consultasexames);
        recyclerViewConsultasexames.setLayoutManager(new LinearLayoutManager(this));

        consultasexamesList = new ArrayList<>();
        adapter = new ConsultasexamesAdapter(consultasexamesList);
        recyclerViewConsultasexames.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);

        Button btnAddConsultaexames = findViewById(R.id.btn_add_consultasexames);
        btnAddConsultaexames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir caixa de diálogo para adicionar nova consulta/exame
                showAddConsultaExameDialog();
            }
        });

        Button btnRemoveConsultaexames = findViewById(R.id.btn_rmv_consultasexames);
        btnRemoveConsultaexames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!consultasexamesList.isEmpty()) {
                    Consultasexames consultaExameToRemove = consultasexamesList.get(consultasexamesList.size() - 1);
                    consultasexamesList.remove(consultaExameToRemove);
                    adapter.notifyDataSetChanged();
                    databaseHelper.removeConsultaExame(consultaExameToRemove.getIdConsultaexame());
                }
            }
        });
    }

    private void showAddConsultaExameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Consulta/Exame");

        // Criar layout para a caixa de diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_consulta_exame, null);
        builder.setView(dialogView);

        EditText inputDescricao = dialogView.findViewById(R.id.input_descricao_consultaexame);
        EditText inputData = dialogView.findViewById(R.id.input_data_consultaexame);

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String descricao = inputDescricao.getText().toString();
                String dataStr = inputData.getText().toString();

                try {
                    // Formatar a data para o tipo DateTime
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    DateTime dataExame = DateTime.newBuilder().setYear(sdf.parse(dataStr).getYear()).build();

                    // Criar nova consulta/exame
                    Consultasexames novaConsultaExame = new Consultasexames(0, descricao, dataExame);
                    consultasexamesList.add(novaConsultaExame);
                    adapter.notifyDataSetChanged();

                    // Adicionar ao banco de dados
                    databaseHelper.addConsultaExame(novaConsultaExame);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Tratar erro de formatação de data
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


}
