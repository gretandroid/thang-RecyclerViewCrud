package com.example.recyclerviewcrud;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recyclerviewcrud.actions.PersonAction;
import com.example.recyclerviewcrud.controller.DataModel;
import com.example.recyclerviewcrud.controller.PersonDao;
import com.example.recyclerviewcrud.model.Person;
import com.example.recyclerviewcrud.recyclerview.PersonAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView personRecyclerView;

    DataModel model;

    // declare a launcher to call an intent to start
    // an execution process ActivityResultContracts
    ActivityResultLauncher<Intent> intentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(DataModel.class);

        // init Dao with model
        PersonDao.useWithModel(model, this);

        // init fake data for model
//        model.addPerson(Arrays.asList(
//                new Person("PHAM", "Thang"),
//                new Person("PHAM", "Alexandre"),
//                new Person("PHAM", "Louis"),
//                new Person("TRINH", "Mai")
//        ));

        // launcher register
        intentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Check whether result is OK
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // retrieve the intent return by second Activity
                            Intent data = result.getData();

                            // get person in intent
                            PersonAction action = (PersonAction) data.getSerializableExtra(PersonAction.ACTION_KEY);
                            Log.d("App", "Return action" + action.toString());
                            Toast.makeText(getBaseContext(), action.toString(), Toast.LENGTH_LONG).show();

                            // MAJ model
                            updateModel(action);
                        }
                    }
                });

        // init UI

        personRecyclerView = findViewById(R.id.personRecyclerView);
        personRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PersonAdapter adapter = new PersonAdapter(model, intentLauncher);
        personRecyclerView.setAdapter(adapter);

        // register live person list hook
        model.getPersons().observe(this, personList -> {
            // Demande MAJ personRecyclerView
            adapter.notifyDataSetChanged();
        });


    }

    private void updateModel(PersonAction action) {
        Person data = action.getData();
        switch (action.getType()) {
            case  ADD :
                model.addPerson(data);
                PersonDao.getInstance().save(data, this);
                break;
            case EDIT :
                Person person = model.updatePerson(data.getId(), data.getSurname(), data.getName());
                PersonDao.getInstance().save(person, this);
                break;
            case DELETE:
                model.removeById(data.getId());
                PersonDao.getInstance().delete(data.getId(), this);
                break;
        }
    }

    public void onClickAddButton(View view) {
        Intent intent = new Intent(this, CrudActivity.class);
        intent.putExtra(PersonAction.ACTION_KEY, new PersonAction(PersonAction.ActionType.ADD, null));
        intentLauncher.launch(intent);
    }
}