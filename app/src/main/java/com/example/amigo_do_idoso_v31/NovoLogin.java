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

        databaseHelper = new DatabaseHelper(this);
        // Encontra o botão pelo ID
        edtTelefone = findViewById(R.id.txtbox_tel);
        edtSenha = findViewById(R.id.txtbox_senha);
        EditText txtboxConfirmasenha = findViewById(R.id.txtbox_confirmasenha);
        Button btnCadastrar = findViewById(R.id.btn_cadastrar);
        Button btnSair = findViewById(R.id.btn_sair);

        // Define o OnClickListener para o botão de cadastro
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = edtTelefone.getText().toString();
                String senha = edtSenha.getText().toString();
                String confirmarSenha = txtboxConfirmasenha.getText().toString();

                if (senha.equals(confirmarSenha)) {
                    if (!databaseHelper.isTelefoneExists(telefone)) {
                        databaseHelper.addLogin(telefone, senha);
                        Toast.makeText(NovoLogin.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
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

        // Define o OnClickListener para o botão de sair
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha a atividade atual
            }
        });
    }
}
