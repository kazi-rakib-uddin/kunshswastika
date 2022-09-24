package com.example.kunshswastika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.databinding.SingleAssingUserListBinding;

import java.util.List;

public class EpinRequestAdpater extends RecyclerView.Adapter<EpinRequestAdpater.ViewHolder> {

    private Context context;
    private List<Assign_User> assign_users;
    clickInterface clickInterface;

    public EpinRequestAdpater(Context context, List<Assign_User> assign_users, clickInterface clickInterface) {
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
    public void onBindViewHolder(@NonNull EpinRequestAdpater.ViewHolder holder, int position) {

        holder.binding.txtName.setText(assign_users.get(position).getName());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickInterface.OnClick(assign_users.get(position).getRefer_code());
            }
        });

    }


    @Override
    public int getItemCount() {
        return assign_users.size();
    }

    public interface clickInterface
    {
        void OnClick(String refer_code);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleAssingUserListBinding binding;

        public ViewHolder(@NonNull SingleAssingUserListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
