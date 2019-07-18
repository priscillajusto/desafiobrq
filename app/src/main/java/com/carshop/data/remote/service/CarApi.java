package com.carshop.data.remote.service;

import com.carshop.model.entity.CarEntity;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarApi {
    @GET("carro/")
    Call<List<CarEntity>> listCar();

    @GET("carro/{id}/")
    Call<CarEntity> findCarById(@Path("id") String id);
}
