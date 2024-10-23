package com.example.amigo_do_idoso_v31;

import static com.example.amigo_do_idoso_v31.DatabaseHelper.COLUMN_CADASTRO_NOME;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapp.utils.PasswordUtils;

public class Login extends AppCompatActivity {

    private EditText edtTelefone;
    private EditText edtSenha;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Verificar se o usuário já está logado
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String telefone = sharedPreferences.getString("Telefone", null);
        String senha = sharedPreferences.getString("Senha", null);
        String nomeUsuario = sharedPreferences.getString("Nome", null);

        if (telefone != null && senha != null) {
            // Simular login automático
            Toast.makeText(Login.this, "Bem-vindo de volta, " + nomeUsuario + "!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Menu.class);
            startActivity(intent);
            finish();
            return;
        }

        edtTelefone = findViewById(R.id.txtbox_tel);
        edtSenha = findViewById(R.id.txtbox_senha);
        Button btnEntrar = findViewById(R.id.btn_entrar);

        dbHelper = new DatabaseHelper(this);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = edtTelefone.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();

                Log.d("LoginDebug", "Telefone digitado (após trim): " + telefone);
                Log.d("LoginDebug", "Senha digitada: " + senha);

                if (!telefone.isEmpty() && !senha.isEmpty()) {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    // Verificar os usuários no banco de dados antes da consulta (para depuração)
                    Cursor testCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_LOGIN, null);
                    if (testCursor.moveToFirst()) {
                        do {
                            String dbTelefone = testCursor.getString(testCursor.getColumnIndex(DatabaseHelper.COLUMN_LOGIN_TELEFONE));
                            String dbSenha = testCursor.getString(testCursor.getColumnIndex(DatabaseHelper.COLUMN_LOGIN_SENHA));
                            Log.d("LoginDebug", "Usuário no banco de dados - Telefone: " + dbTelefone + ", Senha Hash: " + dbSenha);
                        } while (testCursor.moveToNext());
                    }
                    testCursor.close();

                    // Execute a consulta original, mas sem o JOIN para garantir que o problema não está no JOIN
                    Cursor cursor = db.rawQuery(
                            "SELECT " + DatabaseHelper.COLUMN_LOGIN_SENHA +
                                    " FROM " + DatabaseHelper.TABLE_LOGIN +
                                    " WHERE " + DatabaseHelper.COLUMN_LOGIN_TELEFONE + " = ?",
                            new String[]{telefone}
                    );

                    // Verifique se a consulta retornou algum resultado
                    if (cursor.moveToFirst()) {
                        int senhaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOGIN_SENHA);

                        if (senhaIndex != -1) {
                            String storedHashedPassword = cursor.getString(senhaIndex);
                            Log.d("LoginDebug", "Senha armazenada no banco de dados: " + storedHashedPassword);

                            // Substitua pelo método correto para verificar a senha hash
                            if (PasswordUtils.verifyPassword(senha, storedHashedPassword)) {
                                Log.d("LoginDebug", "Login bem-sucedido!");

                                Toast.makeText(Login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Menu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.d("LoginDebug", "Senha incorreta");
                                Toast.makeText(Login.this, "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("LoginDebug", "Coluna de senha não encontrada");
                        }
                    } else {
                        Log.d("LoginDebug", "Usuário não encontrado");
                        Toast.makeText(Login.this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();
                    db.close();
                } else {
                    Log.d("LoginDebug", "Campos vazios");
                    Toast.makeText(Login.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}