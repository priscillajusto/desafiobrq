package com.carshop.presentation.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.carshop.R;
import com.carshop.model.entity.CartEntity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<CartEntity> listCar;

    public CartAdapter(List<CartEntity> listCar) {
        this.listCar = listCar;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_cart, parent, false);

        return new CartAdapter.MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartEntity car = listCar.get(position);

        holder.nameTextView.setText(car.getName());
        holder.amountTextView.setText(car.getAmountAdd() + " x");

        try{
            Locale myLocal = new Locale("pt", "BR");
            String valueFormat = NumberFormat.getCurrencyInstance(myLocal).format(car.getPrice());
            holder.priceTextView.setText(valueFormat);
        }catch (Exception e){

            Log.d("Ocorreu um erro na conversão de moeda", e.getMessage());
            holder.priceTextView.setText("R$ 0,00");
        }
    }

    @Override
    public int getItemCount() {
        return listCar.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView amountTextView;
        TextView priceTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
