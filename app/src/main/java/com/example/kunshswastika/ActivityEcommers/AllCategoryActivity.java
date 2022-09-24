package com.example.kunshswastika.ActivityEcommers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.HomeCatagoryAdapter;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.HomeCatagoryModel;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityAllCategoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoryActivity extends AppCompatActivity {

    private ActivityAllCategoryBinding binding;
    private MyInterface myInterface;
    private ProgressDialog progressDialog;
    private List<HomeCatagoryModel> homeCatagoryModels = new ArrayList<>();
    private HomeCatagoryAdapter homeCatagoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initView();
    }

    private void initView() {

        myInterface = ApiClient.getApiClientEcommers().create(MyInterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        fetchCatagory();
    }

    private void fetchCatagory()
    {
        Call<String> call =myInterface.fetch_catagory();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        if (jsonArray.length() ==0)
                        {
                            Toast.makeText(AllCategoryActivity.this, "Catagory Not Fetch", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            homeCatagoryModels.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String cat_id = jsonObject.getString("id");
                                String category_name = jsonObject.getString("category_name");
                                String cat_img_url = jsonObject.getString("cat_img_url");
                                homeCatagoryModels.add(new HomeCatagoryModel(cat_id,category_name,cat_img_url));
                            }

                            homeCatagoryAdapter = new HomeCatagoryAdapter(AllCategoryActivity.this,homeCatagoryModels);
                            binding.rvCatagory.setAdapter(homeCatagoryAdapter);

                        }

                    } catch (JSONException e) {

                        Toast.makeText(AllCategoryActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(AllCategoryActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(AllCategoryActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
}