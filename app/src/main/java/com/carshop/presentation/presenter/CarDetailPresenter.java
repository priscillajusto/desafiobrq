package com.carshop.presentation.presenter;

import com.carshop.data.database.CartDataBase;
import com.carshop.data.remote.service.CarService;
import com.carshop.model.entity.CarEntity;
import com.carshop.presentation.view.activity.CarDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetailPresenter {

    private CarService service;
    private CarDetailActivity view;
    private CarEntity car;
    private CartDataBase cartDataBase;

    public CarDetailPresenter(CarDetailActivity view){
        this.view = view;
        service = new CarService();
        cartDataBase = new CartDataBase(this.view);
    }

    public void getCar(String id) {
        service.findCarById(id).enqueue(new Callback<CarEntity>() {
            @Override
            public void onResponse(Call<CarEntity> call, Response<CarEntity> response) {
                car = response.body();

                if (car == null || car.getQuantidadeInt() == 0){
                    view.showLayoutErrorDetailCar();
                } else {
                    int i = cartDataBase.amountCarInCart(car.getIdInt());
                    view.showLayoutSucessDetailCar(car);
                }
            }

            @Override
            public void onFailure(Call<CarEntity> call, Throwable t) {
                view.showLayoutErrorDetailCar();
            }
        });
    }

    public int getAmountCarInCart(int id){
        return cartDataBase.amountCarInCart(id);
    }

    public boolean insertCarInCart(int idCarService, String name, int amountTotal, int amountAdd, String price){
        try{
            cartDataBase.insertCarDatabase(idCarService, name, amountTotal, amountAdd, price);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
