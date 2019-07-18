package com.carshop.data.remote.service;

import com.carshop.model.entity.CarEntity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarService {

    //CarEntity car = new CarEntity();

    public Retrofit serviceConnector() {
        return new Retrofit.Builder()
                .baseUrl("http://desafiobrq.herokuapp.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<List<CarEntity>> listCar(){
        return serviceConnector().create(CarApi.class).listCar();
    }

    public Call<CarEntity> findCarById(String id){
        return serviceConnector().create(CarApi.class).findCarById(id);
    }
}
