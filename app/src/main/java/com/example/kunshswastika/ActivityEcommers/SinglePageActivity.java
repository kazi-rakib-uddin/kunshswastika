package com.example.kunshswastika.ActivityEcommers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kunshswastika.Adapter.SinglePageImageAdapter;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.Single_Image_Model;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivitySinglePageBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePageActivity extends AppCompatActivity {
    ActivitySinglePageBinding binding;
    private MyInterface myInterface;
    private ArrayList<Single_Image_Model> single_image_models= new ArrayList<>();
    ProgressDialog progressDialog;
    private String product_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySinglePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {

        myInterface = ApiClient.getApiClientEcommers().create(MyInterface.class);
        product_id = getIntent().getStringExtra("product_id");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        binding.rvImageSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                binding.txtCount.setText("0"+String.valueOf(position+1)+ " / "+"0"+SinglePageImageAdapter.arrayList_catagory.size());
            }

            @Override
            public void onPageSelected(int position) {

                //Toast.makeText(SinglePageActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fetchSingleProduct();
    }

    private void fetchSingleProduct()
    {
        Call<String> call = myInterface.fetch_single_product(product_id);
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
                            Toast.makeText(SinglePageActivity.this, "Product Not Found", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            String product_name = jsonObject.getString("product_name");
                            String product_desp = jsonObject.getString("product_desp");
                            String selling_price = jsonObject.getString("selling_price");
                            String mrp = jsonObject.getString("mrp");

                            JSONArray jsonArray = jsonObject.getJSONArray("image");
                            single_image_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String product_image1 = jsonObject1.getString("product_image1");
                                String product_image2 = jsonObject1.getString("product_image2");
                                String product_image3 = jsonObject1.getString("product_image3");
                                String product_image4 = jsonObject1.getString("product_image4");

                                single_image_models.add(new Single_Image_Model(product_image1));
                                single_image_models.add(new Single_Image_Model(product_image2));
                                single_image_models.add(new Single_Image_Model(product_image3));
                                single_image_models.add(new Single_Image_Model(product_image4));
                            }

                            binding.rvImageSlide.setAdapter(new SinglePageImageAdapter(SinglePageActivity.this,single_image_models));

                            binding.productName.setText(product_name);
                            binding.desc.setText(product_desp);
                            binding.price.setText("₹"+selling_price);
                            binding.mrp.setText("₹"+mrp);
                            binding.mrp.setPaintFlags(binding.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(SinglePageActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(SinglePageActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(SinglePageActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}