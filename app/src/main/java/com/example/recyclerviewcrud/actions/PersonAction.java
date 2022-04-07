package com.example.recyclerviewcrud.actions;

import com.example.recyclerviewcrud.model.Person;

import java.io.Serializable;

public class PersonAction implements Serializable {

    public static final String ACTION_KEY = "action";

    public static enum ActionType implements Serializable {
        ADD(1, "ADD"),
        EDIT(2, "EDIT"),
        DELETE(3, "DELETE");

        private int code;
        private String label;

        ActionType(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private ActionType type;
    private Person data;

    public PersonAction(ActionType type, Person data) {
        this.type = type;
        this.data = data;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Person getData() {
        return data;
    }

    public void setData(Person data) {
        this.data = data;
    }
}
