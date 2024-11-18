package com.example.amigo_do_idoso_v31;

import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    public static final int PERMISSION_REQUEST_CODE = 100;

    public static boolean checkAndRequestPermissions(AppCompatActivity activity) {
        // Lista de permissões necessárias
        String[] permissions = {
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.SCHEDULE_EXACT_ALARM // Para exatidão de alarmes
        };

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
}