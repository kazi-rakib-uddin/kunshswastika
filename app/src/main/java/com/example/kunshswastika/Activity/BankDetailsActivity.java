package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.WithdrawRequestAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Bank_Details_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.Model.Withdraw_Request_Model;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityBankDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailsActivity extends AppCompatActivity {

    ActivityBankDetailsBinding binding;
    MyInterface myInterface;
    User user;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bank Details");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.userName.getText().toString().isEmpty())
                {
                    Toast.makeText(BankDetailsActivity.this, "Enter User Name", Toast.LENGTH_SHORT).show();
                }
                else if (binding.bankName.getText().toString().isEmpty())
                {
                    Toast.makeText(BankDetailsActivity.this, "Enter Bank Name", Toast.LENGTH_SHORT).show();
                }
                else if (binding.ifscCode.getText().toString().isEmpty())
                {
                    Toast.makeText(BankDetailsActivity.this, "Enter IFSC Code", Toast.LENGTH_SHORT).show();
                }
                else if (binding.accountNo.getText().toString().isEmpty())
                {
                    Toast.makeText(BankDetailsActivity.this, "Enter Account Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addBankDetails();
                }
            }
        });

        fetchBankDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchBankDetails()
    {
        Call<String> call = myInterface.fetch_bank_details(user.getU_id());
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if(jsonObject.getString("rec").equals("1")) {
                            binding.userName.setText(jsonObject.getString("user_name"));
                            binding.bankName.setText(jsonObject.getString("bank_name"));
                            binding.ifscCode.setText(jsonObject.getString("ifsc_code"));
                            binding.accountNo.setText(jsonObject.getString("account_number"));
                            progressDialog.dismiss();

                            if (!jsonObject.getString("user_name").equals(""))
                            {
                                binding.btnAdd.setText("Update Bank Details");
                            }
                            else
                            {
                                binding.btnAdd.setText("Add Bank Details");
                            }
                        }
                        else
                        {
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(BankDetailsActivity.this, "Somthing went wrong"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(BankDetailsActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(BankDetailsActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void addBankDetails()
    {
        String user_name = binding.userName.getText().toString();
        String bank_name = binding.bankName.getText().toString();
        String ifsc_code = binding.ifscCode.getText().toString();
        String account_no = binding.accountNo.getText().toString();

        Call<String> call = myInterface.add_bank_details(user.getU_id(),user_name,bank_name,ifsc_code,account_no);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if(jsonObject.getString("rec").equals("1")) {

                            fetchBankDetails();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(BankDetailsActivity.this, "Not Add", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(BankDetailsActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(BankDetailsActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(BankDetailsActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}