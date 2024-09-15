package com.example.amigo_do_idoso_v31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/*import res.layout.*;*/

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Isso força a criação do banco de dados

        // Encontra o botão pelo ID
        Button btnEntrar = findViewById(R.id.btn_entrar);
        Button btnCriarConta = findViewById(R.id.btn_criar_conta);


        // Define o OnClickListener para o botão
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um Intent para iniciar a NovoLogin activity
                Intent intent = new Intent(MainActivity.this, NovoLogin.class);
                startActivity(intent);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um Intent para iniciar a NovoLogin activity
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });



    }

}