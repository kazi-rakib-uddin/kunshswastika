package com.example.kunshswastika.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.Model.Request_User_Model;
import com.example.kunshswastika.databinding.SingleAssingUserListBinding;
import com.example.kunshswastika.databinding.SingleRequestUserListBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RequestUserAdpater extends RecyclerView.Adapter<RequestUserAdpater.ViewHolder> {

    private Context context;
    private List<Request_User_Model> request_user_models;

    public RequestUserAdpater(Context context, List<Request_User_Model> request_user_models) {
        this.context = context;
        this.request_user_models = request_user_models;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleRequestUserListBinding binding = SingleRequestUserListBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestUserAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(request_user_models.get(position).getName());
        holder.binding.quantity.setText("Quantity : "+request_user_models.get(position).getQuantity());
        holder.binding.status.setText(request_user_models.get(position).getStatus());

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

        SingleRequestUserListBinding binding;

        public ViewHolder(@NonNull SingleRequestUserListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
