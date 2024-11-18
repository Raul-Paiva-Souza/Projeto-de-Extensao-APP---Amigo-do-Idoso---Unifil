package com.example.amigo_do_idoso_v31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Solicitar permissões
        if (!PermissionHelper.checkAndRequestPermissions(this)) {
            Toast.makeText(this, "Por favor, conceda todas as permissões para usar o aplicativo.", Toast.LENGTH_LONG).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }

        // Forçar a criação do banco de dados
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Configurar botões
        Button btnEntrar = findViewById(R.id.btn_entrar);
        Button btnCriarConta = findViewById(R.id.btn_criar_conta);

        btnCriarConta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NovoLogin.class);
            startActivity(intent);
        });

        btnEntrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionHelper.PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (!allGranted) {
                Toast.makeText(this, "Permissões necessárias não foram concedidas.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Todas as permissões foram concedidas!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
