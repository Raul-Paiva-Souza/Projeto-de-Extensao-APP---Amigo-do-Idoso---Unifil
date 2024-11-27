package com.example.amigo_do_idoso_v31;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import com.example.myapp.utils.PasswordUtils;
//import com.google.type.DateTime;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Amigo_do_Idoso.db";
    public static final int DATABASE_VERSION = 5;

    // Tabelas
    public static final String TABLE_LOGIN = "Login";
    public static final String TABLE_USUARIO_IDOSO = "UsuarioIdoso";
    public static final String TABLE_ENDERECO = "Endereco";
    public static final String TABLE_CADASTRO = "Cadastro";
    public static final String TABLE_SOS = "SOS";
    public static final String TABLE_MEDICAMENTOS = "Medicamentos";
    public static final String TABLE_CONSULTAS_EXAMES = "ConsultasExames";
    public static final String TABLE_ANAMNESE = "Anamnese";
    public static final String TABLE_AGENDA = "Agenda";
    public static final String TABLE_ESTADOS = "Estados";
    public static final String TABLE_CIDADES = "Cidades";


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
    private static final String COLUMN_ENDERECO_BAIRRO = "Bairro";
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


    // Colunas da tabela ConsultasExames
    public static final String COLUMN_CONSULTAS_EXAMES_ID = "idConsultaExame";
    public static final String COLUMN_CONSULTAS_EXAMES_DESCRICAO = "descricaoConsultaExame";
    public static final String COLUMN_CONSULTAS_EXAMES_DATA = "dataConsultaExame";

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
                COLUMN_ENDERECO_BAIRRO + " VARCHAR(45), " +
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

        String createConsultasExamesTable = "CREATE TABLE " + TABLE_CONSULTAS_EXAMES + " (" +
                COLUMN_CONSULTAS_EXAMES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONSULTAS_EXAMES_DESCRICAO + " TEXT NOT NULL, " +
                COLUMN_CONSULTAS_EXAMES_DATA + " DATE NOT NULL)";



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
        db.execSQL(createConsultasExamesTable);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSULTAS_EXAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CADASTRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENDERECO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO_IDOSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTADOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CIDADES);

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
    public void addConsultaExame(ConsultaExame consultaExame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONSULTAS_EXAMES_DESCRICAO, consultaExame.getDescricao());

        // Supondo que `consultaExame.getDataExame()` retorna um objeto Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dataStr = sdf.format(consultaExame.getDataExame()); // Formata a data diretamente

        values.put(COLUMN_CONSULTAS_EXAMES_DATA, dataStr);
        long result = db.insert(TABLE_CONSULTAS_EXAMES, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Erro ao inserir consulta/exame");
        } else {
            Log.i("DatabaseHelper", "Consulta/exame inserido com sucesso");
        }
        db.close();
    }





    public void removeConsultaExame(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONSULTAS_EXAMES, COLUMN_CONSULTAS_EXAMES_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<ConsultaExame> getAllConsultasExames() {
        List<ConsultaExame> consultasExamesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONSULTAS_EXAMES, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_CONSULTAS_EXAMES_ID);
                int descricaoIndex = cursor.getColumnIndex(COLUMN_CONSULTAS_EXAMES_DESCRICAO);
                int dataIndex = cursor.getColumnIndex(COLUMN_CONSULTAS_EXAMES_DATA);

                if (idIndex != -1 && descricaoIndex != -1 && dataIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String descricao = cursor.getString(descricaoIndex);
                    String dataStr = cursor.getString(dataIndex);

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date date = sdf.parse(dataStr); // Formatar a data corretamente

                        // Criar um objeto Date
                        ConsultaExame consultaExame = new ConsultaExame(id, descricao, date);
                        consultasExamesList.add(consultaExame);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return consultasExamesList;
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
        //return db.insert(TABLE_ENDERECO, null, valores);
        long enderecoId = db.insert(TABLE_ENDERECO, null, valores);
        Log.d("Database", "Endereco ID: " + enderecoId);  // Adicionando log
        return enderecoId;
    }

    // Método para verificar se já existe cadastro
    public boolean existeCadastro() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CADASTRO, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        Log.d("DatabaseHelper", "Existe cadastro: " + existe); // Adicione este log para verificar
        return existe;
    }


    // Método para salvar cadastro
    public void salvarCadastro(String nome, int idade, long enderecoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CADASTRO_NOME, nome);
        values.put(COLUMN_CADASTRO_IDADE, idade);
        values.put(COLUMN_CADASTRO_ENDERECO, enderecoId);
        Log.d("Database", "Salvando cadastro: Nome=" + nome + ", Idade=" + idade + ", EnderecoID=" +
                db.insert(TABLE_CADASTRO, null, values));
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

    public List<Medicamento> getAllMedicamentos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDICAMENTOS, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_MEDICAMENTOS_ID);
                int nomeIndex = cursor.getColumnIndex(COLUMN_MEDICAMENTOS_NOME);
                int dosagemIndex = cursor.getColumnIndex(COLUMN_MEDICAMENTOS_DOSAGEM);

                if (idIndex != -1 && nomeIndex != -1 && dosagemIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String nome = cursor.getString(nomeIndex);
                    double dosagem = cursor.getDouble(dosagemIndex);

                    Medicamento medicamento = new Medicamento(id, nome, dosagem);
                    medicamentos.add(medicamento);
                } else {
                    Log.e("DatabaseHelper", "Coluna não encontrada no cursor.");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return medicamentos;
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

    // Método para obter os dados de anamnese
    public Cursor obterAnamnese(String telefone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ANAMNESE + " WHERE " + COLUMN_ANAMNESE_ID + " = ?";
        Log.d("DatabaseHelper", "Query: " + query);
        return db.rawQuery(query, new String[]{telefone});
    }

    // Método para salvar ou atualizar os dados de anamnese
    public boolean salvarAnamnese(String doencaCronica, String remedioContinuo, String dificuldades) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANAMNESE_DOENCAS, doencaCronica);
        values.put(COLUMN_ANAMNESE_MEDICAMENTO_CONTINUO, remedioContinuo);
        values.put(COLUMN_ANAMNESE_DIFICULDADE_LOCOMOCAO, dificuldades.contains("Locomoção") ? 1 : 0);
        values.put(COLUMN_ANAMNESE_DIFICULDADE_FALA, dificuldades.contains("Fala") ? 1 : 0);
        values.put(COLUMN_ANAMNESE_DIFICULDADE_AUDICAO, dificuldades.contains("Audição") ? 1 : 0);
        values.put(COLUMN_ANAMNESE_DIFICULDADE_VISAO, dificuldades.contains("Visão") ? 1 : 0);

        // Verificar se já existe um registro para o usuário
        Cursor cursor = obterAnamnese(obterTelefoneUsuarioLogado());
        boolean existeRegistro = cursor != null && cursor.moveToFirst();
        long result;

        if (existeRegistro) {
            result = db.update(TABLE_ANAMNESE, values, COLUMN_ANAMNESE_ID + " = ?", new String[]{obterTelefoneUsuarioLogado()});
        } else {
            values.put(COLUMN_ANAMNESE_ID, obterTelefoneUsuarioLogado());
            result = db.insert(TABLE_ANAMNESE, null, values);
        }
        cursor.close();
        return result != -1;
    }
    public String obterTelefoneUsuarioLogado() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOGIN,
                new String[]{COLUMN_LOGIN_TELEFONE},
                null, null, null, null, null);

        String telefoneUsuario = null;
        if (cursor != null && cursor.moveToFirst()) {
            int telefoneIndex = cursor.getColumnIndex(COLUMN_LOGIN_TELEFONE);
            if (telefoneIndex != -1) {  // Verifica se o índice existe
                telefoneUsuario = cursor.getString(telefoneIndex);
            } else {
                Log.e("DatabaseHelper", "Coluna " + COLUMN_LOGIN_TELEFONE + " não encontrada.");
            }
            cursor.close();
        }

        return telefoneUsuario;
    }



    // Método para buscar o nome do usuário a partir do telefone
    public String getNomeUsuario(String telefone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nomeUsuario = null;

        // Query para buscar o nome baseado no telefone
        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_CADASTRO_NOME + " FROM " + TABLE_CADASTRO + " c " +
                        " JOIN " + TABLE_LOGIN + " l ON l." + COLUMN_LOGIN_TELEFONE + " = c." + COLUMN_CADASTRO_ID + // Ajuste o JOIN conforme necessário
                        " WHERE l." + COLUMN_LOGIN_TELEFONE + " = ?", new String[]{telefone}
        );

        // Verifique se encontrou algum resultado
        if (cursor.moveToFirst()) {
            int nomeIndex = cursor.getColumnIndex(COLUMN_CADASTRO_NOME);
            if (nomeIndex != -1) {
                nomeUsuario = cursor.getString(nomeIndex);
            } else {
                Log.e("DatabaseHelper", "Coluna " + COLUMN_CADASTRO_NOME + " não encontrada no cursor.");
            }
        }


        cursor.close();
        return nomeUsuario; // Retorna o nome do usuário ou null se não encontrado
    }

    // Método para obter o cadastro completo do usuário com base no telefone
    public Cursor obterCadastro(String telefone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c." + COLUMN_CADASTRO_NOME + " AS nome, " +
                "c." + COLUMN_CADASTRO_IDADE + " AS idade, " +
                "e." + COLUMN_ENDERECO_LOGRADOURO + " AS rua, " +
                "e." + COLUMN_ENDERECO_NUMERO + " AS numero, " +
                "e." + COLUMN_ENDERECO_BAIRRO + " AS bairro, " +
                "e." + COLUMN_ENDERECO_CIDADE + " AS cidade, " +
                "e." + COLUMN_ENDERECO_ESTADO + " AS estado " +
                "FROM " + TABLE_CADASTRO + " c " +
                "JOIN " + TABLE_ENDERECO + " e ON c." + COLUMN_CADASTRO_ENDERECO + " = e." + COLUMN_ENDERECO_ID +
                " JOIN " + TABLE_LOGIN + " l ON l." + COLUMN_LOGIN_TELEFONE + " = l." + COLUMN_LOGIN_TELEFONE +  // corrigindo a junção com login
                " WHERE l." + COLUMN_LOGIN_TELEFONE + " = ?";
        Log.d("DatabaseHelper", "Query: " + query);
        Cursor cursor = db.rawQuery(query, new String[]{telefone});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int nomeIndex = cursor.getColumnIndex("nome");
                int idadeIndex = cursor.getColumnIndex("idade");
                int ruaIndex = cursor.getColumnIndex("rua");
                int numeroIndex = cursor.getColumnIndex("numero");
                int bairroIndex = cursor.getColumnIndex("bairro");
                int cidadeIndex = cursor.getColumnIndex("cidade");
                int estadoIndex = cursor.getColumnIndex("estado");

                if (nomeIndex != -1 && idadeIndex != -1 && ruaIndex != -1 && numeroIndex != -1 &&
                        bairroIndex != -1 && cidadeIndex != -1 && estadoIndex != -1) {
                    Log.d("DatabaseHelper", "Nome: " + cursor.getString(nomeIndex) +
                            ", Idade: " + cursor.getInt(idadeIndex) +
                            ", Rua: " + cursor.getString(ruaIndex) +
                            ", Numero: " + cursor.getInt(numeroIndex) +
                            ", Bairro: " + cursor.getString(bairroIndex) +
                            ", Cidade: " + cursor.getString(cidadeIndex) +
                            ", Estado: " + cursor.getString(estadoIndex));
                } else {
                    Log.e("DatabaseHelper", "Coluna não encontrada no cursor.");
                }
            } while (cursor.moveToNext());
        } else {
            Log.e("DatabaseHelper", "Cursor vazio ou null.");
        }

        return cursor;
    }
    public void addAgendaItem(String data, String horario, String tipo, String recado, int medicamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AGENDA_DATA, data);
        values.put(COLUMN_AGENDA_HORARIO, horario);
        values.put(COLUMN_AGENDA_TOM_TOQUE, tipo);
        values.put(COLUMN_AGENDA_RECADO, recado);
        values.put(COLUMN_AGENDA_MEDICAMENTOS, medicamentoId);

        long result = db.insert(TABLE_AGENDA, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Erro ao inserir item na agenda");
        } else {
            Log.i("DatabaseHelper", "Item da agenda inserido com sucesso");
        }

        db.insert(TABLE_AGENDA, null, values);

        db.close();
    }

    public List<AgendaItem> getAllAgendaItems() {
        List<AgendaItem> agendaItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_AGENDA, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGENDA_ID));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGENDA_DATA));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGENDA_HORARIO));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGENDA_TOM_TOQUE));
                String recado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGENDA_RECADO));
                int medicamentoId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGENDA_MEDICAMENTOS));

                AgendaItem agendaItem = new AgendaItem(id, data, horario, tipo, recado, medicamentoId);
                agendaItems.add(agendaItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return agendaItems;
    }
}

