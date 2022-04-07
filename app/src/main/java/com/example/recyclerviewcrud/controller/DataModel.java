package com.example.recyclerviewcrud.controller;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recyclerviewcrud.model.Person;

import java.util.ArrayList;
import java.util.List;

public class DataModel extends ViewModel {
    private MutableLiveData<List<Person>> mPersons;

    public void addPerson(Person person) {
        if (mPersons == null) {
            mPersons = new MutableLiveData<>(new ArrayList<>());
        }
        mPersons.getValue().add(person);
        mPersons.setValue(mPersons.getValue());
    }

    public void addPerson(List<Person> persons) {
        persons.forEach(this::addPerson);
    }

    public Person getLastPerson() {
        return mPersons.getValue().get(getNumberPerson() -1);
    }

    public Person getPerson(int position) {
        if (mPersons == null) {
           return null;
        }

        return mPersons.getValue().get(position);
    }

    public MutableLiveData<List<Person>> getPersons() {
        if (mPersons == null) {
            mPersons = new MutableLiveData<>(new ArrayList<>());
        }
        return mPersons;
    }

    public int getNumberPerson() {
        return mPersons.getValue().size();
    }
}
