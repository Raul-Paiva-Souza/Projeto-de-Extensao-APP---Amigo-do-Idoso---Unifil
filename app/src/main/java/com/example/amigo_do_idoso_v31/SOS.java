package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SOS extends AppCompatActivity {

    private Button btnChamarPolicia, btnChamarBombeiros, btnChamarSAMU, btnChamarContatoConfianca, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        btnChamarPolicia = findViewById(R.id.btn_chamar_polícia);
        btnChamarBombeiros = findViewById(R.id.btn_chamar_bombeiros);
        btnChamarSAMU = findViewById(R.id.btn_chamar_SAMU);
        btnChamarContatoConfianca = findViewById(R.id.btn_chamar_contato_de_confianca);
        btnVoltar = findViewById(R.id.btn_voltar_sos_menu);

        btnChamarPolicia.setOnClickListener(v -> fazerChamada("190"));
        btnChamarBombeiros.setOnClickListener(v -> fazerChamada("193"));
        btnChamarSAMU.setOnClickListener(v -> fazerChamada("192"));

        btnChamarContatoConfianca.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(SOS.this);
            String contato = dbHelper.getContatoDeConfiança(); // Método para buscar o contato de confiança
            fazerChamada(contato);
        });

        btnVoltar.setOnClickListener(v -> finish()); // Voltar para a activity anterior
    }

    private void fazerChamada(String numero) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + numero));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
