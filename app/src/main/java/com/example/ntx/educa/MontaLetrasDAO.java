package com.example.ntx.educa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ntx.educa.Contract;
import com.example.ntx.educa.Objetos;

import java.util.ArrayList;

/**
 * Created by nTx on 17/05/2018.
 */

public class MontaLetrasDAO {

    public static MontaLetras buscarDadosNivelAtual(SQLiteDatabase db, String jogador){
        MontaLetras montaLetras;
        Integer nivelAtual = buscarNivelAtual(db, jogador);

        String select =
                "SELECT " +
                        Contract.ContractObjetos.COLUMN_NOME + ", " +
                        Contract.ContractObjetos.COLUMN_NIVEL + ", " +
                        Contract.ContractObjetos.COLUMN_SILABA + " " +
                        "FROM " +
                        Contract.ContractObjetos.TABLE_NAME + " " +
                        "WHERE " + Contract.ContractObjetos.COLUMN_NIVEL + " = " + nivelAtual;

        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            String nome = cursor.getString(cursor.getColumnIndex(
                    Contract.ContractObjetos.COLUMN_NOME
            ));
            String silaba = cursor.getString(cursor.getColumnIndex(
                    Contract.ContractObjetos.COLUMN_SILABA
            ));

            Integer nivel = cursor.getInt(cursor.getColumnIndex(
                    Contract.ContractObjetos.COLUMN_NIVEL
            ));

            montaLetras = new MontaLetras(nivel, silaba, nome, jogador);
            return  montaLetras;
        }
        return null;
    }

    public static Integer buscarNivelAtual(SQLiteDatabase db, String jogador){
        Integer nivel_atual = 1;
        String select =
                "SELECT " +
                Contract.Jogo.COLUMN_NIVEL_MONTA_LETRAS + " " +
                "FROM " +
                Contract.Jogo.TABLE_NAME + " " +
                "WHERE " + Contract.Jogo.COLUMN_JOGADOR + " like '%" + jogador + "%'";

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            nivel_atual = cursor.getInt(cursor.getColumnIndex(
                    Contract.Jogo.COLUMN_NIVEL_MONTA_LETRAS
            ));
        }

        return nivel_atual;
    }

    public static boolean verificarNivelMaximoAtingido(SQLiteDatabase db, Integer nivelAtual){
        Boolean ultimo_nivel = false;
        Integer nivelMaximo = 999999;
        String select =
                "SELECT " +
                        "max( " + Contract.ContractObjetos.COLUMN_NIVEL + " ) " +
                        "FROM " +
                        Contract.ContractObjetos.TABLE_NAME + " ";

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            nivelMaximo = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        }

        if (nivelAtual == nivelMaximo){
            ultimo_nivel = true;
        }

        return ultimo_nivel;
    }

}
