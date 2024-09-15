package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnCadastro = findViewById(R.id.btn_menu_cadastro);
        Button btnAnamnese = findViewById(R.id.btn_menu_anamenese);
        Button btnConsultasEExames = findViewById(R.id.btn_menu_consultas_e_exames);
        Button btnEmergencia = findViewById(R.id.btn_menu_sos);
        Button btnAgenda = findViewById(R.id.btn_menu_agenda);
        Button btnMedicamentos = findViewById(R.id.btn_menu_medicamentos);
        Button btnSair = findViewById(R.id.btn_menu_sair);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Cadastro.class));
            }
        });

        btnAnamnese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Agenda.class));
            }
        });

        btnConsultasEExames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Consultasexames.class));
            }
        });

        btnEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, SOS.class));
            }
        });

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Agenda.class));
            }
        });

        btnMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Medicamentos.class));
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}