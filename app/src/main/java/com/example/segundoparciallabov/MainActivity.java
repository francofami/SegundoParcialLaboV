package com.example.segundoparciallabov;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    IOnGuardarClick listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Segundo Parcial");

        TextView tv = super.findViewById(R.id.tv);
        SharedPreferences preferencesUsuarios = getSharedPreferences("usuarios", MODE_PRIVATE);

        String s = preferencesUsuarios.getString("usuarios", "");

        if (s != "") {
            tv.setText(s);
            Log.d("","Usando Shared Preference");
        } else {
            Handler handler = new Handler(this);

            HiloConexion hiloUsuarios = new HiloConexion(handler);
            hiloUsuarios.start();
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {

        Log.d("","No use Shared Preference");

        TextView tv = super.findViewById(R.id.tv);
        SharedPreferences preferencesUsuarios = getSharedPreferences("usuarios", MODE_PRIVATE);

        tv.setText(message.obj.toString());

        SharedPreferences.Editor edit = preferencesUsuarios.edit();
        edit.putString("usuarios", message.obj.toString());
        edit.apply();

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add) {

            DialogAddUser dialog = new DialogAddUser(this);
            dialog.showDialog();


        } else if (item.getItemId()==R.id.search) {


        }

        return super.onOptionsItemSelected(item);
    }



    /*@Override
    public void onClick(View v) {
        Log.d("","hice click");

        //listener.onGuardarClick();

    }*/



}