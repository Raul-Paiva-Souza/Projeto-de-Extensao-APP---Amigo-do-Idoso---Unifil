package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
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
import android.widget.ViewSwitcher;
import android.widget.AdapterView;

public class Cadastro extends AppCompatActivity {

    private Spinner estadoSpinner;
    private Spinner cidadeSpinner;
    private DatabaseHelper dbHelper;

    private Button btnAvancar, btnSalvar, btnVoltarOpcoes, btnVoltarNovoLogin;
    private ViewSwitcher viewSwitcher;

    // Inicializando os campos relacionados ao RadioGroup e EditText
    private RadioGroup radioGroupMoraComAlguem;
    private EditText txtNomeDoAlguem;
    private EditText txtContatoDoAlguem;
    private Spinner listaTipoRelacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializando os componentes da interface
        dbHelper = new DatabaseHelper(this);

        estadoSpinner = findViewById(R.id.escolhestado);
        cidadeSpinner = findViewById(R.id.escolhecidade);
        btnAvancar = findViewById(R.id.btn_avancar_CadastroparaAnamnese);
        btnSalvar = findViewById(R.id.btn_salvar_cadastro);
        btnVoltarOpcoes = findViewById(R.id.btn_voltar_cadastro_opcoes);
        btnVoltarNovoLogin = findViewById(R.id.btn_voltar_cadastro_novologin);
        viewSwitcher = findViewById(R.id.view_switcher_cadastro);

        // Inicializando os campos relacionados ao RadioGroup e EditText
        radioGroupMoraComAlguem = findViewById(R.id.radiogroup_moracoalguem);
        txtNomeDoAlguem = findViewById(R.id.txtbox_nomedoalguem);
        txtContatoDoAlguem = findViewById(R.id.txtbox_contatodoalguem);
        listaTipoRelacao = findViewById(R.id.lista_tipo_relacao);

        // Verificando o modo (novo cadastro ou edição) com base na Intent
        String modo = getIntent().getStringExtra("modo");

        if (modo != null && modo.equals("editarCadastro")) {
            viewSwitcher.setDisplayedChild(1);  // Mostra os botões de edição
        } else {
            viewSwitcher.setDisplayedChild(0);  // Mostra os botões de novo cadastro
        }

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
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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
        listaTipoRelacao.setSelection(0);

        // Botão para salvar cadastro
        btnSalvar.setOnClickListener(v -> salvarCadastro());

        // Botão para avançar para a Anamnese
        btnAvancar.setOnClickListener(v -> {
            salvarCadastro();  // Salva as informações antes de avançar
            Intent intent = new Intent(Cadastro.this, Anamnese.class);
            startActivity(intent);
        });

        // Botão para voltar para as Opções
        btnVoltarOpcoes.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, Menu.class);
            startActivity(intent);
        });

        // Botão para voltar para o Novo Login
        btnVoltarNovoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, NovoLogin.class);
            startActivity(intent);
        });
    }

    private void salvarCadastro() {
        // Obter os dados do formulário
        String nome = "";
        int idade = Integer.parseInt("");
        long enderecoId = Long.parseLong("");

        // Verificar se o usuário informou que mora com alguém e se os campos estão preenchidos
        if (radioGroupMoraComAlguem.getCheckedRadioButtonId() == R.id.check_moracoalguem_sim) {
            String nomeDoAlguem = txtNomeDoAlguem.getText().toString().trim();
            String contatoDoAlguem = txtContatoDoAlguem.getText().toString().trim();

            if (!nomeDoAlguem.isEmpty() && !contatoDoAlguem.isEmpty()) {
                // Inserir dados na tabela SOS
                dbHelper.inserirSosContato(nomeDoAlguem, contatoDoAlguem);
            }
        }

        // Salvar ou atualizar cadastro na tabela Cadastro
        dbHelper.salvarCadastro(nome, idade, enderecoId);

        // Exibir uma mensagem de sucesso ou erro
        Toast.makeText(this, "Cadastro salvo com sucesso!", Toast.LENGTH_SHORT).show();
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
