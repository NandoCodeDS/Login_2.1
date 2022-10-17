package com.pm.login_20;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.pm.login_20.Interfaces.ProductoAPI;
import com.pm.login_20.Model.About;
import com.pm.login_20.Model.Contact;
import com.pm.login_20.Model.Gallery;
import com.pm.login_20.Model.Home;
import com.pm.login_20.Model.LogPersona;
import com.pm.login_20.Model.Log_inV;
import com.pm.login_20.Model.Rate;
import com.pm.login_20.Utils.Constants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    public static final String EXTRA_MESSAGE = "Bienvenidos a Sci High";
    ProductoAPI productoAPI;
    List<LogPersona> logPersonas;
    private String emailAdd;

    private static final String TAG = "MainActivity";
    private static final String TAG2 = "Log_ok";

    private EditText email;
    private EditText password;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener((drawerToggle));
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:{
                        Toast.makeText(MainActivity.this, "Inicio Seleccionado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.contact:{
                        Toast.makeText(MainActivity.this, "Contacto Seleccionado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Contact.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.gallery:{
                        Toast.makeText(MainActivity.this, "Galería de imagenes Seleccionada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Gallery.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.acerca_de:{
                        Toast.makeText(MainActivity.this, "Información de la APP Seleccionada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, About.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.log_in_ic:{
                        Toast.makeText(MainActivity.this, "Inicio de sesión Seleccionado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Log_inV.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.share:{
                        Toast.makeText(MainActivity.this, "Compartir Seleccionado", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.rate_usc:{
                        Toast.makeText(MainActivity.this, "Calificación Seleccionado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Rate.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        /*setContentView(R.layout.activity_main);
        Button btnLog = findViewById(R.id.btn_login);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);

        //

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String varCorreo= email.getText().toString();

                //getOne(varCorreo);
                getAll();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
    /*public void GET_UserById(View view) {
        Call<List<LogPersona>> loginCall = apiLogin.getLogin();
        loginCall.enqueue(new Callback<List<LogPersona>>() {
            @Override
            public void onResponse(Call<List<LogPersona>> call, Response<List<LogPersona>> response) {
                Log.e(TAG, "on response: " + response.body());
            }

            @Override
            public void onFailure(Call<List<LogPersona>> call, Throwable t) {
                Log.e(TAG,"on failure: "+t.getLocalizedMessage());
            }
        });
    }*/

    private void getOne(String email){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productoAPI = retrofit.create(ProductoAPI.class);
        Call<List<LogPersona>>call = productoAPI.getOne(email);
        call.enqueue(new Callback<List<LogPersona>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LogPersona>> call, Response<List<LogPersona>> response) {
                if(!response.isSuccessful()){
                    Toast toast=Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG);
                    toast.show();

                    Log.e("Response err: ",response.message());
                    return;
                }
                logPersonas=response.body();

                Log.i("prueba", String.valueOf(logPersonas));

            }

            @Override
            public void onFailure(Call<List<LogPersona>> call, Throwable t) {
                Toast toast=Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }


    private void getAll(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productoAPI = retrofit.create(ProductoAPI.class);
        Call<List<LogPersona>>call = productoAPI.getAll();
        call.enqueue(new Callback<List<LogPersona>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LogPersona>> call, Response<List<LogPersona>> response) {
                if(!response.isSuccessful()){
                    Toast toast=Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG);
                    toast.show();

                    Log.e("Response err: ",response.message());
                    return;
                }
                logPersonas=response.body();

                logPersonas.forEach(p -> {
                    int i = Log.i("Prods: ", p.getEmail().toString());
                    emailAdd=p.getEmail();
                });

                Log.i("prueba",emailAdd);

            }

            @Override
            public void onFailure(Call<List<LogPersona>> call, Throwable t) {
                Toast toast=Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }
}