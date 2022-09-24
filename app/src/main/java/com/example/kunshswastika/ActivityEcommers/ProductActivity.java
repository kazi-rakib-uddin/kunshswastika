package com.example.kunshswastika.ActivityEcommers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.HomeFeaturedAdapter;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Product_Model;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityProductBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    private MyInterface myInterface;
    private ProgressDialog progressDialog;
    private String cat_id, sub_cat_id;
    private List<Product_Model> product_models = new ArrayList<>();
    private HomeFeaturedAdapter featuredAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {

        myInterface = ApiClient.getApiClientEcommers().create(MyInterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        cat_id = getIntent().getStringExtra("cat_id");
        sub_cat_id = getIntent().getStringExtra("sub_cat_id");

        fetchProduct();

        featuredAdapter = new HomeFeaturedAdapter(ProductActivity.this,product_models);
        binding.rvProduct.setAdapter(featuredAdapter);

        binding.searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count==0){
                    binding.rvProduct.setVisibility(View.GONE);
                    binding.notFound.setVisibility(View.VISIBLE);
                    //clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    binding.rvProduct.setVisibility(View.VISIBLE);
                    binding.notFound.setVisibility(View.GONE);
                    fetchProduct();
                    //clear.setVisibility(View.GONE);
                }else{
                    filter(s.toString());
                    //clear.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    public void filter(String query) {
        ArrayList<Product_Model> filteredList = new ArrayList<>();
        for (Product_Model model : product_models) {
            if(model.getProduct_name().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(model);
            }
        }

        if (filteredList.isEmpty())
        {
            binding.rvProduct.setVisibility(View.GONE);
            binding.notFound.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Medicine Not Found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            binding.rvProduct.setVisibility(View.VISIBLE);
            binding.notFound.setVisibility(View.GONE);
            featuredAdapter.filterList(filteredList);
        }

    }

    private void fetchProduct()
    {
        Call<String> call = myInterface.fetch_product(cat_id,sub_cat_id);
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
                            binding.notFound.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            binding.notFound.setVisibility(View.GONE);
                            product_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String product_id = jsonObject.getString("id");
                                String product_name = jsonObject.getString("product_name");
                                String product_image = jsonObject.getString("product_image");
                                String mrp = jsonObject.getString("mrp");
                                String selling_price = jsonObject.getString("selling_price");
                                Product_Model model = new Product_Model(product_id,product_name,product_image,selling_price,mrp);
                                product_models.add(model);
                            }

                            featuredAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(ProductActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(ProductActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(ProductActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}