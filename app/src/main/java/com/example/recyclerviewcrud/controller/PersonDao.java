package com.example.recyclerviewcrud.controller;
import static com.example.recyclerviewcrud.persistance.ContractProvider.*;
import static com.example.recyclerviewcrud.persistance.ContractProvider.Person.*;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.recyclerviewcrud.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonDao {

    private static int incrementalId = 0;

    static PersonDao instance;
    DataModel model;

    private PersonDao(DataModel model) {
        this.model = model;
    }

    public static PersonDao getInstance() {
        return instance;
    }
    public static PersonDao useWithModel(DataModel model, Context context) {
        if (instance == null) {
            instance = new PersonDao(model);
        }
        else {
            instance.model = model;
        }

        // load from database
        if (model.getNumberPerson() == 0) {
            List<Person> personsFromDB = instance.getAll(context);
            model.addPerson(personsFromDB);
        }

        return instance;
    }

    // create corresponding methods with content provider
    synchronized public Person save(Person person, Context activity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PERSON_SURNAME, person.getSurname());
        contentValues.put(COL_PERSON_NAME, person.getName());

        if (person.getId() == 0) { // insert
            Uri returnedUri = activity.getContentResolver().insert(URI_PERSON, contentValues);
            person.setId(Integer.parseInt(returnedUri.getLastPathSegment()));
        }
        else { // update
            activity.getContentResolver().update(ContentUris.withAppendedId(URI_PERSON, person.getId()), contentValues, null, null);
        }
        return person; // reload from base
    }

    @SuppressLint("Range")
    public List<Person> getAll(Context activity) {
        List<Person> allPersons = new ArrayList<>();
        Cursor cursor = activity.getContentResolver().query(URI_PERSON,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            String result = null;
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COL_PERSON_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_PERSON_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(COL_PERSON_SURNAME));
                result = new StringBuilder()
                        .append(COL_PERSON_ID + " : ")
                        .append(id + " ")
                        .append(COL_PERSON_NAME + " : ")
                        .append(name + " ")
                        .append(COL_PERSON_SURNAME + " : ")
                        .append(surname + " ")
                        .toString();
                Log.d("App", result);
                allPersons.add(new Person(id, surname, name));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return allPersons;
    }

    @SuppressLint("Range")
    public Person findById(int id, Context activity) {
        Cursor cursor = activity.getContentResolver().query(ContentUris.withAppendedId(URI_PERSON, id),
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            String result = null;
            int returnedId = cursor.getInt(cursor.getColumnIndex(COL_PERSON_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_PERSON_NAME));
            String surname = cursor.getString(cursor.getColumnIndex(COL_PERSON_SURNAME));
            result = new StringBuilder()
                    .append(COL_PERSON_ID + " : ")
                    .append(id + " ")
                    .append(COL_PERSON_NAME + " : ")
                    .append(name + " ")
                    .append(COL_PERSON_SURNAME + " : ")
                    .append(surname + " ")
                    .toString();
            Log.d("App", result);
            cursor.close();
            return new Person(returnedId, surname, name);
        }

        return null;
    }

    public int delete(int id, Context activity) {
        return activity.getContentResolver().delete(ContentUris.withAppendedId(URI_PERSON, id), null, null);
    }
}
