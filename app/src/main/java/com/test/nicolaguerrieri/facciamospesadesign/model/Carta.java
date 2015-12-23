package com.test.nicolaguerrieri.facciamospesadesign.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by nicola.guerrieri2 on 16/09/2015.
 */
public class Carta implements Serializable {

    public Integer id;
    public Bitmap icona;
    public String pathImage;
    public String titolo;
    public String logo;
    public String codice;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Carta() {
        super();
    }

    public Carta(Bitmap icona, String titolo) {
        this.titolo = titolo;
        this.icona = icona;
    }

    public Carta(String logo, String titolo) {
        this.titolo = titolo;
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public Bitmap getIcona() {
        return icona;
    }

    public void setIcona(Bitmap icona) {
        this.icona = icona;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
