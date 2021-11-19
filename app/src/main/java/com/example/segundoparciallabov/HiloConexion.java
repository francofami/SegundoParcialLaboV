package com.example.segundoparciallabov;


import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HiloConexion extends Thread {

    Handler handler;

    public HiloConexion(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        ConexionHTTP conexionHTTP = new ConexionHTTP();

        byte[] usuariosJson = conexionHTTP.obtenerRespuesta("http://10.0.2.2:3001/usuarios");

        String s = new String(usuariosJson);

        Message msg = new Message();
        //msg.obj = this.parserJson(s);
        msg.obj = s;
        handler.sendMessage(msg);
    }

    public List<String> parserJson(String s) {

        List<String> usuarios = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(s);

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                usuarios.add(jsonObject.getString("username"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuarios;

    }

}
