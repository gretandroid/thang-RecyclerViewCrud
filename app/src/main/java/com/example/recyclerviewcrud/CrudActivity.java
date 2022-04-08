package com.example.recyclerviewcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recyclerviewcrud.actions.PersonAction;
import com.example.recyclerviewcrud.model.Person;

public class CrudActivity extends AppCompatActivity {


    private EditText surnameText;
    private EditText nameText;
    private Button crudButton;
    PersonAction action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        surnameText = findViewById(R.id.surnameText);
        nameText = findViewById(R.id.nameText);
        crudButton = findViewById(R.id.crudButton);

        Intent intent = getIntent();
        action = (PersonAction) intent.getSerializableExtra(PersonAction.ACTION_KEY);


        Person data = action.getData();
        switch (action.getType()) {
            case ADD:
                break;
            case EDIT:
                surnameText.setText(data.getSurname());
                nameText.setText(data.getName());
                break;
            case DELETE:
                surnameText.setText(data.getSurname());
                nameText.setText(data.getName());
                surnameText.setEnabled(false);
                nameText.setEnabled(false);
                break;
            default:
        }
        crudButton.setText(action.getType().getLabel());

    }

    public void onClickCrudButton(View view) {

        // Prepare return action in case of edit
        switch (action.getType()) {
            case ADD:
                action.setData(new Person());
            case EDIT:
                String surname = surnameText.getText().toString();
                String name = nameText.getText().toString();
                action.getData().setSurname(surname);
                action.getData().setName(name);
                break;
            case DELETE:
                // do nothing
                break;
            default:

        }


        //on crée un intent
        Intent intentOut = new Intent();

        intentOut.putExtra(PersonAction.ACTION_KEY, action);

        //On renvoi un code à la main activity pour lui indiquer que tous s'est bien passé
        setResult(RESULT_OK, intentOut);

        //on ferme l'activity courente
        finish();
    }

    public void onClickCancelButton(View view) {

        //on crée un intent
        Intent intentOut = new Intent();

        intentOut.putExtra(PersonAction.ACTION_KEY, action);

        //On renvoi un code à la main activity pour lui indiquer que tous s'est bien passé
        setResult(RESULT_CANCELED, intentOut);

        //on ferme l'activity courente
        finish();
    }
}