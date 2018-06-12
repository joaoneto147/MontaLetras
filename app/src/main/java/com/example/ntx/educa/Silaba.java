package com.example.ntx.educa;

import java.util.ArrayList;

/**
 * Created by nTx on 30/05/2018.
 */

public class Silaba{
    private String silaba;
    private Integer posic;

    public Integer getPosic() {
        return posic;
    }

    public void setPosic(Integer posic) {
        this.posic = posic;
    }

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

    public Silaba(String silaba, Integer posic) {
        this.silaba = silaba;
        this.posic = posic;
        localCorreto = false;
    }
}
