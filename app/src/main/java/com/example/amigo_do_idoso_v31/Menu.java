package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Menu extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this); // Inicializa o DatabaseHelper

        TextView saudacaoTextView = findViewById(R.id.text_saudacao);

        // Obter o telefone do SharedPreferences (presumindo que foi salvo após o login)
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String telefoneUsuario = sharedPreferences.getString("Telefone", null);

        // Verificar se o telefone do usuário está disponível
        if (telefoneUsuario != null) {
            // Buscar o nome do usuário no banco de dados
            String nomeUsuario = dbHelper.getNomeUsuario(telefoneUsuario);

            // Obter a saudação baseada na hora do sistema e no nome do usuário
            if (nomeUsuario != null) {
                String saudacao = obterSaudacaoComNome(nomeUsuario);
                saudacaoTextView.setText(saudacao);
            } else {
                saudacaoTextView.setText("Olá! Bem-vindo!");
            }
        } else {
            saudacaoTextView.setText("Olá! Bem-vindo!");
        }

        // Configuração dos botões e outros componentes do menu
        Button btnCadastro = findViewById(R.id.btn_menu_cadastro);
        Button btnAnamnese = findViewById(R.id.btn_menu_anamenese);
        Button btnConsultasexames = findViewById(R.id.btn_menu_consultasexames);
        Button btnEmergencia = findViewById(R.id.btn_menu_sos);
        Button btnAgenda = findViewById(R.id.btn_menu_agenda);
        Button btnMedicamentos = findViewById(R.id.btn_menu_medicamentos);
        Button btnSair = findViewById(R.id.btn_menu_sair);

        btnCadastro.setOnClickListener(v -> startActivity(new Intent(Menu.this, Cadastro.class)));

        btnAnamnese.setOnClickListener(v -> startActivity(new Intent(Menu.this, Anamnese.class)));

        btnConsultasexames.setOnClickListener(v -> startActivity(new Intent(Menu.this, Consultasexames.class)));

        btnEmergencia.setOnClickListener(v -> startActivity(new Intent(Menu.this, SOS.class)));

        btnAgenda.setOnClickListener(v -> startActivity(new Intent(Menu.this, Agenda.class)));

        btnMedicamentos.setOnClickListener(v -> startActivity(new Intent(Menu.this, Medicamentos.class)));

        btnSair.setOnClickListener(v -> startActivity(new Intent(Menu.this, MainActivity.class)));
    }

    private String obterSaudacaoComNome(String nome) {
        // Obter a hora atual
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);

        // Determinar se é Bom dia, Boa tarde ou Boa noite
        String saudacao;
        if (hora >= 0 && hora < 12) {
            saudacao = "Bom dia";
        } else if (hora >= 12 && hora < 18) {
            saudacao = "Boa tarde";
        } else {
            saudacao = "Boa noite";
        }

        // Obter a data atual
        Date dataAtual = new Date();
        SimpleDateFormat formatoData = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String dataFormatada = formatoData.format(dataAtual);

        // Retornar a saudação completa
        return "Olá, " + nome + "! " + saudacao + ". Hoje é " + dataFormatada;
    }
}
