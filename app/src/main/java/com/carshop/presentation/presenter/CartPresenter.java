package com.carshop.presentation.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.carshop.data.database.CartDataBase;
import com.carshop.model.entity.CartEntity;
import com.carshop.presentation.view.activity.CartActivity;
import com.carshop.presentation.view.activity.MainActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartPresenter {

    CartDataBase cartDataBase;
    CartActivity view;

    public CartPresenter(CartActivity view) {
        this.view = view;
        cartDataBase = new CartDataBase(view);
    }

    public void getCartList(){
        List<CartEntity> listCarCart = cartDataBase.listCarCart();

        if(listCarCart == null){
            view.showLayoutEmpty();
        } else {
            view.showLayoutSucess(listCarCart);
        }
    }

    public void deleteCarToCart(int id){
        boolean sucess = cartDataBase.removeCarDatabase(id);

        if(sucess){
            getCartList();
            Toast.makeText(view, "Carro excluído com sucesso", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(view, "Ocorreu um erro ao excluir", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean buyCar(double value){
        if(value <= 100000) {
            boolean sucess = cartDataBase.buyCarDatabase();

            if (sucess){
                Toast.makeText(view, "Carro comprado com sucesso!", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast.makeText(view, "Ocorreu um erro ao comprar o carro", Toast.LENGTH_LONG).show();
                return false;
            }
        } else{
            Toast.makeText(view, "O valor limite é R$ 100.000,00", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public double calculateValueTotal(List<CartEntity> listCart) {
        double total = 0;

        for (int i = 0; i < listCart.size(); i++) {
            CartEntity cartEntity = listCart.get(i);;
            total = total + (cartEntity.getAmountAdd() * cartEntity.getPrice());
        }

        return total;
    }

    public String getMask(double total){
        Locale myLocal = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(myLocal).format(total);
    }
}
