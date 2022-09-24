package com.example.kunshswastika.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private MyInterface myInterface;
    private User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);


        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);

        if (user.getUser_id() !="")
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.phoneNo.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
                }
                else if (binding.password.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    login();
                }
            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }

    private void login() {
        Call<String> call = myInterface.login(binding.phoneNo.getText().toString(), binding.password.getText().toString());
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            user.setUser_id(jsonObject.getString("refer_code"));
                            user.setU_id(jsonObject.getString("user_id"));
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid Mobile Number & Password", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(LoginActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "no response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


        });

    }

}