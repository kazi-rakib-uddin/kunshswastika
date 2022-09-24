package com.example.kunshswastika.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.ViewLevelDetailsAdpater;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Level_Model;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityLevelUserBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelUserActivity extends AppCompatActivity {

    ActivityLevelUserBinding binding;
    User user;
    MyInterface myInterface;
    ViewLevelDetailsAdpater adpater;
    ProgressDialog progressDialog;
    List<Level_Model> level_models = new ArrayList<>();
    String hold_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLevelUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Level");

        user = new User(this);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        hold_level = getIntent().getStringExtra("level");

        fetchLevel();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchLevel()
    {
        Call<String> call = myInterface.fetch_lable(user.getUser_id(),hold_level);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response.body());

                        if (jsonArray.length() ==0)
                        {
                            Toast.makeText(LevelUserActivity.this, "No User Found", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            level_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                try {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String full_name = jsonObject.getString("user_name");

                                    Level_Model model = new Level_Model(full_name);
                                    level_models.add(model);

                                } catch (JSONException e) {

                                    Toast.makeText(LevelUserActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                            binding.rvLevelUser.setAdapter(new ViewLevelDetailsAdpater(LevelUserActivity.this,level_models));
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(LevelUserActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
                else
                {
                    Toast.makeText(LevelUserActivity.this, "no response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(LevelUserActivity.this, "Slow Netword", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}