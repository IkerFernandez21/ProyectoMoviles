package com.example.proyectomoviles;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RCV extends RecyclerView.Adapter<RCV.RVCViewHolder> {
    private List<Articulos> items;

    private LayoutInflater ly;
    private int precio,precioTotal;
    private MainActivity main;
    private TextView txPrecioTotal;





    public static class RVCViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView precio;

        public RVCViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imageViewRow);
            nombre = (TextView) v.findViewById(R.id.textViewRow);
            precio = (TextView) v.findViewById(R.id.textViewRowPrecio);


        }
    }

    public RCV(Context context, List<Articulos> items) {
        this.items = items;
        this.ly = LayoutInflater.from(context);
        this.main= (MainActivity) context;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    @Override
    public RVCViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.articulos_row, viewGroup, false);
        return new RVCViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVCViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.precio.setText(String.valueOf(items.get(i).getPrecio()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio = Integer.parseInt((String) viewHolder.precio.getText());
                precioTotal+=precio;
                txPrecioTotal = main.findViewById(R.id.totalCobro);
                txPrecioTotal.setText(""+precioTotal);

              /*   txPrecioTotal = (TextView) view.findViewById(R.id.totalCobro);
                 txPrecioTotal.setText(""+precioTotal);

*/
            }
        });
    }
}

