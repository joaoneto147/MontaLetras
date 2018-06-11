package com.example.ntx.educa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nTx on 30/05/2018.
 */

public abstract class Objetos {
    protected Integer nivel;
    protected String jogador;

    protected Objetos(Integer nivel, String jogador) {
        this.nivel = nivel;
        this.jogador = jogador;
    }

    protected abstract void TrocarNivel(SQLiteDatabase db);

    protected abstract void IniciarObjetos(SQLiteDatabase db, String jogador);

    protected abstract Boolean VerificarFimJogo();

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public Integer getNivel() {
        return nivel;

    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    protected static void CriarObjetosDefault(SQLiteDatabase db){
        UpdateDataBase upd = new UpdateDataBase();

        upd.adicionarJogadorDefault("ADM", db);

        upd.adicionarObjetosDefault("pato", "pa-to", "1", db);
        upd.adicionarObjetosDefault("vovo", "vo-vo", "2", db);
        upd.adicionarObjetosDefault("sapo", "sa-po", "3", db);
        upd.adicionarObjetosDefault("bola", "bo-la", "4", db);
        upd.adicionarObjetosDefault("foca", "fo-ca", "5", db);
        upd.adicionarObjetosDefault("boca", "bo-ca", "6", db);
        upd.adicionarObjetosDefault("vela", "ve-la", "7", db);
        upd.adicionarObjetosDefault("tatu", "ta-tu", "8", db);
        upd.adicionarObjetosDefault("bule", "bu-le", "9", db);
        upd.adicionarObjetosDefault("casa", "ca-sa", "10", db);
        upd.adicionarObjetosDefault("rato", "ra-to", "11", db);
        upd.adicionarObjetosDefault("gato", "ga-to", "12", db);
        upd.adicionarObjetosDefault("bolo", "bo-lo", "13", db);



        upd.adicionarObjetosDefault("sapato", "sa-pa-to", "14", db);
        upd.adicionarObjetosDefault("boneca", "bo-ne-ca", "15", db);
        upd.adicionarObjetosDefault("caneta", "ca-ne-ta", "16", db);
        upd.adicionarObjetosDefault("pijama", "pi-ja-ma", "17", db);
        upd.adicionarObjetosDefault("macaco", "ma-ca-co", "18", db);
        upd.adicionarObjetosDefault("panela", "pa-ne-la", "19", db);
        upd.adicionarObjetosDefault("caneca", "ca-ne-ca", "20", db);
        upd.adicionarObjetosDefault("girafa", "gi-ra-fa", "21", db);
        upd.adicionarObjetosDefault("cavalo", "ca-va-lo", "22", db);
        upd.adicionarObjetosDefault("jacare", "ja-ca-re", "23", db);
        upd.adicionarObjetosDefault("camelo", "ca-me-lo", "24", db);


    }
}

