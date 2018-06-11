package com.example.ntx.educa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nTx on 30/05/2018.
 */

// This class is for database use. Reference https://developer.android.com/training/basics/data-storage/databases.html
public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_OBJETOS =
            "CREATE TABLE " + Contract.ContractObjetos.TABLE_NAME + " (" +
                    Contract.ContractObjetos._ID + " INTEGER PRIMARY KEY," +
                    Contract.ContractObjetos.COLUMN_NIVEL + " INTEGER, " +
                    Contract.ContractObjetos.COLUMN_NOME + " TEXT," +
                    Contract.ContractObjetos.COLUMN_SILABA + " TEXT )";

    private static final String SQL_CREATE_JOGOS =
            "CREATE TABLE " + Contract.Jogo.TABLE_NAME + " (" +
                    Contract.Jogo._ID + " INTEGER PRIMARY KEY," +
                    Contract.Jogo.COLUMN_JOGADOR + " TEXT, " +
                    Contract.Jogo.COLUMN_NIVEL_MEMORIA + " INTEGER, " +
                    Contract.Jogo.COLUMN_NIVEL_MONTA_LETRAS + " INTEGER )";

    private static final String SQL_DELETE_OBJETOS =
            "DROP TABLE IF EXISTS " + Contract.ContractObjetos.TABLE_NAME;

    private static final String SQL_DELETE_JOGO =
            "DROP TABLE IF EXISTS " + Contract.Jogo.TABLE_NAME;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "edc.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_OBJETOS);
        db.execSQL(SQL_CREATE_JOGOS);
        Objetos.CriarObjetosDefault(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_OBJETOS);
        db.execSQL(SQL_DELETE_JOGO);
        onCreate(db);
    }
}
