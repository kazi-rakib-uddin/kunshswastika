package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.AssignUserAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityAssignBinding;
import com.example.kunshswastika.databinding.DialogAssignUserBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignActivity extends AppCompatActivity implements AssignUserAdpater.clickInterface {

    ActivityAssignBinding binding;
    MyInterface myInterface;
    User user;
    String hold_qnt;
    DialogAssignUserBinding dialogAssignUserBinding;
    Dialog dialog;
    List<Assign_User> list = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Assign");

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        hold_qnt = getIntent().getStringExtra("qnt");

        binding.btnAssing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.quantity.getText().toString().isEmpty())
                {
                    Toast.makeText(AssignActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
                }
                else if (binding.quantity.getText().toString().equals("0"))
                {
                    Toast.makeText(AssignActivity.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(hold_qnt) < Integer.parseInt(binding.quantity.getText().toString()))
                {
                    Toast.makeText(AssignActivity.this, "Insufficient E-Pin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialogAssignUserBinding = DialogAssignUserBinding.inflate(getLayoutInflater());
                    dialog = new Dialog(AssignActivity.this);
                    dialog.setContentView(dialogAssignUserBinding.getRoot());

                    dialogAssignUserBinding.rvUser.addItemDecoration(new DividerItemDecoration(AssignActivity.this,DividerItemDecoration.VERTICAL));

                    fetchUser();

                    dialog.show();

                }
            }
        });
    }

    private void fetchUser()
    {
        Call<String> call = myInterface.fetch_total_epin(user.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("0"))
                        {

                        }
                        else
                        {
                            JSONArray jsonArray = jsonObject.getJSONArray("under_user");

                            if (jsonArray.length() ==0)
                            {
                                dialog.dismiss();
                            }
                            else
                            {
                                list.clear();
                                for (int i=0; i<jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String name = jsonObject1.getString("full_name");
                                    String refer_code = jsonObject1.getString("auto_refer_code");

                                    list.add(new Assign_User(name,refer_code));
                                }

                                dialogAssignUserBinding.rvUser.setAdapter(new AssignUserAdpater(AssignActivity.this,list,AssignActivity.this));
                            }
                        }



                    } catch (JSONException e) {

                        Toast.makeText(AssignActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(AssignActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(AssignActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(String refer_code) {

        assign(refer_code);
    }


    private void assign(String refer_code)
    {
        Call<String> call = myInterface.assign_epin(user.getUser_id(),refer_code,binding.quantity.getText().toString());
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
                            Toast.makeText(AssignActivity.this, "Not Assign", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(AssignActivity.this, "Assign Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            dialog.dismiss();
                            startActivity(new Intent(AssignActivity.this,EpinDetailsActivity.class));
                            finish();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(AssignActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(AssignActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(AssignActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}