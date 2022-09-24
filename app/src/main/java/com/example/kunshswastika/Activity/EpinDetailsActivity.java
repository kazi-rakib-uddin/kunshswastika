package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.AssignUserAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityEpinDetailsBinding;
import com.example.kunshswastika.databinding.DialogAssignUserBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpinDetailsActivity extends AppCompatActivity {

    ActivityEpinDetailsBinding binding;
    User user;
    MyInterface myInterface;
    ProgressDialog progressDialog;
    List<Assign_User> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEpinDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("E-Pin Details");

        user = new User(this);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        binding.btnAssing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.totalQnt.getText().toString().equals("0"))
                {
                    Toast.makeText(EpinDetailsActivity.this, "Insufficient E-Pin", Toast.LENGTH_SHORT).show();
                }
                else {

                    Bundle b = new Bundle();
                    b.putString("qnt", binding.totalQnt.getText().toString());
                    startActivity(new Intent(EpinDetailsActivity.this, AssignActivity.class).putExtras(b));

                }

            }
        });


        binding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("qnt",binding.totalQnt.getText().toString());
                startActivity(new Intent(EpinDetailsActivity.this, RequestEpinActivity.class).putExtras(b));

            }
        });

        fetchTotalEpin();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchTotalEpin()
    {
        Call<String> call = myInterface.fetch_total_epin(user.getUser_id());
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("0"))
                        {
                            binding.totalQnt.setText("0");
                            progressDialog.dismiss();
                        }
                        else
                        {
                            binding.totalQnt.setText(jsonObject.getString("total_epin"));

                        }

                        progressDialog.dismiss();

                    } catch (JSONException e) {

                        Toast.makeText(EpinDetailsActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(EpinDetailsActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(EpinDetailsActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


}