package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

public class Cadastro extends AppCompatActivity {

    private Spinner estadoSpinner, cidadeSpinner, listaTipoRelacao;
    private DatabaseHelper dbHelper;

    private Button btnSalvar, btnVoltarMenu;
    private RadioGroup radioGroupMoraComAlguem;
    private EditText txtNomeDoAlguem, txtContatoDoAlguem, txtNome, txtIdade, txtRua, txtNumero, txtBairro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        dbHelper = new DatabaseHelper(this);

        // Inicializando os componentes da interface
        estadoSpinner = findViewById(R.id.escolhestado);
        cidadeSpinner = findViewById(R.id.escolhecidade);
        btnSalvar = findViewById(R.id.btn_salvar_cadastro);
        btnVoltarMenu = findViewById(R.id.btn_voltar_cadastro);
        radioGroupMoraComAlguem = findViewById(R.id.radiogroup_moracoalguem);
        txtNomeDoAlguem = findViewById(R.id.txtbox_nomedoalguem);
        txtContatoDoAlguem = findViewById(R.id.txtbox_contatodoalguem);
        listaTipoRelacao = findViewById(R.id.lista_tipo_relacao);
        txtNome = findViewById(R.id.txtbox_nome);
        txtIdade = findViewById(R.id.txt_box_idade);
        txtRua = findViewById(R.id.txtbox_rua);
        txtNumero = findViewById(R.id.txtbox_end_num);
        txtBairro = findViewById(R.id.txtbox_bairro);

        // Configurações dos spinners
        ArrayAdapter<CharSequence> estadoAdapter = ArrayAdapter.createFromResource(this,
                R.array.estados_brasil, android.R.layout.simple_spinner_item);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSpinner.setAdapter(estadoAdapter);

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estadoSelecionado = parent.getItemAtPosition(position).toString();
                atualizarSpinnerCidades(estadoSelecionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Configuração do RadioGroup para verificar se mora com alguém
        radioGroupMoraComAlguem.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.check_moracoalguem_sim) {
                txtNomeDoAlguem.setVisibility(View.VISIBLE);
                txtContatoDoAlguem.setVisibility(View.VISIBLE);
                listaTipoRelacao.setVisibility(View.VISIBLE);
            } else {
                txtNomeDoAlguem.setVisibility(View.GONE);
                txtContatoDoAlguem.setVisibility(View.GONE);
                listaTipoRelacao.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<CharSequence> tipoRelacaoAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_relacao, android.R.layout.simple_spinner_item);
        tipoRelacaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaTipoRelacao.setAdapter(tipoRelacaoAdapter);

        // Botão para salvar o cadastro
        btnSalvar.setOnClickListener(v -> salvarCadastro());

        // Botão para voltar ao menu ou login
        btnVoltarMenu.setOnClickListener(v -> voltarMenuOuLogin());
    }

    private void salvarCadastro() {
        String nome = txtNome.getText().toString().trim();
        String idadeStr = txtIdade.getText().toString().trim();
        String rua = txtRua.getText().toString().trim();
        String numeroStr = txtNumero.getText().toString().trim();
        String bairro = txtBairro.getText().toString().trim();

        if (nome.isEmpty() || idadeStr.isEmpty() || rua.isEmpty() || numeroStr.isEmpty() || bairro.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int idade = Integer.parseInt(idadeStr);
        long enderecoId = dbHelper.inserirEndereco(rua, Integer.parseInt(numeroStr), bairro, estadoSpinner.getSelectedItem().toString());

        // Verificar se o usuário informou que mora com alguém
        if (radioGroupMoraComAlguem.getCheckedRadioButtonId() == R.id.check_moracoalguem_sim) {
            String nomeDoAlguem = txtNomeDoAlguem.getText().toString().trim();
            String contatoDoAlguem = txtContatoDoAlguem.getText().toString().trim();

            if (!nomeDoAlguem.isEmpty() && !contatoDoAlguem.isEmpty()) {
                dbHelper.inserirSosContato(nomeDoAlguem, contatoDoAlguem);
            }
        }

        // Verificar se já existe cadastro
        if (dbHelper.existeCadastro()) {
            // Atualizar o cadastro existente
            dbHelper.salvarCadastro(nome, idade, enderecoId);
            mostrarPopupSucesso("Cadastro atualizado com sucesso!", Menu.class);
        } else {
            // Criar um novo cadastro
            dbHelper.salvarCadastro(nome, idade, enderecoId);
            mostrarPopupSucesso("Cadastro realizado com sucesso!", Anamnese.class);
        }
    }

    private void voltarMenuOuLogin() {
        // Verificar se existe cadastro e redirecionar para o Menu ou NovoLogin
        if (dbHelper.existeCadastro()) {
            // Ir para o Menu
            Intent intent = new Intent(Cadastro.this, Menu.class);
            startActivity(intent);
        } else {
            // Ir para o NovoLogin
            Intent intent = new Intent(Cadastro.this, NovoLogin.class);
            startActivity(intent);
        }
    }

    private void mostrarPopupSucesso(String mensagem, Class<?> activityDestino) {
        // Exibir um pop-up de confirmação
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sucesso");
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Redirecionar para a próxima atividade
            Intent intent = new Intent(Cadastro.this, activityDestino);
            startActivity(intent);
            finish(); // Fechar a atividade atual
        });
        builder.show();
    }

    private void atualizarSpinnerCidades(String estado) {
        int arrayId = getResources().getIdentifier("cidades_" + estado.toLowerCase().replace(" ", "_"), "array", getPackageName());
        if (arrayId != 0) {
            ArrayAdapter<CharSequence> cidadeAdapter = ArrayAdapter.createFromResource(this,
                    arrayId, android.R.layout.simple_spinner_item);
            cidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cidadeSpinner.setAdapter(cidadeAdapter);
        }
    }
}
