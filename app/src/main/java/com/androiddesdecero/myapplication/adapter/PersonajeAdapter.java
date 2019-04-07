package com.androiddesdecero.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androiddesdecero.myapplication.R;
import com.androiddesdecero.myapplication.model.Personaje;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeAdapterHolder> {

    private List<Personaje> personajes;

    public PersonajeAdapter(List<Personaje> personajes){
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public PersonajeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_personaje, parent, false);

        return new PersonajeAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeAdapterHolder holder, int position) {
        Personaje personaje = personajes.get(position);
        holder.tvName.setText(personaje.getName());
        holder.tvHeight.setText(personaje.getHeight());
        holder.tvEyeColor.setText(personaje.getEyeColor());
        holder.tvBirth.setText(personaje.getBirthYear());
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    public void setData(List<Personaje> personajes){
        this.personajes = personajes;
        notifyDataSetChanged();
    }

    public class PersonajeAdapterHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvHeight;
        private TextView tvEyeColor;
        private TextView tvBirth;

        public PersonajeAdapterHolder (@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvHeight = itemView.findViewById(R.id.tvHeight);
            tvEyeColor = itemView.findViewById(R.id.tvEyeColor);
            tvBirth = itemView.findViewById(R.id.tvBirth);
        }
    }
}
