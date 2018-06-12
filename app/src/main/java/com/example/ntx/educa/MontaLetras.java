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
        for (Integer posic = 0; posic < silabas.size(); posic ++){
            this.silabas.add(new Silaba(silabas.get(posic), posic));
        }
    }

    @Override
    protected void TrocarNivel(SQLiteDatabase db) {
        Integer novoNivel;
        this.silabas = new ArrayList<Silaba>();
        this.nomeFigura = null;

        if (MontaLetrasDAO.verificarNivelMaximoAtingido(db, super.nivel)) {
            novoNivel = 1;
        }else{
            novoNivel = super.nivel + 1;
        }

        UpdateDataBase.avancarNivelMontaLetras(db, super.jogador, novoNivel);
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


    public String getSilabaTag(Integer tag){
        String retorno = "";
        for (Silaba s : silabas){
            if (s.getPosic() == tag){
                retorno = s.getSilaba();
            }
        }
        return retorno;
    }

    public ArrayList<Silaba> getSilabas() {
        return silabas;
    }

}
