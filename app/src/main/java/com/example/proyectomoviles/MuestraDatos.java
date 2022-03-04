package com.example.proyectomoviles;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MuestraDatos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MuestraDatos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String textoSinAjustar,NombreCli,ValorCuenta;
    private Button btSalir,btEnviar,btFinalizarDia,btVolver;
    private TextView maxCliente,maxCuenta;

    public MuestraDatos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MuestraDatos.
     */
    // TODO: Rename and change types and number of parameters
    public static MuestraDatos newInstance(String param1, String param2) {
        MuestraDatos fragment = new MuestraDatos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_muestra_datos, container, false);
        View main= inflater.inflate(R.layout.activity_main, container, false);
        textoSinAjustar = getArguments().getString("datos");
        String[] parts = textoSinAjustar.split("/");
        NombreCli = parts[1];
        ValorCuenta = parts[0];
        maxCliente = v.findViewById(R.id.tvPersonaMax);
        maxCuenta = v.findViewById(R.id.textView8);
        maxCuenta.setText(ValorCuenta);
        maxCliente.setText(NombreCli);
        btSalir = v.findViewById(R.id.btsalir);
        btEnviar = main.findViewById(R.id.button);
        btFinalizarDia  = main.findViewById(R.id.button2);
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btEnviar.setEnabled(true);
                btFinalizarDia.setEnabled(true);
                getActivity().finish();
            }
        });
        btVolver = v.findViewById(R.id.comandas);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}