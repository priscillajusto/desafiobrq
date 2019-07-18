package com.carshop.presentation.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import com.carshop.R;
import com.carshop.model.entity.CarEntity;
import com.carshop.presentation.presenter.CarDetailPresenter;
import com.squareup.picasso.Picasso;
import java.text.NumberFormat;
import java.util.Locale;

public class CarDetailActivity extends AppCompatActivity {

    private CarDetailPresenter carDetailPresenter;
    private CarEntity car;
    private Group groupSucess;
    private Group groupError;
    private Group groupLoading;
    private TextView textViewToolbar;
    private ImageView imageViewError;
    private ImageView imageViewCarDetail;
    private TextView textViewPrice;
    private TextView textViewName;
    private TextView textViewType;
    private TextView textViewDescriptionDetailCar;
    private ImageView imageViewAdd;
    private ImageView imageViewRemove;
    private TextView textViewAmountCar;
    private Button buttonDetailCar;
    private String idCar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_car);

        groupLoading = findViewById(R.id.groupLoading);
        groupSucess = findViewById(R.id.groupSucess);
        groupError = findViewById(R.id.groupError);

        Toolbar toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        textViewToolbar = findViewById(R.id.textViewToolbar);
        imageViewError = findViewById(R.id.imageViewError);
        imageViewCarDetail = findViewById(R.id.imageViewCarDetail);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewName = findViewById(R.id.textViewName);
        textViewType = findViewById(R.id.textViewType);
        textViewDescriptionDetailCar = findViewById(R.id.textViewDescriptionDetailCar);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        imageViewRemove = findViewById(R.id.imageViewRemove);
        textViewAmountCar = findViewById(R.id.textViewAmountCar);
        buttonDetailCar = findViewById(R.id.buttonDetailCar);

        carDetailPresenter = new CarDetailPresenter(this);

        Bundle dados = getIntent().getExtras();
        idCar = dados.getString("id");

        setSupportActionBar(toolbarPrincipal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

        carDetailPresenter.getCar(idCar);
    }

    public void showLayoutSucessDetailCar(CarEntity c){

        car = c;

        //Configurando o Loading
        groupLoading.setVisibility(View.GONE);
        groupSucess.setVisibility(View.VISIBLE);
        imageViewAdd.setVisibility(View.VISIBLE);

        textViewToolbar.setText(car.getNome().toUpperCase());
        Picasso.get().load(car.getImagem()).error(R.drawable.ic_error).into(imageViewCarDetail);
        textViewName.setText(car.getNome());
        textViewType.setText(car.getMarca());
        textViewDescriptionDetailCar.setText(car.getDescricao());

        int carTotalInt = car.getQuantidadeInt() - carDetailPresenter.getAmountCarInCart(car.getIdInt());
        switch (carTotalInt){
            case 0:
                textViewAmountCar.setVisibility(View.GONE);
                imageViewAdd.setVisibility(View.GONE);
                imageViewRemove.setVisibility(View.GONE);
                buttonDetailCar.setVisibility(View.GONE);
                break;
            case 1:
                textViewAmountCar.setText("1");
                imageViewRemove.setVisibility(View.INVISIBLE);
                imageViewAdd.setVisibility(View.INVISIBLE);
                buttonDetailCar.setVisibility(View.VISIBLE);
                break;
            default:
                textViewAmountCar.setText("1");
                imageViewRemove.setVisibility(View.INVISIBLE);
                buttonDetailCar.setVisibility(View.VISIBLE);
                break;
        }

        //Adicionando o valor com a máscara
        String valueFormat = maskAmount();
        textViewPrice.setText(valueFormat);

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarToBuy();
            }
        });

        imageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCarToBuy();
            }
        });

        buttonDetailCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int carAmount = Integer.parseInt(textViewAmountCar.getText().toString());
                insertCarInCart(car.getIdInt(), car.getNome(), car.getQuantidadeInt(), carAmount, car.getPreco());
            }
        });

    }

    public String maskAmount() {
        try{
            Locale myLocal = new Locale("pt", "BR");
            return NumberFormat.getCurrencyInstance(myLocal).format(Double.parseDouble(car.getPreco()));
        } catch (Exception e){
            Log.d("Erro", e.getMessage());
            return "R$ 0,00";
        }
    }

    @SuppressLint("SetTextI18n")
    public int addCarToBuy() {
        try {
            int carTotalInt = car.getQuantidadeInt() - carDetailPresenter.getAmountCarInCart(car.getIdInt());
            int carAmount = Integer.parseInt(textViewAmountCar.getText().toString());
            imageViewRemove.setVisibility(View.VISIBLE);

            if(carAmount < carTotalInt){
                carAmount++;
                textViewAmountCar.setText(Integer.toString(carAmount));

                if(carAmount == carTotalInt){
                    Toast.makeText(getApplicationContext(), "Limite máximo de carros disponíveis.", Toast.LENGTH_LONG).show();
                    imageViewAdd.setVisibility(View.INVISIBLE);
                }
            }
        }catch (Exception e){
            Log.d("Erro", e.getMessage());
            textViewAmountCar.setText("0");
        }

        return 0;
    }

    @SuppressLint("SetTextI18n")
    public int removeCarToBuy() {
        try {
            int carAmount = Integer.parseInt(textViewAmountCar.getText().toString());
            imageViewAdd.setVisibility(View.VISIBLE);

            carAmount--;
            textViewAmountCar.setText(Integer.toString(carAmount));

            if (carAmount <= 1) {
                imageViewRemove.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            Log.d("Erro", e.getMessage());
            textViewAmountCar.setText("0");
        }

        return 0;
    }

    public void insertCarInCart(int idCarService, String name, int amountTotal, int amountAdd, String price){
        boolean sucesso = carDetailPresenter.insertCarInCart(idCarService, name, amountTotal, amountAdd, price);

        if(sucesso){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            Toast.makeText(getApplicationContext(), "Carro inserido com sucesso no carrinho", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(), "Ocorreu um erro ao adicionar no carrinho", Toast.LENGTH_LONG).show();
        }
    }

    public void showLayoutErrorDetailCar(){
        //Configurando o Loading
        groupError.setVisibility(View.VISIBLE);
        groupLoading.setVisibility(View.GONE);
        groupSucess.setVisibility(View.GONE);
        imageViewAdd.setVisibility(View.INVISIBLE);
        imageViewRemove.setVisibility(View.INVISIBLE);
        buttonDetailCar.setVisibility(View.GONE);

        textViewToolbar.setText(R.string.detaiil_car);

        imageViewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupError.setVisibility(View.GONE);
                groupLoading.setVisibility(View.VISIBLE);
                groupSucess.setVisibility(View.GONE);

                carDetailPresenter.getCar(idCar);
            }
        });
    }
}
