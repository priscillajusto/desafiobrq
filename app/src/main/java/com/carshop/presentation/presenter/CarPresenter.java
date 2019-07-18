package com.carshop.presentation.presenter;

import com.carshop.data.remote.service.CarService;
import com.carshop.model.entity.CarEntity;
import com.carshop.presentation.view.activity.MainActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarPresenter {

    private CarService service;
    private MainActivity view;
    private List<CarEntity> listCar = new ArrayList<>();

    public CarPresenter(MainActivity view) {
        this.view = view;
        service = new CarService();
     }

    public void getCarList() {
        service.listCar().enqueue(new Callback<List<CarEntity>>() {
            @Override
            public void onResponse(Call<List<CarEntity>> call, Response<List<CarEntity>> response) {
                if (response.isSuccessful()) {
                    listCar = response.body();

                    if (listCar == null || listCar.size() == 0) {
                        view.showLayoutEmpty();
                    } else {
                        view.showLayoutSucess(listCar);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CarEntity>> call, Throwable t) {
                view.showLayoutError();
            }
        });
    }
}