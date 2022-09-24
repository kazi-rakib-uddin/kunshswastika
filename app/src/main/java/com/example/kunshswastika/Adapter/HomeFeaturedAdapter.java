package com.example.kunshswastika.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kunshswastika.ActivityEcommers.SinglePageActivity;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.HomeCatagoryModel;
import com.example.kunshswastika.Model.Product_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.SingleRecomendedItemBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFeaturedAdapter extends RecyclerView.Adapter<HomeFeaturedAdapter.MyViewHolder> {

    private Context context;
    private List<Product_Model> product_models;
    private ProgressDialog progressDialog;
    private User user;
    private MyInterface myInterface;

    public HomeFeaturedAdapter(Context context, List<Product_Model> product_models) {
        this.context = context;
        this.product_models = product_models;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        user = new User(context);
        myInterface = ApiClient.getApiClientEcommers().create(MyInterface.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleRecomendedItemBinding binding = SingleRecomendedItemBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(product_models.get(position).getProduct_name());
        Glide.with(context).load(product_models.get(position).getProduct_image()).into(holder.binding.image);
        holder.binding.price.setText("₹"+product_models.get(position).getSelling_price());
        holder.binding.mrp.setText("₹"+product_models.get(position).getMrp());
        holder.binding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("product_id",product_models.get(position).getProduct_id());
                context.startActivity(new Intent(context, SinglePageActivity.class).putExtras(b));
            }
        });

        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String product_id = product_models.get(position).getProduct_id();
                String price = product_models.get(position).getSelling_price();
                String quantity = "1";
                String total_price =product_models.get(position).getSelling_price();

                addToCart(product_id,quantity,price,total_price);
            }
        });


    }

    public void filterList(ArrayList<Product_Model> filterdNames) {
        this.product_models = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return product_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleRecomendedItemBinding binding;
        public MyViewHolder(@NonNull SingleRecomendedItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    private void addToCart(String product_id, String quantity, String price, String total_price)
    {
        Call<String> call = myInterface.add_to_cart(product_id,user.getU_id(),price,quantity,total_price);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {
                            Toast.makeText(context, "Add to Cart", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(context, "Not Add", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(context, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(context, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(context, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
