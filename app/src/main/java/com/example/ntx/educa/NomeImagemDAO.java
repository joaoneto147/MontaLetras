package com.example.ntx.educa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by nTx on 12/06/2018.
 */

public class NomeImagemDAO {
    public static NomeImagem buscarDadosNomeImagem(SQLiteDatabase db){
        NomeImagem nomeImagem;
        ArrayList<String> nomeFigura = new ArrayList<String>();
        ArrayList<String> silabaFormada = new ArrayList<String>();

        String select =
                "SELECT " +
                        Contract.ContractObjetos.COLUMN_NOME + ", " +
                        Contract.ContractObjetos.COLUMN_NIVEL + ", " +
                        Contract.ContractObjetos.COLUMN_SILABA + " " +
                        "FROM " +
                        Contract.ContractObjetos.TABLE_NAME + " ";

        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {

            do {

                nomeFigura.add(cursor.getString(cursor.getColumnIndex(
                        Contract.ContractObjetos.COLUMN_NOME
                )));

                silabaFormada.add(cursor.getString(cursor.getColumnIndex(
                        Contract.ContractObjetos.COLUMN_SILABA
                )));

            } while (cursor.moveToNext());
        }

        nomeImagem = new NomeImagem(nomeFigura, silabaFormada);
        return  nomeImagem;
    }
}
