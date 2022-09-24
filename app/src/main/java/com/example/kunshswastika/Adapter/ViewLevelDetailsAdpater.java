package com.example.kunshswastika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kunshswastika.Model.Level_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.SingleViewDetailsBinding;

import java.util.List;

public class ViewLevelDetailsAdpater extends RecyclerView.Adapter<ViewLevelDetailsAdpater.ViewHolder> {

    private Context context;
    private List<Level_Model> level_models;
    private User user;

    public ViewLevelDetailsAdpater(Context context, List<Level_Model> level_models) {
        this.context = context;
        this.level_models = level_models;

        user = new User(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleViewDetailsBinding binding = SingleViewDetailsBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLevelDetailsAdpater.ViewHolder holder, int position) {

        holder.binding.name.setText("Name : "+level_models.get(position).getFull_name());

    }


    @Override
    public int getItemCount() {
        return level_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleViewDetailsBinding binding;

        public ViewHolder(@NonNull SingleViewDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
