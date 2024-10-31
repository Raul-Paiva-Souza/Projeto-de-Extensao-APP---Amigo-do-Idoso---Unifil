package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.chip.ChipGroup;

public class Anamnese extends AppCompatActivity {
    private Spinner listaDoencasCronicas, listaRemedioContinuo;
    private RadioGroup rgDoencaCronica, rgRemedioContinuo;
    private ChipGroup chipGroupDificuldades;
    private boolean dificuldadeFala = false;
    private boolean dificuldadeAudicao = false;
    private boolean dificuldadeVisao = false;
    private boolean dificuldadeLocomocao = false;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anamnese);

        // Inicializa o DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Inicializa os componentes
        listaDoencasCronicas = findViewById(R.id.lista_doencas_cronicas);
        listaRemedioContinuo = findViewById(R.id.lista_remedio_continuo);
        rgDoencaCronica = findViewById(R.id.radiogroup_doenca_cronica);
        rgRemedioContinuo = findViewById(R.id.radiogroup_remedio_continuo);
        chipGroupDificuldades = findViewById(R.id.chipgroup_dificuldades);

        // Configura os adapters dos spinners
        ArrayAdapter<CharSequence> adapterDoencas = ArrayAdapter.createFromResource(this,
                R.array.doencas_cronicas, android.R.layout.simple_spinner_item);
        adapterDoencas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaDoencasCronicas.setAdapter(adapterDoencas);

        ArrayAdapter<CharSequence> adapterMedicamentos = ArrayAdapter.createFromResource(this,
                R.array.medicamentos_continuos, android.R.layout.simple_spinner_item);
        adapterMedicamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaRemedioContinuo.setAdapter(adapterMedicamentos);

        // Configura os RadioGroups
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

        // Configura o ChipGroup
        chipGroupDificuldades.setOnCheckedChangeListener((group, checkedId) -> {
            dificuldadeFala = checkedId == R.id.chip_dificuldade_fala;
            dificuldadeAudicao = checkedId == R.id.chip_dificuldade_audicao;
            dificuldadeVisao = checkedId == R.id.chip_dificuldade_visao;
            dificuldadeLocomocao = checkedId == R.id.chip_dificuldade_locomocao;
        });

        // Configura o botão de salvar
        findViewById(R.id.btn_salvar_anamnese).setOnClickListener(v -> mostrarPopupConfirmacaoEdicao());

        // Mostrar pop-up de visualização ao entrar na tela
        mostrarPopupVisualizacao();
    }

    private void mostrarPopupVisualizacao() {
        String telefoneUsuario = obterTelefoneDoUsuario();
        Cursor cursor = dbHelper.obterAnamnese(telefoneUsuario);
        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder info = new StringBuilder();

            int doencasIndex = cursor.getColumnIndex("doencas_cronicas");
            int remedioIndex = cursor.getColumnIndex("medicamento_continuo");
            int dificuldadesIndex = cursor.getColumnIndex("dificuldades");

            if (doencasIndex != -1) {
                info.append("Doenças Crônicas: ").append(cursor.getString(doencasIndex)).append("\n");
            }
            if (remedioIndex != -1) {
                info.append("Remédio Contínuo: ").append(cursor.getString(remedioIndex)).append("\n");
            }
            if (dificuldadesIndex != -1) {
                info.append("Dificuldades: ").append(cursor.getString(dificuldadesIndex));
            }

            Log.d("PopupVisualizacao", "Dados do Popup: " + info.toString());

            new AlertDialog.Builder(this)
                    .setTitle("Dados da Anamnese")
                    .setMessage(info.toString())
                    .setPositiveButton("Voltar ao Menu", (dialog, which) -> voltarMenuOuLogin())
                    .setNegativeButton("Editar", (dialog, which) -> carregarDadosExistentes())
                    .show();
        } else {
            Log.d("PopupVisualizacao", "Cursor está vazio ou null");
        }
        if (cursor != null) cursor.close();
    }


    private void mostrarPopupConfirmacaoEdicao() {
        String doencaCronica = listaDoencasCronicas.getSelectedItem().toString();
        String remedioContinuo = listaRemedioContinuo.getSelectedItem().toString();
        String dificuldades = getDificuldadesSelecionadas();

        String dados = "Doenças Crônicas: " + doencaCronica + "\n" +
                "Remédio Contínuo: " + remedioContinuo + "\n" +
                "Dificuldades: " + dificuldades;

        new AlertDialog.Builder(this)
                .setTitle("Confirmar Edição")
                .setMessage("Revise os dados:\n\n" + dados)
                .setPositiveButton("Confirmar", (dialog, which) -> salvarAnamnese())
                .setNegativeButton("Corrigir", null)
                .show();
    }

    private void salvarAnamnese() {
        String doencaCronica = listaDoencasCronicas.getSelectedItem().toString();
        String remedioContinuo = listaRemedioContinuo.getSelectedItem().toString();
        String dificuldades = getDificuldadesSelecionadas();

        boolean sucesso = dbHelper.salvarAnamnese(doencaCronica, remedioContinuo, dificuldades);

        if (sucesso) {
            mostrarPopupSucesso("Anamnese salva com sucesso!", Menu.class);
        } else {
            Toast.makeText(this, "Erro ao salvar a anamnese. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarPopupSucesso(String mensagem, Class<?> activityDestino) {
        new AlertDialog.Builder(this)
                .setTitle("Sucesso")
                .setMessage(mensagem)
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(this, activityDestino);
                    startActivity(intent);
                }).show();
    }

    private void voltarMenuOuLogin() {
        Intent intent = new Intent(this, dbHelper.existeCadastro() ? Menu.class : NovoLogin.class);
        startActivity(intent);
    }

    private String obterTelefoneDoUsuario() {
        return dbHelper.obterTelefoneUsuarioLogado();
    }

    private String getDificuldadesSelecionadas() {
        StringBuilder dificuldades = new StringBuilder();
        if (dificuldadeFala) dificuldades.append("Fala, ");
        if (dificuldadeAudicao) dificuldades.append("Audição, ");
        if (dificuldadeVisao) dificuldades.append("Visão, ");
        if (dificuldadeLocomocao) dificuldades.append("Locomoção, ");
        if (dificuldades.length() > 0) dificuldades.setLength(dificuldades.length() - 2); // Remove a última vírgula e espaço
        return dificuldades.toString();
    }

    private void carregarDadosExistentes() {
        String telefoneUsuario = obterTelefoneDoUsuario();
        Cursor cursor = dbHelper.obterAnamnese(telefoneUsuario);
        if (cursor != null && cursor.moveToFirst()) {
            // Preencha os dados no layout de edição conforme necessário
            // Exemplo:
            int doencaCronicaIndex = cursor.getColumnIndex("doencas_cronicas");
            if (doencaCronicaIndex != -1) {
                listaDoencasCronicas.setSelection(getSpinnerPosition(listaDoencasCronicas, cursor.getString(doencaCronicaIndex)));
            }
            // Repita para outros campos
        }
        if (cursor != null) cursor.close();
    }

    private int getSpinnerPosition(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        return adapter.getPosition(value);
    }
}
