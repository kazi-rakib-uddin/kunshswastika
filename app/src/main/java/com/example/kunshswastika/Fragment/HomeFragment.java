package com.example.kunshswastika.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunshswastika.Activity.MainActivity;
import com.example.kunshswastika.ActivityEcommers.AllCategoryActivity;
import com.example.kunshswastika.Adapter.AutoImageSliderAdapter;
import com.example.kunshswastika.Adapter.HomeBannerAdapter;
import com.example.kunshswastika.Adapter.HomeCatagoryAdapter;
import com.example.kunshswastika.Adapter.HomeFeaturedAdapter;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.HomeCatagoryModel;
import com.example.kunshswastika.Model.Product_Model;
import com.example.kunshswastika.Model.Slider_Model;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    MyInterface myInterface;

    private static String[] array_imgs = new String[] {
            "https://img.freepik.com/free-psd/horizontal-banner-online-fashion-sale_23-2148585404.jpg?w=900&t=st=1660296962~exp=1660297562~hmac=29e6c339f81ad2bddc2925c01e71608a9253dc5f61799bc59fb8e7ea80504b90",
            "https://img.freepik.com/free-psd/sale-banner-template_24972-824.jpg?w=996&t=st=1660296243~exp=1660296843~hmac=43bd834c304f476d23274de08e42de1b8856da1e24659d0ef2885945f4f5bd85",
            "https://img.freepik.com/free-vector/online-shopping-isometric-concept-shopping-cart_107791-317.jpg?w=826&t=st=1660297153~exp=1660297753~hmac=507d520dfbdaf1ff666b8c461de5ac28f0c0a871c91639b9babe50fa45e1f049"
    };

    private static String[] array_banner = new String[] {
            "https://trivenisupermarket.com/img/triveni-indian-supermarket-coming-soon.jpg",
            "https://marcado.in/images/ffff.jpg",
            "https://trivenisupermarket.com/img/what-can-you-get.jpeg"
    };

    private AutoImageSliderAdapter adapter;
    private TextView[] mDots;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private HomeCatagoryAdapter homeCatagoryAdapter;
    private HomeBannerAdapter homeBannerAdapter;
    private HomeFeaturedAdapter featuredAdapter;
    private HomeFeaturedAdapter homeRecomendedAdapter_oil;
    private List<HomeCatagoryModel> homeCatagoryModels = new ArrayList<>();
    private List<Product_Model> product_models = new ArrayList<>();
    private List<Product_Model> recent_product_models = new ArrayList<>();
    private List<HomeCatagoryModel> homeItemModels;
    private List<Slider_Model> slider_models = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false);

        initView();

        return binding.getRoot();
    }


    private void initView() {

        myInterface = ApiClient.getApiClientEcommers().create(MyInterface.class);

        homeBannerAdapter = new HomeBannerAdapter(getContext(),array_banner);

        binding.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AllCategoryActivity.class));
            }
        });

        fetchSliderBanner();
        fetchCatagory();
        fetchFeaturedProduct();
        fetchRecentProduct();

        binding.pagerSlider.addOnPageChangeListener(listener);
        startAutoSlider(slider_models.size());
        addDotsIndicator(slider_models.size(), 0);

    }



    public void addDotsIndicator(int size, int position)
    {
        mDots = new TextView[size];
        binding.linDots.removeAllViews();

        for (int i=0; i<mDots.length; i++)
        {
                mDots[i] = new TextView(getContext());
                mDots[i].setText(Html.fromHtml("&#8226"));
                mDots[i].setTextSize(35);
                mDots[i].setTextColor(getResources().getColor(R.color.transparent));

                binding.linDots.addView(mDots[i]);



        }

        if (mDots.length > 0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(adapter.getCount(),position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = binding.pagerSlider.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                binding.pagerSlider.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }



    private void fetchSliderBanner()
    {
        Call<String> call =myInterface.fetch_slider_banner();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        if (jsonArray.length() ==0)
                        {
                            Toast.makeText(getContext(), "Banner Not Fetch", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            slider_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                slider_models.add(new Slider_Model(jsonObject.getString("banner_img_url")));
                            }

                            adapter = new AutoImageSliderAdapter(getActivity(),slider_models);
                            binding.pagerSlider.setAdapter(adapter);
                            binding.pagerSlider.setCurrentItem(0);

                        }

                    } catch (JSONException e) {

                        Toast.makeText(getContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getContext(), "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
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
                            //Toast.makeText(getContext(), "Catagory Not Fetch", Toast.LENGTH_SHORT).show();
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

                            homeCatagoryAdapter = new HomeCatagoryAdapter(getActivity(),homeCatagoryModels);
                            binding.rvCatagory.setAdapter(homeCatagoryAdapter);

                        }

                    } catch (JSONException e) {

                        Toast.makeText(getContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getContext(), "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFeaturedProduct()
    {
        Call<String> call =myInterface.fetch_featured_product();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        if (jsonArray.length() ==0)
                        {
                            //Toast.makeText(getContext(), "Catagory Not Fetch", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
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

                            featuredAdapter = new HomeFeaturedAdapter(getActivity(),product_models);
                            binding.rvFeatured.setAdapter(featuredAdapter);

                        }

                    } catch (JSONException e) {

                        Toast.makeText(getContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getContext(), "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchRecentProduct()
    {
        Call<String> call =myInterface.fetch_recent_product();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        if (jsonArray.length() ==0)
                        {
                            //Toast.makeText(getContext(), "Catagory Not Fetch", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            recent_product_models.clear();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String product_id = jsonObject.getString("id");
                                String product_name = jsonObject.getString("product_name");
                                String product_image = jsonObject.getString("product_image");
                                String mrp = jsonObject.getString("mrp");
                                String selling_price = jsonObject.getString("selling_price");
                                Product_Model model = new Product_Model(product_id,product_name,product_image,selling_price,mrp);
                                recent_product_models.add(model);
                            }

                            featuredAdapter = new HomeFeaturedAdapter(getActivity(),recent_product_models);
                            binding.rvRecent.setAdapter(featuredAdapter);

                        }

                    } catch (JSONException e) {

                        Toast.makeText(getContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getContext(), "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }


}