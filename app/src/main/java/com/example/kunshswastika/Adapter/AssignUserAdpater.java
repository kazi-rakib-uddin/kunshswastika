package com.example.kunshswastika.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.Model.Level_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.SingleAssingUserListBinding;
import com.example.kunshswastika.databinding.SingleViewDetailsBinding;

import java.util.List;

public class AssignUserAdpater extends RecyclerView.Adapter<AssignUserAdpater.ViewHolder> {

    private Context context;
    private List<Assign_User> assign_users;
    clickInterface clickInterface;

    public AssignUserAdpater(Context context, List<Assign_User> assign_users, clickInterface clickInterface) {
        this.context = context;
        this.assign_users = assign_users;
        this.clickInterface = clickInterface;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleAssingUserListBinding binding = SingleAssingUserListBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignUserAdpater.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.txtName.setText(assign_users.get(position).getName());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickInterface.onClick(assign_users.get(position).getRefer_code());
            }
        });

    }

    public interface clickInterface
    {
        void onClick(String refer_code);
    }

    @Override
    public int getItemCount() {
        return assign_users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleAssingUserListBinding binding;

        public ViewHolder(@NonNull SingleAssingUserListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
