package com.example.amigo_do_idoso_v31;

import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;

public class PermissionHelper {

    public static final int PERMISSION_REQUEST_CODE = 100;

    public static boolean checkAndRequestPermissions(AppCompatActivity activity) {
        // Lista de permissões necessárias
        String[] permissions = {
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.SCHEDULE_EXACT_ALARM
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.VIBRATE,
                    android.Manifest.permission.SCHEDULE_EXACT_ALARM,
                    android.Manifest.permission.POST_NOTIFICATIONS
            };
        }

        // Lista de permissões a solicitar
        boolean hasPermission = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            }
        }

        if (!hasPermission) {
            // Solicitar permissões
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    // A permissão não foi concedida, informe o usuário
                    // Nota: Use um mecanismo adequado para informar ao usuário sobre permissões não concedidas
                }
            }
        }
    }
}
