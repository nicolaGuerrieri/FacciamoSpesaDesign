package com.test.nicolaguerrieri.facciamospesadesign.model;

/**
 * Created by nicola.guerrieri2 on 02/12/2015.
 */
public class ArticoloCustom {
    private String articolo;
    private int quantita;

    private long idLista;
    private long idListaArticolo;

    public String getArticolo() {
        return articolo;
    }

    public void setArticolo(String articolo) {
        this.articolo = articolo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
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
