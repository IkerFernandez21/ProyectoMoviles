package com.example.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private TextView txtCobro ;
    private EditText et1 ;
    private SharedPreferences preferences;
    private List<String> nombres;
    private Button btEnviar,btFinalizarDia,btVerClientes;
    private List <Clientes> clientes;
    private TareaAsinc tarea;
    private ProgressDialog pg;
    private int inicio= 0;
    public MainActivity() {
    }

    public int getInicio() {
        return inicio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List items = new ArrayList();
        txtCobro = this.findViewById(R.id.totalCobro);//añadimos el menu del restaurante
        items.add(new Articulos(R.drawable.bocataatun, "Bocadillo de atun con pimientos", 3.5));
        items.add(new Articulos(R.drawable.bocatabacon, "Bocadillo de bacon", 3));
        items.add(new Articulos(R.drawable.bocatacalamares, "Bocadillo de calamares", 4.5));
        items.add(new Articulos(R.drawable.bocatachistorra, "Bocadillo de chistorra", 3.5));
        items.add(new Articulos(R.drawable.bocatalomo, "Bocadillo de lomo", 5));


        recycler = (RecyclerView) findViewById(R.id.recilador);



        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);


        adapter = new RCV(this,items);

        recycler.setAdapter(adapter);

        btEnviar = findViewById(R.id.button);
        btFinalizarDia= findViewById(R.id.button2);
        clientes = new ArrayList();
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et1 = findViewById(R.id.etNom);
                if(et1.getText().toString().length()!=0){
                    if(txtCobro.getText().toString()!=""){//Comprobamos que haya escrito el nombre y seleccionado alguna comida
                        clientes.add(new Clientes(et1.getText().toString(),Double.parseDouble(txtCobro.getText().toString())));
                        et1.setText("");
                        txtCobro.setText("0");



                    }else{
                        Toast.makeText(MainActivity.this,"Debes seleccionar algun articulo",Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(MainActivity.this,"Debes introducir un nombre",Toast.LENGTH_LONG).show();
                }
            }
        });
        btFinalizarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(calcularDatosMaxCliente().equals("No hay clientes")){
                    Toast.makeText(MainActivity.this, "Debes añadir algun cliente, esta vacio!", Toast.LENGTH_SHORT).show();
                }else{
                    tareaAsinc();
                    try{
                        Thread.sleep(3000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String nomMaxCliente = calcularDatosMaxCliente();
                    double numMaxCuenta = calcularDatosMaxCuenta();
                    String DatosTotales = numMaxCuenta+"/"+nomMaxCliente;
                    MuestraDatos md = new MuestraDatos();
                    Bundle args = new Bundle();
                    args.putString("datos", DatosTotales);
                    md.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.MainLayout, md)
                            .commit();
                    btEnviar.setEnabled(false);
                    btFinalizarDia.setEnabled(false);
                }



            }
        });


    }

    public List<String> getNombres() {
        return nombres;
    }

    private String calcularDatosMaxCliente() {
        HashMap<String, Double> NomCuenta = new HashMap<String, Double>();
        for (Clientes c:clientes) {
            NomCuenta.put(c.getNombre(),c.getTotalGastado());
        }
        if(clientes.size()>0){
            double maxValueInMap=(Collections.max(NomCuenta.values()));
            for (Map.Entry<String, Double> entry : NomCuenta.entrySet()) {

                if (entry.getValue()==maxValueInMap) {
                    return entry.getKey();
                }
            }

        }

        return "No hay clientes";




    }

    private double calcularDatosMaxCuenta() {
        List <Double> nums= new ArrayList<Double>();
        for (Clientes c:clientes) {
            nums.add(c.getTotalGastado());
        }
        return Collections.max(nums);
    }


    private void tareaAsinc() {
        pg = new ProgressDialog(MainActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pg.setMessage("Calculando ganancias...");
        pg.setCancelable(false);
        pg.setMax(100);
        tarea = new TareaAsinc();
        tarea.execute();

    }
    private class TareaAsinc extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            dormir();
            publishProgress(0);
            dormir();
            publishProgress(7);
            dormir();
            publishProgress(28);
            dormir();
            publishProgress(47);
            dormir();
            publishProgress(81);
            dormir();
            publishProgress(100);
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            pg.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            pg.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    TareaAsinc.this.cancel(true);
                }
            });
            pg.setProgress(0);
            pg.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                pg.dismiss();
                Toast.makeText(MainActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }
    }

    private void dormir() {
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}