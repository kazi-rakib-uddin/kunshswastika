package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.AssignUserAdpater;
import com.example.kunshswastika.Adapter.EpinRequestAdpater;
import com.example.kunshswastika.Adapter.RequestUserAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Assign_User;
import com.example.kunshswastika.Model.Request_User_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityRequestEpinBinding;
import com.example.kunshswastika.databinding.DialogAssignUserBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestEpinActivity extends AppCompatActivity implements EpinRequestAdpater.clickInterface {

    ActivityRequestEpinBinding binding;
    MyInterface myInterface;
    User user;
    ProgressDialog progressDialog;
    List<Assign_User> assign_users = new ArrayList<>();
    List<Request_User_Model> request_user_models = new ArrayList<>();
    DialogAssignUserBinding dialogAssignUserBinding;
    Dialog dialog;
    String hold_qnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestEpinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request E-Pin");

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);

        hold_qnt = getIntent().getStringExtra("qnt");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        binding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.quantity.getText().toString().isEmpty())
                {
                    Toast.makeText(RequestEpinActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
                }
                else if (binding.quantity.getText().toString().equals("0"))
                {
                    Toast.makeText(RequestEpinActivity.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialogAssignUserBinding = DialogAssignUserBinding.inflate(getLayoutInflater());
                    dialog = new Dialog(RequestEpinActivity.this);
                    dialog.setContentView(dialogAssignUserBinding.getRoot());

                    dialogAssignUserBinding.rvUser.addItemDecoration(new DividerItemDecoration(RequestEpinActivity.this,DividerItemDecoration.VERTICAL));

                    fetchRequestUser();

                    dialog.show();
                }


            }
        });


        fetchRequestUserList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchRequestUser()
    {
        Call<String> call = myInterface.fetch_request_user(user.getUser_id());
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
                        }
                        else
                        {
                            assign_users.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String full_name= jsonObject.getString("full_name");
                                String auto_refer_code= jsonObject.getString("auto_refer_code");
                                assign_users.add(new Assign_User(full_name,auto_refer_code));
                            }
                            dialogAssignUserBinding.rvUser.setAdapter(new EpinRequestAdpater(RequestEpinActivity.this,assign_users,RequestEpinActivity.this));
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequestEpinActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RequestEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequestEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetchRequestUserList()
    {
        Call<String> call = myInterface.fetch_request_epin(user.getU_id());
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
                            Toast.makeText(RequestEpinActivity.this, "No Request Found", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            request_user_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String full_name= jsonObject.getString("full_name");
                                String quantity= jsonObject.getString("quantity");
                                String status= jsonObject.getString("status");
                                String date= jsonObject.getString("date");

                                Request_User_Model model = new Request_User_Model(full_name,quantity,date,status);
                                request_user_models.add(model);
                            }
                            binding.rvRequestUser.setAdapter(new RequestUserAdpater(RequestEpinActivity.this,request_user_models));
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequestEpinActivity.this, "Somthing went wrong "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RequestEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequestEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void OnClick(String refer_code) {

        request(refer_code);
    }

    private void request(String refer_code)
    {
        Call<String> call = myInterface.epin_request(user.getU_id(),refer_code,binding.quantity.getText().toString());
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
                            Toast.makeText(RequestEpinActivity.this, "Request Not Send", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(RequestEpinActivity.this, "Request Send", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            dialog.dismiss();
                            fetchRequestUserList();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(RequestEpinActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(RequestEpinActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(RequestEpinActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}