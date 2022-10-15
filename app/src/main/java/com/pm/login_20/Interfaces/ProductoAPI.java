package com.pm.login_20.Interfaces;

import com.pm.login_20.Model.LogPersona;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductoAPI {
    @GET("/login/{email}")
    Call<List<LogPersona>> getOne(@Path("email")String email);

    @GET("/login/")
    Call<List<LogPersona>>getAll();

    @POST("/login/")
    Call<LogPersona> addEmpleado(@Body LogPersona logPersona);
}
