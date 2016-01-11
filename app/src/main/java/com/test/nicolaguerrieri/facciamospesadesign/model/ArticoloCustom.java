package com.test.nicolaguerrieri.facciamospesadesign.model;

import android.widget.TextView;

/**
 * Created by nicola.guerrieri2 on 02/12/2015.
 */
public class ArticoloCustom {
    private TextView articolo;
    private TextView quantita;

    private int idLista;
    private int idListaArticolo;

    public TextView getArticolo() {
        return articolo;
    }

    public void setArticolo(TextView articolo) {
        this.articolo = articolo;
    }

    public TextView getQuantita() {
        return quantita;
    }

    public void setQuantita(TextView quantita) {
        this.quantita = quantita;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public int getIdListaArticolo() {
        return idListaArticolo;
    }

    public void setIdListaArticolo(int idListaArticolo) {
        this.idListaArticolo = idListaArticolo;
    }
}
