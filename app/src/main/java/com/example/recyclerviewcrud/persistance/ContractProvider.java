package com.example.recyclerviewcrud.persistance;

import android.net.Uri;

public class ContractProvider {

    public static final String DB_NAME = "person.db";
    // from AndroidManifest.provider.android:authorities
    public static final String AUTHORITIES = "com.example.recyclerviewcrud.persistance.BaseContentProvider";

    public static class Person {
        public static final String TABLE_PERSON = "table_person";
        public static final String COL_PERSON_ID = "person_id";
        public static final String COL_PERSON_SURNAME = "person_surname";
        public static final String COL_PERSON_NAME = "person_name";

        public static final Uri URI_PERSON = Uri.parse("content://" + AUTHORITIES + "/" + TABLE_PERSON);

    }

}
