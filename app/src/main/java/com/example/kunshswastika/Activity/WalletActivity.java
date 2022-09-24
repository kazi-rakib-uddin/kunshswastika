package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.EpinRequestAdpater;
import com.example.kunshswastika.Adapter.WithdrawRequestAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.Model.Withdraw_Request_Model;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityWalletBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    public MenuItem item;
    TextView txt_amount;
    MyInterface myInterface;
    ActivityWalletBinding binding;
    User user;
    String wallet_bal;
    ProgressDialog progressDialog;
    List<Withdraw_Request_Model> withdraw_request_models= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User(this);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Wallet History");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        
        binding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.amount.getText().toString().isEmpty())
                {
                    Toast.makeText(WalletActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if (binding.amount.getText().toString().equals("0"))
                {
                    Toast.makeText(WalletActivity.this, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(binding.amount.getText().toString()) > Integer.parseInt(wallet_bal))
                {
                    Toast.makeText(WalletActivity.this, "Insufficient Amount", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(binding.amount.getText().toString()) <200)
                {
                    Toast.makeText(WalletActivity.this, "minimum Amount ₹200", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    withdrawRequest();
                }
            }
        });

        fetchWithdrawRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        item = menu.findItem(R.id.action_duty);
        MenuItemCompat.setActionView(item, R.layout.custom_wallet);
        LinearLayout notifcount = (LinearLayout) MenuItemCompat.getActionView(item);
        txt_amount = notifcount.findViewById(R.id.txt_amount);

        fetchWalletBelance(txt_amount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchWalletBelance(TextView txt_amount)
    {
        Call<String> call = myInterface.fetch_active_status(user.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {
                            wallet_bal = jsonObject.getString("wallet");
                            txt_amount.setText(wallet_bal);
                        }
                        else
                        {
                            txt_amount.setText("0");

                        }

                    } catch (JSONException e) {

                        Toast.makeText(WalletActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(WalletActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(WalletActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void withdrawRequest()
    {
        Call<String> call = myInterface.withdraw_request_amount(user.getU_id(), binding.amount.getText().toString());
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
                            Toast.makeText(WalletActivity.this, "Request Not Send", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(WalletActivity.this, "Request Send", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            fetchWithdrawRequest();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(WalletActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(WalletActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(WalletActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void fetchWithdrawRequest()
    {
        Call<String> call = myInterface.fetch_withdraw_request(user.getU_id());
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        if (jsonArray.length()==0)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(WalletActivity.this, "No Withdraw Request", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            withdraw_request_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String user_name= jsonObject.getString("user_name");
                                String amount= jsonObject.getString("amount");
                                String status= jsonObject.getString("status");
                                String date= jsonObject.getString("date");
                                withdraw_request_models.add(new Withdraw_Request_Model(user_name,amount,status,date));
                            }
                            binding.rvRequestUser.setAdapter(new WithdrawRequestAdpater(WalletActivity.this,withdraw_request_models));
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(WalletActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(WalletActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(WalletActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}