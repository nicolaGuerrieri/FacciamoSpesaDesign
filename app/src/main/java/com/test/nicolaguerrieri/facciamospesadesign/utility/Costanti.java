package com.test.nicolaguerrieri.facciamospesadesign.utility;

/**
 * Created by nicola.guerrieri2 on 21/09/2015.
 */
public final class Costanti {
    public static final String dbName = "facciamoSpesa";
    public static  final String tableName = "Carte";
    public static  final String columnNameImmagine = "Immagine";
    public static  final String columnNameNome = "Nome";
    public static  final String columnNameID = "ID";
    public static  final String columnNameLogo = "Logo";
    public static  final String columnNameCodice = "Codice";


    public static  final String QUERY_DROP = "DROP TABLE IF EXISTS " + tableName;
    public static final String QUERY_CREATE = "CREATE TABLE IF NOT EXISTS " + Costanti.tableName + " (" + Costanti.columnNameID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Costanti.columnNameNome + " VARCHAR not null, " + Costanti.columnNameImmagine + " BLOB not null, " + Costanti.columnNameLogo + " VARCHAR, " + Costanti.columnNameCodice + " VARCHAR not null);";

}
