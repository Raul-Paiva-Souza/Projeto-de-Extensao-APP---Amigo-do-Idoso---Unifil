package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovoLogin extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText edtTelefone;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_login);

        // Inicializando o helper do banco de dados
        databaseHelper = new DatabaseHelper(this);

        // Vinculando componentes da interface com código Java
        edtTelefone = findViewById(R.id.txtbox_tel);
        edtSenha = findViewById(R.id.txtbox_senha);
        EditText txtboxConfirmasenha = findViewById(R.id.txtbox_confirmasenha);
        Button btnCadastrar = findViewById(R.id.btn_cadastrar);
        Button btnSair = findViewById(R.id.btn_sair);

        // Lógica do botão de cadastro
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = edtTelefone.getText().toString();
                String senha = edtSenha.getText().toString();
                String confirmarSenha = txtboxConfirmasenha.getText().toString();

                // Verificando se as senhas correspondem
                if (senha.equals(confirmarSenha)) {
                    // Verificando se o telefone já está cadastrado
                    if (!databaseHelper.isTelefoneExists(telefone)) {
                        // Adicionando o login ao banco de dados
                        databaseHelper.addLogin(telefone, senha);
                        Toast.makeText(NovoLogin.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Redireciona para a próxima tela de cadastro
                        Intent intent = new Intent(NovoLogin.this, Cadastro.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NovoLogin.this, "Telefone já cadastrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NovoLogin.this, "Senhas não coincidem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Lógica para o botão sair
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NovoLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
