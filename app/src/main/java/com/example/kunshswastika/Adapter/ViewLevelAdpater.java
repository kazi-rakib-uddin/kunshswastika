package com.example.kunshswastika.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kunshswastika.Activity.LevelUserActivity;
import com.example.kunshswastika.Model.Level_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.SingleLevelBinding;
import com.example.kunshswastika.databinding.SingleViewDetailsBinding;

import java.util.List;

public class ViewLevelAdpater extends RecyclerView.Adapter<ViewLevelAdpater.ViewHolder> {

    private Context context;
    private String [] level_models;
    private User user;

    public ViewLevelAdpater(Context context, String [] level_models) {
        this.context = context;
        this.level_models = level_models;

        user = new User(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleLevelBinding binding = SingleLevelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLevelAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.level.setText("Level : "+ level_models[position]);

        holder.binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("level",level_models[position]);
                context.startActivity(new Intent(context, LevelUserActivity.class).putExtras(b));
            }
        });

    }


    @Override
    public int getItemCount() {
        return level_models.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleLevelBinding binding;

        public ViewHolder(@NonNull SingleLevelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
