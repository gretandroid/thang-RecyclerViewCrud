package com.example.recyclerviewcrud.persistance;
import static com.example.recyclerviewcrud.persistance.ContractProvider.*;
import static com.example.recyclerviewcrud.persistance.ContractProvider.AUTHORITIES;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.*;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.COL_PERSON_ID;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.TABLE_PERSON;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// Le content provider permet de MAJ le contenu de la base de donné
// SQLite tout en maintenant à jour un système d'URI permettant d'acces aux resources
// de la base même à partir d'autre application
public class BaseContentProvider extends ContentProvider {



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int TABLE_PERSON_CODE = 1;

    public static final int TABLE_PERSON_ID_CODE = 2;

    static {
        URI_MATCHER.addURI(AUTHORITIES, TABLE_PERSON, TABLE_PERSON_CODE);
        URI_MATCHER.addURI(AUTHORITIES, TABLE_PERSON + "/#", TABLE_PERSON_ID_CODE);
    }
    // on declare le helper pour ouvrir une session à chaque lecture/écriture
    private DatabaseSQliteHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseSQliteHelper(getContext(),
                ContractProvider.DB_NAME,
                null, DatabaseSQliteHelper.VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case TABLE_PERSON_CODE :
                return db.query(TABLE_PERSON,
                        projection,
                        selection,
                        selectionArgs, null, null, sortOrder);

            case TABLE_PERSON_ID_CODE :
                return db.query(TABLE_PERSON,
                        projection,
                        COL_PERSON_ID  + " = " + uri.getLastPathSegment() ,
                        selectionArgs, null, null, sortOrder);
            default :
                return null;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        // Creation ou MAJ de la BDD
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            switch (URI_MATCHER.match(uri)) {

                case TABLE_PERSON_CODE :
                    return insert(uri, values, db, TABLE_PERSON);
                default : return uri;

            }
        }
    }

    private Uri insert(@NonNull Uri uri, @Nullable ContentValues values, SQLiteDatabase db, String tableName) {
        long id;
        id = db.insertOrThrow(tableName, null, values);

        if (id == -1) {
            throw new RuntimeException("Fail insert into " + tableName);
        }
        else {
            Log.d("App", "uri=" + uri.toString() + "/" + id);
            return ContentUris.withAppendedId(uri, id);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case TABLE_PERSON_ID_CODE :
                return db.delete(TABLE_PERSON,
                        COL_PERSON_ID  + " = " + uri.getLastPathSegment(),
                        selectionArgs);
            default :
                return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case TABLE_PERSON_ID_CODE :
                return db.update(TABLE_PERSON,
                        values,
                        COL_PERSON_ID  + " = " + uri.getLastPathSegment() ,
                        selectionArgs);
            default :
                return 0;
        }
    }

    /**
     * Recuperer a dernier partie URI (content://com.example.contentprovider2.ContentProvider/#)
     */
    private long getId(Uri uri) {
        String  lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            return Long.parseLong(lastPathSegment);
        }

        return -1;
    }
}
