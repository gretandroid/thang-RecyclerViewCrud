package com.example.recyclerviewcrud.persistance;

import static com.example.recyclerviewcrud.persistance.ContractProvider.*;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.*;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.COL_PERSON_ID;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.COL_PERSON_NAME;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.COL_PERSON_SURNAME;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.TABLE_PERSON;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Class fournisseur des outils pour la création
// et MAJ du schéma de la base de donnée

public class DatabaseSQliteHelper extends SQLiteOpenHelper {

    //  Numéro de version qu'on modifie manuallement à chaque changement du schema
    public static final int VERSION = 1;

    private static final String CREATE_TABLE_PERSON = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL)",
            TABLE_PERSON,
            COL_PERSON_ID,
            COL_PERSON_NAME,
            COL_PERSON_SURNAME);

    public DatabaseSQliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // lors que ouvre la base en écriture, i.e. appeler dbHelper.getWritableDataBase()
    // cette méthode est déclanché si la base n'existe pas et methode onUpgrade()
    // si il a un chagement
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }
}
