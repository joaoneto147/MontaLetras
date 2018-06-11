package com.example.ntx.educa;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nTx on 30/05/2018.
 */

public class Memoria extends Objetos  {

    private String nome;

    protected Memoria(Integer nivel, String jogador) {
        super(nivel, jogador);
    }

    @Override
    protected void TrocarNivel(SQLiteDatabase db) {

    }

    @Override
    protected void IniciarObjetos(SQLiteDatabase db, String jogador) {

    }

    @Override
    protected Boolean VerificarFimJogo() {
        return null;
    }

    @Override
    public String getJogador() {
        return super.getJogador();
    }

    @Override
    public void setJogador(String jogador) {
        super.setJogador(jogador);
    }

    @Override
    public Integer getNivel() {
        return super.getNivel();
    }

    @Override
    public void setNivel(Integer nivel) {
        super.setNivel(nivel);
    }
}
