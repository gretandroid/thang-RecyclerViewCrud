package com.example.recyclerviewcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recyclerviewcrud.actions.PersonAction;

public class CrudActivity extends AppCompatActivity {


    private Button crudButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        crudButton = findViewById(R.id.crudButton);

        Intent intent = getIntent();
        PersonAction action = (PersonAction) intent.getSerializableExtra(PersonAction.ACTION_KEY);

        crudButton.setText(action.getType().getLabel());

    }

    public void onClickCrudButton(View view) {
    }
}