package com.test.nicolaguerrieri.facciamospesadesign.utility;

/**
 * Created by nicola.guerrieri2 on 21/09/2015.
 */
public final class Costanti {
    public static final String DB_NAME = "facciamoSpesa";

    //CARTE
    public static final String TABLE_NAME_CARTE = "Carte";
    public static final String COLUMN_NAME_IMMAGINE = "Immagine";
    public static final String COLUMN_NAME_NOME = "Nome";
    public static final String COLUMN_NAME_ID = "ID";
    public static final String COLUMN_NAME_LOGO = "Logo";
    public static final String COLUMN_NAME_CODICE = "Codice";


    //SPESA FAST
    public static final String TABLE_NAME_PRODOTTI = "Prodotti";
    public static final String COLUMN_NAME_PRODOTTO = "Prodotto";

    //liste della spesa
    public static final String TABLE_NAME_ARTICOLO = "ARTICOLO";
    public static final String COLUMN_NAME_NOME_ARTICOLO = "NOME_ARTICOLO";

    public static final String TABLE_NAME_LISTA = "LISTA";
    public static final String COLUMN_NAME_NOME_LISTA = "NOME_LISTA";


    public static final String TABLE_NAME_LISTA_ARTICOLO = "LISTA_ARTICOLO";
    public static final String COLUMN_NAME_ID_LISTA = "ID_LISTA";
    public static final String COLUMN_NAME_ID_ARTICOLO = "ID_ARTICOLO";
    public static final String COLUMN_NAME_QUANTITA = "QUANTITA";


    public static final int WELCOME_FRAGMENT = 1;
    public static final int LISTE_SPESA_FRAGMENT = 1;
    public static final int LISTA_FRAGMENT = 0;
    public static final int CARTE_FRAGMENT = 2;
    public static final int VIEW_CARTA_FRAGMENT = 3;
    public static final int SCAN_CARTA_FRAGMENT = 4;
    public static final int SETTINGS_FRAGMENT = 5;


    public static final String QUERY_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME_CARTE;
    public static final String QUERY_DROP_TABLE_NAME_ARTICOLO = "DROP TABLE IF EXISTS " + TABLE_NAME_ARTICOLO;
    public static final String QUERY_DROP_TABLE_NAME_LISTA = "DROP TABLE IF EXISTS " + TABLE_NAME_LISTA;
    public static final String QUERY_DROP_TABLE_NAME_LISTA_ARTICOLO = "DROP TABLE IF EXISTS " + TABLE_NAME_LISTA_ARTICOLO;


    public static final String QUERY_CREATE = "CREATE TABLE IF NOT EXISTS " + Costanti.TABLE_NAME_CARTE + " (" + Costanti.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + Costanti.COLUMN_NAME_NOME + " VARCHAR not null, " + Costanti.COLUMN_NAME_IMMAGINE + " BLOB not null, " + Costanti.COLUMN_NAME_LOGO + " VARCHAR, "
            + Costanti.COLUMN_NAME_CODICE + " VARCHAR not null);";


    public static final String QUERY_CREATE_ARTICOLO = "CREATE TABLE IF NOT EXISTS " + Costanti.TABLE_NAME_ARTICOLO + " (" + Costanti.COLUMN_NAME_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Costanti.COLUMN_NAME_NOME_ARTICOLO + " VARCHAR);";

    public static final String QUERY_CREATE_LISTA = "CREATE TABLE IF NOT EXISTS " + Costanti.TABLE_NAME_LISTA + " (" + Costanti.COLUMN_NAME_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Costanti.COLUMN_NAME_NOME_LISTA + " VARCHAR);";

    public static final String QUERY_CREATE_LISTA_ARTICOLO = "CREATE TABLE IF NOT EXISTS " + Costanti.TABLE_NAME_LISTA_ARTICOLO + " (" + Costanti.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + Costanti.COLUMN_NAME_ID_LISTA + " INTEGER," + Costanti.COLUMN_NAME_ID_ARTICOLO + " integer, " + Costanti.COLUMN_NAME_QUANTITA + " integer,  FOREIGN KEY ("
            + Costanti.COLUMN_NAME_ID_LISTA + ") REFERENCES " + Costanti.TABLE_NAME_LISTA + "(" + Costanti.COLUMN_NAME_ID + "),  FOREIGN KEY ("
            + Costanti.COLUMN_NAME_ID_ARTICOLO + ") REFERENCES " + Costanti.TABLE_NAME_ARTICOLO + "(" + Costanti.COLUMN_NAME_ID + "));";

    public static final String QUERY_JOIN_LISTA_ARTICOL = "select * from " + TABLE_NAME_LISTA_ARTICOLO + " join " + TABLE_NAME_ARTICOLO + " on ("
            + TABLE_NAME_LISTA_ARTICOLO + "." + COLUMN_NAME_ID_ARTICOLO + "=" + TABLE_NAME_ARTICOLO + "." + COLUMN_NAME_ID + ") where "
            + TABLE_NAME_LISTA_ARTICOLO + "." + COLUMN_NAME_ID_LISTA + " = ?;";


    public static final String QUERY_JOIN_LISTA_ARTICOLO = "select * from " + TABLE_NAME_LISTA_ARTICOLO + " join " + TABLE_NAME_ARTICOLO + " on ("
            + TABLE_NAME_LISTA_ARTICOLO + "." + COLUMN_NAME_ID_ARTICOLO + "=" + TABLE_NAME_ARTICOLO + "." + COLUMN_NAME_ID + ") where "
            + TABLE_NAME_LISTA_ARTICOLO + "." + COLUMN_NAME_ID_LISTA + " =";

    public static final String QUERY_CERCA_ARTICOLO = "SELECT " + Costanti.TABLE_NAME_ARTICOLO + "." + Costanti.COLUMN_NAME_ID + " FROM " + Costanti.TABLE_NAME_ARTICOLO + " where " + Costanti.TABLE_NAME_ARTICOLO + "." +Costanti.COLUMN_NAME_NOME_ARTICOLO + " = ?;";

}
