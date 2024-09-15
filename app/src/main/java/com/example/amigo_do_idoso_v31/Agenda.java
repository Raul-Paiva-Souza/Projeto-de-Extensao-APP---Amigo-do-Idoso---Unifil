package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Agenda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Exibir o TimePickerDialog quando uma data for selecionada
                TimePickerDialog timePickerDialog = new TimePickerDialog(Agenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Ação após selecionar a hora
                        Toast.makeText(Agenda.this, "Hora selecionada: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

    }
}
