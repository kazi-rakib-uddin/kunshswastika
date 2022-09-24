package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private MyInterface myInterface;
    private String under_by="";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.userName.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "User Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (binding.fullName.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Full Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (binding.email.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (binding.phoneNo.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Phone Numebr is required", Toast.LENGTH_SHORT).show();
                }
                else if (binding.password.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    registerUser();
                }
            }
        });



        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });


        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.referCode.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Enter Refer Code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkReferCode();
                }
            }
        });


    }

    private void registerUser()
    {
        String user_name = binding.userName.getText().toString();
        String full_name = binding.fullName.getText().toString();
        String email = binding.email.getText().toString();
        String phone = binding.phoneNo.getText().toString();
        String password = binding.password.getText().toString();

        if (binding.referCode.getText().toString().isEmpty())
        {
            under_by = "Admin";

        }
        else
        {
            under_by = binding.referCode.getText().toString();
        }



        Call<String> call = myInterface.register(user_name,full_name,email,phone,password,under_by);
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
                            Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                        else if (jsonObject.getString("rec").equals("2"))
                        {
                            Toast.makeText(RegisterActivity.this, "User Name Already Exists", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RegisterActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "no response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, "Slow network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void checkReferCode()
    {
        Call<String> call = myInterface.check_refer_code(binding.referCode.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {

                            Toast.makeText(RegisterActivity.this, "Valid Refer Code", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Invalid Refer Code", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(RegisterActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

}