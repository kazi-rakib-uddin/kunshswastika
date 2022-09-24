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
import com.example.kunshswastika.Model.Transaction_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.SingleLevelBinding;
import com.example.kunshswastika.databinding.SingleTransastionListBinding;

import java.util.List;

public class TransactionAdpater extends RecyclerView.Adapter<TransactionAdpater.ViewHolder> {

    private Context context;
    private List<Transaction_Model> transaction_models;

    public TransactionAdpater(Context context, List<Transaction_Model> transaction_models) {
        this.context = context;
        this.transaction_models = transaction_models;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleTransastionListBinding binding = SingleTransastionListBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.amount.setText("₹"+transaction_models.get(position).getAmount());
        holder.binding.type.setText(transaction_models.get(position).getType());
        holder.binding.date.setText(transaction_models.get(position).getDate());

        if (transaction_models.get(position).getLevel().equals("null"))
        {
            holder.binding.level.setVisibility(View.GONE);
        }
        else {
            holder.binding.level.setVisibility(View.VISIBLE);
            holder.binding.level.setText("Level  " + transaction_models.get(position).getLevel());
        }

        if (transaction_models.get(position).getName().equals(""))
        {
            holder.binding.name.setVisibility(View.GONE);
        }
        else {
            holder.binding.name.setVisibility(View.VISIBLE);
            holder.binding.name.setText(transaction_models.get(position).getName());
        }


    }


    @Override
    public int getItemCount() {
        return transaction_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleTransastionListBinding binding;

        public ViewHolder(@NonNull SingleTransastionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
