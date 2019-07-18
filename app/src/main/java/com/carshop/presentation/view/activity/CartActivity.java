package com.carshop.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carshop.R;
import com.carshop.model.entity.CarEntity;
import com.carshop.model.entity.CartEntity;
import com.carshop.presentation.presenter.CartPresenter;
import com.carshop.presentation.view.RecyclerItemClickListener;
import com.carshop.presentation.view.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private CartPresenter cartPresenter;
    private RecyclerView recyclerView;
    private TextView textViewValueTotal;
    private Button buttonBuy;
    private ImageView imageViewCartEmpty;
    private TextView textViewCartEmpty;
    private List<CartEntity> listCart = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);

        Toolbar toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        TextView textViewToolbar = findViewById(R.id.textViewToolbar);
        recyclerView = findViewById(R.id.recyclerViewListCart);
        buttonBuy = findViewById(R.id.buttonBuy);
        textViewValueTotal = findViewById(R.id.textViewValueTotal);
        imageViewCartEmpty = findViewById(R.id.imageViewCartEmpty);
        textViewCartEmpty = findViewById(R.id.textViewCartEmpty);

        textViewToolbar.setText(R.string.cart);
        setSupportActionBar(toolbarPrincipal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

        cartPresenter = new CartPresenter(this);
        cartPresenter.getCartList();
    }

    public void showLayoutSucess(List<CartEntity> list){
        listCart.clear();
        listCart = list;

        CartAdapter cartAdapter = new CartAdapter(listCart);

        recyclerView.setVisibility(View.VISIBLE);
        buttonBuy.setVisibility(View.VISIBLE);
        textViewValueTotal.setVisibility(View.VISIBLE);

        //Configurando o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cartAdapter);

        //Evento de click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                deleteCar(position);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }

                        }
                )
        );

        final double value = cartPresenter.calculateValueTotal(listCart);
        String valueFormat = "Total: " + cartPresenter.getMask(value);
        textViewValueTotal.setText(valueFormat);

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean sucess = cartPresenter.buyCar(value);

                if(sucess){
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
                }
            }
        });
    }

    private void deleteCar(int position) {
        CartEntity cart = listCart.get(position);
        cartPresenter.deleteCarToCart(cart.getId());
    }

    public void showLayoutEmpty(){
        recyclerView.setVisibility(View.GONE);
        buttonBuy.setVisibility(View.GONE);
        textViewValueTotal.setVisibility(View.GONE);
        imageViewCartEmpty.setVisibility(View.VISIBLE);
        textViewCartEmpty.setVisibility(View.VISIBLE);
    }
}