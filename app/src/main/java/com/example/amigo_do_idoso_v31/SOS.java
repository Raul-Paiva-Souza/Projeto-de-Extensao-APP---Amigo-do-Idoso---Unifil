package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class SOS extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
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
            String contato = dbHelper.getContatoDeConfiança();
            fazerChamada(contato);
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void fazerChamada(String numero) {
        // Verifica permissão antes de realizar a chamada
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + numero));
            startActivity(intent);
        } else {
            // Solicita permissão se não estiver concedida
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão concedida. Tente novamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão de chamada não concedida.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
