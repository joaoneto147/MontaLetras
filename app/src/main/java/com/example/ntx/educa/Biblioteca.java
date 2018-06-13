package com.example.ntx.educa;

import java.util.ArrayList;

/**
 * Created by nTx on 01/06/2018.
 */

public final class Biblioteca {

    public static ArrayList<String> GetSilaba(String silaba){
        ArrayList<String> retorno = new ArrayList<String>();
        String[] split = silaba.split("-");

        for (String sil : split){
            retorno.add(sil);
        }
        return retorno;
    }


}
