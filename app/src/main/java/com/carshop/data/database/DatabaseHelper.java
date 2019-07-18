package com.carshop.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASENAME = "app";

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Criar tabela
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS cart(idCarService INTERGER, name VARCHAR, amountTotal INTERGER, amountAdd INTERGER, price REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
