package com.example.segundoparciallabov;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DialogAddUser implements View.OnClickListener{

    final android.app.Dialog dialog;

    private Activity activity;
    private Usuario usuario;

    EditText dialog_username;
    TextView dialog_rol;
    Spinner spinner;
    ToggleButton admin;

    Button botonGuardar;

    private IOnGuardarClick listener;

    public DialogAddUser(Activity mainActivity) {
        this.activity = mainActivity;
        this.dialog = new android.app.Dialog(this.activity);

        // this.dialog_username = (EditText) dialog.findViewById(R.id.dialog_username);
        // this.dialog_rol = (EditText) dialog.findViewById(R.id.dialog_username);
    }

    public void guardarUsuario() {

        this.dialog_username = (EditText) dialog.findViewById(R.id.dialog_username);
        // this.dialog_rol = (TextView) dialog.findViewById(R.id.dialog_rol);
        // this.spinner = dialog.findViewById(R.id.spinner);
        this.admin = dialog.findViewById(R.id.admin);

        if (this.validarDatos() == true) {

            this.usuario = new Usuario(generarId(), this.dialog_username.getText().toString(), this.spinner.getSelectedItem().toString(), this.admin.isChecked());

            Log.d("", this.usuario.toString());

            JSONObject usuarioJsonObject = this.generarObjetoJson(this.usuario);

            this.agregarUsuarioASharedPreference(usuarioJsonObject);

            dialog.dismiss();

            // Log.d("", this.usuario.toString());

        } else {

            Log.d("", "Nombre de usuario vacío");

        }

    }

    public JSONObject generarObjetoJson(Usuario usuario) {

        JSONObject usuarioJsonObject = null;
        try {
            usuarioJsonObject = new JSONObject(usuario.toString());
            Log.d("fdgfd", usuarioJsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuarioJsonObject;

    }

    public void agregarUsuarioASharedPreference(JSONObject usuarioJsonObject) {

        SharedPreferences preferencesUsuarios = activity.getSharedPreferences("usuarios", 0);

        String s = preferencesUsuarios.getString("usuarios", "");

        try {
            JSONArray jsonArray = new JSONArray(s);

            jsonArray.put(usuarioJsonObject);

            Log.d("", jsonArray.toString());

            SharedPreferences.Editor edit = preferencesUsuarios.edit();
            edit.putString("usuarios", jsonArray.toString());
            edit.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        s = preferencesUsuarios.getString("usuarios", "");

        TextView tv = activity.findViewById(R.id.tv);
        tv.setText(s);

    }

    public boolean validarDatos() {
        Boolean retorno = false;

        if (this.dialog_username.getText().toString().length() > 0) {
            retorno = true;
        }

        return retorno;
    }

    public Integer generarId() {

        Integer idNueva = 0;

        List<Usuario> usuarios;

        usuarios = this.parserJson();

        idNueva = usuarios.get(usuarios.size()-1).getId() + 1;

        return idNueva;
    }

    void showDialog() {

        // IOnGuardarClick listener;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        this.botonGuardar = (Button) dialog.findViewById(R.id.dialog_guardar);
        botonGuardar.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.activity, R.array.spinner, android.R.layout.simple_spinner_item);

        this.spinner = dialog.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.admin = dialog.findViewById(R.id.admin);

        dialog.show();
    }

    public List<Usuario> parserJson() {

        List<Usuario> usuarios = new ArrayList<>();

        SharedPreferences preferencesUsuarios = activity.getSharedPreferences("usuarios", 0);

        String s = preferencesUsuarios.getString("usuarios", "");

        try {
            JSONArray jsonArray = new JSONArray(s);

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Usuario user = new Usuario(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("username"), jsonObject.getString("rol"), Boolean.parseBoolean(jsonObject.getString("admin")));
                usuarios.add(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuarios;

    }


    @Override
    public void onClick(View view) {
        Log.d("","hice click en guardar XD");

        this.guardarUsuario();

        // this.usuario = new Usuario(666, "", "","");
        // listener.onGuardarClick(this.usuario);
    }
}
