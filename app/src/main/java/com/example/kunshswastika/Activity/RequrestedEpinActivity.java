package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.RequestUserAdpater;
import com.example.kunshswastika.Adapter.RequestedAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Request_User_Model;
import com.example.kunshswastika.Model.Requested_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityRequrestedEpinBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequrestedEpinActivity extends AppCompatActivity implements RequestedAdpater.clickInterface {

    ActivityRequrestedEpinBinding binding;
    MyInterface myInterface;
    User user;
    ProgressDialog progressDialog;
    List<Requested_Model> request_user_models = new ArrayList<>();
    public static String total_epin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequrestedEpinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Requested");

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        fetchEpin();
        fetchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchRequested()
    {
        Call<String> call = myInterface.fetch_requested_epin(user.getUser_id());
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
                            Toast.makeText(RequrestedEpinActivity.this, "No Request Found", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            request_user_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id= jsonObject.getString("id");
                                String my_refer_code= jsonObject.getString("under_id");
                                String full_name= jsonObject.getString("full_name");
                                String quantity= jsonObject.getString("quantity");
                                String u_id= jsonObject.getString("u_id");
                                String date= jsonObject.getString("date");

                                Requested_Model model = new Requested_Model(id,full_name,quantity,date,my_refer_code,u_id);
                                request_user_models.add(model);
                            }
                            binding.rvRequested.setAdapter(new RequestedAdpater(RequrestedEpinActivity.this,request_user_models,RequrestedEpinActivity.this));
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequrestedEpinActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RequrestedEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequrestedEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void fetchEpin()
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

                            total_epin = jsonObject.getString("total_epin");

                        }
                        else
                        {
                            Toast.makeText(RequrestedEpinActivity.this, "Not Fetch", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequrestedEpinActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(RequrestedEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequrestedEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickAccept(String id, String u_id, String quantity) {

        epinAccept(id,u_id,quantity);
    }

    @Override
    public void onClickReject(String id) {

        epinReject(id);
    }

    private void epinAccept(String id, String u_id, String quantity)
    {
        Call<String> call = myInterface.epin_accept(id,u_id,quantity,user.getUser_id());
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
                            Toast.makeText(RequrestedEpinActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            fetchRequested();
                        }
                        else
                        {
                            Toast.makeText(RequrestedEpinActivity.this, "Not Accepted", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequrestedEpinActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RequrestedEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequrestedEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void epinReject(String id)
    {
        Call<String> call = myInterface.epin_reject(id);
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
                            Toast.makeText(RequrestedEpinActivity.this, "Rejected", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            fetchRequested();
                        }
                        else
                        {
                            Toast.makeText(RequrestedEpinActivity.this, "Not Reject", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequrestedEpinActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RequrestedEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequrestedEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}