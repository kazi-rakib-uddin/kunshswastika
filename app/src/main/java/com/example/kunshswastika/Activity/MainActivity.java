package com.example.kunshswastika.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunshswastika.ActivityEcommers.HomeActivity;
import com.example.kunshswastika.ApiClient.ApiClient;
import com.example.kunshswastika.Interface.MyInterface;
import com.example.kunshswastika.Model.User;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public MenuItem item;
    User user;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    AlertDialog.Builder builder;
    MyInterface myInterface;
    ProgressDialog progressDialog;
    String total_epin, wallet_balence, membership_amount;
    TextView txt_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new User(this);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.navView.setItemIconTintList(null);


        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.user_level:
                        startActivity(new Intent(MainActivity.this, ViewLevelActivity.class));
                        break;

                    case R.id.epin_details:
                        startActivity(new Intent(MainActivity.this, EpinDetailsActivity.class));
                        break;

                    case R.id.transaction_history:
                        startActivity(new Intent(MainActivity.this, TransactionActivity.class));
                        break;

                    case R.id.requested_epin:
                        startActivity(new Intent(MainActivity.this, RequrestedEpinActivity.class));
                        break;

                    case R.id.wallet:
                        startActivity(new Intent(MainActivity.this, WalletActivity.class));
                        break;
                    case R.id.bank_details:
                        startActivity(new Intent(MainActivity.this, BankDetailsActivity.class));
                        break;
                    case R.id.ecommers:
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        break;

                    case R.id.club_membership:
                        fetchClubMemberStatus();
                        break;

                    case R.id.active_by_epin:

                        fetchActiveStatus();

                        break;

                }

                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

        binding.viewLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ViewLevelActivity.class));
            }
        });

        binding.linDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, DirectJoiningActivity.class));
            }
        });


        fetchClubMemberAmount();
        fetchMyTeam();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        item = menu.findItem(R.id.action_duty);
        MenuItemCompat.setActionView(item, R.layout.custom_wallet);
        LinearLayout notifcount = (LinearLayout) MenuItemCompat.getActionView(item);
        txt_amount = notifcount.findViewById(R.id.txt_amount);

        fetchWalletBelance(txt_amount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                user.remove();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
                break;
            case android.R.id.home:
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }

        return true;
    }


    private void activeByEpin()
    {
        Call<String> call = myInterface.active_by_epin(user.getUser_id());
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
                            Toast.makeText(MainActivity.this, "Activited", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not Activited", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void activeClubMembership()
    {
        Call<String> call = myInterface.active_club_membership(user.getUser_id(),user.getU_id());
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
                            Toast.makeText(MainActivity.this, "Membership Activited", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not Activited", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetchClubMemberAmount()
    {
        Call<String> call = myInterface.fetch_club_membership_amount();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {
                            membership_amount = jsonObject.getString("amount");

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not fetch", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWalletBelance(TextView txt_amount)
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
                            wallet_balence = jsonObject.getString("wallet");
                            txt_amount.setText(wallet_balence);

                            binding.registrationDate.setText(jsonObject.getString("register_date"));
                            binding.sponserName.setText(jsonObject.getString("name"));
                            binding.directCount.setText(jsonObject.getString("direct_refer_count"));
                            binding.clubMembershipDate.setText(jsonObject.getString("club_active_date"));
                            binding.activtionDate.setText(jsonObject.getString("epin_active_date"));
                            binding.pendingAmount.setText(jsonObject.getString("wallet"));

                            if (jsonObject.getString("club_membership").equals("Y"))
                            {
                                binding.clubMembershipStatus.setText("Yes");
                            }
                            else
                            {
                                binding.clubMembershipStatus.setText("No");
                            }

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not Activited", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMyTeam()
    {
        Call<String> call = myInterface.fetch_my_team(user.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {

                            binding.myTeam.setText(jsonObject.getString("count"));

                        }
                        else
                        {
                            binding.myTeam.setText("0");

                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTotalEarning()
    {
        Call<String> call = myInterface.fetch_total_earning(user.getU_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() !=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if (jsonObject.getString("rec").equals("1"))
                        {

                            binding.totalEarning.setText(jsonObject.getString("total_earning"));

                        }
                        else
                        {
                            binding.totalEarning.setText("0");

                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchActiveStatus()
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
                            String status = jsonObject.getString("status");
                            total_epin = jsonObject.getString("total_epin");

                            if (status.equals("Y"))
                            {
                                Toast.makeText(MainActivity.this, "Already Active", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {

                                alertDialog();

                            }

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not Activited", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchClubMemberStatus()
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
                            String club_member_status = jsonObject.getString("club_membership");
                            total_epin = jsonObject.getString("total_epin");

                            if (club_member_status.equals("Y"))
                            {
                                Toast.makeText(MainActivity.this, "Already in Member", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {

                                alertDialogClubMember();

                            }

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not Activited", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Slow Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alertDialog()
    {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Active ?")
                .setCancelable(false)
                .setPositiveButton("Active", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (total_epin.equals("0"))
                        {
                            Toast.makeText(MainActivity.this, "Insufficient E-Pin", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            activeByEpin();
                        }
                        

                    }
                })
                .setNegativeButton("No", null);
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("E-Pin Active");
        alert.show();
    }

    private void alertDialogClubMember()
    {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Active Club Membership ?")
                .setCancelable(false)
                .setPositiveButton("Active", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (Integer.parseInt(wallet_balence) < Integer.parseInt(membership_amount))
                        {
                            Toast.makeText(MainActivity.this, "Insufficient Wallet Balance", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            activeClubMembership();
                        }


                    }
                })
                .setNegativeButton("No", null);
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Club Membership");
        alert.show();
    }

}