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
import com.example.kunshswastika.Model.Request_User_Model;
import com.example.kunshswastika.Model.Requested_Model;
import com.example.kunshswastika.databinding.SingleRequestUserListBinding;
import com.example.kunshswastika.databinding.SingleRequestedListBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RequestedAdpater extends RecyclerView.Adapter<RequestedAdpater.ViewHolder> {

    private Context context;
    private List<Requested_Model> request_user_models;
    clickInterface clickInterface;

    public RequestedAdpater(Context context, List<Requested_Model> request_user_models, clickInterface clickInterface) {
        this.context = context;
        this.request_user_models = request_user_models;
        this.clickInterface = clickInterface;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleRequestedListBinding binding = SingleRequestedListBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(request_user_models.get(position).getName());
        holder.binding.quantity.setText("Quantity : "+request_user_models.get(position).getQuantity());

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
        
        
        holder.binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (Integer.parseInt(RequrestedEpinActivity.total_epin) < Integer.parseInt(request_user_models.get(position).getQuantity()))
                {
                    Toast.makeText(context, "Insufficient E-Pin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    clickInterface.onClickAccept(request_user_models.get(position).getId(), request_user_models.get(position).getU_id(), request_user_models.get(position).getQuantity());
                }
            }
        });


        holder.binding.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickInterface.onClickReject(request_user_models.get(position).getId());
            }
        });


    }

    public interface clickInterface
    {
        void onClickAccept(String id, String u_id, String quantity);
        void onClickReject(String id);
    }

    @Override
    public int getItemCount() {
        return request_user_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleRequestedListBinding binding;

        public ViewHolder(@NonNull SingleRequestedListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
