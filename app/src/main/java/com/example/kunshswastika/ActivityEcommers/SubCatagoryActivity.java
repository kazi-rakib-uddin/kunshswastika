package com.example.kunshswastika.ActivityEcommers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.HomeCatagoryAdapter;
import com.example.kunshswastika.Adapter.SubCatagoryAdapter;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.HomeCatagoryModel;
import com.example.kunshswastika.Model.Sub_Catagory_Model;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivitySubCatagoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCatagoryActivity extends AppCompatActivity {

    ActivitySubCatagoryBinding binding;
    private MyInterface myInterface;
    private SubCatagoryAdapter adapter;
    private List<Sub_Catagory_Model> sub_catagory_models = new ArrayList<>();
    ProgressDialog progressDialog;
    public static String cat_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubCatagoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {

        myInterface = ApiClient.getApiClientEcommers().create(MyInterface.class);
        cat_id = getIntent().getStringExtra("cat_id");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        fetchSubCatagory();

    }

    private void fetchSubCatagory()
    {
        Call<String> call =myInterface.fetch_sub_category(cat_id);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        if (jsonArray.length() ==0)
                        {
                            binding.notFound.setVisibility(View.VISIBLE);
                            binding.notFound.setText("Not Found");
                            progressDialog.dismiss();
                        }
                        else
                        {
                            binding.notFound.setVisibility(View.GONE);
                            sub_catagory_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String sub_cat_id = jsonObject.getString("id");
                                String cat_id = jsonObject.getString("category_id");
                                String sub_category_name = jsonObject.getString("subcat_name");
                                String sub_cat_img_url = jsonObject.getString("sub_cat_img_url");
                                sub_catagory_models.add(new Sub_Catagory_Model(sub_cat_id,cat_id,sub_category_name,sub_cat_img_url));
                            }

                            adapter = new SubCatagoryAdapter(SubCatagoryActivity.this,sub_catagory_models);
                            binding.rvSubCatagory.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(SubCatagoryActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(SubCatagoryActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(SubCatagoryActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}