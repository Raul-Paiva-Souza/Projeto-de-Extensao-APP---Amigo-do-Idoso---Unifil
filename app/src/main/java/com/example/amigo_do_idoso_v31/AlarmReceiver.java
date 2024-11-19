package com.example.amigo_do_idoso_v31;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class AlarmReceiver extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String mensagem = intent.getStringExtra("mensagem");

        // Exibir notificação
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "AGENDA_CHANNEL")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Lembrete da Agenda")
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());

        // Verificar permissão antes de tocar o alarme
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound);
                mediaPlayer.setOnCompletionListener(mp -> mp.release());
                mediaPlayer.start();
            } catch (Exception e) {
                Toast.makeText(context, "Erro ao reproduzir som de alarme", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Permissão para áudio/vibração não concedida", Toast.LENGTH_SHORT).show();
        }
    }
}
