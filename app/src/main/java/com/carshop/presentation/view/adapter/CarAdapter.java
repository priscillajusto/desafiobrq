package com.carshop.presentation.view.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.carshop.R;
import com.carshop.model.entity.CarEntity;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private List<CarEntity> listCar;

    public CarAdapter(List<CarEntity> listCar) {
        this.listCar = listCar;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_car, parent, false);

        return new MyViewHolder(itemList);

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CarEntity car = listCar.get(position);

        Picasso.get().load(car.getImagem()).error(R.drawable.ic_error).into(holder.iconImageView);
        holder.nameTextView.setText(car.getNome());
        holder.typeTextView.setText(car.getMarca());

        try{
            Locale myLocal = new Locale("pt", "BR");
            String valueFormat = NumberFormat.getCurrencyInstance(myLocal).format(Double.parseDouble(car.getPreco()));
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView nameTextView;
        TextView typeTextView;
        TextView priceTextView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            iconImageView = itemView.findViewById(R.id.iconImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);

        }
    }
}
