package com.example.segundoparciallabov;

public class Usuario {

    private Integer id;
    private String username;
    private String rol;
    private Boolean admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Usuario(Integer id, String username, String rol, Boolean admin) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return '{' + "'id':" + this.id + "," + "'username':" + '"' + this.username + '"' + "," + "'rol':" + '"' + this.rol + '"' + "," + "'admin':" + this.admin + "}";
    }
}

/*[{"id":1,"username":"dtwigg","rol":"Supervisor","admin":true},
        {"id":2,"username":"ygagen","rol":"Supervisor","admin":false},
        {"id":3,"username":"ssutor","rol":"Construction Manager","admin":false},
        {"id":4,"username":"rdyter","rol":"Supervisor","admin":true},
        {"id":5,"username":"ahellwich","rol":"Project Manager","admin":false},
        "Usuario{id=6, username='dfdsf', rol='Supervisor', admin='false'}"]*/

