package com.example.kunshswastika.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kunshswastika.Model.Bank_Details_Model;
import com.example.kunshswastika.Model.Withdraw_Request_Model;
import com.example.kunshswastika.databinding.SingleBankDetailsBinding;
import com.example.kunshswastika.databinding.SingleWithdrawRequestBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BankDetailsAdpater extends RecyclerView.Adapter<BankDetailsAdpater.ViewHolder> {

    private Context context;
    private List<Bank_Details_Model> request_user_models;

    public BankDetailsAdpater(Context context, List<Bank_Details_Model> request_user_models) {
        this.context = context;
        this.request_user_models = request_user_models;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleBankDetailsBinding binding = SingleBankDetailsBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BankDetailsAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(request_user_models.get(position).getName());
        holder.binding.bankName.setText("Bank Name : "+request_user_models.get(position).getBank_name());
        holder.binding.ifscCode.setText("IFSC Code : "+request_user_models.get(position).getIfsc_code());
        holder.binding.ifscCode.setText("Account Number : "+request_user_models.get(position).getAccount_no());


    }


    @Override
    public int getItemCount() {
        return request_user_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleBankDetailsBinding binding;

        public ViewHolder(@NonNull SingleBankDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
