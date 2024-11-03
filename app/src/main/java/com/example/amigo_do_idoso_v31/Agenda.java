package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class Agenda extends AppCompatActivity {

    private String dataSelecionada = "";
    private String horaSelecionada = "";
    private RadioGroup radioGroup;
    private RadioButton btnMedicamento, btnConsultaExame;
    private EditText recadoEditText;
    private Button btnSalvarAgenda;
    private Button btnAgendaSair;
    private DatabaseHelper databaseHelper;
    private AgendaAdapter adapter;

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "AgendaChannel";
            String description = "Canal para lembretes da agenda";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("AGENDA_CHANNEL", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        createNotificationChannel();

        databaseHelper = new DatabaseHelper(this);

        CalendarView calendarView = findViewById(R.id.calendarView);
        radioGroup = findViewById(R.id.radioGroup);
        btnMedicamento = findViewById(R.id.btn_medicamento);
        btnConsultaExame = findViewById(R.id.btn_consultaexame);
        recadoEditText = findViewById(R.id.recadoEditText);
        btnSalvarAgenda = findViewById(R.id.btn_salvar_agenda);
        btnAgendaSair = findViewById(R.id.btn_voltar_agenda_menu);

        // Inicializar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_agenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AgendaItem> agendaItems = databaseHelper.getAllAgendaItems();
        adapter = new AgendaAdapter(agendaItems);
        recyclerView.setAdapter(adapter);

        // Seleção de data e hora
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            TimePickerDialog timePickerDialog = new TimePickerDialog(Agenda.this, (view1, hourOfDay, minute) -> {
                horaSelecionada = String.format("%02d:%02d", hourOfDay, minute);
            }, 0, 0, true);
            timePickerDialog.show();
        });

        // Botão de salvar
        btnSalvarAgenda.setOnClickListener(v -> {
            String tipoAgendamento = "";
            if (btnMedicamento.isChecked()) {
                tipoAgendamento = "Medicamento";
            } else if (btnConsultaExame.isChecked()) {
                tipoAgendamento = "Consulta/Exame";
            }

            if (!dataSelecionada.isEmpty() && !horaSelecionada.isEmpty() && !tipoAgendamento.isEmpty()) {
                String recado = recadoEditText.getText().toString();
                AgendaItem novoItem = new AgendaItem(0, dataSelecionada, horaSelecionada, tipoAgendamento, recado, -1);

                databaseHelper.addAgendaItem(dataSelecionada, horaSelecionada, tipoAgendamento, recado, -1);
                setAlarm(novoItem);

                // Recarregar a lista e atualizar a interface
                List<AgendaItem> agendaItemsAtualizada = databaseHelper.getAllAgendaItems();
                adapter.updateAgendaItems(agendaItemsAtualizada);

                exibirAlerta(tipoAgendamento + " agendado com sucesso!");
            } else {
                exibirAlerta("Selecione todos os campos");
            }
        });

        btnAgendaSair.setOnClickListener(v -> startActivity(new Intent(Agenda.this, Menu.class)));
    }

    // Método para configurar o alarme
    private void setAlarm(AgendaItem item) {
        Calendar calendar = Calendar.getInstance();
        String[] dataParts = item.getData().split("/");
        String[] horaParts = item.getHorario().split(":");

        calendar.set(Calendar.YEAR, Integer.parseInt(dataParts[2]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dataParts[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataParts[0]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(horaParts[1]));
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("mensagem", item.getRecado());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, item.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void exibirAlerta(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de Agendamento")
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
