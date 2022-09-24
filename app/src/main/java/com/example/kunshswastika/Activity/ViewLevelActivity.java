package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.ViewLevelAdpater;
import com.example.kunshswastika.Adapter.ViewLevelDetailsAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Level_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.databinding.ActivityViewLevelBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLevelActivity extends AppCompatActivity {

    ActivityViewLevelBinding binding;
    MyInterface myInterface;
    ProgressDialog progressDialog;
    ViewLevelDetailsAdpater viewLevelAdpater;
    ViewLevelAdpater levelAdpater;
    User user;
    String [] lebel = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Level");

        user = new User(this);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        levelAdpater = new ViewLevelAdpater(this,lebel);
        binding.rvLevel.setAdapter(levelAdpater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}