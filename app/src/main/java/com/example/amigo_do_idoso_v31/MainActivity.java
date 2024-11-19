package com.example.amigo_do_idoso_v31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Solicitar permissões
        if (!PermissionHelper.checkAndRequestPermissions(this)) {
            Toast.makeText(this, "Por favor, conceda todas as permissões para usar o aplicativo.", Toast.LENGTH_LONG).show();
        }

        // Solicitar permissão de notificação se necessário
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33)
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
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

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de notificações concedida.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão de notificações não foi concedida.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
