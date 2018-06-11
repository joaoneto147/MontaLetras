package com.example.ntx.educa;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by nTx on 30/05/2018.
 */

public class MontaLetras extends Objetos {
    private String nomeFigura;
    private ArrayList<Silaba> silabas;

    public MontaLetras(Integer nivel, String silaba, String nome, String jogador) {
        super(nivel, jogador);
        this.silabas = new ArrayList<Silaba>();
        this.nomeFigura = nome;

        ArrayList<String> silabas = Biblioteca.GetSilaba(silaba);
        for (String s : silabas){
            this.silabas.add(new Silaba(s));
        }
    }

    @Override
    protected void TrocarNivel(SQLiteDatabase db) {
        this.silabas.clear();
        this.nomeFigura = null;
        UpdateDataBase.avancarNivelMontaLetras(db, super.jogador, super.nivel + 1);
        IniciarObjetos(db, super.jogador);
    }

    @Override
    protected void IniciarObjetos(SQLiteDatabase db, String jogador) {
        MontaLetras temp = MontaLetrasDAO.buscarDadosNivelAtual(db, jogador);

        super.nivel = temp.getNivel();
        super.jogador = temp.getJogador();
        this.nomeFigura = temp.getNomeFigura();
        this.silabas = temp.getSilabas();
    }

    @Override
    protected Boolean VerificarFimJogo() {
        Boolean retorno = true;

        for (Silaba s : silabas){

            if (!s.getLocalCorreto()) {
                retorno = false;
                break;
            }
        }

        return retorno;
    }

    public String getNomeFigura() {
        return nomeFigura;
    }

    public void setNomeFigura(String nomeFigura) {
        this.nomeFigura = nomeFigura;
    }

    public ArrayList<Silaba> getSilabas() {
        return silabas;
    }

    public void setSilabas(ArrayList<Silaba> silabas) {
        this.silabas = silabas;
    }
}
