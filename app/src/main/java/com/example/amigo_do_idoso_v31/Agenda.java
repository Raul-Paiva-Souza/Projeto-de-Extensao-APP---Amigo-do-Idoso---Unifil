package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.view.View;
import android.widget.Button;

public class Agenda extends AppCompatActivity {

    private String dataSelecionada = "";
    private String horaSelecionada = "";
    private RadioGroup radioGroup;
    private RadioButton btnMedicamento, btnConsultaExame;

    private Button btnSalvarAgenda;
    private Button btnAgendaSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        CalendarView calendarView = findViewById(R.id.calendarView);
        radioGroup = findViewById(R.id.radioGroup);
        btnMedicamento = findViewById(R.id.btn_medicamento);
        btnConsultaExame = findViewById(R.id.btn_consultaexame);
        btnSalvarAgenda = findViewById(R.id.btn_salvar_agenda);
        btnAgendaSair = findViewById(R.id.btn_voltar_agenda_menu);


        // Seleção de data
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            // Exibir o TimePickerDialog quando uma data for selecionada
            TimePickerDialog timePickerDialog = new TimePickerDialog(Agenda.this, (view1, hourOfDay, minute) -> {
                // Formatar a hora selecionada
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
                exibirAlerta(tipoAgendamento);
            } else {
                // Mostra mensagem se algo não foi selecionado
                exibirAlerta("Selecione todos os campos");
            }
        });
        btnAgendaSair.setOnClickListener(v -> startActivity(new Intent(Agenda.this, Medicamentos.class)));




    }

    // Método para exibir o alerta de confirmação
    private void exibirAlerta(String tipoAgendamento) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de Agendamento")
                .setMessage(tipoAgendamento + " agendado para " + dataSelecionada + " às " + horaSelecionada + " com sucesso!")
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
