package com.example.ntx.educa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


// This class contains every helper function that relates to updating the database
public class UpdateDataBase {

    public static long avancarNivelMontaLetras(SQLiteDatabase db, String jogador, Integer novoNivel) {

        String updsql = "UPDATE " +
                Contract.Jogo.TABLE_NAME + " " +
                "SET " +
                Contract.Jogo.COLUMN_NIVEL_MONTA_LETRAS + " = " + novoNivel + "  " +
                "WHERE " + Contract.Jogo.COLUMN_JOGADOR + " like '%" + jogador + "%'";


            db.execSQL(updsql);

            return 100000;

    }

    public long adicionarObjetosDefault(String nome, String silaba, String nivel, SQLiteDatabase db) {
        ContentValues values = new ContentValues();


        values.put(Contract.ContractObjetos.COLUMN_NOME, nome);
        values.put(Contract.ContractObjetos.COLUMN_SILABA, silaba);
        values.put(Contract.ContractObjetos.COLUMN_NIVEL, nivel);
  //teste
        // insert into a row in database
        return db.insert(Contract.ContractObjetos.TABLE_NAME, null, values);
    }

    public long adicionarJogadorDefault(String nome, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(Contract.Jogo.COLUMN_JOGADOR, nome);
        values.put(Contract.Jogo.COLUMN_NIVEL_MEMORIA, 1);
        values.put(Contract.Jogo.COLUMN_NIVEL_MONTA_LETRAS, 1);

        // insert into a row in database
        return db.insert(Contract.Jogo.TABLE_NAME, null, values);
    }

}



