package com.example.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private TextView txtCobro ;
    private Button btEnviar;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List items = new ArrayList();
        txtCobro = this.findViewById(R.id.totalCobro);
        items.add(new Articulos(R.drawable.platano, "Angel Beats", 230));
        items.add(new Articulos(R.drawable.platano, "Death Note", 456));
        items.add(new Articulos(R.drawable.platano, "Fate Stay Night", 342));
        items.add(new Articulos(R.drawable.platano, "Welcome to the NHK", 645));
        items.add(new Articulos(R.drawable.platano, "Suzumiya Haruhi", 459));

// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recilador);


// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new RCV(this,items);
        recycler.setAdapter(adapter);

        btEnviar = findViewById(R.id.button);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }


}