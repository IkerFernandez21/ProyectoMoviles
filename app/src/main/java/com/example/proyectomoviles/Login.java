package com.example.proyectomoviles;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Login extends AppCompatActivity {
    private SQLiteDatabase bd;
    private SharedPreferences preferences;
    private DatabaseHelper databaseHelper;
    private Usuarios user;
    private Dialog alerta;
    private String nombre,contraseña,nom,pas;
    EditText username, password, reg_username, reg_password,
            reg_firstName, reg_lastName, reg_email, reg_confirmemail;
    Button login, signUp, reg_register;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        rememberMe = findViewById(R.id.rememberMe);
        preferences = getSharedPreferences("PrefrenciasCorreo",Context.MODE_PRIVATE);

        databaseHelper = new DatabaseHelper(this);
        String CorreoAlmacenado = preferences.getString("email","");
        if (!CorreoAlmacenado.equals("")){
            username.setText(CorreoAlmacenado);
            rememberMe.setChecked(true);
        }
        ClickLogin();



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });


    }






    private void ClickLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().trim().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(view, "Rellena los campos",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    txtInLayoutUsername.setError("Usuario no escrito");
                } else {
                    //Here you can write the codes for checking username
                }
                if (password.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Rellena los campos",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    txtInLayoutPassword.setError("Contraseña no escrita");
                } else {
                    //Here you can write the codes for checking password
                }



                verifyFromSQLite(username.getText().toString(),password.getText().toString());

            }

        });

    }
    private void verifyFromSQLite(String email,String pass) {

        if (databaseHelper.checkUser(email,pass)) {
            Intent accountsIntent = new Intent(this, MainActivity.class);
            accountsIntent.putExtra("email", email);
            if(rememberMe.isChecked()){
                guardarCorreo();
            }else if(!rememberMe.isChecked()){
                borrarCorreo();
            }
            startActivity(accountsIntent);
        } else {
            // Snack Bar to show success message that record is wrong
           Toast.makeText(getApplicationContext(), "Error en el usuario o contraseña , vuelva a intentarlo", Toast.LENGTH_LONG).show();
        }
    }

    private void borrarCorreo() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email","");
        editor.commit();
    }

    private void guardarCorreo() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",username.getText().toString());
        editor.commit();
    }

    //The method for opening the registration page and another processes or checks for registering
    private void ClickSignUp() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.register, null);
        dialog.setView(dialogView);

        databaseHelper = new DatabaseHelper(this);
        user = new Usuarios();
        reg_username = dialogView.findViewById(R.id.reg_username);
        reg_password = dialogView.findViewById(R.id.reg_password);
        reg_firstName = dialogView.findViewById(R.id.reg_firstName);
        reg_lastName = dialogView.findViewById(R.id.reg_lastName);
        reg_email = dialogView.findViewById(R.id.reg_email);
        reg_confirmemail = dialogView.findViewById(R.id.reg_confirmemail);
        reg_register = dialogView.findViewById(R.id.reg_register);
        txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);

        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reg_username.getText().toString().trim().isEmpty()) {

                    reg_username.setError("Rellena este campo");
                } else {
                    nombre = reg_username.getText().toString();
                }
                if (reg_password.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                    reg_password.setError("Rellena este campo");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                    contraseña = reg_password.getText().toString();
                }
                if (reg_firstName.getText().toString().trim().isEmpty()) {

                    reg_firstName.setError("Rellena este campo");
                } else {


                }
                if (reg_lastName.getText().toString().trim().isEmpty()) {

                    reg_lastName.setError("Rellena este campo");
                } else {

                }
                if (reg_email.getText().toString().trim().isEmpty()) {

                    reg_email.setError("Rellena este campo");
                } else {

                }
                if (reg_confirmemail.getText().toString().trim().isEmpty()) {

                    reg_confirmemail.setError("Rellena este campo");
                } else {

                }

                postDataToSQLite(reg_username.getText().toString(),reg_password.getText().toString(),reg_email.getText().toString());
                Intent intent = new Intent(Login.this,Login.class);
                startActivity(intent);

            }
        });
        alerta =dialog.show();



    }
    private void postDataToSQLite(String nom,String pass,String email) {

        if (!databaseHelper.checkUser(email)){
            user.setName(nom);
            user.setEmail(email);
            user.setPassword(pass);
            databaseHelper.addUser(user);

           Toast.makeText(getApplicationContext(),"Conseguido, añadidido correcto",Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(getApplicationContext(),"Error al añadir,email ya existente",Toast.LENGTH_LONG).show();
        }
    }

    }

