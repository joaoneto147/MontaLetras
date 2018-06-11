package com.example.ntx.educa;

import java.util.ArrayList;

/**
 * Created by nTx on 30/05/2018.
 */

public class Silaba{
    private String silaba;
    private Boolean localCorreto;

    public String getSilaba() {
        return silaba;
    }

    public void setSilaba(String silaba) {
        this.silaba = silaba;
    }

    public Boolean getLocalCorreto() {
        return localCorreto;
    }

    public void setLocalCorreto(Boolean localCorreto) {
        this.localCorreto = localCorreto;
    }

    public Silaba(String silaba) {
        this.silaba = silaba;
        localCorreto = false;
    }
}
