package com.example.recyclerviewcrud.controller;

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
    public static PersonDao useWithModel(DataModel model) {
        if (instance == null) {
            instance = new PersonDao(model);
        }
        else {
            instance.model = model;
        }

        return instance;
    }

    public static int generateId() {
        return ++incrementalId;
    }


}
