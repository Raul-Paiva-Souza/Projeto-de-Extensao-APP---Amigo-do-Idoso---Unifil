package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.google.android.material.chip.ChipGroup;

public class Anamnese extends AppCompatActivity {

    private Spinner listaDoencasCronicas, listaRemedioContinuo;
    private RadioGroup rgDoencaCronica, rgRemedioContinuo;
    private ChipGroup chipGroupDificuldades;
    private boolean dificuldadeFala = false;
    private boolean dificuldadeAudicao = false;
    private boolean dificuldadeVisao = false;
    private boolean dificuldadeLocomocao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anamnese);

        listaDoencasCronicas = findViewById(R.id.lista_doencas_cronicas);
        listaRemedioContinuo = findViewById(R.id.lista_remedio_continuo);
        rgDoencaCronica = findViewById(R.id.radiogroup_doenca_cronica);
        rgRemedioContinuo = findViewById(R.id.radiogroup_remedio_continuo);

        rgDoencaCronica.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.btn_doenca_cronica_sim) {
                listaDoencasCronicas.setVisibility(View.VISIBLE);
            } else {
                listaDoencasCronicas.setVisibility(View.GONE);
            }
        });

        rgRemedioContinuo.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.btn_remedio_continuo_sim) {
                listaRemedioContinuo.setVisibility(View.VISIBLE);
            } else {
                listaRemedioContinuo.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<CharSequence> adapterDoencas = ArrayAdapter.createFromResource(this,
                R.array.doencas_cronicas, android.R.layout.simple_spinner_item);
        adapterDoencas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaDoencasCronicas.setAdapter(adapterDoencas);

        ArrayAdapter<CharSequence> adapterMedicamentos = ArrayAdapter.createFromResource(this,
                R.array.medicamentos_continuos, android.R.layout.simple_spinner_item);
        adapterMedicamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaRemedioContinuo.setAdapter(adapterMedicamentos);

        chipGroupDificuldades = findViewById(R.id.chipgroup_dificuldades);
        chipGroupDificuldades.setOnCheckedChangeListener((group, checkedId) -> {
            dificuldadeFala = false;
            dificuldadeAudicao = false;
            dificuldadeVisao = false;
            dificuldadeLocomocao = false;

            if (checkedId == R.id.chip_dificuldade_fala) {
                dificuldadeFala = true;
            } else if (checkedId == R.id.chip_dificuldade_audicao) {
                dificuldadeAudicao = true;
            } else if (checkedId == R.id.chip_dificuldade_visao) {
                dificuldadeVisao = true;
            } else if (checkedId == R.id.chip_dificuldade_locomocao) {
                dificuldadeLocomocao = true;
            }
        });

        // Set a listener for the save button
        findViewById(R.id.btn_salvar_anamnese).setOnClickListener(v -> {
            Intent intent = new Intent(Anamnese.this, Menu.class);
            startActivity(intent);
        });
    }
}
