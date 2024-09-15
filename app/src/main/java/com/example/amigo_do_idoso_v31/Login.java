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

                Log.d("LoginDebug", "Telefone digitado: " + telefone);
                Log.d("LoginDebug", "Senha digitada: " + senha);

                if (!telefone.isEmpty() && !senha.isEmpty()) {
                    String hashedPassword = PasswordUtils.hashPassword(senha);
                    Log.d("LoginDebug", "Senha hashed: " + hashedPassword);

                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    // Consulta com JOIN para recuperar o nome do usuário da tabela Cadastro
                    Cursor cursor = db.rawQuery(
                            "SELECT c." + DatabaseHelper.COLUMN_CADASTRO_NOME +
                                    " FROM " + DatabaseHelper.TABLE_LOGIN + " l " +
                                    " JOIN " + DatabaseHelper.TABLE_CADASTRO + " c " +
                                    " ON l." + DatabaseHelper.COLUMN_LOGIN_TELEFONE + " = c." + DatabaseHelper.COLUMN_LOGIN_TELEFONE +
                                    " WHERE l." + DatabaseHelper.COLUMN_LOGIN_TELEFONE + "=? AND l." + DatabaseHelper.COLUMN_LOGIN_SENHA + "=?",
                            new String[]{telefone, hashedPassword}
                    );

                    if (cursor.moveToFirst()) {
                        // Verifique se o índice da coluna é válido
                        int nomeIndex = cursor.getColumnIndex(COLUMN_CADASTRO_NOME);
                        if (nomeIndex != -1) {
                            String nomeUsuario = cursor.getString(nomeIndex);
                            Log.d("LoginDebug", "Login bem-sucedido, nome do usuário: " + nomeUsuario);

                            // Salvar telefone, senha e nome no SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Telefone", telefone);
                            editor.putString("Senha", hashedPassword);
                            editor.putString("Nome", nomeUsuario);
                            editor.apply();

                            Toast.makeText(Login.this, "Bem-vindo de volta, " + nomeUsuario + "!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Menu.class);
                            startActivity(intent);
                        } else {
                            Log.d("LoginDebug", "A coluna nome não foi encontrada no cursor.");
                            Toast.makeText(Login.this, "Erro ao obter o nome do usuário.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("LoginDebug", "Credenciais inválidas");
                        Toast.makeText(Login.this, "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
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
