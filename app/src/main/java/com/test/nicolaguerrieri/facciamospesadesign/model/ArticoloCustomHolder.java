package com.test.nicolaguerrieri.facciamospesadesign.model;

import android.widget.TextView;

/**
 * Created by nicola.guerrieri2 on 02/12/2015.
 */
public class ArticoloCustomHolder {
    private TextView articolo;
    private TextView quantita;

    private long idLista;
    private long idListaArticolo;

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

    public long getIdLista() {
        return idLista;
    }

    public void setIdLista(long idLista) {
        this.idLista = idLista;
    }

    public long getIdListaArticolo() {
        return idListaArticolo;
    }

    public void setIdListaArticolo(long idListaArticolo) {
        this.idListaArticolo = idListaArticolo;
    }
}
