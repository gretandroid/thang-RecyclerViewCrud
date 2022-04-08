package com.example.recyclerviewcrud.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewcrud.CrudActivity;
import com.example.recyclerviewcrud.R;
import com.example.recyclerviewcrud.actions.PersonAction;
import com.example.recyclerviewcrud.controller.DataModel;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    DataModel model;
    ActivityResultLauncher<Intent> intentLauncher;

    public PersonAdapter(DataModel model, ActivityResultLauncher<Intent> intentLauncher) {
        this.model = model;
        this.intentLauncher = intentLauncher;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater permetre de creer (inflater) des views correspondants
        // aux fichiers xml
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.getSurnameTextView().setText(model.getPerson(position).getSurname());
        holder.getNameTextView().setText(model.getPerson(position).getName());
        holder.getEditImageView().setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), CrudActivity.class);
            intent.putExtra(PersonAction.ACTION_KEY, new PersonAction(PersonAction.ActionType.EDIT, model.getPerson(position)));
            intentLauncher.launch(intent);
        });

        holder.getDeleteImageView().setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), CrudActivity.class);
            intent.putExtra(PersonAction.ACTION_KEY, new PersonAction(PersonAction.ActionType.DELETE, model.getPerson(position)));
            intentLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return model.getNumberPerson();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView surnameTextView;
        TextView nameTextView;
        ImageView editImageView;
        ImageView deleteImageView;


        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            surnameTextView = itemView.findViewById(R.id.surnameTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            editImageView = itemView.findViewById(R.id.editImageView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
        }

        public TextView getSurnameTextView() {
            return surnameTextView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public ImageView getEditImageView() {
            return editImageView;
        }

        public ImageView getDeleteImageView() {
            return deleteImageView;
        }
    }
}
