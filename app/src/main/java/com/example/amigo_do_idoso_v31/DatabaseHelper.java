package com.example.amigo_do_idoso_v31;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapp.utils.PasswordUtils;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Amigo_do_Idoso.db";
    public static final int DATABASE_VERSION = 3;

    // Tabelas
    public static final String TABLE_LOGIN = "Login";
    public static final String TABLE_USUARIO_IDOSO = "UsuarioIdoso";
    public static final String TABLE_ENDERECO = "Endereco";
    public static final String TABLE_CADASTRO = "Cadastro";
    public static final String TABLE_SOS = "SOS";
    public static final String TABLE_MEDICAMENTOS = "Medicamentos";
    public static final String TABLE_ANAMNESE = "Anamnese";
    public static final String TABLE_AGENDA = "Agenda";

    // Colunas da tabela Login
    public static final String COLUMN_LOGIN_SENHA = "senha";
    public static final String COLUMN_LOGIN_TELEFONE = "PK_Telefone";

    // Colunas da tabela UsuarioIdoso
    public static final String COLUMN_USUARIO_IDOSO_ID = "PK_UsuarioIdoso";
    public static final String COLUMN_USUARIO_IDOSO_TELEFONE = "FK_Telefone";
    public static final String COLUMN_USUARIO_IDOSO_NOME = "Nome";

    // Colunas da tabela Endereco
    public static final String COLUMN_ENDERECO_ID = "PK_idEndereco";
    public static final String COLUMN_ENDERECO_LOGRADOURO = "Logradouro";
    public static final String COLUMN_ENDERECO_NUMERO = "Numero";
    public static final String COLUMN_ENDERECO_CIDADE = "Cidade";
    public static final String COLUMN_ENDERECO_ESTADO = "Estado";
    public static final String COLUMN_ENDERECO_PAIS = "Pais";

    // Colunas da tabela Cadastro
    public static final String COLUMN_CADASTRO_ID = "PK_idCad";
    public static final String COLUMN_CADASTRO_NOME = "Nome";
    public static final String COLUMN_CADASTRO_IDADE = "Idade";
    public static final String COLUMN_CADASTRO_ENDERECO = "FK_idEndereco";

    // Colunas da tabela SOS
    public static final String COLUMN_SOS_ID = "PK_Contato";
    public static final String COLUMN_SOS_NOME_CONTATO = "NomeContato";
    public static final String COLUMN_SOS_TEL_CONTATO = "TelContato";

    // Colunas da tabela Medicamentos
    public static final String COLUMN_MEDICAMENTOS_ID = "PK_idMedicamentos";
    public static final String COLUMN_MEDICAMENTOS_NOME = "nome_med";
    public static final String COLUMN_MEDICAMENTOS_DOSAGEM = "dosagem";
    public static final String COLUMN_MEDICAMENTOS_MEDIDA_DOSAGEM = "tipo_dosagem";


    // Colunas da tabela Anamnese
    public static final String COLUMN_ANAMNESE_ID = "PK_Anamnese";
    public static final String COLUMN_ANAMNESE_DOENCAS = "doencas_cronicas";
    public static final String COLUMN_ANAMNESE_DIFICULDADE_LOCOMOCAO = "dificuldade_locomocao";
    public static final String COLUMN_ANAMNESE_DIFICULDADE_FALA = "dificuldade_fala";
    public static final String COLUMN_ANAMNESE_DIFICULDADE_VISAO = "dificuldade_visao";
    public static final String COLUMN_ANAMNESE_DIFICULDADE_AUDICAO = "dificuldade_audicao";
    public static final String COLUMN_ANAMNESE_MEDICAMENTO_CONTINUO = "medicamento_continuo";
    public static final String COLUMN_ANAMNESE_MEDICAMENTOS = "FK_idMedicamentos";

    // Colunas da tabela Agenda
    public static final String COLUMN_AGENDA_ID = "PK_idAlarme";
    public static final String COLUMN_AGENDA_DATA = "data";
    public static final String COLUMN_AGENDA_HORARIO = "horario";
    public static final String COLUMN_AGENDA_TOM_TOQUE = "tom_toque";
    public static final String COLUMN_AGENDA_RECADO = "recado";
    public static final String COLUMN_AGENDA_MEDICAMENTOS = "FK_idMedicamentos";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoginTable = "CREATE TABLE " + TABLE_LOGIN + " (" +
                COLUMN_LOGIN_TELEFONE + " TEXT PRIMARY KEY, " +
                COLUMN_LOGIN_SENHA + " STRING(128) NOT NULL)";

        String createUsuarioIdosoTable = "CREATE TABLE " + TABLE_USUARIO_IDOSO + " (" +
                COLUMN_USUARIO_IDOSO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_IDOSO_TELEFONE + " TEXT NOT NULL, " +
                COLUMN_USUARIO_IDOSO_NOME + " VARCHAR(45) NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_USUARIO_IDOSO_TELEFONE + ") REFERENCES " + TABLE_LOGIN + "(" + COLUMN_LOGIN_TELEFONE + "))";


        String createEnderecoTable = "CREATE TABLE " + TABLE_ENDERECO + " (" +
                COLUMN_ENDERECO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENDERECO_LOGRADOURO + " VARCHAR(45), " +
                COLUMN_ENDERECO_NUMERO + " INTEGER, " +
                COLUMN_ENDERECO_CIDADE + " VARCHAR(45), " +
                COLUMN_ENDERECO_ESTADO + " VARCHAR(45), " +
                COLUMN_ENDERECO_PAIS + " VARCHAR(45))";

        String createCadastroTable = "CREATE TABLE " + TABLE_CADASTRO + " (" +
                COLUMN_CADASTRO_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CADASTRO_NOME + " VARCHAR(45) NOT NULL, " +
                COLUMN_CADASTRO_IDADE + " INTEGER NOT NULL, " +
                COLUMN_CADASTRO_ENDERECO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_CADASTRO_ENDERECO + ") REFERENCES " + TABLE_ENDERECO + "(" + COLUMN_ENDERECO_ID + "))";

        String createSosTable = "CREATE TABLE " + TABLE_SOS + " (" +
                COLUMN_SOS_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_SOS_NOME_CONTATO + " VARCHAR(45), " +
                COLUMN_SOS_TEL_CONTATO + " INTEGER)";

        String createMedicamentosTable = "CREATE TABLE " + TABLE_MEDICAMENTOS + " (" +
                COLUMN_MEDICAMENTOS_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_MEDICAMENTOS_NOME + " VARCHAR(45), " +
                COLUMN_MEDICAMENTOS_DOSAGEM + " VARCHAR(45), " +
                COLUMN_MEDICAMENTOS_MEDIDA_DOSAGEM + " VARCHAR(45)) ";

        String createAnamneseTable = "CREATE TABLE " + TABLE_ANAMNESE + " (" +
                COLUMN_ANAMNESE_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANAMNESE_DOENCAS + " TEXT, " +
                COLUMN_ANAMNESE_MEDICAMENTO_CONTINUO + " BOOLEAN, " +
                COLUMN_ANAMNESE_MEDICAMENTOS + " TEXT, " +
                COLUMN_ANAMNESE_DIFICULDADE_LOCOMOCAO + " INTEGER, " +
                COLUMN_ANAMNESE_DIFICULDADE_FALA + " INTEGER, " +
                COLUMN_ANAMNESE_DIFICULDADE_AUDICAO + " INTEGER, " +
                COLUMN_ANAMNESE_DIFICULDADE_VISAO + " INTEGER)";

        String createAgendaTable = "CREATE TABLE " + TABLE_AGENDA + " (" +
                COLUMN_AGENDA_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_AGENDA_DATA + " DATE, " +
                COLUMN_AGENDA_HORARIO + " TIME, " +
                COLUMN_AGENDA_TOM_TOQUE + " VARCHAR(45), " +
                COLUMN_AGENDA_RECADO + " VARCHAR(255), " +
                COLUMN_AGENDA_MEDICAMENTOS + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_AGENDA_MEDICAMENTOS + ") REFERENCES " + TABLE_MEDICAMENTOS + "(" + COLUMN_MEDICAMENTOS_ID + "))";

        // Executa os comandos SQL para criar as tabelas
        db.execSQL(createLoginTable);
        db.execSQL(createUsuarioIdosoTable);
        db.execSQL(createEnderecoTable);
        db.execSQL(createCadastroTable);
        db.execSQL(createSosTable);
        db.execSQL(createMedicamentosTable);
        db.execSQL(createAnamneseTable);
        db.execSQL(createAgendaTable);

        // Inserir os três primeiros contatos fixos na tabela SOS
        db.execSQL("INSERT INTO " + TABLE_SOS + " VALUES (1, 'Polícia', 190)");
        db.execSQL("INSERT INTO " + TABLE_SOS + " VALUES (2, 'Bombeiros', 193)");
        db.execSQL("INSERT INTO " + TABLE_SOS + " VALUES (3, 'SAMU', 192)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Exclui as tabelas antigas se existirem
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENDA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANAMNESE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CADASTRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENDERECO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO_IDOSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Cria as tabelas novamente
        onCreate(db);
    }


    // Método para verificar se o telefone já existe
    public boolean isTelefoneExists(String telefone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_LOGIN_TELEFONE + "=?", new String[]{telefone});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    // Método para adicionar um novo login
    public void addLogin(String telefone, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN_TELEFONE, telefone);
        values.put(COLUMN_LOGIN_SENHA, PasswordUtils.hashPassword(senha));
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    // Método para verificar se o login existe
    public boolean checkLogin(String telefone, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_LOGIN_TELEFONE};
        String selection = COLUMN_LOGIN_TELEFONE + " = ? AND " + COLUMN_LOGIN_SENHA + " = ?";
        String[] selectionArgs = {telefone, PasswordUtils.hashPassword(senha)};
        Cursor cursor = db.query(TABLE_LOGIN, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }


    // Método para adicionar uma consulta/exame
    public void addConsultaExame(Consultasexames consultaExame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descricao_consultasexame", consultaExame.getDescricao_consultaexame());
        values.put("dataexame", consultaExame.getDataexame().toString());
        db.insert("ConsultasExames", null, values);
        db.close();
    }

    // Método para remover uma consulta/exame pelo ID
    public void removeConsultaExame(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ConsultasExames", "idConsultaexame = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para inserir dados na tabela Cadastro
    public long inserirCadastro(String nome, int idade, long enderecoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUMN_CADASTRO_NOME, nome);
        valores.put(COLUMN_CADASTRO_IDADE, idade);
        valores.put(COLUMN_CADASTRO_ENDERECO, enderecoId);
        return db.insert(TABLE_CADASTRO, null, valores);
    }

    // Método para inserir dados na tabela Endereco
    public long inserirEndereco(String logradouro, int numero, String cidade, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUMN_ENDERECO_LOGRADOURO, logradouro);
        valores.put(COLUMN_ENDERECO_NUMERO, numero);
        valores.put(COLUMN_ENDERECO_CIDADE, cidade);
        valores.put(COLUMN_ENDERECO_ESTADO, estado);
        return db.insert(TABLE_ENDERECO, null, valores);
    }

    // Método para verificar se já existe cadastro
    public boolean existeCadastro() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CADASTRO, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Método para salvar cadastro
    public void salvarCadastro(String nome, int idade, long enderecoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CADASTRO_NOME, nome);
        values.put(COLUMN_CADASTRO_IDADE, idade);
        values.put(COLUMN_CADASTRO_ENDERECO, enderecoId);
        db.insert(TABLE_CADASTRO, null, values);
        db.close();
    }

    // Método para inserir contato na tabela SOS
    public void inserirSosContato(String nome, String contato) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOS_NOME_CONTATO, nome);
        values.put(COLUMN_SOS_TEL_CONTATO, contato);
        db.insert(TABLE_SOS, null, values);
        db.close();
    }

    public void addMedicamento(Medicamento medicamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEDICAMENTOS_NOME, medicamento.getNome());
        values.put(COLUMN_MEDICAMENTOS_DOSAGEM, medicamento.getDosagem());
        values.put(COLUMN_MEDICAMENTOS_MEDIDA_DOSAGEM, "mg");

        db.insert(TABLE_MEDICAMENTOS, null, values);
        db.close();
    }

    public void removeMedicamento(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICAMENTOS, COLUMN_MEDICAMENTOS_ID + "=?", new String[]{String.valueOf(id)});
        db.close();


    }

    public String getContatoDeConfiança() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SOS,
                new String[]{COLUMN_SOS_TEL_CONTATO},
                COLUMN_SOS_ID + " = ?",
                new String[]{"4"}, // ID do contato de confiança
                null, null, null);

        String numero = ""; // Inicializa a variável com uma string vazia

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(COLUMN_SOS_TEL_CONTATO);
                    if (columnIndex != -1) {
                        numero = cursor.getString(columnIndex);
                    } else {
                        // Se o índice da coluna for inválido, você pode logar ou tratar o erro
                        Log.e("DatabaseHelper", "Coluna " + COLUMN_SOS_TEL_CONTATO + " não encontrada.");
                    }
                }
            } finally {
                cursor.close(); // Assegure-se de fechar o cursor
            }
        }

        return numero;
    }

}

