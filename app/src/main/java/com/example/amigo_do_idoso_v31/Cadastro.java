package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

    private String nome;
    private int idade;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();
        configurarSpinners();
        configurarRadioGroup();

        // Configura os botões e carrega os dados para edição ou visualização
        configurarBotoes();
    }

    // Exibe o pop-up se o cadastro existir, ao retomar a atividade
    @Override
    protected void onResume() {
        super.onResume();
        if (dbHelper.existeCadastro()) {
            Log.d("PopupVisualizacao", "Chamando mostrarPopupVisualizacao");
            mostrarPopupVisualizacao();
        }
    }

    private void inicializarComponentes() {
        dbHelper = new DatabaseHelper(this);
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
    }

    private void configurarSpinners() {
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

        ArrayAdapter<CharSequence> tipoRelacaoAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_relacao, android.R.layout.simple_spinner_item);
        tipoRelacaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaTipoRelacao.setAdapter(tipoRelacaoAdapter);
    }

    private void configurarRadioGroup() {
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
    }

    private void mostrarPopupVisualizacao() {
        String telefoneUsuario = obterTelefoneDoUsuario();
        Cursor cursor = dbHelper.obterCadastro(telefoneUsuario);
        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder info = new StringBuilder();
            info.append("Nome: ").append(cursor.getString(cursor.getColumnIndex("nome"))).append("\n");
            info.append("Idade: ").append(cursor.getInt(cursor.getColumnIndex("idade"))).append("\n");
            info.append("Endereço: ").append(cursor.getString(cursor.getColumnIndex("rua"))).append(", ")
                    .append(cursor.getInt(cursor.getColumnIndex("numero"))).append(", ")
                    .append(cursor.getString(cursor.getColumnIndex("bairro"))).append(", ")
                    .append(cursor.getString(cursor.getColumnIndex("cidade"))).append(", ")
                    .append(cursor.getString(cursor.getColumnIndex("estado")));
            Log.d("PopupVisualizacao", "Dados do Popup: " + info.toString()); // Verifique o conteúdo a ser exibido no popup
            new AlertDialog.Builder(this)
                    .setTitle("Dados do Cadastro")
                    .setMessage(info.toString())
                    .setPositiveButton("Voltar ao Menu", (dialog, which) -> voltarMenuOuLogin())
                    .setNegativeButton("Editar", (dialog, which) -> carregarDadosExistentes())
                    .show();
        } else {
            Log.d("PopupVisualizacao", "Cursor está vazio ou null"); // Adicione este log para casos em que o cursor não tem dados
        }
        if (cursor != null) cursor.close();
    }



    private void configurarBotoes() {
        btnSalvar.setOnClickListener(v -> mostrarPopupConfirmacaoEdicao());
        btnVoltarMenu.setOnClickListener(v -> voltarMenuOuLogin());
    }

    private void mostrarPopupConfirmacaoEdicao() {
        String nome = txtNome.getText().toString().trim();
        String idadeStr = txtIdade.getText().toString().trim();
        String rua = txtRua.getText().toString().trim();
        String numeroStr = txtNumero.getText().toString().trim();
        String bairro = txtBairro.getText().toString().trim();

        String dados = "Nome: " + nome + "\n" +
                "Idade: " + idadeStr + "\n" +
                "Endereço: " + rua + ", " + numeroStr + ", " + bairro;

        new AlertDialog.Builder(this)
                .setTitle("Confirmar Edição")
                .setMessage("Revise os dados:\n\n" + dados)
                .setPositiveButton("Confirmar", (dialog, which) -> salvarCadastro())
                .setNegativeButton("Corrigir", null)
                .show();
    }

    /**
     * Salva ou atualiza o cadastro no banco de dados.
     */
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
        Log.d("CadastroActivity", "Nome: " + nome + ", Idade: " + idade + ", Endereco ID: " + enderecoId);

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



    private void atualizarSpinnerCidades(String estadoSelecionado) {
        int arrayCidades = getResources().getIdentifier("cidades_" + estadoSelecionado.toLowerCase(), "array", getPackageName());
        ArrayAdapter<CharSequence> cidadeAdapter = ArrayAdapter.createFromResource(this,
                arrayCidades, android.R.layout.simple_spinner_item);
        cidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cidadeSpinner.setAdapter(cidadeAdapter);
    }

    private int getSpinnerPosition(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        return adapter.getPosition(value);
    }
    private String obterTelefoneDoUsuario() {
        String telefoneUsuario = dbHelper.obterTelefoneUsuarioLogado();
        Log.d("PopupVisualizacao", "Telefone do usuário: " + telefoneUsuario); // Verificar o telefone obtido
        return telefoneUsuario;
    }


    private void carregarDadosExistentes() {
        // Verificar se há um cadastro existente no banco de dados
        String telefoneUsuario = obterTelefoneDoUsuario();
        Cursor cursor = dbHelper.obterCadastro(telefoneUsuario);

        if (cursor != null && cursor.moveToFirst()) {
            txtNome.setText(cursor.getString(cursor.getColumnIndex("nome")));
            txtIdade.setText(cursor.getString(cursor.getColumnIndex("idade")));
            txtRua.setText(cursor.getString(cursor.getColumnIndex("rua")));
            txtNumero.setText(cursor.getString(cursor.getColumnIndex("numero")));
            txtBairro.setText(cursor.getString(cursor.getColumnIndex("bairro")));

            String estado = cursor.getString(cursor.getColumnIndex("estado"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            estadoSpinner.setSelection(getSpinnerPosition(estadoSpinner, estado));
            atualizarSpinnerCidades(estado);
            cidadeSpinner.setSelection(getSpinnerPosition(cidadeSpinner, cidade));
        }

        if (cursor != null) {
            cursor.close();
        }

    }
}

