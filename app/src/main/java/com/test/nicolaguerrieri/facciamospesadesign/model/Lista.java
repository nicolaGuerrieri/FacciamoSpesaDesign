package com.test.nicolaguerrieri.facciamospesadesign.model;

import java.io.Serializable;

/**
 * Created by nicola.guerrieri2 on 16/09/2015.
 */
public class Lista implements Serializable {

    public Integer id;
    public String nomeLista;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }
}
