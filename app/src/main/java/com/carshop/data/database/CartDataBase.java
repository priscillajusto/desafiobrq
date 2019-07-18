package com.carshop.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.carshop.model.entity.CarEntity;
import com.carshop.model.entity.CartEntity;

import java.util.ArrayList;
import java.util.List;

public class CartDataBase{

    private SQLiteDatabase database;

    private CartEntity cartEntity;

    public CartDataBase(Context context) {
        database = new DatabaseHelper(context).getWritableDatabase();
    }

    public boolean insertCarDatabase(int idCarService, String name, int amountTotal, int amountAdd, String price){
        try{
            database.execSQL("INSERT INTO cart(idCarService, name, amountTotal, amountAdd, price)" +
                             "VALUES(" + idCarService + ", '" + name + "', " + amountTotal + ", " + amountAdd + ", " + price + ")");

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<CartEntity> listCarCart(){
        try{
            List<CartEntity> listCart = new ArrayList<>();
            Cursor cars = database.rawQuery("SELECT rowid, idCarService, name, amountTotal, amountAdd, price FROM cart", null);

            int indexId = cars.getColumnIndex("rowid");
            int indexIdCarService = cars.getColumnIndex("idCarService");
            int indexName = cars.getColumnIndex("name");
            int indexAmountTotal = cars.getColumnIndex("amountTotal");
            int indexAmountAdd = cars.getColumnIndex("amountAdd");
            int indexPrice = cars.getColumnIndex("price");

            cars.moveToFirst();
            listCart.clear();

            do {
                cartEntity = new CartEntity(cars.getInt(indexId), cars.getInt(indexIdCarService) ,cars.getString(indexName), cars.getInt(indexAmountTotal), cars.getInt(indexAmountAdd), cars.getDouble(indexPrice));
                listCart.add(cartEntity);
            } while(cars.moveToNext());

            return listCart;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeCarDatabase(int id){
        try{
            database.execSQL("DELETE FROM cart " +
                             "WHERE rowid = " + id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean buyCarDatabase(){
        try{
            database.execSQL("DELETE FROM cart");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int amountCarInCart(int id){
        try{
            Cursor cursorSumCar = database.rawQuery("SELECT SUM(amountAdd) FROM cart WHERE idCarService = " + id, null);
            cursorSumCar.moveToFirst();
            return cursorSumCar.getInt(0);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
