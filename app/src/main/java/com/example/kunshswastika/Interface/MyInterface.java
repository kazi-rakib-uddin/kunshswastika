package com.example.kunshswastika.Interface;

import com.example.kunshswastika.Model.Level_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<String> login(@Field("mobile") String mobile, @Field("password") String password);


    @POST("sponsors.php")
    Call<String> fetch_sponsors();

    @FormUrlEncoded
    @POST("fetch_refer.php")
    Call<String> fetch_refer(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_user.php")
    Call<String> fetch_user(@Field("refer_id") String refer_id);


    @FormUrlEncoded
    @POST("fetch_user_under_parent.php")
    Call<String> fetch_user_under_parent(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(@Field("user_name") String user_name, @Field("name") String name, @Field("email") String email, @Field("mobile") String mobile,
                       @Field("password") String password, @Field("under_by") String under_by);

    @FormUrlEncoded
    @POST("check_refer_code.php")
    Call<String> check_refer_code(@Field("refer_code") String refer_code);

    @FormUrlEncoded
    @POST("fetch_under_lable_user.php")
    Call<String> fetch_lable(@Field("under_by") String under_by, @Field("level") String level);

    @FormUrlEncoded
    @POST("fetch_total_epin.php")
    Call<String> fetch_total_epin(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_request_user.php")
    Call<String> fetch_request_user(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("epin_request.php")
    Call<String> epin_request(@Field("user_id") String user_id, @Field("under_by") String under_by, @Field("quantity") String quantity);

    @FormUrlEncoded
    @POST("assign_epin.php")
    Call<String> assign_epin(@Field("user_id") String user_id, @Field("under_by_id") String under_by_id, @Field("quantity") String quantity);

    @FormUrlEncoded
    @POST("fetch_request_epin.php")
    Call<String> fetch_request_epin(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("active_by_epin.php")
    Call<String> active_by_epin(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("active_club_membership.php")
    Call<String> active_club_membership(@Field("user_id") String user_id, @Field("u_id") String u_id);

    @FormUrlEncoded
    @POST("fetch_active_status.php")
    Call<String> fetch_active_status(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_transaction_history.php")
    Call<String> fetch_transaction_history(@Field("user_id") String user_id);


    @GET("fetch_club_membership_amount.php")
    Call<String> fetch_club_membership_amount();

    @FormUrlEncoded
    @POST("fetch_requested_epin.php")
    Call<String> fetch_requested_epin(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("epin_accept.php")
    Call<String> epin_accept(@Field("id") String id, @Field("u_id") String u_id, @Field("quantity") String quantity,
                             @Field("my_refer_code") String my_refer_code);

    @FormUrlEncoded
    @POST("epin_reject.php")
    Call<String> epin_reject(@Field("id") String id);

    @FormUrlEncoded
    @POST("fetch_direct_joining_user.php")
    Call<String> fetch_direct_joining_user(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_my_team.php")
    Call<String> fetch_my_team(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_total_earning.php")
    Call<String> fetch_total_earning(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("withdraw_request_amount.php")
    Call<String> withdraw_request_amount(@Field("user_id") String user_id, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("fetch_withdraw_request.php")
    Call<String> fetch_withdraw_request(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_bank_details.php")
    Call<String> fetch_bank_details(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("add_bank_details.php")
    Call<String> add_bank_details(@Field("user_id") String user_id, @Field("user_name") String user_name, @Field("bank_name") String bank_name,
                                  @Field("ifsc_code") String ifsc_code, @Field("account_no") String account_no);

    //Ecommerce Api
    @GET("fetch_slider_banner.php")
    Call<String> fetch_slider_banner();

    @GET("fetch_catagory.php")
    Call<String> fetch_catagory();

    @GET("fetch_featured_product.php")
    Call<String> fetch_featured_product();

    @GET("fetch_recent_product.php")
    Call<String> fetch_recent_product();

    @FormUrlEncoded
    @POST("fetch_sub_cat.php")
    Call<String> fetch_sub_category(@Field("cat_id") String cat_id);

    @FormUrlEncoded
    @POST("fetch_single_product.php")
    Call<String> fetch_single_product(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("fetch_product.php")
    Call<String> fetch_product(@Field("cat_id") String cat_id, @Field("sub_cat_id") String sub_cat_id);

    @FormUrlEncoded
    @POST("add_to_cart.php")
    Call<String> add_to_cart(@Field("product_id") String product_id, @Field("user_id") String user_id,
                             @Field("price") String price, @Field("count") String count, @Field("total_price") String total_price);

}
