package com.example.kunshswastika.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kunshswastika.Activity.RequrestedEpinActivity;
import com.example.kunshswastika.Model.Requested_Model;
import com.example.kunshswastika.Model.Withdraw_Request_Model;
import com.example.kunshswastika.databinding.SingleRequestedListBinding;
import com.example.kunshswastika.databinding.SingleWithdrawRequestBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WithdrawRequestAdpater extends RecyclerView.Adapter<WithdrawRequestAdpater.ViewHolder> {

    private Context context;
    private List<Withdraw_Request_Model> request_user_models;

    public WithdrawRequestAdpater(Context context, List<Withdraw_Request_Model> request_user_models) {
        this.context = context;
        this.request_user_models = request_user_models;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleWithdrawRequestBinding binding = SingleWithdrawRequestBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WithdrawRequestAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(request_user_models.get(position).getName());
        holder.binding.status.setText(request_user_models.get(position).getStatus());
        holder.binding.amount.setText("Amount : "+request_user_models.get(position).getAmount());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(request_user_models.get(position).getDate());
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            String f_date = fmtOut.format(date);
            Date date1 = fmtOut.parse(f_date);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd,LLL yyyy");
            String format_date = simpleDateFormat.format(date1);
            holder.binding.date.setText(format_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        



    }


    @Override
    public int getItemCount() {
        return request_user_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleWithdrawRequestBinding binding;

        public ViewHolder(@NonNull SingleWithdrawRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
