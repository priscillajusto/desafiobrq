package com.carshop.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carshop.R;
import com.carshop.model.entity.CarEntity;
import com.carshop.presentation.presenter.CarPresenter;
import com.carshop.presentation.view.RecyclerItemClickListener;
import com.carshop.presentation.view.adapter.CarAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Group groupLoading;
    private Group groupError;
    private Group groupEmpty;
    private RecyclerView recyclerView;
    private List<CarEntity> listCar = new ArrayList<>();
    private CarAdapter carAdapter = new CarAdapter(listCar);
    private CarPresenter carPresenter = new CarPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupError = findViewById(R.id.groupError);
        groupLoading = findViewById(R.id.groupLoading);
        groupEmpty = findViewById(R.id.groupEmpty);
        recyclerView = findViewById(R.id.recyclerViewListCar);
        Toolbar toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        TextView textViewToolbar = findViewById(R.id.textViewToolbar);

        textViewToolbar.setText(R.string.app_name);
        setSupportActionBar(toolbarPrincipal);

        carPresenter.getCarList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showLayoutSucess(List<CarEntity> list){
        listCar.addAll(list);

        //Configurando o Loading
        recyclerView.setVisibility(View.VISIBLE);
        groupLoading.setVisibility(View.GONE);

        //Configurando o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(carAdapter);

        //Evento de click
        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(
                    getApplicationContext(),
                    recyclerView,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            CarEntity car = listCar.get(position);

                            Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
                            intent.putExtra("id", car.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    }
            )
        );
    }

    public void showLayoutEmpty(){
        //Configurando o Loading
        groupEmpty.setVisibility(View.VISIBLE);
        groupLoading.setVisibility(View.GONE);
    }

    public void showLayoutError(){
        //Configurando o Loading
        groupError.setVisibility(View.VISIBLE);
        groupLoading.setVisibility(View.GONE);

        ImageView imageViewError = findViewById(R.id.imageViewError);
        imageViewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupError.setVisibility(View.GONE);
                groupLoading.setVisibility(View.VISIBLE);
                carPresenter.getCarList();
            }
        });
    }

}