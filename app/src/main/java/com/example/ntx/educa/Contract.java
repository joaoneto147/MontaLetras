package com.example.ntx.educa;

import android.provider.BaseColumns;

/**
 * Created by nTx on 30/05/2018.
 */

public final class Contract {

    private Contract() {}

    public static class ContractObjetos implements BaseColumns {
        public static final String TABLE_NAME = "OBJETOS";
        public static final String COLUMN_NOME = "NOME";
        public static final String COLUMN_SILABA = "SILABA";
        public static final String COLUMN_NIVEL = "NIVEL";
    }

    public static class Jogo implements  BaseColumns{
        public static final String TABLE_NAME = "JOGO";
        public static final String COLUMN_NIVEL_MEMORIA = "NIVEL_MEMORIA";
        public static final String COLUMN_NIVEL_MONTA_LETRAS = "NIVEL_MONTA_LETRAS";
        public static final String COLUMN_JOGADOR = "JOGADOR";
    }

}
