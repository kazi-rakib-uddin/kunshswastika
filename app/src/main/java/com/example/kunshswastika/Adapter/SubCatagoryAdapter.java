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

import com.bumptech.glide.Glide;
import com.example.kunshswastika.ActivityEcommers.ProductActivity;
import com.example.kunshswastika.ActivityEcommers.SubCatagoryActivity;
import com.example.kunshswastika.Model.HomeCatagoryModel;
import com.example.kunshswastika.Model.Sub_Catagory_Model;
import com.example.kunshswastika.databinding.SingleHomeCatagoryBinding;

import java.util.List;

public class SubCatagoryAdapter extends RecyclerView.Adapter<SubCatagoryAdapter.MyViewHolder> {

    private Context context;
    private List<Sub_Catagory_Model> homeCatagoryModels;

    public SubCatagoryAdapter(Context context, List<Sub_Catagory_Model> homeCatagoryModels) {
        this.context = context;
        this.homeCatagoryModels = homeCatagoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleHomeCatagoryBinding binding = SingleHomeCatagoryBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        Glide.with(context).load(homeCatagoryModels.get(position).getImage()).into(holder.binding.image);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("cat_id", SubCatagoryActivity.cat_id);
                b.putString("sub_cat_id", homeCatagoryModels.get(position).getId());
                context.startActivity(new Intent(context, ProductActivity.class).putExtras(b));
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCatagoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleHomeCatagoryBinding binding;
        public MyViewHolder(@NonNull SingleHomeCatagoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
